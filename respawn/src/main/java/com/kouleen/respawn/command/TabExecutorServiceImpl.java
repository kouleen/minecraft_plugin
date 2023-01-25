package com.kouleen.respawn.command;

import com.kouleen.respawn.constant.GlobalPluginEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqing
 * @since 2023/1/24 22:57
 */
public class TabExecutorServiceImpl implements TabExecutorService {

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return GlobalPluginEnum.getCommandList().parallelStream().filter(str -> str.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
