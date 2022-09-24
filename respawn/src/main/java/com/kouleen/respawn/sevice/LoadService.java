package com.kouleen.respawn.sevice;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.domain.JavaPluginBean;

/**
 * @author zhangqing
 * @since 2022/9/24 13:40
 */
public interface LoadService {

    boolean initializationBean(Main main, JavaPluginBean javaPluginBean);
}
