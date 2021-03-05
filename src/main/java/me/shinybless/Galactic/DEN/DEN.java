package me.shinybless.Galactic.DEN;

import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class DEN implements Listener {
    private Main plugin;

    public DEN(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static HashMap<Location, Integer> nexus = new HashMap<>();
    Location nexusro = new Location(Bukkit.getWorld("world"), 1, 1, 1);
    Location nexusaz = new Location(Bukkit.getWorld("world"), 2, 2, 2);
    Location nexusam = new Location(Bukkit.getWorld("world"), 3, 3, 3);
    Location nexusve = new Location(Bukkit.getWorld("world"), 4, 4, 4);

    @EventHandler
    public void onClick (PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location loc = block.getLocation();
        Action action = event.getAction();
        nexus.putIfAbsent(nexusro, 100);
        nexus.putIfAbsent(nexusaz, 100);
        nexus.putIfAbsent(nexusam, 100);
        nexus.putIfAbsent(nexusve, 100);
        int vidarojo = nexus.get(nexusro);
        int vidaazul = nexus.get(nexusro);
        int vidaamarillo = nexus.get(nexusro);
        int vidaverde = nexus.get(nexusro);
        if (action == Action.LEFT_CLICK_BLOCK){
            if (loc == nexusro){
                if (!(Teams.redteam.contains(player))) {
                    nexus.put(loc, vidarojo - 1);
                }
            }
            if (loc == nexusaz){
                if (!(Teams.blueteam.contains(player))) {
                    nexus.put(loc, vidaazul - 1);
                }
            }
            if (loc == nexusam){
                nexus.put(loc, vidaamarillo - 1);
            }
            if (loc == nexusve){
                nexus.put(loc, vidaverde - 1);
            }
        }
    }
}
