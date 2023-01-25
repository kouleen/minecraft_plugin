package com.kouleen.suppermessage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author zhangqing
 * @since 2023/1/24 23:11
 */
public interface ListenerService extends Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    void addPlayer(PlayerJoinEvent playerJoinEvent);
}
