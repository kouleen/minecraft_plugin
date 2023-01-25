package com.kouleen.notbuild.listener;

import com.kouleen.notbuild.domain.JavaPluginBean;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * @author zhangqing
 * @since 2023/1/12 14:46
 */
public interface NotBuildListenerService extends Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    void hangingBreakByEntityEvent(HangingBreakByEntityEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void breakBlock(BlockBreakEvent event);

    @EventHandler(priority = EventPriority.LOWEST)
    void hangingPlace(HangingPlaceEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void placeBlock(BlockPlaceEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void fill(PlayerBucketFillEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void empty(PlayerBucketEmptyEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void leavesDecay(LeavesDecayEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    void blockFade(BlockFadeEvent event);

    void setJavaPluginBean(JavaPluginBean javaPluginBean);
}
