package me.shinybless.Galactic.Towers;

import me.shinybless.Galactic.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Menu implements Listener {
    private Main plugin;

    public Menu(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static Inventory scens = Bukkit.createInventory(null, 27, "Scenarios");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Scenarios")) {
            event.setCancelled(true);
        }
    }

    public static void ScenariosMenu(Player p) {
        scens.setItem(0, MenuItems.HasteyBoysMenu());
        scens.setItem(1, MenuItems.CutCleanMenu());
        scens.setItem(2, MenuItems.NoFallMenu());
        scens.setItem(3, MenuItems.SwitcherooMenu());
        scens.setItem(4, MenuItems.SkyOffMenu());
        //scens.setItem(5, MenuItems.UnderOffMenu());
        p.openInventory(scens);
    }
}
