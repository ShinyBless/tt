package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Scenarios implements Listener {
    private Main plugin;

    public Scenarios(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void SwitcherooScen(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            Entity shooter = (Entity) arrow.getShooter();
            if (event.getEntity() instanceof Player) {
                Player enemy = (Player) event.getEntity();
                if (shooter instanceof Player) {
                    if (Comandos.Switcheroo) {
                        Location shooterlocation = shooter.getLocation();
                        Location enemylocation = enemy.getLocation();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                shooter.teleport(enemylocation);
                                enemy.teleport(shooterlocation);
                            }
                        }, 1L);
                    }

                }
            }
        }
    }

    @EventHandler
    public void CutCleanScen(EntityDeathEvent event) {
        if (Comandos.CutClean) {
            if (event.getEntityType() == EntityType.COW) {
                int cowdrops = Eventos.getRandomInt(3);
                int cowdrops3 = Eventos.getRandomInt(2);
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.COOKED_BEEF, cowdrops));
                event.getDrops().add(new ItemStack(Material.LEATHER, cowdrops3));
            } else if (event.getEntityType() == EntityType.PIG) {
                int pigdrops = Eventos.getRandomInt(3);
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.GRILLED_PORK, pigdrops));
            } else if (event.getEntityType() == EntityType.CHICKEN) {
                int chickendrops = Eventos.getRandomInt(3);
                event.getDrops().remove(new ItemStack(Material.RAW_CHICKEN));
                event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, chickendrops));
            } else if (event.getEntityType() == EntityType.RABBIT) {
                int rabbitdrops = Eventos.getRandomInt(3);
                event.getDrops().remove(new ItemStack(Material.RABBIT));
                event.getDrops().add(new ItemStack(Material.COOKED_RABBIT, rabbitdrops));
            } else if (event.getEntityType() == EntityType.SHEEP) {
                int sheepdrops = Eventos.getRandomInt(3);
                event.getDrops().remove(new ItemStack(Material.MUTTON));
                event.getDrops().add(new ItemStack(Material.COOKED_MUTTON, sheepdrops));
            }
        }
    }

    @EventHandler
    public void CutCleanScen2(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (Comandos.CutClean) {
            if (block.getType() == Material.COAL_ORE) {
                block.getDrops().clear();
                player.getInventory().addItem(new ItemStack(Material.TORCH, 4));
            } else if (block.getType() == Material.IRON_ORE) {
                block.getDrops().clear();
                player.giveExp(7 / 10);
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            } else if (block.getType() == Material.GOLD_ORE) {
                block.getDrops().clear();
                player.giveExp(1);
                player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
            } else if (block.getType() == Material.SAND) {
                block.getDrops().clear();
                player.giveExp(1 / 10);
                player.getInventory().addItem(new ItemStack(Material.GLASS));
            } else if (block.getType() == Material.GRAVEL) {
                block.getDrops().clear();
                player.getInventory().addItem(new ItemStack(Material.FLINT));
            }
        }
    }

    @EventHandler
    public void NoFallScen(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (Comandos.NoFall) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void SkyOffScen(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlockY() > 210) {
            if (Comandos.SkyOff) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BowlessScen(EntityShootBowEvent event){
        if (Comandos.Bowless) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BowlessScen2 (InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (Comandos.Bowless) {
            if (clicked.getType() == Material.BOW) {
                event.getInventory().removeItem(clicked);
            }
        }
    }

    @EventHandler
    public void EnchantslessScen (BlockPlaceEvent event){
        Block block = event.getBlock();
        if (Comandos.EnchantsNerf) {
            if (block.getType() == Material.BOOKSHELF){
                event.setCancelled(true);
            }
        }
        if (Comandos.EnchantsOFF){
            if (block.getType() == Material.BOOKSHELF || block.getType() == Material.ENCHANTMENT_TABLE){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EnchantslessScen2 (EnchantItemEvent event){
        if (Comandos.EnchantsOFF){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void HasteyBoysScen(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (Comandos.HasteyBoys) {
            if (clicked.getType() == Material.WOOD_PICKAXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.STONE_PICKAXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.IRON_PICKAXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.GOLD_PICKAXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.DIAMOND_PICKAXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.WOOD_AXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.STONE_AXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.IRON_AXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.GOLD_AXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.DIAMOND_AXE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.WOOD_SPADE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.STONE_SPADE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.IRON_SPADE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.GOLD_SPADE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.DIAMOND_SPADE && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
            if (clicked.getType() == Material.SHEARS && !clicked.containsEnchantment(Enchantment.DIG_SPEED)) {
                clicked.addEnchantment(Enchantment.DIG_SPEED, 3);
                clicked.addEnchantment(Enchantment.DURABILITY, 1);
            }
        }
    }

    public static ArrayList<String> health = new ArrayList<>();
    public static ArrayList<String> strength = new ArrayList<>();
    public static ArrayList<String> speed = new ArrayList<>();
    public static ArrayList<String> haste = new ArrayList<>();
    public static ArrayList<String> jump = new ArrayList<>();
    public static ArrayList<String> resistance = new ArrayList<>();

    @EventHandler
    public void SuperHeroesScen(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        int poder = Eventos.getRandomInt(5);
        if (Comandos.SuperHeroes) {
            if (!health.contains(player.getName()) && !strength.contains(player.getName()) && !speed.contains(player.getName()) && !haste.contains(player.getName()) && !jump.contains(player.getName()) && !resistance.contains(player.getName())) {
                if (poder == 0) {
                    health.add(player.getName());
                    player.setMaxHealth(40);
                    player.sendMessage("§7[§6SuperHeroes§7]➛ Double Health");
                } else if (poder == 1) {
                    strength.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Strength");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 200L);
                } else if (poder == 2) {
                    speed.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Speed");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 200L);
                } else if (poder == 3) {
                    haste.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Haste");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 200L);
                } else if (poder == 4) {
                    jump.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Jump Boost");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 20L);
                } else if (poder == 5) {
                    resistance.add(player.getName());
                    new BukkitRunnable() {
                        public void run() {
                            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                            player.sendMessage("§7[§6SuperHeroes§7]➛ Resistance");
                            player.setMaxHealth(20);
                        }
                    }.runTaskLater(plugin, 200L);
                }
            } else if (health.contains(player.getName())) {
                player.setMaxHealth(40);
            } else if (strength.contains(player.getName())) {
                new BukkitRunnable() {
                    public void run() {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                        player.setMaxHealth(20);
                    }
                }.runTaskLater(plugin, 200L);
            } else if (speed.contains(player.getName())) {
                new BukkitRunnable() {
                    public void run() {
                        player.removePotionEffect(PotionEffectType.SPEED);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        player.setMaxHealth(20);
                    }
                }.runTaskLater(plugin, 200L);
            } else if (haste.contains(player.getName())) {
                new BukkitRunnable() {
                    public void run() {
                        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
                        player.setMaxHealth(20);
                    }
                }.runTaskLater(plugin, 200L);
            } else if (jump.contains(player.getName())) {
                new BukkitRunnable() {
                    public void run() {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
                        player.setMaxHealth(20);
                    }
                }.runTaskLater(plugin, 20L);
            } else if (resistance.contains(player.getName())) {
                new BukkitRunnable() {
                    public void run() {
                        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                        player.setMaxHealth(20);
                    }
                }.runTaskLater(plugin, 200L);
            }
        }
    }
}


