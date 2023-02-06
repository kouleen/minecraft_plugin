package com.kouleen.suppermessage.command;

import com.kouleen.suppermessage.SupperMessage;
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
            YamlConfiguration yamlConfiguration = this.getYamlConfiguration(javaPluginBean);
            SupperMessageService supperMessageService = javaPluginBean.getSupperMessageService();
            if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.HELP.getCode())) {
                sender.sendMessage("§2§l====§8§l【§a§l super-message §8§l】§2§l====");
                sender.sendMessage("§2§l====§8§l【§a§l QQ群：645375329 §8§l】§2§l====");
                this.handleMessage(yamlConfiguration,sender,"message.help.close","§7§l/message <off>  §6no harassment, shut down the outside service message");
                this.handleMessage(yamlConfiguration,sender,"message.help.open","§7§l/message <on>  §6I want to flirt, open up the message");
                this.handleMessage(yamlConfiguration,sender,"message.help.msg","§7§l/message <Send a chat message>  §6There are a lot of words");
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.RELOAD.getCode())){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if (!player.hasPermission("suppermessage.reload")){
                        this.handleMessage(yamlConfiguration,sender,"message.global.error-permission","[message] &4&lYou don't have permission to operate!");
                        return true;
                    }
                }
                SupperMessage supperMessage = javaPluginBean.getSupperMessage();
                supperMessage.reloadConfig();
                supperMessage.saveDefaultConfig();
                javaPluginBean.setFileConfiguration(supperMessage.getConfig());
                this.handleMessage(this.getYamlConfiguration(javaPluginBean),sender,"message.reload","§4§lThe configuration overload succeeded");
                return true;
            }
            if(sender instanceof Player){
                Player player = (Player) sender;
                UUID uniqueId = player.getUniqueId();
                if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.OFF.getCode())) {
                    if (players != null && !players.containsKey(uniqueId)) {
                        players.put(uniqueId, player.getName());
                        this.handleMessage(yamlConfiguration,sender,"message.close.success","[message] §2§lThe Settings are successful. External service messages are closed");
                        return true;
                    }
                    this.handleMessage(yamlConfiguration,sender,"message.close.repeat","[message] &4&lExternal service messages are disabled. Do not repeat this operation");
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase(GlobalPluginEnum.ON.getCode())) {
                    if (players != null && players.containsKey(uniqueId)) {
                        players.remove(uniqueId);
                        this.handleMessage(yamlConfiguration,sender,"message.open.success","[message] &e&lThe Settings are successful. External service messages are enabled");
                        return true;
                    }
                    this.handleMessage(yamlConfiguration,sender,"message.open.repeat","[message] &4&lExternal service messages have been enabled. Do not repeat this operation");
                    return true;
                }
                if (players != null && players.containsKey(uniqueId)) {
                    this.handleMessage(yamlConfiguration,sender,"message.global.error-player","[message] &4&lYour current external message is not enabled");
                    return true;
                }
                return supperMessageService.producer(javaPluginBean,args, player, sender);
            } else {
                this.handleMessage(yamlConfiguration,sender,"message.global.error-console","[message] &4&lThis command does not allow the console to be enabled");
            }
        }
        return false;
    }
}
