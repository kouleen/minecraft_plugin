package com.kouleen.suppermessage;

import com.kouleen.suppermessage.command.CommandServiceImpl;
import com.kouleen.suppermessage.command.TabExecutorServiceImpl;
import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.listener.ListenerServiceImpl;
import com.kouleen.suppermessage.service.SupperMessageService;
import com.kouleen.suppermessage.service.SupperMessageServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SupperMessage extends JavaPlugin {

    private final JavaPluginBean javaPluginBean = new JavaPluginBean();

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        javaPluginBean.setSupperMessage(this);
        javaPluginBean.setFileConfiguration(this.getConfig());
        javaPluginBean.setTabExecutorService(new TabExecutorServiceImpl());
        javaPluginBean.setSupperMessageService(new SupperMessageServiceImpl());
        javaPluginBean.setCommandService(new CommandServiceImpl(javaPluginBean));
        javaPluginBean.setListenerService(new ListenerServiceImpl());
    }

    @Override
    public void onEnable() {
        SupperMessageService supperMessageService = javaPluginBean.getSupperMessageService();
        supperMessageService.consumer(javaPluginBean);
        Bukkit.getPluginManager().registerEvents(javaPluginBean.getListenerService(), this);
        PluginCommand pluginCommand = getCommand("message");
        pluginCommand.setExecutor(javaPluginBean.getCommandService());
        pluginCommand.setTabCompleter(javaPluginBean.getTabExecutorService());
    }

    @Override
    public void onDisable() {

    }

    public static void main(String[] args) {
    }
}
