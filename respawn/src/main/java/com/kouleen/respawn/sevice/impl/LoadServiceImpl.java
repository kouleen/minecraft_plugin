package com.kouleen.respawn.sevice.impl;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.sevice.EnableService;
import com.kouleen.respawn.sevice.LoadService;
import net.milkbowl.vault.economy.Economy;


/**
 * @author zhangqing
 * @since 2022/9/24 13:41
 */
public class LoadServiceImpl implements LoadService {

    @Override
    public boolean initializationBean(Main main,JavaPluginBean javaPluginBean) {
        javaPluginBean.setLogger(main.getLogger());
        EnableService enableService = new EnableServiceImpl();
        javaPluginBean.setEnableService(enableService);
        Economy economy = enableService.registrationPlugin(Economy.class);
        javaPluginBean.setEconomy(economy);
        javaPluginBean.setFileConfiguration(main.getConfig());
        javaPluginBean.setMain(main);
        return true;
    }
}
