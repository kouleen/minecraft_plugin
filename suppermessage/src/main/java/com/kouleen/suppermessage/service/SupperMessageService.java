package com.kouleen.suppermessage.service;

import com.kouleen.suppermessage.SupperMessage;
import com.kouleen.suppermessage.command.CommandService;
import com.kouleen.suppermessage.command.TabExecutorService;
import com.kouleen.suppermessage.constant.GlobalConstant;
import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.domain.RabbitMQDTO;
import com.kouleen.suppermessage.listener.ListenerService;
import com.kouleen.suppermessage.singleton.GlobalSingleton;
import com.rabbitmq.client.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * @author zhangqing
 * @since 2023/1/25 13:51
 */
public interface SupperMessageService {

    /**
     * 获取连接通道
     */
    Channel getChannel(RabbitMQDTO rabbitMQDTO) throws IOException, TimeoutException;

    /**
     * 生产消息
     */
    default boolean producer(JavaPluginBean javaPluginBean, String[] args, Player player, CommandSender commandSender){
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

    /**
     * 消费消息
     */
    default void consumer(JavaPluginBean javaPluginBean){
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

    default RabbitMQDTO injectionRabbitMQ(FileConfiguration fileConfiguration){
        RabbitMQDTO rabbitMQDTO = new RabbitMQDTO();
        if (fileConfiguration.getString("global.connection.host") != null && fileConfiguration.getString("global.connection.host").length() > 0){
            rabbitMQDTO.setHost(fileConfiguration.getString("global.connection.host"));
        }else {
            rabbitMQDTO.setHost(GlobalConstant.HOST);
        }
        if (fileConfiguration.getString("global.connection.port") != null && fileConfiguration.getString("global.connection.port").length() > 0){
            rabbitMQDTO.setPort(fileConfiguration.getInt("global.connection.port"));
        }else {
            rabbitMQDTO.setPort(GlobalConstant.PORT);
        }
        if (fileConfiguration.getString("global.connection.username") != null && fileConfiguration.getString("global.connection.username").length() > 0) {
            rabbitMQDTO.setUsername(fileConfiguration.getString("global.connection.username"));
        }else {
            rabbitMQDTO.setUsername(GlobalConstant.USERNAME);
        }
        if (fileConfiguration.getString("global.connection.password") != null && fileConfiguration.getString("global.connection.password").length() > 0) {
            rabbitMQDTO.setPassword(fileConfiguration.getString("global.connection.password"));
        }else {
            rabbitMQDTO.setPassword(GlobalConstant.PASSWORD);
        }
        if (fileConfiguration.getString("global.connection.virtualhost") != null && fileConfiguration.getString("global.connection.virtualhost").length() > 0) {
            rabbitMQDTO.setVirtualHost(fileConfiguration.getString("global.connection.virtualhost"));
        }else {
            rabbitMQDTO.setVirtualHost(GlobalConstant.VIRTUALHOST);
        }
        if (fileConfiguration.getString("global.connection.queue-name") != null && fileConfiguration.getString("global.connection.queue-name").length() > 0) {
            rabbitMQDTO.setQueueName(fileConfiguration.getString("global.connection.queue-name"));
        }else {
            rabbitMQDTO.setQueueName(GlobalConstant.QUEUE_NAME);
        }
        return rabbitMQDTO;
    }

    default String getTime(){
        long date;
        URLConnection connection;
        try {
            URL url = new URL("http://www.baidu.com");
            connection = url.openConnection();
            connection.connect();
            date = connection.getDate();
            if(date == 0){
                throw new RuntimeException("请求失败");
            }
        } catch (Exception e) {
            date = new Date().getTime();
        }
        return new SimpleDateFormat("yyyy-MM-dd hh:dd:ss").format(new Date(date));
    }

    /**
     * 注册事件
     */
    default boolean registerEvents(JavaPluginBean javaPluginBean){
        SupperMessage supperMessage = javaPluginBean.getSupperMessage();
        Logger logger = supperMessage.getLogger();
        logger.info("listening event registration ------------------> start");
        ListenerService listenerService = javaPluginBean.getListenerService();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listenerService,supperMessage);
        logger.info("listening event registration ------------------> end");
        return true;
    }

    /**
     * 注册命令
     */
    default boolean registerCommand(JavaPluginBean javaPluginBean){
        SupperMessage supperMessage = javaPluginBean.getSupperMessage();
        Logger logger = supperMessage.getLogger();
        logger.info("registration command -----------------> start");
        PluginCommand pluginCommand = supperMessage.getCommand("message");
        if(pluginCommand != null){
            CommandService commandService = javaPluginBean.getCommandService();
            pluginCommand.setExecutor(commandService);
            TabExecutorService tabExecutorService = javaPluginBean.getTabExecutorService();
            pluginCommand.setTabCompleter(tabExecutorService);
            logger.info("registration command success");
        }
        logger.info("registration command -----------------> end");
        return true;
    }

    /**
     * 重载文件配置
     */
    default boolean saveConfigFile(JavaPluginBean javaPluginBean){
        SupperMessage supperMessage = javaPluginBean.getSupperMessage();
        Logger logger = supperMessage.getLogger();
        logger.warning("save the file and refresh it ------------------- start ");
        supperMessage.saveDefaultConfig();
        supperMessage.reloadConfig();
        logger.warning("save the file and refresh it ------------------- end ");
        return true;
    }
}
