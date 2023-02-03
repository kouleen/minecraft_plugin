package com.kouleen.suppermessage.singleton;

import com.kouleen.suppermessage.common.CommonExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangqing
 * @since 2023/1/25 20:16
 */
public final class GlobalSingleton implements Singleton{

    private static final class PlayersHolder {
        static final Map<UUID, String> players = new HashMap<>();
    }

    public Map<UUID, String> getPlayer() {
        return PlayersHolder.players;
    }

    public static Map<UUID, String> getPlayers() {
        return PlayersHolder.players;
    }

    private static final class ThreadPoolExecutorHolder {
        static final ThreadPoolExecutor threadPoolExecutor = CommonExecutor.getCommonExecutor();
    }

    public ThreadPoolExecutor getThreadExecutor(){
        return ThreadPoolExecutorHolder.threadPoolExecutor;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return ThreadPoolExecutorHolder.threadPoolExecutor;
    }

}