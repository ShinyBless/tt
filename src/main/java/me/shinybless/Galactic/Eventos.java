package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.FastBoard.FastBoard;
import me.shinybless.Galactic.Towers.Towers;
import me.shinybless.Galactic.TowersWars.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Eventos implements Listener{
    private Main plugin;

    public String prefix = (ChatColor.GRAY + "(" + ChatColor.GREEN + "+" + ChatColor.GRAY + ") ");
    public String prefix2 = (ChatColor.GRAY + "(" + ChatColor.RED + "-" + ChatColor.GRAY + ") ");

    public static ArrayList<Player> staff = new ArrayList<>();
    public static HashMap<UUID, String> offline = new HashMap<>();
    public static HashMap<UUID, String> offline2 = new HashMap<>();

    public Eventos(Main plugin){
        this.plugin=plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static boolean percentChance(double percent){
        if(percent > 100 || percent < 0 ){
            throw new IllegalArgumentException("§7Un porcentaje no puede ser mayor que 100 o menor que 0!");
        }
        double result = new Random().nextDouble() * 100;
        return result <= percent;
    }

    public static Integer getRandomInt(Integer max){
        Random ran = new Random();
        return ran.nextInt(max + 1);
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location spawn = new Location(Bukkit.getWorld("world"), 0.4, 64, 1024.4);
        event.setJoinMessage(prefix + ChatColor.YELLOW + player.getName());
        /*if (Comandos.TowersWars){
            if (NPC.NPC == null || NPC.NPC.isEmpty()){
                return;
            } else {
                NPC.addJoinPacket(event.getPlayer());
            }
        }*/
        if (!Towers.TowersStart){
            player.teleport(spawn);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
            player.setGameMode(GameMode.ADVENTURE);
        } else {
            FastBoard board = new FastBoard(player);
            board.updateTitle("§7●§9Galactic§7●");
            Main.boards.put(player.getName(), board);
            if (!Staff.jail.contains(player.getUniqueId())) {
                if (Teams.blueteam.contains(player)) {
                    player.teleport(Towers.bluespawn);
                } else if (Teams.redteam.contains(player)) {
                    player.teleport(Towers.redspawn);
                }
            } else {
                player.teleport(Staff.jailzone);
            }
            if (Scenarios.health.contains(player)){
                if (player.getMaxHealth() != 40){
                    player.setMaxHealth(40);
                }
            }
            if (Scenarios.strength.contains(player)){
                if (!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                }
            }
            if (Scenarios.speed.contains(player)){
                if (!player.hasPotionEffect(PotionEffectType.SPEED)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                }
            }
            if (Scenarios.haste.contains(player)){
                if (!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
                }
            }
            if (Scenarios.jump.contains(player)){
                if (!player.hasPotionEffect(PotionEffectType.JUMP)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
                }
            }
            if (Scenarios.resistance.contains(player)){
                if (!player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                }
            }
        }

        if (!Teams.globalchat.contains(player) && !Teams.teamchat.contains(player) && !Teams.staffchat.contains(player)){
            Teams.globalchat.add(player);
        }
        if (offline.containsKey(player.getUniqueId())){
            if (offline.get(player.getUniqueId()).equalsIgnoreCase("BlueTeam")){
                Teams.blueteam.add(player);
            }
        }
        if (offline.containsKey(player.getUniqueId())){
            if (offline.get(player.getUniqueId()).equalsIgnoreCase("RedTeam")){
                Teams.redteam.add(player);
            }
        }
        if (player.hasPermission("galactic.staffchat.view")){
            staff.add(player);
        }
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(prefix2 + ChatColor.YELLOW + player.getName());
        if (Teams.blueteam.contains(player)){
            offline.put(player.getUniqueId(), "BlueTeam");
            offline2.put(player.getUniqueId(), "Offline");
            new BukkitRunnable(){
                public void run(){
                    for(Player p : Teams.staffchat){
                        p.sendMessage("§7[§9Galactic§7]➛ El jugador §d" + player.getName() + " §7ha abandonado la partida por mas de 5 minutos!");
                        offline.remove(player.getUniqueId());
                        offline2.remove(player.getUniqueId());
                        if (offline2.get(player.getUniqueId()).equalsIgnoreCase("Online")){
                            cancel();
                            offline.remove(player.getUniqueId());
                            offline2.remove(player.getUniqueId());
                        }
                    }
                }
            }.runTaskLater(plugin, 6000L);
        }
        if (Teams.redteam.contains(player)){
            offline.put(player.getUniqueId(), "RedTeam");
            offline2.put(player.getUniqueId(), "Offline");
            new BukkitRunnable(){
                public void run(){
                    for(Player p : Teams.staffchat){
                        p.sendMessage("§7[§9Galactic§7]➛ El jugador §d" + player.getName() + " §7ha abandonado la partida por mas de 5 minutos!");
                        offline.remove(player.getUniqueId());
                        offline2.remove(player.getUniqueId());
                        if (offline2.get(player.getUniqueId()).equalsIgnoreCase("Online")){
                            cancel();
                            offline.remove(player.getUniqueId());
                            offline2.remove(player.getUniqueId());
                        }
                    }
                }
            }.runTaskLater(plugin, 6000L);
        }
    }

    @EventHandler
    public void PvPOff (EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (!Towers.TowersStart){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BreakProtection (BlockBreakEvent event){
        Player player = event.getPlayer();
        if (!Towers.TowersStart){
            if (!player.hasPermission("galactic.break")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlaceProtection (BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (!Towers.TowersStart){
            if (!player.hasPermission("galactic.place")) {
                event.setCancelled(true);
            }
        }
    }

}
