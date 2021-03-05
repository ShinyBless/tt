package me.shinybless.Galactic.TowersWars;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;

public class TowersWars implements Listener, CommandExecutor {
    private Main plugin;

    public TowersWars(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("towerswars").setExecutor(this);
    }

    //private NPC npc;
    public static boolean TowersWarsStart = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("TowersWars") && sender.hasPermission("galactic.TowersWars")){
            if (args.length != 1){
                sender.sendMessage("§7Faltaron o sobraron argumentos!");
                sender.sendMessage("§7/TowersWars start");
                return true;
            } else {
                TowersWarsStart = true;
                Villager blue = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 0, 200, 0), Villager.class);
                blue.setCustomName("§bShop");
                blue.setAI(false);
                Villager red = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 5, 200, 0), Villager.class);
                red.setCustomName("§dSexShop");
                red.setAI(false);
            }
        }
        /*if (cmd.getName().equalsIgnoreCase("createnpc") && sender.hasPermission("galactic.TowersWars")){
            NPC.createNPC(new Location(Bukkit.getWorld("world"), 0, 200, 0), "§3chinchulin");
            npc.enableRotation();
            sender.sendMessage("madre mia tio has creado un npc");
        }*/
        return false;
    }
}
