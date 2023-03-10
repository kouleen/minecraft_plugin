package com.kouleen.suppermessage.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/24 22:53
 */
public interface TabExecutorService extends TabExecutor {

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    default boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     *     command inside of a command block, this will be the player, not
     *     the command block.
     * @param command Command which was executed
     * @param alias The alias used
     * @param args The arguments passed to the command, including final
     *     partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     *     to default to the command executor
     */
    @Override
    default List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        return Collections.emptyList();
    }
}
