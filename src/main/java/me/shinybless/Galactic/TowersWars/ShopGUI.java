package me.shinybless.Galactic.TowersWars;

import me.shinybless.Galactic.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

public class ShopGUI implements Listener {
    private Main plugin;
    public static String shopTitle = "§bShop";

    public ShopGUI(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    Inventory shop = Bukkit.createInventory(null, 36, shopTitle);

    public void openInv(Player p){




        p.openInventory(shop);
    }

    @EventHandler
    public void onClick (PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity.getCustomName() != null) {
            if (entity.getCustomName().equalsIgnoreCase("§bShop") || entity.getCustomName().equalsIgnoreCase("§cShop")) {
                openInv(player);
            }
        }
    }
}
