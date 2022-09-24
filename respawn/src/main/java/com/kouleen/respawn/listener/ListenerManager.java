package com.kouleen.respawn.listener;

import com.kouleen.respawn.domain.JavaPluginBean;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

/**
 * @author zhangqing
 * @since 2022/9/24 11:48
 */
public class ListenerManager implements Listener {

    private final JavaPluginBean javaPluginBean;

    public ListenerManager(JavaPluginBean javaPluginBean) {
        this.javaPluginBean = javaPluginBean;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        Player player = playerDeathEvent.getEntity();
        if (!player.isOp()) {
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            // 玩家死亡触发的全服消息
            if(fileConfiguration.getBoolean("death.enableBroadcastMessage")){
                String messageConfig = fileConfiguration.getString("message.2");
                String message = String.format(player.getName(), messageConfig);
                Bukkit.broadcastMessage(color(message));
            }
            // 玩家死亡触发事件保存背包
            playerDeathEvent.setKeepInventory(fileConfiguration.getBoolean("death.keepInventory"));
            // 清除该玩家死亡掉落物
            playerDeathEvent.getDrops().clear();
            //设置为死亡保存等级
            playerDeathEvent.setKeepLevel(fileConfiguration.getBoolean("death.keepLevel"));
            Random random = new Random();
            int level = player.getLevel();
            int index = 1 + random.nextInt(fileConfiguration.getInt("death.level-scope"));
            if(level <= 30 && (level - index) < 0){
                player.setLevel(0);
            }else {
                player.setLevel(level - index);
            }
            PlayerInventory playerInventory = player.getInventory();
            if (level <= fileConfiguration.getInt("death.maxDropMoney")) {
                this.deathItem(index, playerInventory, player);
                player.sendMessage(color(player.getName() + fileConfiguration.getString("message.1")));
                return;
            }

            this.deathItem(index, playerInventory, player);
            if(fileConfiguration.getBoolean("death.enableDropMoney")){
                Economy economy = javaPluginBean.getEconomy();
                EconomyResponse economyResponse = economy.depositPlayer(player, fileConfiguration.getDouble("death.dropMoney"));
                if (economyResponse.transactionSuccess()) {
                    player.sendMessage(color(fileConfiguration.getString("message.3")));
                }
            }
        }
    }

    /**
     * @param str 自定义信息
     * @param args 可变参数
     * @return 带颜色的字符串
     */
    public String color(String str, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(str, args));
    }

    /**
     * 死亡随机掉落物品
     *
     * @param index 索引
     * @param inventory 背包
     * @param player 玩家
     */
    private void deathItem(int index, PlayerInventory inventory, Player player) {
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
