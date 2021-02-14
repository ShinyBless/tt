package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        plugin.getCommand("staffconfig").setExecutor(this);
        plugin.getCommand("config").setExecutor(this);
        plugin.getCommand("muteall").setExecutor(this);
        plugin.getCommand("captains").setExecutor(this);
        plugin.getCommand("broadcast").setExecutor(this);
    }

    ArrayList<UUID> mute = new ArrayList<>();

    public static boolean RedCaptain = true;
    public static boolean BlueCaptain = false;

    public static boolean MuteAll = false;

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
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El jugador " + ChatColor.YELLOW + target.getName() + " §7ha sido muteado por " + args[1] + " minutos");
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
            StaffMenu((Player) sender);
        } else if (sender.hasPermission("galactic.captains") && cmd.getName().equalsIgnoreCase("captains")){
            if (args.length == 0){
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7Elegir captains: /captains add/remove blue/red <jugador>!");
                sender.sendMessage("§7Empezar los captains: /captains start!");
            } else if (args.length == 1){
                if (args[0].equalsIgnoreCase("start")){
                    if (!Teams.redcaptain.isEmpty()){
                        if (!Teams.bluecaptain.isEmpty()){
                            Bukkit.broadcastMessage("§7[§6Captains§7]➛ Turno de §e" + Teams.redcaptain.get(0) + " §7 para elegir!");
                            Teams.redcaptain.get(0).sendMessage("§7Elige un jugador usando /choose <jugador> ");
                        } else {
                            sender.sendMessage("§7No se puede empezar al no haber un capitan del equipo Azul");
                        }
                    } else {
                        sender.sendMessage("§7No se puede empezar al no haber un capitan del equipo Rojo");
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("config")) {
            sender.sendMessage("§7[§9Galactic§7]");
            sender.sendMessage("§1---------------------");
            sender.sendMessage("§7[§bEspecificaciones§7]↴");
            if (Towers) {
                sender.sendMessage("§6➛Modalidad: §7Towers");
            } else if (Walls){
                sender.sendMessage("§6➛Modalidad: §7Walls");
            } else if (Bingo){
                sender.sendMessage("§6➛Modalidad: §7Bingo");
            }
            if (Captains){
                sender.sendMessage("§6➛Teams: §7Captains");
            } else if (InCaptains){
                sender.sendMessage("§6➛Teams: §7InCaptains");
            } else if (SlaveMarket){
                sender.sendMessage("§6➛Teams: §7SlaveMarket");
            } else if (Rigged) {
                sender.sendMessage("§6➛Teams: §7Rigged");
            } else if (Random) {
                sender.sendMessage("§6➛Teams: §7Random");
            } else if (Chosen){
                sender.sendMessage("§6➛Teams: §7Chosen");
            }
            if (Comandos.SkyOff){
                sender.sendMessage("§6➛Sky: §7OFF");
            } else {
                sender.sendMessage("§6➛Sky: §7ON");
            }
            if (Comandos.UnderOff) {
                sender.sendMessage("§6➛Under: §7OFF");
            } else {
                sender.sendMessage("§6➛Under: §7ON");
            }
            if (EnchantsON){
                sender.sendMessage("§6➛Enchants: §7ON");
            } else if (EnchantsNerf){
                sender.sendMessage("§6➛Enchants: §7Nerf");
            } else if (EnchantsOFF){
                sender.sendMessage("§6➛Enchants: §7OFF");
            }
            sender.sendMessage("§1---------------------");
            sender.sendMessage("§7[§bScenarios§7]↴");
            if (Comandos.HasteyBoys) {
                sender.sendMessage("§6➛HasteyBoys: §7Tus herramientas se encantan con eficiencia 3 y durabilidad 1.");
            }
            if (Comandos.CutClean) {
                sender.sendMessage("§6➛CutClean: §7La comida y los minerales se cocinan automaticamente al dropear.");
            }
            if (Comandos.NoFall) {
                sender.sendMessage("§6➛NoFall: §7No hay daño de caida.");
            }
            if (Comandos.Switcheroo) {
                sender.sendMessage("§6➛Switcheroo: §7Intercambias posiciones con un jugador al pegarle una flecha.");
            }
            if (Comandos.SuperHeroes) {
                sender.sendMessage("§6➛SuperHeroes: §7Al empezar te da un efecto permanente que puede ser Fuerza, Doble Vida, Speed 2, Haste 2, Jump Boost 4, o Resistencia 2.");
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("broadcast") && sender.hasPermission("galactic.broadcast")){
            StringBuilder message = new StringBuilder("");
            for (String part : args) {
                if (!message.toString().equals(""))
                    message.append(" ");
                message.append(part);
            }
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "§7[§9Galactic§7] ➛ §f" + message.toString()));
        }
        return false;
    }

    public Inventory staffmenu = Bukkit.createInventory(null, 27, "StaffMenu");
    public Inventory staffscenarios = Bukkit.createInventory(null, 27, "StaffScenarios");
    public Inventory staffconfig = Bukkit.createInventory(null, 27, "StaffConfig");
    public Inventory stafftowers = Bukkit.createInventory(null, 27, "StaffTowers");

    public static boolean Towers = true;
    public static boolean Walls = false;
    public static boolean Bingo = false;

    public static boolean Captains = true;
    public static boolean InCaptains = false;
    public static boolean Random = false;
    public static boolean Rigged = false;
    public static boolean SlaveMarket = false;
    public static boolean Chosen = false;

    public static boolean EnchantsON = true;
    public static boolean EnchantsNerf = false;
    public static boolean EnchantsOFF = false;

    public void StaffMenu(Player p){
        staffmenu.setItem(11, MenuItems.staffmenuitem1());
        staffmenu.setItem(15, MenuItems.staffmenuitem2());
        p.openInventory(staffmenu);
    }

    public void StaffScen(Player p){
        staffscenarios.setItem(0, MenuItems.staffscenitem1());
        staffscenarios.setItem(1, MenuItems.staffscenitem2());
        staffscenarios.setItem(2, MenuItems.staffscenitem3());
        staffscenarios.setItem(3, MenuItems.staffscenitem4());
        staffscenarios.setItem(4, MenuItems.staffscenitem5());
        p.openInventory(staffscenarios);
    }

    public void StaffConfig(Player p){
        staffconfig.setItem(11, MenuItems.staffconfigitem1());
        staffconfig.setItem(13, MenuItems.staffconfigitem2());
        staffconfig.setItem(15, MenuItems.staffconfigitem3());
        p.openInventory(staffconfig);
    }

    public void StaffTowers(Player p){
        stafftowers.setItem(12, MenuItems.stafftowersitem1());
        stafftowers.setItem(13, MenuItems.stafftowersitem2());
        stafftowers.setItem(14, MenuItems.stafftowersitem3());
        p.openInventory(stafftowers);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (event.getView().getTitle().equalsIgnoreCase("StaffMenu")) {
            if (clicked.getType() == Material.DIAMOND_PICKAXE) {
                event.setCancelled(true);
                StaffScen(player);
            } else if (clicked.getType() == Material.NETHER_STAR) {
                event.setCancelled(true);
                StaffConfig(player);
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("StaffScenarios")) {
            if (clicked.getType() == Material.IRON_PICKAXE) {
                if (!Comandos.HasteyBoys) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §bHasteyBoys §2ON");
                    StaffScen(player);
                    Comandos.HasteyBoys = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §bHasteyBoys §4OFF");
                    StaffScen(player);
                    Comandos.HasteyBoys = false;
                }
            } else if (clicked.getType() == Material.IRON_INGOT) {
                if (!Comandos.CutClean) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §2ON");
                    StaffScen(player);
                    Comandos.CutClean = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §4OFF");
                    StaffScen(player);
                    Comandos.CutClean = false;
                }
            } else if (clicked.getType() == Material.DIAMOND_BOOTS) {
                if (!Comandos.NoFall) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §2ON");
                    StaffScen(player);
                    Comandos.NoFall = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §4OFF");
                    StaffScen(player);
                    Comandos.NoFall = false;
                }
            } else if (clicked.getType() == Material.ARROW) {
                if (!Comandos.Switcheroo) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §aSwitcheroo §2ON");
                    StaffScen(player);
                    Comandos.Switcheroo = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §aSwitcheroo §4OFF");
                    StaffScen(player);
                    Comandos.Switcheroo = false;
                }
            } else if (clicked.getType() == Material.BEACON) {
                if (!Comandos.SuperHeroes) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6SuperHeroes §2ON");
                    StaffScen(player);
                    Comandos.SuperHeroes = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6SuperHeroes §4OFF");
                    StaffScen(player);
                    Comandos.SuperHeroes = false;
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("StaffConfig")) {
            if (clicked.getType() == Material.BOW) {
                if (Towers) {
                    event.setCancelled(true);
                    player.sendMessage("§7Modalidad actual: §6Walls");
                    Towers = false;
                    Walls = true;
                    StaffConfig(player);
                } else if (Walls) {
                    event.setCancelled(true);
                    player.sendMessage("§7Modalidad actual: §6Bingo");
                    Walls = false;
                    Bingo = true;
                    StaffConfig(player);
                } else if (Bingo) {
                    event.setCancelled(true);
                    player.sendMessage("§7Modalidad actual: §6Towers");
                    Bingo = false;
                    Towers = true;
                    StaffConfig(player);
                } else {
                    event.setCancelled(true);
                }
            } else if (clicked.getType() == Material.REDSTONE_COMPARATOR) {
                if (Towers) {
                    event.setCancelled(true);
                    StaffTowers(player);
                } else if (Walls) {
                    event.setCancelled(true);
                } else if (Bingo) {
                    event.setCancelled(true);
                }
            } else if (clicked.getType() == Material.TOTEM) {
                if (Captains) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6InCaptains");
                    Captains = false;
                    InCaptains = true;
                    StaffConfig(player);
                } else if (InCaptains) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6SlaveMarket");
                    InCaptains = false;
                    SlaveMarket = true;
                    StaffConfig(player);
                } else
                if (SlaveMarket) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6Rigged");
                    SlaveMarket = false;
                    Rigged = true;
                    StaffConfig(player);
                } else
                if (Rigged) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6Random");
                    Rigged = false;
                    Random = true;
                    StaffConfig(player);
                } else
                if (Random) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6Chosen");
                    Random = false;
                    Chosen = true;
                    StaffConfig(player);
                } else
                if (Chosen) {
                    event.setCancelled(true);
                    player.sendMessage("§7Teams actuales: §6Captains");
                    Chosen = false;
                    Captains = true;
                    StaffConfig(player);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("StaffTowers")) {
            if (clicked.getType() == Material.FEATHER) {
                if (Comandos.SkyOff) {
                    event.setCancelled(true);
                    player.sendMessage("§7Activado el Sky");
                    Comandos.SkyOff = false;
                    StaffTowers(player);
                } else {
                    event.setCancelled(true);
                    player.sendMessage("§7Desactivado el Sky");
                    Comandos.SkyOff = true;
                    StaffTowers(player);
                }
            } else if (clicked.getType() == Material.ENCHANTMENT_TABLE) {
                if (EnchantsON) {
                    event.setCancelled(true);
                    player.sendMessage("§7Enchants Nerf");
                    EnchantsON = false;
                    EnchantsNerf = true;
                    StaffTowers(player);
                } else if (EnchantsNerf) {
                    event.setCancelled(true);
                    player.sendMessage("§7Enchants OFF");
                    EnchantsNerf = false;
                    EnchantsOFF = true;
                    StaffTowers(player);
                } else if (EnchantsOFF) {
                    event.setCancelled(true);
                    player.sendMessage("§7Enchants ON");
                    EnchantsOFF = false;
                    EnchantsON = true;
                    StaffTowers(player);
                } else {
                    event.setCancelled(true);
                }
            } else if (clicked.getType() == Material.NETHER_BRICK) {
                if (Comandos.UnderOff) {
                    event.setCancelled(true);
                    player.sendMessage("§7Activado el Under");
                    Comandos.UnderOff = false;
                    StaffTowers(player);
                } else {
                    event.setCancelled(true);
                    player.sendMessage("§7Desactivado el Under");
                    Comandos.UnderOff = true;
                    StaffTowers(player);
                }
            }
        }
    }


    @EventHandler
    public void onMessage(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (mute.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("§7Estas mute!");
        }
        if (MuteAll){
            event.setCancelled(true);
            player.sendMessage("§7El chat esta mute!");
        }
    }
}

