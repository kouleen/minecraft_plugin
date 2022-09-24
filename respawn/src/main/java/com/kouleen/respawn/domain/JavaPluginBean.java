package com.kouleen.respawn.domain;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.sevice.EnableService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author zhangqing
 * @since 2022/9/24 13:51
 */
public class JavaPluginBean implements Serializable {

    private EnableService enableService;

    private Logger logger;

    private Main main;

    private Economy economy;

    private FileConfiguration fileConfiguration;

    public EnableService getEnableService() {
        return enableService;
    }

    public void setEnableService(EnableService enableService) {
        this.enableService = enableService;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Economy getEconomy() {
        return economy;
    }

    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }
}
