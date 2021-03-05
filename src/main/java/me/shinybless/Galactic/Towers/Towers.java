package me.shinybless.Galactic.Towers;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Scenarios;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.Eventos;
import me.shinybless.Galactic.FastBoard.FastBoard;
import me.shinybless.Galactic.Main;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.List;

public class Towers implements Listener, CommandExecutor {
    private static Main plugin;

    public Towers(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("towers").setExecutor(this);
        plugin.getCommand("dropiron").setExecutor(this);
        plugin.getCommand("droplapis").setExecutor(this);
    }

    public static Boolean CongelTimer = Boolean.FALSE;
    public static boolean TowersStart = false;

    public static boolean TimedRespawn = false;
    public static boolean TimedRespawn2 = false;

    Location irongen = new Location(Bukkit.getWorld("world"), 0.4, 203, 1166.4);
    Location lapisgen = new Location(Bukkit.getWorld("world"), 0.4, 203, 1138);
    public static Location redspawn = new Location(Bukkit.getWorld("world"), 82, 192, 1152);
    public static Location bluespawn = new Location(Bukkit.getWorld("world"), -83, 192, 1152);

    public static String timer;
    public static String congelTimer;

    public static ArrayList<Material> droppeditems = new ArrayList<>();
    public static HashMap<String, Integer> score = new HashMap<>();
    public static HashMap<String, Integer> points = new HashMap<>();
    public static HashMap<String, Integer> kills = new HashMap<>();
    public static HashMap<String, Integer> muertes = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("galactic.towers.start") && cmd.getName().equalsIgnoreCase("towers")) {
            if (args.length == 0) {
                sender.sendMessage("§7Faltaron argumentos!");
                sender.sendMessage("§7/Towers start/stop");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!TowersStart) {
                        Bukkit.broadcastMessage("§7[§9Galactic§7]➛ El chat ha sido muteado");
                        Bukkit.broadcastMessage("§7[§6Towers§7]➛ El juego comenzará en §b15 segundos§7!");
                        Staff.MuteAll = true;
                        new BukkitRunnable() {
                            public void run() {
                                TowersStart = true;
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    GameStart(p);
                                    time();
                                    LapisGen();
                                    IronGen();
                                    if (Comandos.SuperHeroes) {
                                        StartSuperHeroes(p);
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
                                Bukkit.broadcastMessage("§7[§6Towers§7]➛ Reglas de Towers");
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
                    if (TowersStart) {
                        TowersStart = false;
                        CongelTimer = true;
                        sender.sendMessage("§7Towers stop");
                    } else {
                        return false;
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("dropiron") && sender.hasPermission("galactic.dropiron")) {
            if (sender instanceof Player) {
                ((Player) sender).getWorld().dropItem(irongen, new ItemStack(Material.IRON_INGOT));
            }
        } else if (cmd.getName().equalsIgnoreCase("droplapis") && sender.hasPermission("galactic.droplapis")) {
            if (sender instanceof Player) {
                ((Player) sender).getWorld().dropItem(lapisgen, new ItemStack(Material.LAPIS_LAZULI));
            }
        }
        return false;
    }

    public static void GameStart(Player player) {
        if (Teams.redteam.contains(player)) {
            player.teleport(redspawn);
            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
            player.getInventory().setHelmet(redhelm());
            player.getInventory().setChestplate(redchest());
            player.getInventory().setLeggings(redlegs());
            player.getInventory().setBoots(redboots());
        } else if (Teams.blueteam.contains(player)) {
            player.teleport(bluespawn);
            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
            player.getInventory().setHelmet(bluehelm());
            player.getInventory().setChestplate(bluechest());
            player.getInventory().setLeggings(bluelegs());
            player.getInventory().setBoots(blueboots());
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.removePotionEffect(PotionEffectType.SATURATION);
        FastBoard board = new FastBoard(player);
        board.updateTitle("§7●§9Galactic§7●");
        Main.boards.put(player.getName(), board);
    }

    public void Stats () {
        List<Integer> killtop = new LinkedList<>(kills.values());
        Collections.sort(killtop);
        Collections.reverse(killtop); //Ordenarlo
        Iterator<Map.Entry<String, Integer>> Kills = kills.entrySet().iterator();

        if (score.get("BlueTeam") == 10) {
            Bukkit.broadcastMessage("§1-----------------------");
        } else if (score.get("RedTeam") == 10) {
            Bukkit.broadcastMessage("§4-----------------------");
        }
        Bukkit.broadcastMessage("            §6Ganador");
        if (score.get("BlueTeam") == 10) {
            Bukkit.broadcastMessage("          §7Equipo §9Azul");
        } else if (score.get("RedTeam") == 10) {
            Bukkit.broadcastMessage("          §7Equipo §cRojo");
        }
        Bukkit.broadcastMessage("           §7Top Kills");

        while(Kills.hasNext()) {

            Map.Entry<String, Integer> entry = Kills.next();

            if (entry.getValue() == killtop.get(0)) {
                String kt1 = entry.getKey();
                Bukkit.broadcastMessage("       §61- §7" + kt1 + " §8⇨ §f" + killtop.get(0));
            }
            if (entry.getValue() == killtop.get(1)) {
                String kt2 = entry.getKey();
                Bukkit.broadcastMessage("       §62- §7" + kt2 + " §8⇨ §f" + killtop.get(1));
            }
            if (entry.getValue() == killtop.get(2)) {
                String kt3 = entry.getKey();
                Bukkit.broadcastMessage("       §63- §7" + kt3 + " §8⇨ §f" + killtop.get(2));
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("       §6Tus Kills §8⇨ §f" + kills.get(p.getName()));
            }
            if (score.get("BlueTeam") == 10) {
                Bukkit.broadcastMessage("§1-----------------------");
            } else if (score.get("RedTeam") == 10) {
                Bukkit.broadcastMessage("§4-----------------------");
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (player.getKiller() != null) {
            Player killer = player.getKiller();
            kills.putIfAbsent(killer.getName(), 0);
            muertes.putIfAbsent(player.getName(), 0);
            int lvl = killer.getLevel();
            int kill = kills.get(killer.getName());
            int muerte = muertes.get(player.getName());
            if (TowersStart) {
                killer.setLevel(lvl + 4);
                kills.put(killer.getName(), kill + 1);
                muertes.put(player.getName(), muerte + 1);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -1, 202, 1165), new Location(Bukkit.getWorld("world"), 1, 203, 1167), block)) {
                event.setCancelled(true);
                player.sendMessage("§7No puedes poner bloques en esta zona!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak2 (BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -1, 202, 1137), new Location(Bukkit.getWorld("world"), 1, 203, 1139), block)) {
                event.setCancelled(true);
                player.sendMessage("§7No puedes poner bloques en esta zona!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak3 (BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -81, 189, 1149), new Location(Bukkit.getWorld("world"), -87, 207, 1155), block)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak4 (BlockBreakEvent event) {
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), 81, 189, 1149), new Location(Bukkit.getWorld("world"), 87, 207, 1155), block)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -1, 202, 1165), new Location(Bukkit.getWorld("world"), 1, 203, 1167), block)) {
                event.setCancelled(true);
                player.sendMessage("§7No puedes poner bloques en esta zona!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace2 (BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -1, 202, 1137), new Location(Bukkit.getWorld("world"), 1, 203, 1139), block)) {
                event.setCancelled(true);
                player.sendMessage("§7No puedes poner bloques en esta zona!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace3 (BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), -81, 189, 1149), new Location(Bukkit.getWorld("world"), -87, 207, 1155), block)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace4 (BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (TowersStart) {
            if (isInLocationBlock(new Location(Bukkit.getWorld("world"), 81, 189, 1149), new Location(Bukkit.getWorld("world"), 87, 207, 1155), block)) {
                event.setCancelled(true);
            }
        }
    }

    public void LapisGen() {
        new BukkitRunnable() {
            public void run() {
                if (TowersStart){
                    World world = Bukkit.getWorld("world");
                    for(Entity current : world.getEntities()) {
                        if (current instanceof Item) {
                            if (((Item) current).getItemStack().getType() == (Material.LAPIS_LAZULI)) {
                                current.remove();
                            }
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 120L);
        new BukkitRunnable() {
            public void run() {
                if (TowersStart){
                    lapisgen.getWorld().dropItem(lapisgen, new ItemStack(Material.LAPIS_LAZULI, 1));
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 5L, 120L);
    }

    public void IronGen() {
        new BukkitRunnable() {
            public void run() {
                if (TowersStart) {
                    World world = Bukkit.getWorld("world");
                    assert world != null;
                    for (Entity current : world.getEntities()) {
                        if (current instanceof Item) {
                            if (((Item) current).getItemStack().getType() == (Material.IRON_INGOT)) {
                                current.remove();
                            }
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 200L);
        new BukkitRunnable() {
            public void run() {
                if (TowersStart){
                    irongen.getWorld().dropItem(irongen, new ItemStack(Material.IRON_INGOT, 1));
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 5L, 200L);
    }

    @EventHandler
    public void Respawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location redspawn = new Location(player.getWorld(), 84, 192, 1152);
        Location bluespawn = new Location(player.getWorld(), -83, 192, 1152);
        if (TowersStart) {
            if (TimedRespawn && !TimedRespawn2){
                player.setGameMode(GameMode.SPECTATOR);
                new BukkitRunnable(){
                    public void run(){
                        player.setGameMode(GameMode.SURVIVAL);
                        if (Teams.redteam.contains(player)) {
                            player.teleport(redspawn);
                            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                            player.getInventory().setHelmet(redhelm());
                            player.getInventory().setChestplate(redchest());
                            player.getInventory().setLeggings(redlegs());
                            player.getInventory().setBoots(redboots());
                        } else if (Teams.blueteam.contains(player)) {
                            player.teleport(bluespawn);
                            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                            player.getInventory().setHelmet(bluehelm());
                            player.getInventory().setChestplate(bluechest());
                            player.getInventory().setLeggings(bluelegs());
                            player.getInventory().setBoots(blueboots());
                        }
                        if (Comandos.SuperHeroes) {
                            if (Scenarios.health.contains(player.getName())) {
                                player.setHealth(50);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            } else {
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.strength.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.speed.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.haste.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            }
                            if (Scenarios.jump.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.resistance.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                        }
                    }
                }.runTaskLater(plugin, 100L);
            } else if (TimedRespawn2){
                player.setGameMode(GameMode.SPECTATOR);
                new BukkitRunnable() {
                    public void run() {
                        player.setGameMode(GameMode.SURVIVAL);
                        if (Teams.redteam.contains(player)) {
                            player.teleport(redspawn);
                            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                            player.getInventory().setHelmet(redhelm());
                            player.getInventory().setChestplate(redchest());
                            player.getInventory().setLeggings(redlegs());
                            player.getInventory().setBoots(redboots());
                        } else if (Teams.blueteam.contains(player)) {
                            player.teleport(bluespawn);
                            player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                            player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                            player.getInventory().setHelmet(bluehelm());
                            player.getInventory().setChestplate(bluechest());
                            player.getInventory().setLeggings(bluelegs());
                            player.getInventory().setBoots(blueboots());
                        }
                        if (Comandos.SuperHeroes) {
                            if (Scenarios.health.contains(player.getName())) {
                                player.setHealth(60);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            } else {
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.strength.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.speed.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.haste.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 3));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            }
                            if (Scenarios.jump.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                            if (Scenarios.resistance.contains(player.getName())) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                            }
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                        }
                    }
                }.runTaskLater(plugin, 200L);
            } else {
                if (Teams.redteam.contains(player)) {
                    event.setRespawnLocation(redspawn);
                    player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                    player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                    player.getInventory().setHelmet(redhelm());
                    player.getInventory().setChestplate(redchest());
                    player.getInventory().setLeggings(redlegs());
                    player.getInventory().setBoots(redboots());
                } else if (Teams.blueteam.contains(player)) {
                    event.setRespawnLocation(bluespawn);
                    player.getInventory().setItem(3, new ItemStack(Material.BAKED_POTATO, 8));
                    player.getInventory().setItem(4, new ItemStack(Material.QUARTZ_BLOCK, 16));
                    player.getInventory().setHelmet(bluehelm());
                    player.getInventory().setChestplate(bluechest());
                    player.getInventory().setLeggings(bluelegs());
                    player.getInventory().setBoots(blueboots());
                }
                if (Comandos.SuperHeroes) {
                    if (Scenarios.health.contains(player.getName())) {
                        player.setHealth(40);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    } else {
                        player.setHealth(20);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    }
                    if (Scenarios.strength.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    }
                    if (Scenarios.speed.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    }
                    if (Scenarios.haste.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                    }
                    if (Scenarios.jump.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    }
                    if (Scenarios.resistance.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                    }
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 8));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 8));
                }
            }
        }
    }

    public static void StartSuperHeroes (Player player) {
        int poder = Eventos.getRandomInt(5);
        if (Comandos.SuperHeroes) {
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
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
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
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
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
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
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
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 4));
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

    @EventHandler
    public void onStep(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = event.getTo().getBlock();
        score.putIfAbsent("BlueTeam", 0);
        score.putIfAbsent("RedTeam", 0);
        int bluescore = score.get("BlueTeam");
        int redscore = score.get("RedTeam");
        Location bpc = new Location(player.getWorld(), -82, 200, 1151);
        Location bpc2 = new Location(player.getWorld(), -84, 201, 1153);
        Location rpc = new Location(player.getWorld(), 83, 200, 1153);
        Location rpc2 = new Location(player.getWorld(), 85, 201, 1151);
        int x1b = Math.min(bpc.getBlockX(), bpc2.getBlockX());
        int x2b = Math.max(bpc.getBlockX(), bpc2.getBlockX());
        int y1b = Math.min(bpc.getBlockY(), bpc2.getBlockY());
        int y2b = Math.max(bpc.getBlockY(), bpc2.getBlockY());
        int z1b = Math.min(bpc.getBlockZ(), bpc2.getBlockZ());
        int z2b = Math.max(bpc.getBlockZ(), bpc2.getBlockZ());
        int x1r = Math.min(rpc.getBlockX(), rpc2.getBlockX());
        int x2r = Math.max(rpc.getBlockX(), rpc2.getBlockX());
        int y1r = Math.min(rpc.getBlockY(), rpc2.getBlockY());
        int y2r = Math.max(rpc.getBlockY(), rpc2.getBlockY());
        int z1r = Math.min(rpc.getBlockZ(), rpc2.getBlockZ());
        int z2r = Math.max(rpc.getBlockZ(), rpc2.getBlockZ());
        Location redspawn = new Location(player.getWorld(), 82, 192, 1152);
        Location bluespawn = new Location(player.getWorld(), -83, 192, 1152);
        if (TowersStart) {
            if (Teams.redteam.contains(player)) {
                if (block.getX() >= x1b) {
                    if (block.getX() <= x2b) {
                        if (block.getY() >= y1b) {
                            if (block.getY() <= y2b) {
                                if (block.getZ() >= z1b) {
                                    if (block.getZ() <= z2b) {
                                        score.put("RedTeam", redscore + 1);
                                        if (redscore + 1 == 10) {
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ §e" + player.getName() + " §7ha anotado un punto para el equipo §cRojo§7! Tienen §f" + (redscore + 1) + " §7punto/s!");
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ El equipo §cRojo §7ha ganado!");
                                            player.teleport(redspawn);
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.setGameMode(GameMode.SPECTATOR);
                                                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 1);
                                                Stats();
                                            }
                                            TowersStart = false;
                                            CongelTimer = Boolean.TRUE;
                                        } else {
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ §e" + player.getName() + " §7ha anotado un punto para el equipo §cRojo§7! Tienen §f" + (redscore + 1) + " §7punto/s!");
                                            player.teleport(redspawn);
                                            for (Player p : Teams.redteam) {
                                                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 1);
                                            }
                                            if (Scenarios.health.contains(player.getName())) {
                                                player.setHealth(40);
                                            } else {
                                                player.setHealth(20);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (Teams.blueteam.contains(player)) {
                if (block.getX() >= x1r) {
                    if (block.getX() <= x2r) {
                        if (block.getY() >= y1r) {
                            if (block.getY() <= y2r) {
                                if (block.getZ() >= z1r) {
                                    if (block.getZ() <= z2r) {
                                        score.put("BlueTeam", bluescore + 1);
                                        if (bluescore + 1 == 10) {
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ §e" + player.getName() + " §7ha anotado un punto para el equipo §9Azul§7! Tienen §f" + (bluescore + 1) + " §7punto/s");
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ El equipo §9Azul §7ha ganado!");
                                            player.teleport(bluespawn);
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.setGameMode(GameMode.SPECTATOR);
                                                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 1);
                                                Stats();
                                            }
                                            TowersStart = false;
                                            CongelTimer = Boolean.TRUE;
                                        } else {
                                            Bukkit.broadcastMessage("§7[§6Towers§7]➛ §e" + player.getName() + " §7ha anotado un punto para el equipo §9Azul§7! Tienen §f" + (bluescore + 1) + " §7punto/s");
                                            player.teleport(bluespawn);
                                            for (Player p : Teams.blueteam) {
                                                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 1);
                                            }
                                            if (Scenarios.health.contains(player.getName())) {
                                                player.setHealth(40);
                                            } else {
                                                player.setHealth(20);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static ItemStack redhelm (){
        ItemStack redhelm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta redhelmmeta = (LeatherArmorMeta) redhelm.getItemMeta();
        redhelmmeta.setColor(Color.fromRGB(255, 0, 0));
        redhelmmeta.setUnbreakable(true);
        redhelmmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        redhelm.setItemMeta(redhelmmeta);
        return redhelm;
    }

    public static ItemStack redchest (){
        ItemStack redchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta redchestmeta = (LeatherArmorMeta) redchest.getItemMeta();
        redchestmeta.setColor(Color.fromRGB(255, 0, 0));
        redchestmeta.setUnbreakable(true);
        redchestmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        redchest.setItemMeta(redchestmeta);
        return redchest;
    }

    public static ItemStack redlegs (){
        ItemStack redlegs = new ItemStack(Material.LEATHER_LEGGINGS);
        redlegs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta redlegsmeta = (LeatherArmorMeta) redlegs.getItemMeta();
        redlegsmeta.setColor(Color.fromRGB(255, 0, 0));
        redlegsmeta.setUnbreakable(true);
        redlegsmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        redlegs.setItemMeta(redlegsmeta);
        return redlegs;
    }

    public static ItemStack redboots (){
        ItemStack redboots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta redbootsmeta = (LeatherArmorMeta) redboots.getItemMeta();
        redbootsmeta.setColor(Color.fromRGB(255, 0, 0));
        redbootsmeta.setUnbreakable(true);
        redbootsmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        redboots.setItemMeta(redbootsmeta);
        return redboots;
    }

    public static ItemStack bluehelm (){
        ItemStack bluehelm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bluehelmmeta = (LeatherArmorMeta) bluehelm.getItemMeta();
        bluehelmmeta.setColor(Color.fromRGB(0, 0, 255));
        bluehelmmeta.setUnbreakable(true);
        bluehelmmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bluehelm.setItemMeta(bluehelmmeta);
        return bluehelm;
    }

    public static ItemStack bluechest (){
        ItemStack bluechest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bluechestmeta = (LeatherArmorMeta) bluechest.getItemMeta();
        bluechestmeta.setColor(Color.fromRGB(0, 0, 255));
        bluechestmeta.setUnbreakable(true);
        bluechestmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bluechest.setItemMeta(bluechestmeta);
        return bluechest;
    }

    public static ItemStack bluelegs (){
        ItemStack bluelegs = new ItemStack(Material.LEATHER_LEGGINGS);
        bluelegs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta bluelegsmeta = (LeatherArmorMeta) bluelegs.getItemMeta();
        bluelegsmeta.setColor(Color.fromRGB(0, 0, 255));
        bluelegsmeta.setUnbreakable(true);
        bluelegsmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bluelegs.setItemMeta(bluelegsmeta);
        return bluelegs;
    }

    public static ItemStack blueboots (){
        ItemStack blueboots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bluebootsmeta = (LeatherArmorMeta) blueboots.getItemMeta();
        bluebootsmeta.setColor(Color.fromRGB(0, 0, 255));
        bluebootsmeta.setUnbreakable(true);
        bluebootsmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        blueboots.setItemMeta(bluebootsmeta);
        return blueboots;
    }

    public void time(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int min = 00;
            int sec = 00;
            int hor = 00;
            String secc;
            String minn;
            String horr;
            @Override
            public void run() {
                if (sec < 10){
                    secc = "0" + sec;
                } else if (sec > 9){
                    secc = "" + sec;
                }
                if (min < 10){
                    minn = "0" + min;
                } else if (min > 9){
                    minn = "" + min;
                }
                if (hor < 10){
                    horr = "0" + hor;
                } else if (sec > 9){
                    horr = "" + hor;
                }
                timer = horr + ":" + minn + ":" + secc;
                sec = sec + 1;
                if (sec == 60){
                    sec = 00;
                    min = min + 1;
                }
                if (min == 60){
                    min = 00;
                    hor = hor + 1;
                }
                if (CongelTimer){
                    CongelTimer = Boolean.FALSE;
                    congelTimer = horr + ":" + minn + ":" + secc;
                }
                if (min == 30 && hor == 0) {
                    if (!TimedRespawn) {
                        TimedRespawn = true;
                        Bukkit.broadcastMessage("§7[§9Galactic§7]➛ Han pasado §f30 minutos §7de juego. Ahora los jugadores tardarán §d5 segundos §7en respawnear.");
                    }
                }
                if (hor == 1) {
                    if (!TimedRespawn2) {
                        TimedRespawn2 = true;
                        Bukkit.broadcastMessage("§7[§9Galactic§7]➛ Han pasado §f60 minutos §7de juego. Ahora los jugadores tardarán §d10 segundos §7en respawnear.");
                    }
                }
            }
        },0L, 20L);
    }

    public static boolean isInLocation(Location loc1, Location loc2, Entity entity){
        Location loc = entity.getLocation();
        int xl1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int xl2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int yl1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int yl2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int zl1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int zl2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        if (loc.getBlockX() >= xl1 && loc.getBlockX() <= xl2) {
            if (loc.getBlockY() >= yl1 && loc.getBlockY() <= yl2) {
                if (loc.getBlockZ() >= zl1 && loc.getBlockZ() <= zl2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInLocationBlock(Location loc1, Location loc2, Block block){
        Location loc = block.getLocation();
        int xl1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int xl2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int yl1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int yl2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int zl1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int zl2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        if (loc.getBlockX() >= xl1 && loc.getBlockX() <= xl2) {
            if (loc.getBlockY() >= yl1 && loc.getBlockY() <= yl2) {
                if (loc.getBlockZ() >= zl1 && loc.getBlockZ() <= zl2) {
                    return true;
                }
            }
        }
        return false;
    }
}
