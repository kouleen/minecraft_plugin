package com.kouleen.respawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author zhangqing
 * @since 2023/1/24 22:50
 */
public interface CommandService extends CommandExecutor {

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
    default boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return false;
    }
}
