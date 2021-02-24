package me.shinybless.Galactic.FastBoard;

import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Towers;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class ScoreBoard implements Listener{

    private Main plugin;

    public ScoreBoard(Main plugin){
        this.plugin = plugin;
    }

    public static void update(FastBoard board){

        if (Towers.TowersStart) {
            board.updateLines("",
                    "§cRed §0➩ §f" + Towers.score.get("RedTeam"),
                    "",
                    "§9Blue §0➩ §f" + Towers.score.get("BlueTeam"),
                    "",
                    "§6Time §0➩ §f" + Towers.timer);
        }else{
            board.updateLines("",
                    "§cRed §0➩ §f" + Towers.score.get("RedTeam"),
                    "",
                    "§9Blue §0➩ §f" + Towers.score.get("BlueTeam"),
                    "",
                    "§6Time §0➩ §f" + Towers.congelTimer);
        }
    }
}