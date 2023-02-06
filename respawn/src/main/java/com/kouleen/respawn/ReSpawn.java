package com.kouleen.respawn;

import com.kouleen.respawn.command.CommandServiceImpl;
import com.kouleen.respawn.command.TabExecutorServiceImpl;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.listener.ListenerServiceImpl;
import com.kouleen.respawn.service.ReSpawnService;
import com.kouleen.respawn.service.ReSpawnServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;

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
        ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
        reSpawnService.saveConfigFile(javaPluginBean);
        reSpawnService.registerEvents(javaPluginBean);
        reSpawnService.registerCommand(javaPluginBean);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
        reSpawnService.saveConfigFile(javaPluginBean);
        super.onDisable();
    }
}
