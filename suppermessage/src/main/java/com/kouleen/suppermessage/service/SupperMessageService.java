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

import java.io.File;
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
    boolean producer(JavaPluginBean javaPluginBean, String[] args, Player player, CommandSender commandSender);

    /**
     * 消费消息
     */
    void consumer(JavaPluginBean javaPluginBean);

    boolean closeChannel();

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
        ListenerService listenerService = javaPluginBean.getListenerService();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listenerService,supperMessage);
        return true;
    }

    /**
     * 注册命令
     */
    default boolean registerCommand(JavaPluginBean javaPluginBean){
        SupperMessage supperMessage = javaPluginBean.getSupperMessage();
        Logger logger = supperMessage.getLogger();
        PluginCommand pluginCommand = supperMessage.getCommand("message");
        if(pluginCommand != null){
            CommandService commandService = javaPluginBean.getCommandService();
            pluginCommand.setExecutor(commandService);
            TabExecutorService tabExecutorService = javaPluginBean.getTabExecutorService();
            pluginCommand.setTabCompleter(tabExecutorService);
        }
        logger.info("§c _____ ______   _______   ________   ________  ________  ________  _______      ");
        logger.info("§c|\\   _ \\  _   \\|\\  ___ \\ |\\   ____\\ |\\   ____\\|\\   __  \\|\\   ____\\|\\  ___ \\     ");
        logger.info("§c\\ \\  \\\\\\__\\ \\  \\ \\   __/|\\ \\  \\___|_\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\___|\\ \\   __/|    ");
        logger.info("§c \\ \\  \\\\|__| \\  \\ \\  \\_|/_\\ \\_____  \\\\ \\_____  \\ \\   __  \\ \\  \\  __\\ \\  \\_|/__  ");
        logger.info("§c  \\ \\  \\    \\ \\  \\ \\  \\_|\\ \\|____|\\  \\\\|____|\\  \\ \\  \\ \\  \\ \\  \\|\\  \\ \\  \\_|\\ \\ ");
        logger.info("§c   \\ \\__\\    \\ \\__\\ \\_______\\____\\_\\  \\ ____\\_\\  \\ \\__\\ \\__\\ \\_______\\ \\_______\\");
        logger.info("§c    \\|__|     \\|__|\\|_______|\\_________\\\\_________\\|__|\\|__|\\|_______|\\|_______|");
        logger.info("§c                            \\|_________\\|_________|                             ");
        return true;
    }

    /**
     * 重载文件配置
     */
    default boolean saveConfigFile(JavaPluginBean javaPluginBean){
        SupperMessage supperMessage = javaPluginBean.getSupperMessage();
        File dataFolder = supperMessage.getDataFolder();
        if(!new File(dataFolder,"message/language_US.yml").exists()){
            supperMessage.saveResource("message/language_US.yml",false);
        }
        if(!new File(dataFolder,"message/language_ZH.yml").exists()){
            supperMessage.saveResource("message/language_ZH.yml",false);
        }
        supperMessage.saveDefaultConfig();
        supperMessage.reloadConfig();
        return true;
    }
}
