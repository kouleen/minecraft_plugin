package com.kouleen.respawn.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

/**
 * @author zhangqing
 * @since 2023/1/24 23:11
 */
public interface ListenerService extends Listener {

    /**
     * 死亡处理器
     * @param playerDeathEvent 玩家死亡事件
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    void onDeath(PlayerDeathEvent playerDeathEvent);

    /**
     * @param str 自定义信息
     * @param args 可变参数
     * @return 带颜色的字符串
     */
    default String color(String str, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(str, args));
    }

    /**
     * 死亡随机掉落物品
     *
     * @param index 索引
     * @param inventory 背包
     * @param player 玩家
     */
    default void deathItem(int index, PlayerInventory inventory, Player player) {
        Random random = new Random();
        for (int i = 0; i < index; i++) {
            int nextInt = random.nextInt(40);
            ItemStack item = inventory.getItem(nextInt);
            if (item != null && item.getType() != Material.AIR) {
                inventory.setItem(nextInt, new ItemStack(Material.AIR));
                player.getWorld().dropItemNaturally(player.getLocation(), item);
                if(item.getType() != Material.AIR){
                    int maxStackSize = item.getType().getMaxStackSize();
                    player.sendMessage(player.getName() + "死亡了，遗失了" + maxStackSize+"个"+ item.getType().name());
                }
            }
        }
    }
}
