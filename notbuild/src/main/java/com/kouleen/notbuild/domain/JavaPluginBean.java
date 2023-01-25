package com.kouleen.notbuild.domain;

import com.kouleen.notbuild.NotBuild;
import com.kouleen.notbuild.command.NotBuildCommandService;
import com.kouleen.notbuild.command.NotBuildTabCompleterService;
import com.kouleen.notbuild.listener.NotBuildListenerService;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/12 15:05
 */
public class JavaPluginBean implements Serializable {

    private NotBuild notBuild;

    private FileConfiguration fileConfiguration;

    private NotBuildListenerService notBuildListenerService;

    private NotBuildCommandService notBuildCommandService;

    private NotBuildTabCompleterService notBuildTabCompleterService;

    public NotBuild getNotBuild() {
        return notBuild;
    }

    public void setNotBuild(NotBuild notBuild) {
        this.notBuild = notBuild;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public NotBuildListenerService getNotBuildListenerService() {
        return notBuildListenerService;
    }

    public void setNotBuildListenerService(NotBuildListenerService notBuildListenerService) {
        this.notBuildListenerService = notBuildListenerService;
    }

    public NotBuildCommandService getNotBuildCommandService() {
        return notBuildCommandService;
    }

    public void setNotBuildCommandService(NotBuildCommandService notBuildCommandService) {
        this.notBuildCommandService = notBuildCommandService;
    }

    public NotBuildTabCompleterService getNotBuildTabCompleterService() {
        return notBuildTabCompleterService;
    }

    public void setNotBuildTabCompleterService(NotBuildTabCompleterService notBuildTabCompleterService) {
        this.notBuildTabCompleterService = notBuildTabCompleterService;
    }

    public void notBuildInit() {
        notBuild.saveDefaultConfig();
        notBuild.reloadConfig();
    }

    public void notBuildSave() {
        notBuild.saveConfig();
        notBuild.reloadConfig();
    }

    public void notBuildReload() {
        notBuild.reloadConfig();
    }
}
