package com.kouleen.suppermessage.listener;

import com.kouleen.suppermessage.singleton.GlobalSingleton;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqing
 * @since 2023/1/24 23:12
 */
public class ListenerServiceImpl implements ListenerService{

    @Override
    public void addPlayer(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Map<UUID, String> playerMap = GlobalSingleton.getPlayers();
        playerMap.remove(player.getUniqueId());
    }
}
