package com.kouleen.respawn;

import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.sevice.EnableService;
import com.kouleen.respawn.sevice.LoadService;
import com.kouleen.respawn.sevice.impl.LoadServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * @author zhangqing
 */
public class Main extends JavaPlugin {

    private static final LoadService LOAD_SERVICE = new LoadServiceImpl();

    private static final JavaPluginBean JAVA_PLUGIN_BEAN = new JavaPluginBean();

    @Override
    public void onLoad() {
        LOAD_SERVICE.initializationBean(this,JAVA_PLUGIN_BEAN);
        Logger logger = JAVA_PLUGIN_BEAN.getLogger();
        logger.info(String.format("%s 被成功预加载了",getName()));
        super.onLoad();
    }

    @Override
    public void onEnable() {
        Logger logger = JAVA_PLUGIN_BEAN.getLogger();
        EnableService enableService = JAVA_PLUGIN_BEAN.getEnableService();
        logger.info(String.format("%s 被成功加载了",getName()));
        enableService.saveConfigFile(this,JAVA_PLUGIN_BEAN);
        enableService.registerEvents(this,JAVA_PLUGIN_BEAN);
        enableService.registerCommand(this,JAVA_PLUGIN_BEAN);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Logger logger = JAVA_PLUGIN_BEAN.getLogger();
        EnableService enableService = JAVA_PLUGIN_BEAN.getEnableService();
        logger.info(String.format("%s 被成功卸载了",getName()));
        enableService.saveConfigFile(this,JAVA_PLUGIN_BEAN);
        super.onDisable();
    }
}