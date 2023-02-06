package com.kouleen.suppermessage;

import com.kouleen.suppermessage.command.CommandServiceImpl;
import com.kouleen.suppermessage.command.TabExecutorServiceImpl;
import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.listener.ListenerServiceImpl;
import com.kouleen.suppermessage.service.SupperMessageService;
import com.kouleen.suppermessage.service.SupperMessageServiceImpl;
import com.kouleen.suppermessage.singleton.GlobalSingleton;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadPoolExecutor;

public final class SupperMessage extends JavaPlugin {

    private final static JavaPluginBean javaPluginBean = new JavaPluginBean();

    @Override
    public void onLoad() {
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
        supperMessageService.saveConfigFile(javaPluginBean);
        supperMessageService.printBannerConsole(javaPluginBean);
        supperMessageService.consumer(javaPluginBean);
        supperMessageService.registerCommand(javaPluginBean);
        supperMessageService.registerEvents(javaPluginBean);
    }

    @Override
    public void onDisable() {
        SupperMessageService supperMessageService = javaPluginBean.getSupperMessageService();
        supperMessageService.closeChannel(javaPluginBean);
        ThreadPoolExecutor threadPoolExecutor = GlobalSingleton.getThreadPoolExecutor();
        threadPoolExecutor.shutdown();
    }

    public static void main(String[] args) {
    }
}
