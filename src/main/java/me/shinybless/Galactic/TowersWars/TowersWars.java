package me.shinybless.Galactic.TowersWars;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Scenarios;
import me.shinybless.Galactic.Towers.Towers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class TowersWars implements Listener, CommandExecutor {
    private final Main plugin;

    public TowersWars(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("towerswars").setExecutor(this);
    }

    public static boolean TowersWarsStart = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("TowersWars") && sender.hasPermission("galactic.TowersWars")){
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/TowersWars start/stop");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!TowersWarsStart) {
                        Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El chat ha sido muteado");
                        Bukkit.broadcastMessage("§7[§6TowersWars§7]➛ El juego comenzará en §b15 segundos§7!");
                        Staff.MuteAll = true;
                        new BukkitRunnable() {
                            public void run() {
                                TowersWarsStart = true;
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    Towers.GameStart(p);
                                    Main.tt.time();
                                    Main.tt.LapisGen();
                                    Main.tt.IronGen();
                                    Villager blue = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5), Villager.class);
                                    blue.setCustomName("§bShop");
                                    blue.setAI(false);
                                    blue.setCanPickupItems(false);
                                    blue.setCollidable(false);
                                    blue.setInvulnerable(true);
                                    Villager red = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 5.5, 200, 0.5), Villager.class);
                                    red.setCustomName("§cShop");
                                    red.setAI(false);
                                    red.setCanPickupItems(false);
                                    red.setCollidable(false);
                                    red.setInvulnerable(true);
                                    if (Comandos.SuperHeroes) {
                                        Towers.StartSuperHeroes(p);
                                    }
                                }
                                if (Comandos.TeamSwap){
                                    Scenarios.TeamSwapScen();
                                }
                                Staff.MuteAll = false;
                            }
                        }.runTaskLater(plugin, 300L);
                        new BukkitRunnable(){
                            public void run(){
                                Bukkit.broadcastMessage("§7[§6TowersWars§7]➛ Reglas de TowersWars");
                                Bukkit.broadcastMessage("§61- §fProhibido salirse, a excepción de que sea chosen o todo el team se rinda.");
                            }
                        }.runTaskLater(plugin, 60L);
                        new BukkitRunnable(){
                            public void run(){
                                Bukkit.broadcastMessage("§62- §fGriefing OFF§f.");
                            }
                        }.runTaskLater(plugin, 120L);
                        new BukkitRunnable(){
                            public void run(){
                                Bukkit.broadcastMessage("§63- §fSi es Sky OFF, solo puede haber puentes desde el centro al principio de la base enemiga, y desde finales de la base enemiga hacia su punto.");
                            }
                        }.runTaskLater(plugin, 180L);
                        new BukkitRunnable(){
                            public void run(){
                                Bukkit.broadcastMessage("§64- §fProhibido campear en el punto. Se puede campear en el puente del spawn, abajo del punto.");
                            }
                        }.runTaskLater(plugin, 240L);
                        new BukkitRunnable(){
                            public void run(){
                                Bukkit.broadcastMessage("§65- §fProhibido buscar vacios legales.");
                                Bukkit.broadcastMessage("§6Para mas detalles, ir al canal de #reglas en el discord.");
                                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El chat ha sido desmuteado");
                            }
                        }.runTaskLater(plugin, 300L);
                    } else {
                        sender.sendMessage("§7Ya hay un Towers en proceso!");
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (TowersWarsStart) {
                        TowersWarsStart = false;
                        Towers.CongelTimer = true;
                        sender.sendMessage("§7Towers stop");
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}


                /*TowersWarsStart = true;
                Villager blue = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 0, 200, 0), Villager.class);
                blue.setCustomName("§bShop");
                blue.setAI(false);
                Villager red = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 5, 200, 0), Villager.class);
                red.setCustomName("§bShop");
                red.setAI(false);*/
