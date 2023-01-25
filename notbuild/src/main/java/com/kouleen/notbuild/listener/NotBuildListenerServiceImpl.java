package com.kouleen.notbuild.listener;

import com.kouleen.notbuild.domain.JavaPluginBean;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/12 14:47
 */
public class NotBuildListenerServiceImpl implements NotBuildListenerService{

    private JavaPluginBean javaPluginBean;

    @Override
    public void hangingBreakByEntityEvent(HangingBreakByEntityEvent event) {
        Entity entity = event.getRemover();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            String worldName = player.getWorld().getName();
            if (!player.hasPermission("notbuild.build")
                    && !player.hasPermission("notbuild.break")
                    && !player.hasPermission("Build." + worldName)
                    && !player.hasPermission("Break." + worldName)) {
                FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
                List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
                if (notBuildWorld.contains(worldName)) {
                    String breakSwitch = fileConfiguration.getString("break.switch");
                    if (breakSwitch.equals("true")) {
                        String breakMessage = fileConfiguration.getString("break.message");
                        String breakMessages = breakMessage.replace("&", "§");
                        player.sendMessage(breakMessages);
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @Override
    public void breakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if (!player.hasPermission("notbuild.build")
                && !player.hasPermission("notbuild.break")
                && !player.hasPermission("Build." + worldName)
                && !player.hasPermission("Break." + worldName)) {
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
            if (notBuildWorld.contains(worldName)) {
                String breakSwitch = fileConfiguration.getString("break.switch");
                if (breakSwitch.equals("true")) {
                    String breakMessage = fileConfiguration.getString("break.message");
                    String breakMessages = breakMessage.replace("&", "§");
                    player.sendMessage(breakMessages);

                }
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void hangingPlace(HangingPlaceEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if (!player.hasPermission("notbuild.build")
                && !player.hasPermission("notbuild.place")
                && !player.hasPermission("build." + worldName)
                && !player.hasPermission("place." + worldName)) {
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
            if (notBuildWorld.contains(worldName)) {
                String breakSwitch = fileConfiguration.getString("break.switch");
                if (breakSwitch.equals("true")) {
                    String breakMessage = fileConfiguration.getString("break.message");
                    String breakMessages = breakMessage.replace("&", "§");
                    player.sendMessage(breakMessages);
                }
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void placeBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if (!player.hasPermission("notbuild.build")
                && !player.hasPermission("notbuild.place")
                && !player.hasPermission("build." + worldName)
                && !player.hasPermission("place." + worldName)) {
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
            if (notBuildWorld.contains(worldName)) {
                String placeSwitch = fileConfiguration.getString("place.switch");
                if (placeSwitch.equals("true")) {
                    String placeMessage = fileConfiguration.getString("place.message");
                    String placeMessages = placeMessage.replace("&", "§");
                    player.sendMessage(placeMessages);
                }
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void fill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
        if (notBuildWorld.contains(worldName) &&
                !player.hasPermission("notbuild.build")
                && !player.hasPermission("notbuild.place")
                && !player.hasPermission("build." + worldName)
                && !player.hasPermission("place." + worldName)) {
            String placeSwitch = fileConfiguration.getString("break.switch");
            if (placeSwitch.equals("true")) {
                String placeMessage = fileConfiguration.getString("break.message");
                String placeMessages = placeMessage.replace("&", "§");
                player.sendMessage(placeMessages);
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void empty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
        if (notBuildWorld.contains(worldName) &&
                !player.hasPermission("notbuild.build")
                && !player.hasPermission("notbuild.place")
                && !player.hasPermission("build." + worldName)
                && !player.hasPermission("place." + worldName)) {
            String placeSwitch = fileConfiguration.getString("place.switch");
            if (placeSwitch.equals("true")) {
                String placeMessage = fileConfiguration.getString("place.message");
                String placeMessages = placeMessage.replace("&", "§");
                player.sendMessage(placeMessages);
                event.setCancelled(true);
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void leavesDecay(LeavesDecayEvent leavesDecayEvent) {
        Block block = leavesDecayEvent.getBlock();
        String worldName = block.getWorld().getName();
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        List<String> leavesDecayWorld = fileConfiguration.getStringList("leaves-decay-world");
        if (leavesDecayWorld.contains(worldName)) {
            leavesDecayEvent.setCancelled(true);
        }
    }

    @Override
    public void blockFade(BlockFadeEvent blockFadeEvent) {
        Block block = blockFadeEvent.getBlock();
        String worldName = block.getWorld().getName();
        FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
        List<String> blockFadeWorld = fileConfiguration.getStringList("block-fade-world");
        if (blockFadeWorld.contains(worldName)) {
            blockFadeEvent.setCancelled(true);
        }
    }

    public void setJavaPluginBean(JavaPluginBean javaPluginBean) {
        this.javaPluginBean = javaPluginBean;
    }
}
