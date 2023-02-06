package com.kouleen.freemenu.service;

import com.kouleen.freemenu.FreeMenu;
import com.kouleen.freemenu.command.CommandService;
import com.kouleen.freemenu.domain.JavaPluginBean;
import com.kouleen.freemenu.listener.ListenerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author zhangqing
 * @since 2023/2/1 16:32
 */
public interface FreeMenuService {

    /**
     * 打开菜单
     * @param player 玩家
     * @return 菜单界面
     */
    Inventory openFreeMenu(Player player);

    /**
     * 注册监听器
     * @param javaPluginBean 聚合参数
     */
    default void registerEvents(JavaPluginBean javaPluginBean){
        ListenerService listenerService = javaPluginBean.getListenerService();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listenerService,javaPluginBean.getFreeMenu());
    }

    /**
     * 注册命令
     * @param javaPluginBean 聚合参数
     */
    default void registerCommand(JavaPluginBean javaPluginBean){
        FreeMenu freeMenu = javaPluginBean.getFreeMenu();
        PluginCommand pluginCommand = freeMenu.getCommand("freemenu");
        if(pluginCommand == null){
            throw new CommandException("plugin command is null");
        }
        pluginCommand.setExecutor(javaPluginBean.getCommandService());
        pluginCommand.setTabCompleter(javaPluginBean.getTabExecutorService());
    }

    /**
     * 配置IO相关
     * @param javaPluginBean 聚合参数
     */
    default void saveConfigFile(JavaPluginBean javaPluginBean){
        FreeMenu freeMenu = javaPluginBean.getFreeMenu();
        freeMenu.saveDefaultConfig();
        File dataFolder = freeMenu.getDataFolder();
        if (!new File(dataFolder,"local/language_US.yml").exists()) {
            freeMenu.saveResource("local/language_US.yml", true);
        }
        if (!new File(dataFolder,"local/language_ZH.yml").exists()) {
            freeMenu.saveResource("local/language_ZH.yml", true);
        }
    }
}
