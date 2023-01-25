package com.kouleen.respawn.command;

import com.kouleen.respawn.ReSpawn;
import com.kouleen.respawn.constant.GlobalPluginEnum;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.service.ReSpawnService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author zhangqing
 * @since 2023/1/24 22:57
 */
public class CommandServiceImpl implements CommandService{

    private final JavaPluginBean javaPluginBean;

    public CommandServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    /**
     * Executes the given command, returning its success
     *
     * @param commandSender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length > 0){
            if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.RELOAD.getCmd())){
                ReSpawnService reSpawnService = javaPluginBean.getReSpawnService();
                ReSpawn reSpawn = javaPluginBean.getReSpawn();
                reSpawnService.saveConfigFile(javaPluginBean);
                commandSender.sendMessage("§7[" + "§e" + reSpawn.getName() + "§7]" + "§f: " + "§a§l配置重载成功");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.HELP.getCmd())){
                commandSender.sendMessage("§3§l======§eRespawn======");
                commandSender.sendMessage(" ");
                commandSender.sendMessage("§7/respawn help : " + "帮助信息");
                commandSender.sendMessage("§7/respawn reload : " + "配置重载");
                return true;
            }
        }
        return false;
    }
}
