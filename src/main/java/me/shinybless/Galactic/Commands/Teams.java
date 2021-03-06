package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.Eventos;
import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Scenarios;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Teams implements Listener, CommandExecutor {
    private Main plugin;

    public Teams(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("start").setExecutor(this);
        plugin.getCommand("team").setExecutor(this);
        plugin.getCommand("teamchat").setExecutor(this);
        plugin.getCommand("staffchat").setExecutor(this);
        plugin.getCommand("captains").setExecutor(this);
        plugin.getCommand("choose").setExecutor(this);
    }

    public static ArrayList<Player> redteam = new ArrayList<>();
    public static ArrayList<Player> blueteam = new ArrayList<>();
    public static ArrayList<Player> greenteam = new ArrayList<>();
    public static ArrayList<Player> yellowteam = new ArrayList<>();

    public static ArrayList<Player> redcaptain = new ArrayList<>();
    public static ArrayList<Player> bluecaptain = new ArrayList<>();

    public static ArrayList<Player> globalchat = new ArrayList<>();
    public static ArrayList<Player> teamchat = new ArrayList<>();
    public static ArrayList<Player> staffchat = new ArrayList<>();

    Location spawn = new Location(Bukkit.getWorld("world"), 0.4, 64, 1024.4);
    Location redcage = new Location(Bukkit.getWorld("world"), 12.5, 65, 1024.5);
    Location bluecage = new Location(Bukkit.getWorld("world"), -11.5, 65, 1024.5);

    public static boolean CaptainsStart = false;
    public static boolean RedCaptain = true;
    public static boolean BlueCaptain = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();
        Team red = board.getTeam("Red");
        Team blue = board.getTeam("Blue");
        if (sender.hasPermission("Galactic.team") && cmd.getName().equalsIgnoreCase("team")) {
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
                            if (Comandos.Bingo) {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join red " + target.getName());
                            } else {
                                red.addPlayer(target);
                            }
                            if (Comandos.Towers) {
                                if (Towers.TowersStart) {
                                    Towers.GameStart(target);
                                    if (Comandos.SuperHeroes) {
                                        Towers.StartSuperHeroes(target);
                                    }
                                } else {
                                    target.teleport(redcage);
                                }
                            }
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
                            red.removePlayer(target);
                            target.teleport(spawn);
                            Scenarios.resistance.remove(target.getName());
                            Scenarios.health.remove(target.getName());
                            Scenarios.strength.remove(target.getName());
                            Scenarios.speed.remove(target.getName());
                            Scenarios.haste.remove(target.getName());
                            Scenarios.jump.remove(target.getName());
                            return true;
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!");
                        sender.sendMessage("§7/team red/blue join/leave <jugador>");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("blue")) {
                    if (args[1].equalsIgnoreCase("join")) {
                        if (blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §9Azul!");
                        } else if (redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §cRojo!");
                        } else {
                            blueteam.add(target);
                            target.sendMessage("§7Ahora estás en el equipo §9Azul");
                            sender.sendMessage("§e" + target.getName() + " §7ahora forma parte del equipo §9Azul");
                            if (Comandos.Bingo) {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join blue " + target.getName());
                            } else {
                                blue.addPlayer(target);
                            }
                            if (Comandos.Towers) {
                                if (Towers.TowersStart) {
                                    if (Comandos.SuperHeroes) {
                                        Towers.StartSuperHeroes(target);
                                    }
                                    Towers.GameStart(target);
                                } else {
                                    target.teleport(bluecage);
                                }
                            }
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("leave")) {
                        if (!blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 no esta en el equipo §9Azul!");
                        } else {
                            blueteam.remove(target);
                            target.sendMessage("§7Ya no estás en el equipo §9Azul");
                            sender.sendMessage("§e" + target.getName() + " §7ya no forma parte del equipo §9Azul");
                            blue.removePlayer(target);
                            target.teleport(spawn);
                            Scenarios.resistance.remove(target.getName());
                            Scenarios.health.remove(target.getName());
                            Scenarios.strength.remove(target.getName());
                            Scenarios.speed.remove(target.getName());
                            Scenarios.haste.remove(target.getName());
                            Scenarios.jump.remove(target.getName());
                            return true;
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!");
                        sender.sendMessage("§7/team red/blue join/leave <jugador>");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("green")) {
                    if (args[1].equalsIgnoreCase("join")) {
                        if (blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §9Azul!");
                        } else if (redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §cRojo!");
                        } else if (greenteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §aVerde!");
                        } else if (yellowteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §eAmarillo!");
                        } else {
                            greenteam.add(target);
                            target.sendMessage("§7Ahora estás en el equipo §aVerde");
                            sender.sendMessage("§e" + target.getName() + " §7ahora forma parte del equipo §aVerde");
                            if (Comandos.Bingo) {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join green " + target.getName());
                            } else {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join Green " + target.getName());
                            }
                            if (Comandos.Towers) {
                                if (Towers.TowersStart) {
                                    if (Comandos.SuperHeroes) {
                                        Towers.StartSuperHeroes(target);
                                    }
                                    Towers.GameStart(target);
                                } else {
                                    target.teleport(bluecage);
                                }
                            }
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("leave")) {
                        if (!greenteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 no esta en el equipo §aVerde!");
                        } else {
                            greenteam.remove(target);
                            target.sendMessage("§7Ya no estás en el equipo §aVerde");
                            sender.sendMessage("§e" + target.getName() + " §7ya no forma parte del equipo §aVerde");
                            Bukkit.getServer().dispatchCommand(sender, "minecraft:team leave " + target.getName());
                            target.teleport(spawn);
                            Scenarios.resistance.remove(target.getName());
                            Scenarios.health.remove(target.getName());
                            Scenarios.strength.remove(target.getName());
                            Scenarios.speed.remove(target.getName());
                            Scenarios.haste.remove(target.getName());
                            Scenarios.jump.remove(target.getName());
                            return true;
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!");
                        sender.sendMessage("§7/team red/blue join/leave <jugador>");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("yellow")) {
                    if (args[1].equalsIgnoreCase("join")) {
                        if (blueteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §9Azul!");
                        } else if (redteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §cRojo!");
                        } else if (greenteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §aVerde!");
                        } else if (yellowteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 ya forma parte del equipo §eAmarillo!");
                        } else {
                            yellowteam.add(target);
                            target.sendMessage("§7Ahora estás en el equipo §eAmarillo");
                            sender.sendMessage("§e" + target.getName() + " §7ahora forma parte del equipo §eAmarillo");
                            if (Comandos.Bingo) {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join yellow " + target.getName());
                            } else {
                                Bukkit.getServer().dispatchCommand(sender, "minecraft:team join Yellow " + target.getName());
                            }
                            if (Comandos.Towers) {
                                if (Towers.TowersStart) {
                                    if (Comandos.SuperHeroes) {
                                        Towers.StartSuperHeroes(target);
                                    }
                                    Towers.GameStart(target);
                                } else {
                                    target.teleport(bluecage);
                                }
                            }
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("leave")) {
                        if (!yellowteam.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + "§7 no esta en el equipo §eAmarillo!");
                        } else {
                            yellowteam.remove(target);
                            target.sendMessage("§7Ya no estás en el equipo §eAmarillo");
                            sender.sendMessage("§e" + target.getName() + " §7ya no forma parte del equipo §eAmarillo");
                            Bukkit.getServer().dispatchCommand(sender, "scoreboard teams leave " + target.getName());
                            target.teleport(spawn);
                            Scenarios.resistance.remove(target.getName());
                            Scenarios.health.remove(target.getName());
                            Scenarios.strength.remove(target.getName());
                            Scenarios.speed.remove(target.getName());
                            Scenarios.haste.remove(target.getName());
                            Scenarios.jump.remove(target.getName());
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
        } else if (sender.hasPermission("galactic.captains") && cmd.getName().equalsIgnoreCase("captains")){
            if (args.length < 2){
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7Elegir captains: /captains add/remove blue/red <jugador>!");
                sender.sendMessage("§7Empezar los captains: /captains start!");
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!Teams.redcaptain.isEmpty()) {
                        if (!Teams.bluecaptain.isEmpty()) {
                            CaptainsStart = true;
                            Bukkit.broadcastMessage("§7[§6Captains§7]➛ Turno de §e" + Teams.redcaptain.get(0) + " §7 para elegir!");
                            Teams.redcaptain.get(0).sendMessage("§7Elige un jugador usando /choose <jugador> ");
                        } else {
                            sender.sendMessage("§7No se puede empezar al no haber un Captain del equipo Azul");
                        }
                    } else {
                        sender.sendMessage("§7No se puede empezar al no haber un Captain del equipo Rojo");
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (CaptainsStart) {
                        CaptainsStart = false;
                        Bukkit.broadcastMessage("§7[§6Captains§7]➛ Ha finalizado la selección de Captains!");
                    } else {
                        sender.sendMessage("§7La selección de Captains no ha comenzado!");
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    Player target = Bukkit.getPlayerExact(args[2]);
                    if (args[1].equalsIgnoreCase("blue")) {
                        if (!bluecaptain.isEmpty()) {
                            sender.sendMessage("§7El equipo §9Azul §7ya tiene a §e" + bluecaptain.get(0) + " §7como Captain!");
                        } else {
                            bluecaptain.add(target);
                            Bukkit.getServer().dispatchCommand(sender, "scoreboard teams join Blue " + target.getName());
                            sender.sendMessage("§e" + target.getName() + " §7ahora es el Captain del equipo §9Azul§7!");
                            target.sendMessage("§7Ahora eres el Captain del equipo §9Azul");
                        }
                    } else if (args[1].equalsIgnoreCase("red")) {
                        if (!redcaptain.isEmpty()) {
                            sender.sendMessage("§7El equipo §cRojo §7ya tiene a §e" + redcaptain.get(0) + " §7como Captain!");
                        } else {
                            redcaptain.add(target);
                            Bukkit.getServer().dispatchCommand(sender, "scoreboard teams join Red " + target.getName());
                            sender.sendMessage("§e" + target.getName() + " §7ahora es el Captain del equipo §cRojo§7!");
                            target.sendMessage("§7Ahora eres el Captain del equipo §cRojo");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("remove")){
                    Player target = Bukkit.getPlayerExact(args[2]);
                    if (args[1].equalsIgnoreCase("blue")) {
                        if (!bluecaptain.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + " §7no es el Captain del equipo §9Azul §7!");
                        } else {
                            bluecaptain.remove(target);
                            Bukkit.getServer().dispatchCommand(sender, "scoreboard teams leave " + target.getName());
                            sender.sendMessage("§e" + target.getName() + " §7ya no es el Captain del equipo §9Azul§7!");
                            target.sendMessage("§7Dejaste de ser el Captain del equipo §9Azul");
                        }
                    } else if (args[1].equalsIgnoreCase("red")) {
                        if (!redcaptain.contains(target)) {
                            sender.sendMessage("§e" + target.getName() + " §7no es el Captain del equipo §9Azul §7!");
                        } else {
                            redcaptain.add(target);
                            Bukkit.getServer().dispatchCommand(sender, "scoreboard teams leave " + target.getName());
                            sender.sendMessage("§e" + target.getName() + " §7ya no es el Captain del equipo §cRojo§7!");
                            target.sendMessage("§7Dejaste de ser el Captain del equipo §cRojo");
                        }
                    } else {
                        sender.sendMessage("§7Argumento incorrecto!" + "§7Empezar o frenar los captains: /captains start/stop" + "§7Elegir captains: /captains add/remove blue/red <jugador>");
                    }
                } else {
                    sender.sendMessage("§7Argumento incorrecto!" + "§7Empezar o frenar los captains: /captains start/stop" + "§7Elegir captains: /captains add/remove blue/red <jugador>");
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("captains.choose")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/choose <jugador>!");
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (CaptainsStart) {
                    if (RedCaptain) {
                        if (redcaptain.contains(sender)) {
                            if (Bukkit.getOnlinePlayers().contains(target) && target != sender && !blueteam.contains(target) && !redteam.contains(target)) {
                                redteam.add(target);
                                Bukkit.getServer().dispatchCommand(sender, "scoreboard teams join Red " + target.getName());
                                target.sendMessage("§7Ahora estas en el equipo §cRojo");
                                RedCaptain = false;
                                BlueCaptain = true;
                                Bukkit.broadcastMessage("§7[§6Captains§7]➛ §c" + redcaptain.get(0) + " §7ha elegido a §e " + target.getName());
                                Bukkit.broadcastMessage("§7[§6Captains§7]➛ Turno de §9" + bluecaptain.get(0) + " §7de elegir");
                                bluecaptain.get(0).sendMessage("§7Elige un jugador usando /choose <jugador>");
                            } else {
                                sender.sendMessage("§7Solo puedes decir el nombre de un jugador que no este en un equipo!");
                            }
                        } else {
                            sender.sendMessage("§7Solo los captains pueden usar este comando!");
                        }
                    } else if (BlueCaptain) {
                        if (bluecaptain.contains(sender)) {
                            if (Bukkit.getOnlinePlayers().contains(target) && target != sender && !redteam.contains(target) && !blueteam.contains(target)) {
                                blueteam.add(target);
                                Bukkit.getServer().dispatchCommand(sender, "scoreboard teams join Blue " + target.getName());
                                target.sendMessage("§7Ahora estas en el equipo §9Azul");
                                BlueCaptain = false;
                                RedCaptain = true;
                                Bukkit.broadcastMessage("§7[§6Captains§7]➛ §9" + bluecaptain.get(0) + " §7ha elegido a §e " + target.getName());
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
        } else if (cmd.getName().equalsIgnoreCase("teamchat")) {
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
        } else if (sender.hasPermission("galactic.staffchat") && cmd.getName().equalsIgnoreCase("staffchat")){
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
        if (globalchat.contains(player)){
            for (Player p : Bukkit.getOnlinePlayers()){
                if (message.contains(p.getName())){
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                }
            }
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
                } else if (greenteam.contains(player)) {
                    event.setFormat(teamprefix + "§a" + player.getName() + " §8➩ §b" + message);
                    event.getRecipients().clear();
                    event.getRecipients().addAll(greenteam);
                } else if (yellowteam.contains(player)) {
                    event.setFormat(teamprefix + "§e" + player.getName() + " §8➩ §b" + message);
                    event.getRecipients().clear();
                    event.getRecipients().addAll(yellowteam);
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
