package com.kouleen.respawn.service;

import com.kouleen.respawn.domain.JavaPluginBean;
import net.milkbowl.vault.economy.Economy;

/**
 * @author zhangqing
 * @since 2023/1/25 0:25
 */
public interface ReSpawnService {

    /**
     * 注册事件
     */
    boolean registerEvents(JavaPluginBean javaPluginBean);

    /**
     * 注册命令
     */
    boolean registerCommand(JavaPluginBean javaPluginBean);

    /**
     * 注册经济插件
     */
    Economy registrationPlugin(Class<Economy> clazz);

    /**
     * 重载文件配置
     */
    boolean saveConfigFile(JavaPluginBean javaPluginBean);
}
