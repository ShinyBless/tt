package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.FastBoard.FastBoard;
import me.shinybless.Galactic.FastBoard.ScoreBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {

    public static Map<String, FastBoard> boards = new HashMap<>();
    public static ArrayList<String> galacticplayers = new ArrayList<String>();

    @Override
    public void onEnable() {
        new Scenarios(this);
        new Comandos(this);
        new Eventos(this);
        new Menu(this);
        new Staff(this);
        new Teams(this);
        new Towers(this);
        new ScoreBoard(this);
        new Whitelist(this);
        this.LoadPlayers();

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                ScoreBoard.update(board);
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        this.SaveConfigyml();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!galacticplayers.contains(player.getName())){
            galacticplayers.add(player.getName());
        }
    }

    public void SaveConfigyml(){
        for (String p : galacticplayers){
            getConfig().set("galactic.players", galacticplayers);
        }
    }

    public void LoadPlayers(){
        if (getConfig().getConfigurationSection("galactic.players") != null) {
            Set<String> set = getConfig().getConfigurationSection("galactic.players").getKeys(false);
            galacticplayers.addAll(set);
        }
    }
}
