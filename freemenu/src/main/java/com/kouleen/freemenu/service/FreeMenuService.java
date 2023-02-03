package com.kouleen.freemenu.service;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author zhangqing
 * @since 2023/2/1 16:32
 */
public interface FreeMenuService {

    Inventory openFreeMenu(Player player);
}
