package com.kouleen.freemenu.domain;

import com.kouleen.freemenu.FreeMenu;
import com.kouleen.freemenu.command.CommandService;
import com.kouleen.freemenu.command.TabExecutorService;
import com.kouleen.freemenu.listener.ListenerService;
import com.kouleen.freemenu.service.FreeMenuService;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/25 13:51
 */
public class JavaPluginBean implements Serializable {

    private FreeMenu freeMenu;

    private CommandService commandService;

    private ListenerService listenerService;

    private FileConfiguration fileConfiguration;

    private TabExecutorService tabExecutorService;

    private FreeMenuService freeMenuService;

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

    public FreeMenu getFreeMenu() {
        return freeMenu;
    }

    public void setFreeMenu(FreeMenu freeMenu) {
        this.freeMenu = freeMenu;
    }

    public FreeMenuService getFreeMenuService() {
        return freeMenuService;
    }

    public void setFreeMenuService(FreeMenuService freeMenuService) {
        this.freeMenuService = freeMenuService;
    }
}
