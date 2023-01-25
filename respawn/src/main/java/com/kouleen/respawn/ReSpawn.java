package com.kouleen.respawn;

import com.kouleen.respawn.command.CommandServiceImpl;
import com.kouleen.respawn.command.TabExecutorServiceImpl;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.listener.ListenerServiceImpl;
import com.kouleen.respawn.service.ReSpawnService;
import com.kouleen.respawn.service.ReSpawnServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ReSpawn extends JavaPlugin {

    private final JavaPluginBean javaPluginBean = new JavaPluginBean();

    @Override
    public void onLoad() {
        javaPluginBean.setReSpawn(this);
        javaPluginBean.setReSpawnService(new ReSpawnServiceImpl());
        javaPluginBean.setCommandService(new CommandServiceImpl(javaPluginBean));
        javaPluginBean.setTabExecutorService(new TabExecutorServiceImpl());
        javaPluginBean.setListenerService(new ListenerServiceImpl(javaPluginBean));
    }

    @Override
    public void onEnable() {
        Logger logger = this.getLogger();
        logger.info(String.format("%s 被成功加载了",getName()));
        ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
        reSpawnService.saveConfigFile(javaPluginBean);
        reSpawnService.registerEvents(javaPluginBean);
        reSpawnService.registerCommand(javaPluginBean);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Logger logger = this.getLogger();
        logger.info(String.format("%s 被成功卸载了",getName()));
        ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
        reSpawnService.saveConfigFile(javaPluginBean);
        super.onDisable();
    }
}
