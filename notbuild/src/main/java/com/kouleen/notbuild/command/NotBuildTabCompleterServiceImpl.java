package com.kouleen.notbuild.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqing
 * @since 2023/1/12 20:28
 */
public class NotBuildTabCompleterServiceImpl implements NotBuildTabCompleterService{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("add", "remove", "reload").parallelStream().filter(str -> str.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
