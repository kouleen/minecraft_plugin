package com.kouleen.suppermessage;

import com.kouleen.suppermessage.command.CommandServiceImpl;
import com.kouleen.suppermessage.command.TabExecutorServiceImpl;
import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.listener.ListenerServiceImpl;
import com.kouleen.suppermessage.service.SupperMessageService;
import com.kouleen.suppermessage.service.SupperMessageServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SupperMessage extends JavaPlugin {

    private final JavaPluginBean javaPluginBean = new JavaPluginBean();

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
        Logger logger = getLogger();
        logger.info(String.format("%s 被成功加载了",getName()));
        SupperMessageService supperMessageService = javaPluginBean.getSupperMessageService();
        supperMessageService.consumer(javaPluginBean);
    }

    @Override
    public void onDisable() {

    }
}
