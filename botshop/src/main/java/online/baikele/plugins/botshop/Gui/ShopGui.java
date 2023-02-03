//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package online.baikele.plugins.botshop.Gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import online.baikele.plugins.botshop.BotShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ShopGui {

    static Plugin plugin = BotShop.getPlugin(BotShop.class);

    public static Inventory Shopgui(Player player) {
        File file = new File(plugin.getDataFolder(), "shop.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        int shopSize = yamlConfiguration.getInt("Shop.Size");
        String shopTitle = yamlConfiguration.getString("Shop.Title");
        Inventory inventory = Bukkit.createInventory(player, shopSize, shopTitle);
        int length = 0;

        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("Item");
        if(configurationSection != null){
            for (String itemKey : configurationSection.getKeys(false)) {
                String newKey = "Item." + itemKey + ".";
                String itemName = yamlConfiguration.getString(newKey + "item");
                Material material = Material.valueOf(itemName);
                ItemStack itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    String displayName = yamlConfiguration.getString(newKey + "name");
                    if(displayName != null){
                        itemMeta.setDisplayName(displayName);
                    }
                    List<String> loreList = new ArrayList<>();
                    yamlConfiguration.getStringList(newKey + "lore").forEach(lore ->{
                        String loreName = lore.replace("&", "§");
                        loreList.add(loreName);
                    });
                    itemMeta.setLore(loreList);
                    itemStack.setItemMeta(itemMeta);
                }
                inventory.setItem(length,itemStack);
            }
        }
        return inventory;
    }
}
