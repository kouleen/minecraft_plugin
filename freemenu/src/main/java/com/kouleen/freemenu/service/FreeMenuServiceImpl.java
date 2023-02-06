package com.kouleen.freemenu.service;

import com.kouleen.freemenu.FreeMenu;
import com.kouleen.freemenu.domain.JavaPluginBean;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

/**
 * @author zhangqing
 * @since 2023/2/1 16:33
 */
public class FreeMenuServiceImpl implements FreeMenuService{

    private final JavaPluginBean javaPluginBean;

    public FreeMenuServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public Inventory openFreeMenu(Player player) {
        FreeMenu freeMenu = javaPluginBean.getFreeMenu();
        File file = new File(freeMenu.getDataFolder(), "shop.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return null;
    }
}
