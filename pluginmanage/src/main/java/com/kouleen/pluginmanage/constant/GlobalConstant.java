package com.kouleen.pluginmanage.constant;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/24 23:43
 */
public interface GlobalConstant extends Serializable {

    /**
     * 配置文件名称
     */
    String PLUGIN_FILE = "plugin.yml";

    /**
     * 项目主目录
     */
    String FILE_HOME = "";

    /**
     * 登录的地址   POST
     */
    String LOGIN_URL = "http://download.kouleen.cn/login/do-login";

    /**
     * 注册用户地址   POST
     */
    String REGISTERED = "http://download.kouleen.cn/login/registered";

    /**
     * 添加插件地址   POST
     */
    String ADD_PLUGIN = "http://download.kouleen.cn/server/add-plugin";

    /**
     * 分页查询信息   POST
     */
    String PAGE_LIKE_PLUGIN = "http://download.kouleen.cn/server/like";

    /**
     * 分页查询所有的插件信息接口 POST
     */
    String PAGE_PLUGIN = "http://download.kouleen.cn/plugin/page";

    /**
     * 邮箱正则
     */
    String CHECK_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
}
