package com.kouleen.freemenu.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhangqing
 * @since 2023/2/1 16:29
 */
public interface CommandService extends CommandExecutor {

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        return true;
    }
}
