package com.kouleen.notbuild.command;

import com.kouleen.notbuild.domain.JavaPluginBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author zhangqing
 * @since 2023/1/12 14:43
 */
public class NotBuildCommandServiceImpl implements NotBuildCommandService {

    private JavaPluginBean javaPluginBean;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§4控制台不能使用这个命令！");
            return false;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("notbuild")) {
            if (!sender.hasPermission("notbuild.admin")) {
                sender.sendMessage("§4你没有使用这个命令的权限！");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage("NotBuild插件帮助");
                sender.sendMessage("/notbuild add —— 添加你所在的世界到不可破坏的世界中");
                sender.sendMessage("/notbuild remove —— 设置你所在的世界为可以破坏");
                sender.sendMessage("/notbuild reload —— 重载配置文件");
                return false;
            }
            if (args.length != 1) {
                sender.sendMessage("§4你没有权限使用这个指令！");
                return false;
            }
            FileConfiguration fileConfiguration = javaPluginBean.getFileConfiguration();
            List<String> notBuildWorld = fileConfiguration.getStringList("not-build-world");
            if (args[0].equalsIgnoreCase("add")) {
                if (notBuildWorld.contains(((Player)sender).getWorld().getName())) {
                    sender.sendMessage("§4当前世界已是不可破坏");
                    return false;
                }
                notBuildWorld.add(player.getWorld().getName());
                fileConfiguration.set("not-build-world", notBuildWorld);
                javaPluginBean.notBuildSave();
                sender.sendMessage("§4当前世界已改为不可破坏");
                return false;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (notBuildWorld.contains(((Player)sender).getWorld().getName())) {
                    notBuildWorld.remove(((Player)sender).getWorld().getName());
                    fileConfiguration.set("not-build-world", notBuildWorld);
                    javaPluginBean.notBuildSave();
                    sender.sendMessage("§4当前世界已改为可破坏");
                    return false;
                }
                sender.sendMessage("§4当前世界已是可破坏");
            }
            if (args[0].equalsIgnoreCase("reload")) {
                javaPluginBean.notBuildReload();
                sender.sendMessage("§bNotbuild重载完毕");
            }
        }
        return false;
    }

    public void setJavaPluginBean(JavaPluginBean javaPluginBean) {
        this.javaPluginBean = javaPluginBean;
    }
}
