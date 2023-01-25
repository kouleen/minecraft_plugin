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
public final class GlobalSingleton {

    private static Map<UUID, String> players;

    private static ThreadPoolExecutor threadPoolExecutor;

    public static Map<UUID, String> getPlayers() {
        if (players == null) {
            synchronized (GlobalSingleton.class) {
                if (players == null) {
                    players = new HashMap<>();
                }
            }
        }
        return players;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        if(threadPoolExecutor == null){
            synchronized (GlobalSingleton.class){
                if(threadPoolExecutor == null){
                    threadPoolExecutor = CommonExecutor.getCommonExecutor();
                }
            }
        }
        return threadPoolExecutor;
    }

}