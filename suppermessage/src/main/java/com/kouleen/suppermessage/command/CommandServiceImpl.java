package com.kouleen.suppermessage.command;

import com.kouleen.suppermessage.constant.GlobalPluginEnum;
import com.kouleen.suppermessage.domain.JavaPluginBean;
import com.kouleen.suppermessage.service.SupperMessageService;
import com.kouleen.suppermessage.singleton.GlobalSingleton;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
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
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            String lang = fileConfiguration.getString("global.lang");
            YamlConfiguration yamlConfiguration = null;
            if(lang.equals("language_ZH.yml")){
                yamlConfiguration = YamlConfiguration.loadConfiguration(new File(javaPluginBean.getSupperMessage().getDataFolder(), "message/language_ZH.yml"));
            }else {
                yamlConfiguration = YamlConfiguration.loadConfiguration(new File(javaPluginBean.getSupperMessage().getDataFolder(), "message/language_US.yml"));
            }
            SupperMessageService supperMessageService = javaPluginBean.getSupperMessageService();
            if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.HELP.getCode())) {
                sender.sendMessage("§2§l====§8§l【§a§l super-message §8§l】§2§l====");
                sender.sendMessage("§2§l====§8§l【§a§l QQ群：645375329 §8§l】§2§l====");
                String offMessage = yamlConfiguration.getString("message.help.off");
                if(offMessage != null && !offMessage.equals("")){
                    sender.sendMessage(offMessage.replace("&", "§"));
                }else {
                    sender.sendMessage("§7§l/message <off>  §6no harassment, shut down the outside service message");
                }
                String onMessage = yamlConfiguration.getString("message.help.on");
                if(onMessage != null && !onMessage.equals("")){
                    sender.sendMessage(onMessage.replace("&", "§"));
                }else {
                    sender.sendMessage("§7§l/message <on>  §6I want to flirt, open up the message");
                }
                String msgMessage = yamlConfiguration.getString("message.help.msg");
                if(msgMessage != null && !msgMessage.equals("")){
                    sender.sendMessage(msgMessage.replace("&", "§"));
                }else {
                    sender.sendMessage("§7§l/message <Send a chat message>  §6There are a lot of words");
                }
                return true;
            }
            if(sender instanceof Player){
                Player player = (Player) sender;
                UUID uniqueId = player.getUniqueId();
                if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.OFF.getCode())) {
                    if (players != null && !players.containsKey(uniqueId)) {
                        players.put(uniqueId, player.getName());
                        sender.sendMessage("[message] §2§l设置成功,已关闭外部服务消息");
                        return true;
                    }
                    sender.sendMessage("[message] §4§l当前设置已关闭外部服务消息，请勿重复操作");
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.ON.getCode())) {
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
                return supperMessageService.producer(javaPluginBean,args, player, sender);
            }else {
                sender.sendMessage("[message] §4§l此命令不允许控制台启用");
            }
        }
        return false;
    }
}
