package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.Eventos;
import me.shinybless.Galactic.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class Teams implements Listener, CommandExecutor {
    private Main plugin;

    public Teams(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("start").setExecutor(this);
        plugin.getCommand("team").setExecutor(this);
        plugin.getCommand("teamchat").setExecutor(this);
        plugin.getCommand("tc").setExecutor(this);
        plugin.getCommand("staffchat").setExecutor(this);
        plugin.getCommand("sc").setExecutor(this);
        plugin.getCommand("choose").setExecutor(this);
    }

    public static ArrayList<Player> redteam = new ArrayList<>();
    public static ArrayList<Player> blueteam = new ArrayList<>();

    public static ArrayList<Player> redcaptain = new ArrayList<>();
    public static ArrayList<Player> bluecaptain = new ArrayList<>();

    public static ArrayList<Player> globalchat = new ArrayList<>();
    public static ArrayList<Player> teamchat = new ArrayList<>();
    public static ArrayList<Player> staffchat = new ArrayList<>();

    public static boolean CaptainsStart = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("Galactic.towers") && cmd.getName().equalsIgnoreCase("start")) {

        } else if (sender.hasPermission("Galactic.team") && cmd.getName().equalsIgnoreCase("team")) {
            if (args.length < 2) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/team red/blue join/leave <jugador>");
                return true;
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (target == null) {
                    sender.sendMessage("§7El jugador no está conectado o no existe!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("red")) {
                    if (args[1].equalsIgnoreCase("join")) {
                        if (blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §9Azul!");
                        } else if (redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §cRojo!");
                        } else {
                            redteam.add(target);
                            target.sendMessage("§7Ahora estás en el equipo §cRojo");
                            sender.sendMessage("§e" + target.getName() + " §7ahora forma parte del equipo §cRojo");
                            target.setPlayerListName("§c" + target.getName());
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("leave")) {
                        if (!redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §cRojo!");
                        } else if (blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §9Azul!");
                        } else {
                            redteam.remove(target);
                            target.sendMessage("§7Ya no estás en el equipo §cRojo");
                            sender.sendMessage("§e" + target.getName() + " §7ya no forma parte del equipo §cRojo");
                            target.setPlayerListName("§f" + target.getName());
                            return true;
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!");
                        sender.sendMessage("§7/team red/blue join/leave <jugador>");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("blue")) {
                    if (args[1].equalsIgnoreCase("join")) {
                        if (redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte de un equipo!");
                        } else {
                            blueteam.add(target);
                            target.sendMessage("§7Ahora estás en el equipo §9Azul");
                            sender.sendMessage("§e" + target.getName() + " §7ahora forma parte del equipo §9Azul");
                            target.setPlayerListName("§9" + target.getName());
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("leave")) {
                        if (!blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 no esta en el equipo §9Azul!");
                        } else {
                            blueteam.remove(target);
                            target.sendMessage("§7Ya no estás en el equipo §9Azul");
                            sender.sendMessage("§e" + target.getName() + " §7ya no forma parte del equipo §9Azul");
                            target.setPlayerListName("§f" + target.getName());

                            return true;
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!");
                        sender.sendMessage("§7/team red/blue join/leave <jugador>");
                        return true;
                    }
                } else {
                    sender.sendMessage("§7Argumento incorrecto!");
                    sender.sendMessage("§7/team red/blue join/leave <jugador>");
                    return true;
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("captains.choose")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/choose <jugador>!");
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (CaptainsStart) {
                    if (Staff.RedCaptain) {
                        if (redcaptain.contains(sender)) {
                            if (Bukkit.getOnlinePlayers().contains(target) && target != sender && !blueteam.contains(target) && !redteam.contains(target)) {
                                redteam.add(target);
                                target.sendMessage("§7Ahora estas en el equipo §cRojo");
                                Staff.RedCaptain = false;
                                Staff.BlueCaptain = true;
                                Bukkit.broadcastMessage("§7[§6Captains§7]➛ Turno de §9" + bluecaptain.get(0) + " §7de elegir");
                                bluecaptain.get(0).sendMessage("§7Elige un jugador usando /choose <jugador>");
                            } else {
                                sender.sendMessage("§7Solo puedes decir el nombre de un jugador que no este en un equipo!");
                            }
                        } else {
                            sender.sendMessage("§7Solo los captains pueden usar este comando!");
                        }
                    } else if (Staff.BlueCaptain) {
                        if (bluecaptain.contains(sender)) {
                            if (Bukkit.getOnlinePlayers().contains(target) && target != sender && !redteam.contains(target) && !blueteam.contains(target)) {
                                blueteam.add(target);
                                target.sendMessage("§7Ahora estas en el equipo §9Azul");
                                Staff.BlueCaptain = false;
                                Staff.RedCaptain = true;
                                Bukkit.broadcastMessage("§7[§6Captains§7]➛ Turno de §c" + redcaptain.get(0) + " §7de elegir");
                                redcaptain.get(0).sendMessage("§7Elige un jugador usando /choose <jugador>");
                            } else {
                                sender.sendMessage("§7Solo puedes decir el nombre de un jugador que no este en un equipo!");
                            }
                        } else {
                            sender.sendMessage("§7Solo los captains pueden usar ese comando!");
                        }
                    } else {
                        sender.sendMessage("§7No es tu turno para elegir!");
                    }
                } else {
                    sender.sendMessage("§7La selección de captains no ha comenzado!");
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("teamchat") || cmd.getName().equalsIgnoreCase("tc")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!teamchat.contains(p)) {
                    teamchat.add(p);
                    p.sendMessage("§7[§9Galactic§7]➛ Ahora tus mensajes se enviaran por el §bChat de Team");
                    if (staffchat.contains(p)){
                        p.sendMessage("§7[§9Galactic§7]➛ Estas en §dStaffChat§7, para hablar en §bChat de Team§7 debes desactivarlo usando /sc");
                    }
                } else {
                    teamchat.remove(p);
                    p.sendMessage("§7[§9Galactic§7]➛ Ahora tus mensajes se enviaran por el §aChat Global");
                }
            }
        } else if (sender.hasPermission("galactic.staffchat") && cmd.getName().equalsIgnoreCase("staffchat") || sender.hasPermission("galactic.staffchat") && cmd.getName().equalsIgnoreCase("sc")){
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!staffchat.contains(p)) {
                    staffchat.add(p);
                    p.sendMessage("§7[§9Galactic§7]➛ Ahora tus mensajes se enviaran por el §dChat de Staff");
                } else {
                    staffchat.remove(p);
                    if (teamchat.contains(p)) {
                        p.sendMessage("§7[§9Galactic§7]➛ Ahora tus mensajes se enviaran por el §bChat de Team");
                    } else {
                        p.sendMessage("§7[§9Galactic§7]➛ Ahora tus mensajes se enviaran por el §aChat Global");
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        String teamprefix = "§7[§bTeamChat§7] ";
        String staffprefix = "§7[§dStaffChat§7] ";
        if (message.contains("&k")) {
            player.sendMessage("§cNo puedes escribir &k!");
            return;
        }
        if (globalchat.contains(player)) {
            if (teamchat.contains(player)) {
                if (staffchat.contains(player)) {
                    event.setFormat(staffprefix + "§6" + player.getName() + " §8➩ §b" + ChatColor.translateAlternateColorCodes('&', message));
                    event.getRecipients().clear();
                    event.getRecipients().addAll(Eventos.staff);
                } else if (blueteam.contains(player)) {
                    event.setFormat(teamprefix + "§9" + player.getName() + " §8➩ §b" + message);
                    event.getRecipients().clear();
                    event.getRecipients().addAll(blueteam);
                } else if (redteam.contains(player)) {
                    event.setFormat(teamprefix + "§c" + player.getName() + " §8➩ §b" + message);
                    event.getRecipients().clear();
                    event.getRecipients().addAll(redteam);
                } else {
                    if (player.hasPermission("galactic.prefix.owner")) {
                        event.setFormat("§bOwner §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.programador")){
                        event.setFormat("§dProgramador §7" +  player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.admin")) {
                        event.setFormat("§1Admin §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.staff")) {
                        event.setFormat("§9Staff §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.host")) {
                        event.setFormat("§cHost §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.aurora")) {
                        event.setFormat("\uD83C\uDF20 §aAurora §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else if (player.hasPermission("galactic.prefix.stella")){
                        event.setFormat("§e⭐ §6Stella §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        event.setFormat("§7" + player.getName() + " §8➩ §f" + message);
                    }
                }
            } else if (staffchat.contains(player)) {
                event.setFormat(staffprefix + "§6" + player.getName() + " §8➩ §b" + message);
                event.getRecipients().clear();
                event.getRecipients().addAll(Eventos.staff);
            } else {
                if (player.hasPermission("galactic.prefix.owner")) {
                    event.setFormat("§bOwner §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                }else if (player.hasPermission("galactic.prefix.programador")){
                    event.setFormat("§dProgramador §7" +  player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else if (player.hasPermission("galactic.prefix.admin")) {
                    event.setFormat("§1Admin §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else if (player.hasPermission("galactic.prefix.staff")) {
                    event.setFormat("§9Staff §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else if (player.hasPermission("galactic.prefix.host")) {
                    event.setFormat("§cHost §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else if (player.hasPermission("galactic.prefix.aurora")) {
                    event.setFormat("§a✦ Aurora §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else if (player.hasPermission("galactic.prefix.stella")){
                    event.setFormat("§e⭐ §6Stella §7" + player.getName() + " §8➩ §f" + ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    event.setFormat("§7" + player.getName() + " §8➩ §f" + message);
                }
            }
        }
    }
}
