package me.shinybless.Galactic;

import me.shinybless.Galactic.Commands.Teams;
import me.shinybless.Galactic.FastBoard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jcp.xml.dsig.internal.dom.Utils;

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
        FastBoard board = new FastBoard(player);
        board.updateTitle("§7●§9Galactic§7●");
        Main.boards.put(player.getName(), board);
        event.setJoinMessage(prefix + ChatColor.YELLOW + player.getName());
        Teams.globalchat.add(player);
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
            new BukkitRunnable(){
                public void run(){
                    for(Player p : Teams.staffchat){
                        p.sendMessage("§7[§9Galactic§7]➛ El jugador §d" + player.getName() + " §7ha abandonado la partida por mas de 5 minutos!");
                        offline.remove(player.getUniqueId());
                        if (player.isOnline()){
                            cancel();
                        }
                    }
                }
            }.runTaskLater(plugin, 6000L);
        }
        if (Teams.redteam.contains(player)){
            offline.put(player.getUniqueId(), "RedTeam");
            new BukkitRunnable(){
                public void run(){
                    for(Player p : Teams.staffchat){
                        p.sendMessage("§7[§9Galactic§7]➛ El jugador §d" + player.getName() + " §7ha abandonado la partida por mas de 5 minutos!");
                        offline.remove(player.getUniqueId());
                        if (player.isOnline()){
                            cancel();
                        }
                    }
                }
            }.runTaskLater(plugin, 6000L);
        }
        Teams.globalchat.remove(player);
        Teams.teamchat.remove(player);
        Teams.staffchat.remove(player);
    }

}
