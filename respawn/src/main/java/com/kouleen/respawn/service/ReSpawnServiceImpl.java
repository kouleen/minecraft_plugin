package com.kouleen.respawn.service;

import com.kouleen.respawn.ReSpawn;
import com.kouleen.respawn.command.CommandService;
import com.kouleen.respawn.command.TabExecutorService;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.listener.ListenerService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import sun.plugin.dom.exception.PluginNotSupportedException;

/**
 * @author zhangqing
 * @since 2023/1/25 0:27
 */
public class ReSpawnServiceImpl implements ReSpawnService{

    /**
     * 注册事件
     */
    @Override
    public boolean registerEvents(JavaPluginBean javaPluginBean) {
        ReSpawn reSpawn = javaPluginBean.getReSpawn();
        ListenerService listenerManager = javaPluginBean.getListenerService();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listenerManager,reSpawn);
        return true;
    }

    /**
     * 注册命令
     */
    @Override
    public boolean registerCommand(JavaPluginBean javaPluginBean) {
        ReSpawn reSpawn = javaPluginBean.getReSpawn();
        PluginCommand pluginCommand = reSpawn.getCommand("respawn");
        if(pluginCommand != null){
            CommandService commandService = javaPluginBean.getCommandService();
            pluginCommand.setExecutor(commandService);
            TabExecutorService tabExecutorService = javaPluginBean.getTabExecutorService();
            pluginCommand.setTabCompleter(tabExecutorService);
        }
        return true;
    }

    /**
     * 注册经济插件
     */
    @Override
    public Economy registrationPlugin(Class<Economy> clazz) {
        ServicesManager servicesManager = Bukkit.getServicesManager();
        RegisteredServiceProvider<Economy> registration = servicesManager.getRegistration(clazz);
        if(clazz == null){
            throw new PluginNotSupportedException("failed to obtain the economic plug-in. procedure");
        }
        return registration.getProvider();
    }

    /**
     * 重载文件配置
     */
    @Override
    public boolean saveConfigFile(JavaPluginBean javaPluginBean) {
        ReSpawn reSpawn = javaPluginBean.getReSpawn();
        reSpawn.saveDefaultConfig();
        reSpawn.reloadConfig();
        return true;
    }
}
