package com.kouleen.suppermessage.domain;

import com.kouleen.suppermessage.SupperMessage;
import com.kouleen.suppermessage.command.CommandService;
import com.kouleen.suppermessage.command.TabExecutorService;
import com.kouleen.suppermessage.listener.ListenerService;
import com.kouleen.suppermessage.service.SupperMessageService;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/25 13:51
 */
public class JavaPluginBean implements Serializable {

    private SupperMessage supperMessage;

    private CommandService commandService;

    private ListenerService listenerService;

    private FileConfiguration fileConfiguration;

    private TabExecutorService tabExecutorService;

    private SupperMessageService supperMessageService;

    public SupperMessage getSupperMessage() {
        return supperMessage;
    }

    public void setSupperMessage(SupperMessage supperMessage) {
        this.supperMessage = supperMessage;
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

    public SupperMessageService getSupperMessageService() {
        return supperMessageService;
    }

    public void setSupperMessageService(SupperMessageService supperMessageService) {
        this.supperMessageService = supperMessageService;
    }
}
