package com.kouleen.notbuild;

import com.kouleen.notbuild.command.NotBuildCommandService;
import com.kouleen.notbuild.command.NotBuildCommandServiceImpl;
import com.kouleen.notbuild.command.NotBuildTabCompleterService;
import com.kouleen.notbuild.command.NotBuildTabCompleterServiceImpl;
import com.kouleen.notbuild.domain.JavaPluginBean;
import com.kouleen.notbuild.listener.NotBuildListenerService;
import com.kouleen.notbuild.listener.NotBuildListenerServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class NotBuild extends JavaPlugin {

    private JavaPluginBean javaPluginBean;

    @Override
    public void onLoad() {
        javaPluginBean = new JavaPluginBean();
        javaPluginBean.setNotBuild(this);
        javaPluginBean.notBuildInit();
        javaPluginBean.setFileConfiguration(getConfig());
        javaPluginBean.setNotBuildCommandService(new NotBuildCommandServiceImpl());
        javaPluginBean.setNotBuildListenerService(new NotBuildListenerServiceImpl());
        javaPluginBean.setNotBuildTabCompleterService(new NotBuildTabCompleterServiceImpl());
    }

    @Override
    public void onEnable() {
        NotBuildListenerService notBuildListenerService = javaPluginBean.getNotBuildListenerService();
        Bukkit.getPluginManager().registerEvents(notBuildListenerService, javaPluginBean.getNotBuild());
        notBuildListenerService.setJavaPluginBean(javaPluginBean);

        PluginCommand pluginCommand = javaPluginBean.getNotBuild().getCommand("notbuild");
        NotBuildCommandService notBuildCommandService = javaPluginBean.getNotBuildCommandService();
        notBuildCommandService.setJavaPluginBean(javaPluginBean);
        pluginCommand.setExecutor(notBuildCommandService);

        NotBuildTabCompleterService notBuildTabCompleterService = javaPluginBean.getNotBuildTabCompleterService();
        pluginCommand.setTabCompleter(notBuildTabCompleterService);
    }

    @Override
    public void onDisable() {
    }
}
