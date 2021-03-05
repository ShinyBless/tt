package me.shinybless.Galactic.FastBoard;

import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Towers.Towers;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ScoreBoard implements Listener{

    private Main plugin;

    public ScoreBoard(Main plugin){
        this.plugin = plugin;
    }

    public static void update(FastBoard board){

        if (Towers.TowersStart) {
            board.updateLines("§bOnline§7: §f" + Bukkit.getOnlinePlayers().size(),
                    "§bPing§7: §f" + getPing(board.getPlayer()),
                    "",
                    "§cRed §0➩ §f" + Towers.score.get("RedTeam"),
                    "",
                    "§9Blue §0➩ §f" + Towers.score.get("BlueTeam"),
                    "",
                    "§6Time §0➩ §f" + Towers.timer);
        }else{
            board.updateLines("§bOnline§7: §f" + Bukkit.getOnlinePlayers().size(),
                    "§bPing§7: §f" + getPing(board.getPlayer()),
                    "",
                    "§cRed §0➩ §f" + Towers.score.get("RedTeam"),
                    "",
                    "§9Blue §0➩ §f" + Towers.score.get("BlueTeam"),
                    "",
                    "§6Time §0➩ §f" + Towers.congelTimer);
        }
    }

    public static int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }
}