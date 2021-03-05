package me.shinybless.Galactic.TowersWars;

import com.mojang.authlib.GameProfile;
import me.shinybless.Galactic.Main;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC implements Listener {
    private Main plugin;

    public NPC(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
//§


}
































    /*public static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();

    private double x;
    private double z;

    public static void createNPC(Location loc, String name){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(loc.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 180, 180);
        addNPCPacket(npc);
        NPC.add(npc);
    }

    public static void addNPCPacket(EntityPlayer npc){
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    public static void addJoinPacket(Player player){
        for (EntityPlayer npc : NPC){
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    private double calculateDistance(Player p) {
        for (EntityPlayer npc : NPC) {
            double diffX = npc.locX() - p.getLocation().getX(), diffZ = npc.locZ() - p.getLocation().getZ();
            x = diffX < 0 ? (diffX * -1) : diffX;
            z = diffZ < 0 ? (diffZ * -1) : diffZ;
        }
        return Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
    }

    public void enableRotation() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(calculateDistance(p) > 5) continue;
                for (EntityPlayer npc : NPC) {

                    // Get the Player Connection so we can send Packets
                    PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;

                    // Calculate the Yaw for the NPC
                    Vector difference = p.getLocation().subtract(npc.getBukkitEntity().getLocation()).toVector().normalize();
                    byte yaw = (byte) MathHelper.d((Math.toDegrees(Math.atan2(difference.getZ(), difference.getX()) - Math.PI / 2) * 256.0F) / 360.0F);

                    // Calculate the Pitch for the NPC
                    Vector height = npc.getBukkitEntity().getLocation().subtract(p.getLocation()).toVector().normalize();
                    byte pitch = (byte) MathHelper.d((Math.toDegrees(Math.atan(height.getY())) * 256.0F) / 360.0F);

                    // Send the Packets to update the NPC
                    connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, yaw));
                    connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), yaw, pitch, true));
                }
            }
        }, 1, 1);
    }*/
