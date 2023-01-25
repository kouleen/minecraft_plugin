package com.kouleen.pluginmanage.domain;

import com.kouleen.pluginmanage.PluginManage;
import com.kouleen.pluginmanage.command.CommandService;
import com.kouleen.pluginmanage.command.TabExecutorService;

import java.io.Serializable;

/**
 * @author zhangqing
 * @since 2023/1/24 23:09
 */
public class JavaPluginBean implements Serializable {

    private PluginManage pluginManage;

    private CommandService commandService;

    private TabExecutorService tabExecutorService;
}
