package com.kouleen.suppermessage.command;

import com.kouleen.suppermessage.domain.JavaPluginBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author zhangqing
 * @since 2023/1/24 22:50
 */
public interface CommandService extends CommandExecutor {

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    default boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return false;
    }

    /**
     * @param yamlConfiguration 配置文件
     * @param commandSender 命令输出器
     * @param resourcePath example:message.close.success
     * @param defaultMessage example:[message] §2§lThe Settings are successful. External service messages are closed
     */
    default void handleMessage(YamlConfiguration yamlConfiguration,CommandSender commandSender,String resourcePath,String defaultMessage){
        String msgMessage = yamlConfiguration.getString(resourcePath);
        if(msgMessage != null && !msgMessage.equals("")){
            commandSender.sendMessage(msgMessage.replace("&", "§"));
        }else {
            commandSender.sendMessage(defaultMessage);
        }
    }

    default YamlConfiguration getYamlConfiguration(JavaPluginBean javaPluginBean) {
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        String lang = fileConfiguration.getString("global.lang");
        if (lang.equals("language_ZH.yml")) {
            return YamlConfiguration.loadConfiguration(new File(javaPluginBean.getSupperMessage().getDataFolder(), "message/language_ZH.yml"));
        }
        return YamlConfiguration.loadConfiguration(new File(javaPluginBean.getSupperMessage().getDataFolder(), "message/language_US.yml"));
    }
}
