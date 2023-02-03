package com.kouleen.freemenu.service;

import com.kouleen.freemenu.FreeMenu;
import com.kouleen.freemenu.domain.JavaPluginBean;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * @author zhangqing
 * @since 2023/2/1 16:33
 */
public class FreeMenuServiceImpl implements FreeMenuService{

    static Plugin plugin = FreeMenu.getPlugin(FreeMenu.class);

    private final JavaPluginBean javaPluginBean;

    public FreeMenuServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public Inventory openFreeMenu(Player player) {
        File file = new File(plugin.getDataFolder(), "shop.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return null;
    }
}
