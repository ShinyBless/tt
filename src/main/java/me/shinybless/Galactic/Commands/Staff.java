package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.MenuItems;
import me.shinybless.Galactic.Towers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class Staff implements CommandExecutor, Listener {
    private Main plugin;

    public Staff(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("mute").setExecutor(this);
        plugin.getCommand("unmute").setExecutor(this);
        plugin.getCommand("muteall").setExecutor(this);
        plugin.getCommand("jail").setExecutor(this);
        plugin.getCommand("unjail").setExecutor(this);
        plugin.getCommand("staffconfig").setExecutor(this);
        plugin.getCommand("broadcast").setExecutor(this);
        plugin.getCommand("bc").setExecutor(this);
    }

    public static ArrayList<UUID> mute = new ArrayList<>();
    public static ArrayList<UUID> jail = new ArrayList<>();

    public static boolean MuteAll = false;

    public static Location jailzone = new Location(Bukkit.getWorld("world"), 0.5, 207, 1199.5);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("galactic.mute") && cmd.getName().equalsIgnoreCase("mute")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/mute <jugador> <tiempo>");
                return true;
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null){
                    sender.sendMessage("§7El jugador no está en linea!");
                } else {
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido muteado");
                    mute.add(target.getUniqueId());
                    return true;
                }
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null){
                    sender.sendMessage("§7El jugador no está en linea!");
                } else {
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido muteado por " + args[1] + " minuto/s");
                    mute.add(target.getUniqueId());
                    int time = Integer.parseInt(args[1]);
                    new BukkitRunnable() {
                        public void run() {
                            mute.remove(target.getUniqueId());
                            target.sendMessage("§7Has sido desmuteado!");
                        }
                    }.runTaskLater(plugin, time * 1200);
                    return true;
                }
            }
        } else if (sender.hasPermission("galactic.unmute") && cmd.getName().equalsIgnoreCase("unmute")){
            if (args.length == 0){
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/unmute <jugador>");
                return true;
            } else if (args.length == 1){
                Player target = Bukkit.getPlayerExact(args[0]);
                if (!mute.contains(target.getUniqueId())){
                    sender.sendMessage(ChatColor.YELLOW + target.getName() + " §7no esta mute" );
                } else {
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido desmuteado");
                    mute.remove(target.getUniqueId());
                    target.sendMessage("§7Has sido desmuteado!");
                }
                return true;
            }
        } else if (sender.hasPermission("galactic.muteall") && cmd.getName().equalsIgnoreCase("muteall")){
            if (!MuteAll){
                MuteAll = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El chat ha sido muteado por §e" + sender.getName());
            } else {
                MuteAll = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El chat ha sido desmuteado por §e" + sender.getName());
            }
        } else if (sender.hasPermission("galactic.staffconfig") && cmd.getName().equalsIgnoreCase("StaffConfig")) {
            Comandos.StaffMenu((Player) sender);
        } else if (cmd.getName().equalsIgnoreCase("broadcast") && sender.hasPermission("galactic.broadcast") || cmd.getName().equalsIgnoreCase("bc") && sender.hasPermission("galactic.broadcast")){
            StringBuilder message = new StringBuilder("");
            for (String part : args) {
                if (!message.toString().equals(""))
                    message.append(" ");
                message.append(part);
            }
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "§7[§9Galactic§7] ➛ §f" + message.toString()));
        } else if (sender.hasPermission("galactic.jail") && cmd.getName().equalsIgnoreCase("jail")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/jail <jugador> <tiempo> <razón>");
                return true;
            } else if (args.length == 3) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage("§7El jugador no está en linea!");
                } else {
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido encarcelado por " + args[1] + " minuto/s." + " Razón: §d" + args[2]);
                    jail.add(target.getUniqueId());
                    mute.add(target.getUniqueId());
                    target.teleport(jailzone);
                    int time = Integer.parseInt(args[1]);
                    new BukkitRunnable() {
                        public void run() {
                            if (!jail.contains(target.getUniqueId())){
                                cancel();
                            }
                            jail.remove(target.getUniqueId());
                            mute.remove(target.getUniqueId());
                            target.sendMessage("§7Has sido desencarcelado y desmuteado!");
                            if (Teams.blueteam.contains(target)) {
                                target.teleport(Towers.bluespawn);
                            }
                            if (Teams.redteam.contains(target)) {
                                target.teleport(Towers.redspawn);
                            }
                        }
                    }.runTaskLater(plugin, time * 1200);
                    return true;
                }
            } else {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/jail <jugador> <tiempo> <razón>");
                return true;
            }
        } else if (sender.hasPermission("galactic.unjail") && cmd.getName().equalsIgnoreCase("unjail")){
            if (args.length == 1){
                Player target = Bukkit.getPlayerExact(args[0]);
                if (!jail.contains(target.getUniqueId())){
                    sender.sendMessage(ChatColor.YELLOW + target.getName() + " §7no está encarcelado" );
                } else {
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido desencarcelado");
                    mute.remove(target.getUniqueId());
                    jail.remove(target.getUniqueId());
                    target.sendMessage("§7Has sido desencarcelado y desmuteado!");
                }
                return true;
            } else {
                sender.sendMessage("§7Argumentos incorrectos!");
                sender.sendMessage("§7/unjail <jugador>");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (mute.contains(player.getUniqueId())) {
            if (Teams.globalchat.contains(player)) {
                event.setCancelled(true);
                player.sendMessage("§7Estas mute!");
            }
        }
        if (MuteAll){
            if (!Teams.globalchat.contains(player)) {
                event.setCancelled(true);
                player.sendMessage("§7El chat esta mute!");
            }
        }
    }

    @EventHandler
    public void onBreak (BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location j = new Location(player.getWorld(), -3, 0, 1196);
        Location j2 = new Location(player.getWorld(), 3, 213, 1202);
        int x1 = Math.min(j.getBlockX(), j2.getBlockX());
        int x2 = Math.max(j.getBlockX(), j2.getBlockX());
        int y1 = Math.min(j.getBlockY(), j2.getBlockY());
        int y2 = Math.max(j.getBlockY(), j2.getBlockY());
        int z1 = Math.min(j.getBlockZ(), j2.getBlockZ());
        int z2 = Math.max(j.getBlockZ(), j2.getBlockZ());
        if (block.getX() >= x1 && block.getX() <= x2) {
            if (block.getY() >= y1 && block.getY() <= y2) {
                if (block.getZ() >= z1 && block.getZ() <= z2) {
                    event.setCancelled(true);
                    player.sendMessage("§7No puedes romper bloques en esta zona!");
                }
            }
        }
    }

    @EventHandler
    public void onPlace (BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location j = new Location(player.getWorld(), -3, 0, 1196);
        Location j2 = new Location(player.getWorld(), 3, 213, 1202);
        int x1 = Math.min(j.getBlockX(), j2.getBlockX());
        int x2 = Math.max(j.getBlockX(), j2.getBlockX());
        int y1 = Math.min(j.getBlockY(), j2.getBlockY());
        int y2 = Math.max(j.getBlockY(), j2.getBlockY());
        int z1 = Math.min(j.getBlockZ(), j2.getBlockZ());
        int z2 = Math.max(j.getBlockZ(), j2.getBlockZ());
        if (block.getX() >= x1 && block.getX() <= x2) {
            if (block.getY() >= y1 && block.getY() <= y2) {
                if (block.getZ() >= z1 && block.getZ() <= z2) {
                    event.setCancelled(true);
                    player.sendMessage("§7No puedes poner bloques en esta zona!");
                }
            }
        }
    }
}

