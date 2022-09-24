package com.kouleen.respawn.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqing
 * @since 2022/9/24 11:10
 */
public enum PluginEnum {

    RELOAD("reload","重载"),
    HELP("help","帮助");

    final String cmd;

    final String desc;

    PluginEnum(String cmd,String desc){
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
        PluginEnum[] pluginEnums = PluginEnum.values();
        for (PluginEnum pluginEnum : pluginEnums) {
            commandList.add(pluginEnum.getCmd());
        }
        return commandList;
    }
}
