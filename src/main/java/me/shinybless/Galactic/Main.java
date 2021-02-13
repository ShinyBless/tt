package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.FastBoard.FastBoard;
import me.shinybless.Galactic.FastBoard.ScoreBoard;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static Map<String, FastBoard> boards = new HashMap<>();

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

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                ScoreBoard.update(board);
            }
        }, 0L, 5L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
