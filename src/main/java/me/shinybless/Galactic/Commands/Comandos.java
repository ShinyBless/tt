package me.shinybless.Galactic.Commands;

import me.shinybless.Galactic.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Comandos implements CommandExecutor, Listener {
    private static Main plugin;

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
        plugin.getCommand("givepowers").setExecutor(this);
        plugin.getCommand("config").setExecutor(this);
    }

    public static Inventory staffmenu = Bukkit.createInventory(null, 27, "StaffMenu");
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

    public static boolean HasteyBoys = false;
    public static boolean Switcheroo = false;
    public static boolean CutClean = false;
    public static boolean NoFall = false;
    public static boolean SkyOff = false;
    public static boolean UnderOff = false;
    public static boolean SuperHeroes = false;
    public static boolean Bowless = false;
    public static boolean EnchantsOFF = false;
    public static boolean EnchantsNerf = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (sender.hasPermission("galactic.hasteyboys") && cmd.getName().equalsIgnoreCase("hasteyboys")) {
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
        if (sender.hasPermission("galactic.switcheroo") && cmd.getName().equalsIgnoreCase("switcheroo")) {
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
        if (sender.hasPermission("galactic.cutclean") && cmd.getName().equalsIgnoreCase("cutclean")) {
            if (!CutClean) {
                CutClean = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §2on");
                return true;
            } else if (CutClean) {
                CutClean = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §eCutClean §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.nofall") && cmd.getName().equalsIgnoreCase("nofall")) {
            if (!NoFall) {
                NoFall = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §2on");
                return true;
            } else if (NoFall) {
                NoFall = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fNoFall §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.superheroes") && cmd.getName().equalsIgnoreCase("superheroes")) {
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
        } else if (sender.hasPermission("galactic.skyoff") && cmd.getName().equalsIgnoreCase("skyoff")) {
            if (!SkyOff) {
                SkyOff = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fSkyOff §2on");
                return true;
            } else if (SkyOff) {
                SkyOff = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §fSkyOff §4off");
                return true;
            }
        } else if (sender.hasPermission("galactic.underoff") && cmd.getName().equalsIgnoreCase("underoff")) {
            if (!UnderOff) {
                UnderOff = true;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §cUnderOff §2on");
                return true;
            } else if (UnderOff) {
                UnderOff = false;
                Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §cUnderOff §4off");
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("scens")) {
            Menu.ScenariosMenu((Player) sender);
            return true;
        } else if (sender.hasPermission("galactic.superheroes") && cmd.getName().equalsIgnoreCase("givepowers")){
            for (Player p : Teams.blueteam){
                RunSuperHeroes(p);
            }
            for (Player p : Teams.redteam){
                RunSuperHeroes(p);
            }
            for (Player p : Teams.greenteam){
                RunSuperHeroes(p);
            }
            for (Player p : Teams.yellowteam){
                RunSuperHeroes(p);
            }
        } else if (cmd.getName().equalsIgnoreCase("config")) {
            sender.sendMessage("§7[§9Galactic§7]");
            sender.sendMessage("§1---------------------");
            sender.sendMessage("§7[§bEspecificaciones§7]↴");
            if (Towers) {
                sender.sendMessage("§6➛Modalidad: §7Towers");
            } else if (Walls) {
                sender.sendMessage("§6➛Modalidad: §7Walls");
            } else if (Bingo) {
                sender.sendMessage("§6➛Modalidad: §7Bingo");
            }
            if (Captains) {
                sender.sendMessage("§6➛Teams: §7Captains");
            } else if (InCaptains) {
                sender.sendMessage("§6➛Teams: §7InCaptains");
            } else if (SlaveMarket) {
                sender.sendMessage("§6➛Teams: §7SlaveMarket");
            } else if (Rigged) {
                sender.sendMessage("§6➛Teams: §7Rigged");
            } else if (Random) {
                sender.sendMessage("§6➛Teams: §7Random");
            } else if (Chosen) {
                sender.sendMessage("§6➛Teams: §7Chosen");
            }
            if (Comandos.SkyOff) {
                sender.sendMessage("§6➛Sky: §7OFF");
            } else {
                sender.sendMessage("§6➛Sky: §7ON");
            }
            if (Comandos.UnderOff) {
                sender.sendMessage("§6➛Under: §7OFF");
            } else {
                sender.sendMessage("§6➛Under: §7ON");
            }
            if (EnchantsON) {
                sender.sendMessage("§6➛Enchants: §7ON");
            } else if (EnchantsNerf) {
                sender.sendMessage("§6➛Enchants: §7Nerf");
            } else if (EnchantsOFF) {
                sender.sendMessage("§6➛Enchants: §7OFF");
            }
            sender.sendMessage("§1---------------------");
            sender.sendMessage("§7[§bScenarios§7]↴");
            if (HasteyBoys) {
                sender.sendMessage("§6➛HasteyBoys: §7Tus herramientas se encantan con eficiencia 3 y durabilidad 1.");
            }
            if (CutClean) {
                sender.sendMessage("§6➛CutClean: §7La comida y los minerales se cocinan automaticamente al dropear.");
            }
            if (NoFall) {
                sender.sendMessage("§6➛NoFall: §7No hay daño de caida.");
            }
            if (Switcheroo) {
                sender.sendMessage("§6➛Switcheroo: §7Intercambias posiciones con un jugador al pegarle una flecha.");
            }
            if (SuperHeroes) {
                sender.sendMessage("§6➛SuperHeroes: §7Al empezar te da un efecto permanente que puede ser Fuerza, Doble Vida, Speed, Haste 2, Jump Boost 2, o Resistencia.");
            }
            if (Bowless) {
                sender.sendMessage("§6➛Bowless: §7No se pueden usar arcos.");
            }
            return true;
        }
        return false;
    }

    public static void RunSuperHeroes (Player player) {
        int poder = Eventos.getRandomInt(5);
        if (SuperHeroes) {
            if (!Scenarios.health.contains(player.getName()) &&
                    !Scenarios.strength.contains(player.getName()) &&
                    !Scenarios.speed.contains(player.getName()) &&
                    !Scenarios.haste.contains(player.getName()) &&
                    !Scenarios.jump.contains(player.getName()) &&
                    !Scenarios.resistance.contains(player.getName())) {
                if (poder == 0) {
                    Scenarios.health.add(player.getName());
                    player.setMaxHealth(40);
                    player.sendMessage("§7[§6SuperHeroes§7]➛ Double Health");
                } else if (poder == 1) {
                    Scenarios.strength.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Strength");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                } else if (poder == 2) {
                    Scenarios.speed.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Speed");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                } else if (poder == 3) {
                    Scenarios.haste.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Haste");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                } else if (poder == 4) {
                    Scenarios.jump.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Jump Boost");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                } else if (poder == 5) {
                    Scenarios.resistance.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Resistance");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                }
            }
        }
    }

    public static void StaffMenu(Player p){
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
        staffscenarios.setItem(5, MenuItems.staffscenitem6());
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
            } else if (clicked.getType() == Material.BOW) {
                if (!Bowless) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6Bowless §2ON");
                    StaffScen(player);
                    Bowless = true;
                } else {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("§7[§9Galactic§7]➛ §6Bowless §4OFF");
                    StaffScen(player);
                    Bowless = false;
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
                if (SkyOff) {
                    event.setCancelled(true);
                    player.sendMessage("§7Activado el Sky");
                    SkyOff = false;
                    StaffTowers(player);
                } else {
                    event.setCancelled(true);
                    player.sendMessage("§7Desactivado el Sky");
                    SkyOff = true;
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
}
