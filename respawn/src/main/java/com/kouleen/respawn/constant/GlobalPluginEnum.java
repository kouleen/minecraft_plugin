package com.kouleen.respawn.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/25 0:05
 */
public enum GlobalPluginEnum {

    RELOAD("reload","重载"),
    HELP("help","帮助");

    final String cmd;

    final String desc;

    GlobalPluginEnum(String cmd,String desc){
        this.cmd = cmd;
        this.desc = desc;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDesc() {
        return desc;
    }

    public static List<String> getCommandList(){
        List<String> commandList = new ArrayList<>();
        GlobalPluginEnum[] pluginEnums = GlobalPluginEnum.values();
        for (GlobalPluginEnum pluginEnum : pluginEnums) {
            commandList.add(pluginEnum.getCmd());
        }
        return commandList;
    }
}
