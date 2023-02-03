package com.kouleen.suppermessage.singleton;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangqing
 * @since 2023/1/30 15:42
 */
public interface Singleton {

    Map<UUID, String> getPlayer();

    ThreadPoolExecutor getThreadExecutor();
}
