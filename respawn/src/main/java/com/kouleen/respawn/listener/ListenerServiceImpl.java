package com.kouleen.respawn.listener;

import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.service.ReSpawnService;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

/**
 * @author zhangqing
 * @since 2023/1/24 23:12
 */
public class ListenerServiceImpl implements ListenerService{

    private final JavaPluginBean javaPluginBean;

    public ListenerServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
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
                ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
                Economy economy = reSpawnService.registrationPlugin(Economy.class);
                EconomyResponse economyResponse = economy.depositPlayer(player, fileConfiguration.getDouble("death.dropMoney"));
                if (economyResponse.transactionSuccess()) {
                    player.sendMessage(color(fileConfiguration.getString("message.3")));
                }
            }
        }
    }
}
