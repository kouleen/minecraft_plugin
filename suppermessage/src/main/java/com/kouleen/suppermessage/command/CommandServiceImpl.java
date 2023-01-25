package com.kouleen.suppermessage.command;

import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.singleton.GlobalSingleton;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqing
 * @since 2023/1/24 22:57
 */
public class CommandServiceImpl implements CommandService {

    private final JavaPluginBean javaPluginBean;

    public CommandServiceImpl(JavaPluginBean javaPluginBean){
        this.javaPluginBean = javaPluginBean;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<UUID, String> players = GlobalSingleton.getPlayers();
        if (args.length >= 1) {
            if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§2§l====§8§l【§a§l super-message §8§l】§2§l====");
                sender.sendMessage("§2§l====§8§l【§a§l QQ群：645375329 §8§l】§2§l====");
                sender.sendMessage("§7§l/message <off>  " + "§6拒绝骚扰，关闭外服消息");
                sender.sendMessage("§7§l/message <on>  " + "§6我想撩骚，开启外服消息");
                sender.sendMessage("§7§l/message <发送的聊天消息>  " + "§6骚话一大堆");
                return true;
            }
            if(sender instanceof Player){
                Player player = (Player) sender;
                UUID uniqueId = player.getUniqueId();
                if (args.length == 1 && args[0].equalsIgnoreCase("off")) {
                    if (players != null && !players.containsKey(uniqueId)) {
                        players.put(uniqueId, player.getName());
                        sender.sendMessage("[message] §2§l设置成功,已关闭外部服务消息");
                        return true;
                    }
                    sender.sendMessage("[message] §4§l当前设置已关闭外部服务消息，请勿重复操作");
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("on")) {
                    if (players != null && players.containsKey(uniqueId)) {
                        players.remove(uniqueId);
                        sender.sendMessage("[message] §e§l设置成功，已开启外部服务消息");
                        return true;
                    }
                    sender.sendMessage("[message] §4§l当前设置已已开启外部服务消息，请勿重复操作");
                    return true;
                }
                if (players != null && players.containsKey(uniqueId)) {
                    sender.sendMessage("[message] §4§l你当前的外部信息没开启");
                    return true;
                }
                return javaPluginBean.getSupperMessageService().producer(javaPluginBean,args, player, sender);
            }else {
                sender.sendMessage("[message] §4§l此命令不允许控制台启用");
            }
        }
        return false;
    }
}
