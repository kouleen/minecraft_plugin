//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package online.baikele.plugins.botshop.Bot;

import com.illtamer.infinite.bot.api.event.message.GroupMessageEvent;
import com.illtamer.infinite.bot.minecraft.api.StaticAPI;
import com.illtamer.infinite.bot.minecraft.api.event.EventHandler;
import com.illtamer.infinite.bot.minecraft.api.event.Listener;
import com.illtamer.infinite.bot.minecraft.pojo.PlayerData;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import online.baikele.plugins.botshop.BotShop;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Sign implements Listener {
    Plugin plugin = BotShop.getPlugin(BotShop.class);

    @EventHandler
    public void onEvent(GroupMessageEvent event) throws IOException {
        String msg = event.getMessage().toString();
        long userId = event.getSender().getUserId();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(date1);
        File dataYML = new File(this.plugin.getDataFolder(), "data.yml");
        YamlConfiguration datas = YamlConfiguration.loadConfiguration(dataYML);
        PlayerData data = StaticAPI.getRepository().queryByUserId(userId);
        OfflinePlayer player;
        String msgs;
        Iterator var14;
        String msgss;
        if (event.getMessage() != null && msg.equalsIgnoreCase(this.plugin.getConfig().getString("Sign.message")) && StaticAPI.inGroups(event.getGroupId())) {
            if (data == null) {
                event.reply(this.plugin.getConfig().getString("Message.invalid"));
                return;
            }

            player = Bukkit.getOfflinePlayer(UUID.fromString(data.getUuid()));
            String lasterTime = datas.getString(player.getName() + ".time");
            if (lasterTime != null && lasterTime.equalsIgnoreCase(currentTime)) {
                msgs = "";

                for(var14 = this.plugin.getConfig().getStringList("Sign.not").iterator(); var14.hasNext(); msgs = msgs + msgss + "\n") {
                    msgss = (String)var14.next();
                }

                event.reply(msgs);
                return;
            }

            msgs = this.plugin.getConfig().getString("Sign.points");
            String[] points = msgs.split("-");
            int d = (int)(Math.random() * (double)(Integer.parseInt(points[1]) - Integer.parseInt(points[0])) + (double)Integer.parseInt(points[0]));
            int pointsnum = datas.getInt(player.getName() + ".points");
            datas.set(player.getName() + ".points", pointsnum + d);
            datas.set(player.getName() + ".time", currentTime);
            datas.save(dataYML);
            int pointsn = datas.getInt(player.getName() + ".points");

            for(Iterator var19 = this.plugin.getConfig().getStringList("Sign.send").iterator(); var19.hasNext(); msgs = msgs + msgss.replace("%ap%", "" + d).replace("%p%", "" + pointsn) + "\n") {
                msgss = (String)var19.next();
            }

            event.reply(msgs);
        } else if (event.getMessage() != null && msg.equalsIgnoreCase(this.plugin.getConfig().getString("points.message")) && StaticAPI.inGroups(event.getGroupId())) {
            if (data == null) {
                event.reply(this.plugin.getConfig().getString("Message.invalid"));
                return;
            }

            player = Bukkit.getOfflinePlayer(UUID.fromString(data.getUuid()));
            int pointsnum = datas.getInt(player.getName() + ".points");
            msgs = "";

            for(var14 = this.plugin.getConfig().getStringList("points.send").iterator(); var14.hasNext(); msgs = msgs + msgss.replace("%p%", "" + pointsnum) + "\n") {
                msgss = (String)var14.next();
            }

            event.reply(msgs);
        }

    }
}
