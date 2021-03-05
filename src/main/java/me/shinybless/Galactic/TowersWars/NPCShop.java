package me.shinybless.Galactic.TowersWars;

import me.shinybless.Galactic.Main;
import org.bukkit.event.Listener;

public class NPCShop implements Listener {
    private Main plugin;

    public NPCShop(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //public Inventory shop = Bukkit.createInventory()
}
