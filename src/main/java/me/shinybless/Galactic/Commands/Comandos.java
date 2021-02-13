package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Menu;
import me.shinybless.Galactic.Scenarios;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Comandos implements CommandExecutor, Listener {
    private Main plugin;

    public Comandos(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("hasteyboys").setExecutor(this);
        plugin.getCommand("switcheroo").setExecutor(this);
        plugin.getCommand("cutclean").setExecutor(this);
        plugin.getCommand("nofall").setExecutor(this);
        plugin.getCommand("skyoff").setExecutor(this);
        plugin.getCommand("superheroes").setExecutor(this);
        plugin.getCommand("scens").setExecutor(this);
    }

    public static boolean HasteyBoys = false;
    public static boolean Switcheroo = false;
    public static boolean CutClean = false;
    public static boolean NoFall = false;
    public static boolean SkyOff = false;
    public static boolean UnderOff = false;
    public static boolean SuperHeroes = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender.hasPermission("galactic.hasteyboys") && command.getName().equalsIgnoreCase("hasteyboys")) {
            if (!HasteyBoys) {
                HasteyBoys = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §bHasteyBoys §2on");
                return true;
            } else if (HasteyBoys) {
                HasteyBoys = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §bHasteyBoys §4off");
                return true;
            }
        }
        if (sender.hasPermission("galactic.switcheroo") && command.getName().equalsIgnoreCase("switcheroo")) {
            if (!Switcheroo) {
                Switcheroo = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §aSwitcheroo §2on");
                return true;
            } else if (Switcheroo) {
                Switcheroo = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §aSwitcheroo §4off");
                return true;
            }
        }
        if (sender.hasPermission("galactic.cutclean") && command.getName().equalsIgnoreCase("cutclean")) {
            if (!CutClean) {
                CutClean = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §2on");
                return true;
            } else if (CutClean) {
                CutClean = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.nofall") && command.getName().equalsIgnoreCase("nofall")) {
            if (!NoFall) {
                NoFall = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §2on");
                return true;
            } else if (NoFall) {
                NoFall = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.superheroes") && command.getName().equalsIgnoreCase("superheroes")) {
            if (!SuperHeroes) {
                SuperHeroes = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6SuperHeroes §2on");
                return true;
            } else if (SuperHeroes) {
                SuperHeroes = false;
                Scenarios.health.clear();
                Scenarios.strength.clear();
                Scenarios.speed.clear();
                Scenarios.haste.clear();
                Scenarios.jump.clear();
                Scenarios.resistance.clear();
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6SuperHeroes §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.skyoff") && command.getName().equalsIgnoreCase("skyoff")) {
            if (!SkyOff) {
                SkyOff = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fSkyOff §2on");
                return true;
            } else if (SkyOff) {
                SkyOff = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fSkyOff §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.underoff") && command.getName().equalsIgnoreCase("underoff")) {
            if (!UnderOff) {
                UnderOff = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §cUnderOff §2on");
                return true;
            } else if (UnderOff) {
                UnderOff = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §cUnderOff §4off");
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("scens")) {
            Menu.ScenariosMenu((Player) sender);
            return true;
        }
        return false;
    }
}
