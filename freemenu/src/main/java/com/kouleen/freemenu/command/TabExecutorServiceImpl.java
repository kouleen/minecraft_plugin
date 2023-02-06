package com.kouleen.freemenu.command;

import com.kouleen.freemenu.constant.GlobalPluginEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqing
 * @since 2023/2/6 15:40
 */
public class TabExecutorServiceImpl implements TabExecutorService{

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                return GlobalPluginEnum.getPluginList().parallelStream().filter(cmd -> cmd.contains(args[0].toLowerCase())).collect(Collectors.toList());
            }
            return Collections.singletonList(GlobalPluginEnum.HELP.getCode()).parallelStream().filter(cmd -> cmd.contains(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
