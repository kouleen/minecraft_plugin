package com.kouleen.freemenu;

import com.kouleen.freemenu.command.CommandServiceImpl;
import com.kouleen.freemenu.command.TabExecutorServiceImpl;
import com.kouleen.freemenu.domain.JavaPluginBean;
import com.kouleen.freemenu.listener.ListenerServiceImpl;
import com.kouleen.freemenu.service.FreeMenuService;
import com.kouleen.freemenu.service.FreeMenuServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;


public final class FreeMenu extends JavaPlugin {

    private static final JavaPluginBean javaPluginBean = new JavaPluginBean();

    @Override
    public void onLoad() {
        javaPluginBean.setFreeMenu(this);
        javaPluginBean.setFileConfiguration(this.getConfig());
        javaPluginBean.setTabExecutorService(new TabExecutorServiceImpl());
        javaPluginBean.setCommandService(new CommandServiceImpl(javaPluginBean));
        javaPluginBean.setListenerService(new ListenerServiceImpl());
        javaPluginBean.setFreeMenuService(new FreeMenuServiceImpl(javaPluginBean));
    }

    @Override
    public void onEnable() {
        FreeMenuService freeMenuService = javaPluginBean.getFreeMenuService();
        freeMenuService.saveConfigFile(javaPluginBean);
        freeMenuService.registerCommand(javaPluginBean);
        freeMenuService.registerEvents(javaPluginBean);
    }

    @Override
    public void onDisable() {
    }

    public static void main(String[] args) {
    }
}
