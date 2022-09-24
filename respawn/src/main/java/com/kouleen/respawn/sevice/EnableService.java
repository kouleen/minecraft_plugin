package com.kouleen.respawn.sevice;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.domain.JavaPluginBean;
import net.milkbowl.vault.economy.Economy;

/**
 * @author zhangqing
 * @since 2022/9/24 11:57
 */
public interface EnableService {

    /**
     * 注册事件
     */
    boolean registerEvents(Main main, JavaPluginBean javaPluginBean);

    /**
     * 注册经济插件
     */
    Economy registrationPlugin(Class<Economy> clazz);

    /**
     * 重载文件配置
     */
    boolean saveConfigFile(Main main, JavaPluginBean javaPluginBean);

    /**
     * 注册命令
     */
    boolean registerCommand(Main main, JavaPluginBean javaPluginBean);
}
