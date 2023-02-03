package com.kouleen.freemenu;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FreeMenu extends JavaPlugin {

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        File dataFolder = this.getDataFolder();
        if (!new File(dataFolder,"local/language_US.yml").exists()) {
            this.saveResource("local/language_US.yml", true);
        }
        if (!new File(dataFolder,"local/language_ZH.yml").exists()) {
            this.saveResource("local/language_ZH.yml", true);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
    }
}
