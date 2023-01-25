package com.kouleen.respawn.domain;

import com.kouleen.respawn.ReSpawn;
import com.kouleen.respawn.command.CommandService;
import com.kouleen.respawn.command.TabExecutorService;
import com.kouleen.respawn.listener.ListenerService;
import com.kouleen.respawn.service.ReSpawnService;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/25 0:03
 */
public class JavaPluginBean implements Serializable {

    private ReSpawn reSpawn;

    private ReSpawnService reSpawnService;

    private CommandService commandService;

    private ListenerService listenerService;

    private FileConfiguration fileConfiguration;

    private TabExecutorService tabExecutorService;

    public ReSpawn getReSpawn() {
        return reSpawn;
    }

    public void setReSpawn(ReSpawn reSpawn) {
        this.reSpawn = reSpawn;
    }

    public ReSpawnService getReSpawnService() {
        return reSpawnService;
    }

    public void setReSpawnService(ReSpawnService reSpawnService) {
        this.reSpawnService = reSpawnService;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    public ListenerService getListenerService() {
        return listenerService;
    }

    public void setListenerService(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public TabExecutorService getTabExecutorService() {
        return tabExecutorService;
    }

    public void setTabExecutorService(TabExecutorService tabExecutorService) {
        this.tabExecutorService = tabExecutorService;
    }
}
