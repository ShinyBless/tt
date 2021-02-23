package me.shinybless.Galactic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class Whitelist implements Listener, CommandExecutor {
    private Main plugin;

    public Whitelist(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin.getCommand("whitelist").setExecutor(this);
    }

    public static boolean Whitelist = true;
    public static boolean WhitelistOwner = false;
    public static ArrayList<String> whitelist = new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("galactic.whitelist") && cmd.getName().equalsIgnoreCase("whitelist")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7Usa /whitelist help para ver con detalle su uso");
            } else {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage("§7[§2Whitelist§7]");
                    sender.sendMessage("§b/Whitelist add/remove §7: Mete o saca a alguien de la whitelist");
                    sender.sendMessage("§b/Whitelist off §7: Desactiva la whitelist");
                    sender.sendMessage("§b/Whitelist on §7: Activa la whitelist");
                    sender.sendMessage("§b/Whitelist online §7: Agrega a los jugadores conectados a la whitelist y la activa");
                    sender.sendMessage("§b/Whitelist owner §7: Activa la whitelist y permite entrar solo al owner (solo el owner puede usar este comando)");
                } else if (args[0].equalsIgnoreCase("add")) {
                    String str = args[1];
                    if (!Main.galacticplayers.contains(str)){
                        sender.sendMessage("§7El jugador no existe!");
                    } else {
                        whitelist.add(str);
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ §e " + str + " §7ha sido añadido a la §2Whitelist");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (!whitelist.contains(target.getName())){
                        sender.sendMessage("§7Ese jugador no esta en la §aWhitelist!");
                    } else {
                        whitelist.remove(target.getName());
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ §e " + target.getName() + " §7ha sido removido de la §aWhitelist");
                    }
                } else if (args[0].equalsIgnoreCase("off")) {
                    if (Whitelist) {
                        Whitelist = false;
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ La §aWhitelist §7ha sido deshabilitada");
                    } else {
                        sender.sendMessage("§7La §aWhitelist §7ya está deshabilitada");
                        sender.sendMessage("§7Usa §b/Whitelist on §7para habilitarla");
                    }
                } else if (args[0].equalsIgnoreCase("on")){
                    if (!Whitelist) {
                        Whitelist = true;
                        WhitelistOwner = false;
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ La §aWhitelist §7ha sido habilitada");
                    } else {
                        sender.sendMessage("§7La §aWhitelist §7ya está habilitada");
                        sender.sendMessage("§7Usa §b/Whitelist off §7para deshabilitarla");
                    }
                } else if (args[0].equalsIgnoreCase("online")) {
                    Whitelist = true;
                    WhitelistOwner = false;
                    for (Player p : Bukkit.getOnlinePlayers()){
                        whitelist.add(p.getName());
                    }
                    Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ Todos los jugadores conectados han sido añadidos a la §aWhitelist");
                    Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ La §aWhitelist §7ha sido habilitada");
                } else if (args[0].equalsIgnoreCase("owner") && sender.hasPermission("galactic.whitelist.owner")) {
                    if (!WhitelistOwner) {
                        WhitelistOwner = true;
                        Whitelist = false;
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ La §aWhitelist §7ahora se encuentra en modo §bOwner");
                    } else {
                        WhitelistOwner = false;
                        Whitelist = true;
                        Bukkit.broadcastMessage("§7[§2Whitelist§7]➛ La §aWhitelist §7ahora se encuentra en modo §7Normal");
                    }
                }
            }
        }
        return false;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (Whitelist){
            if (!player.hasPermission("galactic.whitelist")){
                if (!whitelist.contains(player.getName())) {
                    player.kickPlayer("§cNo estás en la Whitelist!");
                    event.setJoinMessage("");
                }
            }
        }
        if (WhitelistOwner){
            if (!player.hasPermission("galactic.whitelist.owner")){
                player.kickPlayer("§cNo estás en la Whitelist!");
                event.setJoinMessage("");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onQuit (PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (Whitelist){
            if (!player.hasPermission("galactic.whitelist")){
                if (!whitelist.contains(player.getName())) {
                    event.setQuitMessage("");
                }
            }
        }
        if (WhitelistOwner){
            if (!player.hasPermission("galactic.whitelist.owner")){
                event.setQuitMessage("");
            }
        }
    }
}
