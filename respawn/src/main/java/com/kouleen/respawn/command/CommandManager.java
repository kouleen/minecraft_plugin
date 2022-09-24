package com.kouleen.respawn.command;

import com.kouleen.respawn.Main;
import com.kouleen.respawn.constant.PluginEnum;
import com.kouleen.respawn.domain.JavaPluginBean;
import com.kouleen.respawn.sevice.EnableService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqing
 * @since 2022/9/24 11:09
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    private final JavaPluginBean javaPluginBean;

    public CommandManager(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length > 0){
            if (args.length == 1 && args[0].equalsIgnoreCase(PluginEnum.RELOAD.getCmd())){
                EnableService enableService = javaPluginBean.getEnableService();
                Main main = javaPluginBean.getMain();
                enableService.saveConfigFile(main,javaPluginBean);
                commandSender.sendMessage("§7[" + "§e" + main.getName() + "§7]" + "§f: " + "§a§l配置重载成功");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase(PluginEnum.HELP.getCmd())){
                commandSender.sendMessage("§3§l======§eRespawn======");
                commandSender.sendMessage(" ");
                commandSender.sendMessage("§7/respawn help : " + "帮助信息");
                commandSender.sendMessage("§7/respawn reload : " + "配置重载");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length == 1){
            return PluginEnum.getCommandList().parallelStream().filter(str -> str.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
