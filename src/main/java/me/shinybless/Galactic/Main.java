package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.FastBoard.FastBoard;
import me.shinybless.Galactic.FastBoard.ScoreBoard;
import me.shinybless.Galactic.Towers.Towers;
import me.shinybless.Galactic.TowersWars.TowersWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.*;

public final class Main extends JavaPlugin {

    public static Map<String, FastBoard> boards = new HashMap<>();
    public static ArrayList<String> galacticplayers = new ArrayList<String>();
    public static Towers tt;

    @Override
    public void onEnable() {

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        //General
        new Scenarios(this);
        new Eventos(this);
        new ScoreBoard(this);
        new Whitelist(this);

        //Comandos
        new Comandos(this);
        new Teams(this);
        new Staff(this);

        //Towers
        new Menu(this);
        new Towers(this);

        //TowersWars
        new TowersWars(this);


        Blue();
        Red();

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                ScoreBoard.update(board);
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        //this.SaveConfigyml();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!galacticplayers.contains(player.getName())){
            galacticplayers.add(player.getName());
        }
    }

    public void Blue(){
        ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getNewScoreboard();
        Team blue = board.registerNewTeam("Blue");
        blue.setAllowFriendlyFire(false);
        blue.setColor(ChatColor.BLUE);
        blue.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
    }

    public void Red(){
        ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getNewScoreboard();
        Team red = board.registerNewTeam("Red");
        red.setAllowFriendlyFire(false);
        red.setColor(ChatColor.RED);
        red.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
    }

    /*public void SaveConfigyml(){
        for (String p : ){
            getConfig().set("", );
        }
    }

    public void LoadPlayers(){
        if (getConfig().getConfigurationSection("") != null) {
            Set<String> set = getConfig().getConfigurationSection("").getKeys(false);
            .addAll(set);
        }
    }*/
}
