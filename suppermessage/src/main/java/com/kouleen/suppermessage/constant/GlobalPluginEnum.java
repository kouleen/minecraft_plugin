package com.kouleen.suppermessage.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/25 13:52
 */
public enum GlobalPluginEnum {

    OFF("off","关闭"),
    ON("on","开启"),
    HELP("help","帮助");

    private final String code;

    private final String desc;

    GlobalPluginEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static List<String> getPluginList(){
        List<String> pluginList = new ArrayList<>();
        GlobalPluginEnum[] globalPluginEnums = GlobalPluginEnum.values();
        for (GlobalPluginEnum globalPluginEnum : globalPluginEnums) {
            pluginList.add(globalPluginEnum.getCode());
        }
        return pluginList;
    }
}
