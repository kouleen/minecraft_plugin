package com.kouleen.suppermessage.command;

import com.kouleen.suppermessage.constant.GlobalPluginEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqing
 * @since 2023/1/24 22:57
 */
public class TabExecutorServiceImpl implements TabExecutorService{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                return GlobalPluginEnum.getPluginList().parallelStream().filter(cmd -> cmd.contains(args[0].toLowerCase())).collect(Collectors.toList());
            }
            return Collections.singletonList(GlobalPluginEnum.HELP.getCode()).parallelStream().filter(cmd -> cmd.contains(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
