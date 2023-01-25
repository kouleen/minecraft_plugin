package com.kouleen.pluginmanage.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/24 23:44
 */
public enum GlobalPluginEnum {

    // 一级命令
    COMMAND(Arrays.asList(
                    CommandEnum.reloadConfig.name(),       // 重载
                    CommandEnum.load.name(),               // 加载
                    CommandEnum.page.name(),               // 分页远程查询插件
                    CommandEnum.help.name(),               // 帮助
                    CommandEnum.loadAll.name(),            // 全加载
                    CommandEnum.unload.name(),             // 卸载
                    CommandEnum.unloadAll.name(),          // 全卸载
                    CommandEnum.download.name(),           // 下载
                    CommandEnum.syncInfo.name(),           // 同步信息
                    CommandEnum.zip.name(),                // 压缩
                    CommandEnum.unZip.name(),              // 解压
                    CommandEnum.like.name(),               // 查看文件
                    CommandEnum.delete.name(),             // 删除文件
                    CommandEnum.registered.name(),         // 注册账号
                    CommandEnum.addPlugin.name()           // 添加插件信息
                    )),;

    private final List<String> value;

    GlobalPluginEnum(List<String> value){
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }
}
