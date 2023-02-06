package com.kouleen.freemenu.command;

import com.kouleen.freemenu.domain.JavaPluginBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhangqing
 * @since 2023/2/6 15:40
 */
public class CommandServiceImpl implements CommandService{

    private final JavaPluginBean javaPluginBean;

    public CommandServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return true;
    }
}
