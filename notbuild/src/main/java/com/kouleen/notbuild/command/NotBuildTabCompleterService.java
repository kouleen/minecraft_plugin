package com.kouleen.notbuild.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/12 20:28
 */
public interface NotBuildTabCompleterService extends TabCompleter {

    List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
}
