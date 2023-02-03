package com.kouleen.suppermessage.service;

import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.domain.RabbitMQDTO;
import com.kouleen.suppermessage.singleton.GlobalSingleton;
import com.rabbitmq.client.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangqing
 * @since 2023/1/25 13:51
 */
public class SupperMessageServiceImpl implements SupperMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupperMessageServiceImpl.class);

    private static class ChannelHolder{
        static Channel channel;
    }

    @Override
    public Channel getChannel(RabbitMQDTO rabbitMQDTO) throws IOException, TimeoutException {
        if(ChannelHolder.channel != null){
            return ChannelHolder.channel;
        }
        //初始化连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQDTO.getHost());
        factory.setPort(rabbitMQDTO.getPort());
        factory.setUsername(rabbitMQDTO.getUsername());
        factory.setPassword(rabbitMQDTO.getPassword());
        factory.setVirtualHost(rabbitMQDTO.getVirtualHost());
        //创建连接
        Connection conn = factory.newConnection();
        return ChannelHolder.channel = conn.createChannel();
    }

    @Override
    public boolean producer(JavaPluginBean javaPluginBean, String[] args, Player player, CommandSender commandSender) {
        if (args.length >= 1) {
            StringBuilder builder = new StringBuilder();
            List<String> strList = Arrays.asList(args);
            strList.forEach(str -> builder.append(str).append(" "));
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            RabbitMQDTO rabbitMQDTO = this.injectionRabbitMQ(fileConfiguration);
            GlobalSingleton.getThreadPoolExecutor().execute(() ->{
                String message = "[" + this.getTime() + "] " + "[" + player.getName() + "] " + builder;
                try {
                    Channel channel = this.getChannel(rabbitMQDTO);
                    channel.queueDeclare(rabbitMQDTO.getQueueName(), false, false, true, null);
                    channel.basicPublish("", rabbitMQDTO.getQueueName(), null, message.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    commandSender.sendMessage("§4§l网络异常,发送失败");
                }
            });
        }
        return true;
    }

    @Override
    public void consumer(JavaPluginBean javaPluginBean) {
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        RabbitMQDTO rabbitMQDTO = this.injectionRabbitMQ(fileConfiguration);
        GlobalSingleton.getThreadPoolExecutor().execute(() -> {
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            Map<UUID, String> players = GlobalSingleton.getPlayers();
            ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();
            try {
                Channel channel = this.getChannel(rabbitMQDTO);
                //声明队列
                channel.queueDeclare(rabbitMQDTO.getQueueName(), false, false, true, null);
                channel.basicQos(1);
                //定义消费者
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, StandardCharsets.UTF_8);
                        try {
                            if (onlinePlayers.size() == 0) {
                                consoleCommandSender.sendMessage(message);
                            }
                            onlinePlayers.forEach(player -> {
                                if (players.size() > 0 && players.containsKey(player.getUniqueId())) {
                                    consoleCommandSender.sendMessage(message);
                                } else {
                                    player.sendMessage(message);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }
                };
                channel.basicConsume(rabbitMQDTO.getQueueName(), false, consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean closeChannel() {
        if(ChannelHolder.channel != null){
            try {
                ChannelHolder.channel.close();
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info("信道关闭成功");
        }
        return true;
    }
}
