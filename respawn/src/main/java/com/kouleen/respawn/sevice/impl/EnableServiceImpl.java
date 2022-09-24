package com.kouleen.respawn.sevice.impl;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.command.CommandManager;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.listener.ListenerManager;
import com.kouleen.respawn.sevice.EnableService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import java.util.logging.Logger;

/**
 * @author zhangqing
 * @since 2022/9/24 11:58
 */
public class EnableServiceImpl implements EnableService {

    @Override
    public boolean registerEvents(Main main, JavaPluginBean javaPluginBean) {
        Logger logger = javaPluginBean.getLogger();
        logger.info("事件注册 ------------------> start");
        ListenerManager listenerManager = new ListenerManager(javaPluginBean);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listenerManager,main);
        logger.info("事件注册 ------------------> end");
        return false;
    }

    @Override
    public Economy registrationPlugin(Class<Economy> clazz) {
        ServicesManager servicesManager = Bukkit.getServicesManager();
        RegisteredServiceProvider<Economy> registration = servicesManager.getRegistration(clazz);
        if(clazz != null){
            return registration.getProvider();
        }
        return null;
    }

    @Override
    public boolean saveConfigFile(Main main, JavaPluginBean javaPluginBean) {
        Logger logger = javaPluginBean.getLogger();
        logger.warning("保存文件，并刷新 ------------------- start ");
        main.saveDefaultConfig();
        main.reloadConfig();
        logger.warning("保存文件，并刷新 ------------------- end ");
        return true;
    }

    @Override
    public boolean registerCommand(Main main,JavaPluginBean javaPluginBean) {
        Logger logger = javaPluginBean.getLogger();
        logger.info("注册命令 -----------------> start");
        PluginCommand pluginCommand = main.getCommand("respawn");
        if(pluginCommand != null){
            CommandManager commandManager = new CommandManager(javaPluginBean);
            pluginCommand.setExecutor(commandManager);
            pluginCommand.setTabCompleter(commandManager);
            logger.info("注册命令 success");
        }
        logger.info("注册命令 -----------------> end");
        return false;
    }
}
