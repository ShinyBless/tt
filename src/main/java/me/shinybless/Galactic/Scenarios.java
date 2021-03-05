package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.Eventos;
import me.shinybless.Galactic.Main;
import me.shinybless.Galactic.Towers.Towers;
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
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Scenarios implements Listener {
    private static Main plugin;

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
            if (block.getType() == Material.BOOKSHELF || block.getType() == Material.ENCHANTING_TABLE){
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

    public static void TeamInventoryScen(Player player){
        for (Player p : Teams.blueteam){
            Inventory blueinv = Bukkit.createInventory(p, 27);
            if (Teams.blueteam.contains(player)){
                player.openInventory(blueinv);
            }
        }
        for (Player p : Teams.redteam){
            Inventory redinv = Bukkit.createInventory(p, 27);
            if (Teams.redteam.contains(player)){
                player.openInventory(redinv);
            }
        }
    }

    public static void TeamSwapScen() {
        new BukkitRunnable() {
            public void run() {
                if (Comandos.TeamSwap) {
                    if (Towers.TowersStart) {
                        int team1 = Eventos.getRandomInt(Teams.blueteam.size());
                        int team2 = Eventos.getRandomInt(Teams.redteam.size());
                        Player blue = Teams.blueteam.get(team1);
                        Player red = Teams.redteam.get(team2);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team blue leave " + blue);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team red join " + blue);

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team red leave " + red);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team blue join " + red);

                        Bukkit.broadcastMessage("§7[§2TeamSwap§7]➛ §9" + blue.getName() + " §7y §c" + red.getName() + " §7han intercambiado sus equipos!");
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L,100L);
    }

    @EventHandler
    public void HasteyBoysScen(InventoryClickEvent event) {
        if (Comandos.HasteyBoys) {
            if (event.getClickedInventory() != null) {
                if (event.getCurrentItem() != null) {
                    ItemStack item = event.getCurrentItem();
                    Material type = item.getType();
                    if (type == Material.WOODEN_AXE || type == Material.WOODEN_PICKAXE || type == Material.WOODEN_SHOVEL || type == Material.WOODEN_HOE ||
                            type == Material.STONE_AXE || type == Material.STONE_PICKAXE || type == Material.STONE_SHOVEL || type == Material.STONE_HOE ||
                            type == Material.IRON_AXE || type == Material.IRON_PICKAXE || type == Material.IRON_SHOVEL || type == Material.IRON_HOE ||
                            type == Material.GOLDEN_AXE || type == Material.GOLDEN_PICKAXE || type == Material.GOLDEN_SHOVEL || type == Material.GOLDEN_HOE ||
                            type == Material.DIAMOND_AXE || type == Material.DIAMOND_PICKAXE || type == Material.DIAMOND_SHOVEL || type == Material.DIAMOND_HOE ||
                            type == Material.SHEARS){
                        if (!item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                            item.addEnchantment(Enchantment.DURABILITY, 1);
                        }
                        if (item.getEnchantments().containsKey(Enchantment.DIG_SPEED) && item.getEnchantmentLevel(Enchantment.DIG_SPEED) >= 3) return;
                        item.addEnchantment(Enchantment.DIG_SPEED, 3);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (Comandos.CutClean && !event.getEntity().getWorld().getName().equalsIgnoreCase("spawn") && !event.getEntity().getWorld().getName().equalsIgnoreCase("arena")) {
            for (ItemStack drops : event.getDrops()) {
                if (drops.getType() == Material.PORKCHOP) {
                    drops.setType(Material.COOKED_PORKCHOP);
                } else if (drops.getType() == Material.BEEF) {
                    drops.setType(Material.COOKED_BEEF);
                } else if (drops.getType() == Material.CHICKEN) {
                    drops.setType(Material.COOKED_CHICKEN);
                } else if (drops.getType() == Material.MUTTON) {
                    drops.setType(Material.COOKED_MUTTON);
                } else if (drops.getType() == Material.COD) {
                    drops.setType(Material.COOKED_COD);
                } else if (drops.getType() == Material.SALMON){
                    drops.setType(Material.COOKED_SALMON);
                } else if (drops.getType() == Material.RABBIT){
                    drops.setType(Material.COOKED_RABBIT);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (Comandos.CutClean && !e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().getWorld().getName().equalsIgnoreCase("arena")) {
            Block block = e.getBlock();
            Location loc = new Location(block.getWorld(), (double)block.getLocation().getBlockX() + 0.5D, (double)block.getLocation().getBlockY() + 0.5D, (double)block.getLocation().getBlockZ() + 0.5D);
            if (block.getType() == Material.IRON_ORE) {
                if (e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE) {
                    e.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getState().update();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
                    ((ExperienceOrb)block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(2);
                }
            }
            if (block.getType() == Material.GOLD_ORE) {
                if (e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE){
                    e.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getState().update();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
                    ((ExperienceOrb)block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(3);
                }
            }
            if (block.getType() == Material.ANCIENT_DEBRIS) {
                if (e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE){
                    e.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getState().update();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.NETHERITE_SCRAP));
                    ((ExperienceOrb)block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(5);
                }
            }
            if (block.getType() == Material.COAL_ORE) {
                if (e.getPlayer().getItemInHand().getType() == Material.WOODEN_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE) {
                    e.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getState().update();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.TORCH, 2));
                    ((ExperienceOrb)block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(1);
                }
            }
        }
    }

    @EventHandler
    public void VengefulSpiritsScen (PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Location loc = player.getLocation();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        if (Comandos.VengefulSpirits) {
            if (Eventos.percentChance(20)) {
                LivingEntity zombie = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                zombie.getEquipment().clear();
                zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                zombie.setCanPickupItems(false);
            } else if (Eventos.percentChance(15)) {
                LivingEntity skeleton = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.SKELETON);
                skeleton.getEquipment().clear();
                skeleton.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                skeleton.getEquipment().setItemInMainHand(bow);
                skeleton.setCanPickupItems(false);
            } else if (Eventos.percentChance(12)) {
                player.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
            } else if (Eventos.percentChance(12)) {
                player.getWorld().spawnEntity(loc, EntityType.SPIDER);
            } else if (Eventos.percentChance(10)) {
                player.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
            } else if (Eventos.percentChance(9)) {
                player.getWorld().spawnEntity(loc, EntityType.CREEPER);
            } else if (Eventos.percentChance(8)) {
                player.getWorld().spawnEntity(loc, EntityType.WITCH);
            } else if (Eventos.percentChance(6)) {
                player.getWorld().spawnEntity(loc, EntityType.BLAZE);
            } else if (Eventos.percentChance(4)) {
                player.getWorld().spawnEntity(loc, EntityType.GHAST);
            } else if (Eventos.percentChance(3)) {
                player.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
            } else if (Eventos.percentChance(1)) {
                player.getWorld().spawnEntity(loc, EntityType.PIGLIN_BRUTE);
            }
        }
    }

    @EventHandler
    public void VengefulSpiritsScen2 (EntityDeathEvent event){
        Entity entity = event.getEntity();
        if (Comandos.VengefulSpirits){
            if (!(entity instanceof Player)) {
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void AAAAnosequenombreponerleScen (PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Location loc = player.getLocation();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        ItemStack zhelm = new ItemStack(Material.IRON_HELMET);
        zhelm.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        zhelm.addEnchantment(Enchantment.THORNS, 1);
        ItemStack zchest = new ItemStack(Material.IRON_CHESTPLATE);
        zchest.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3);
        zchest.addEnchantment(Enchantment.THORNS, 1);
        ItemStack shelm = new ItemStack(Material.LEATHER_HELMET);
        shelm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        shelm.addEnchantment(Enchantment.THORNS, 3);
        if (Comandos.aaaa){
            if (Eventos.percentChance(25)){
                if (Eventos.percentChance(20)) {
                    LivingEntity zombie = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    zombie.getEquipment().clear();
                    zombie.getEquipment().setHelmet(zhelm);
                    zombie.getEquipment().setChestplate(zchest);
                    zombie.getEquipment().setItemInMainHand(sword);
                    zombie.setCanPickupItems(false);
                } else if (Eventos.percentChance(15)) {
                    LivingEntity skeleton = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.SKELETON);
                    skeleton.getEquipment().clear();
                    skeleton.getEquipment().setHelmet(shelm);
                    skeleton.getEquipment().setItemInMainHand(bow);
                    skeleton.setCanPickupItems(false);
                } else if (Eventos.percentChance(12)) {
                    LivingEntity silverfish = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
                    silverfish.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
                } else if (Eventos.percentChance(12)) {
                    LivingEntity spider = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.SPIDER);
                    spider.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
                    spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                } else if (Eventos.percentChance(10)) {
                    LivingEntity cavespider = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                    cavespider.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
                } else if (Eventos.percentChance(9)) {
                    LivingEntity creeper = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.CREEPER);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                } else if (Eventos.percentChance(8)) {
                    LivingEntity witch = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.WITCH);
                    witch.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 0));
                } else if (Eventos.percentChance(6)) {
                    LivingEntity blaze = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.BLAZE);
                    blaze.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 3));
                } else if (Eventos.percentChance(4)) {
                    LivingEntity ghast = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.GHAST);
                    ghast.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                } else if (Eventos.percentChance(3)) {
                    LivingEntity witherskeleton = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                    witherskeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
                    witherskeleton.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 2));
                } else if (Eventos.percentChance(1)) {
                    LivingEntity piglinbrute = (LivingEntity) player.getWorld().spawnEntity(loc, EntityType.PIGLIN_BRUTE);
                    piglinbrute.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
                }
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


