package fr.anto42.emma.game.modes.deathNoteV4.utils;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NearHealthView extends BukkitRunnable {
    DecimalFormat formatter = new DecimalFormat("#,###.0");
    private final DNModule dn;

    public NearHealthView(DNModule dn) {
        this.dn = dn;
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (UHCPlayer uhcPlayer : dn.getKiraHasEyesList()) {
                boolean crea = false;
                ArrayList<Player> list = new ArrayList<>();
                list.clear();
                for (Entity ent : p.getNearbyEntities(30, 30, 30)) {
                    if (ent instanceof Player) {
                        Player pp = (Player) ent;
                        list.add(uhcPlayer.getBukkitPlayer());
                        crea = true;
                    }
                    if (crea && !p.isSneaking() && !p.hasPotionEffect(PotionEffectType.INVISIBILITY) && !p.getGameMode().equals(GameMode.SPECTATOR)) {
                        // 0, 2, 0
                        Location loc = p.getLocation().add(0, 2, 0);

                        String vie = "" + p.getHealth() / 2.0;
                        double amount = Double.parseDouble(vie);
                        String c = formatter.format(amount);
                        if (amount < 1) c = "0" + c;

                        String displayLife = "§c" + c + " §4❤";

                        WorldServer worldServer = ((CraftWorld) loc.getWorld()) .getHandle();
                        EntityArmorStand stand = new EntityArmorStand(worldServer, loc.getX(), loc.getY(), loc.getZ());

                        stand.setCustomName(displayLife);
                        stand.setCustomNameVisible(true);

                        stand.setInvisible(true);
                        stand.setGravity(false);
                        stand.setSmall(true);

                        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);

                        for (Player pp : list)
                            ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);

                        new BukkitRunnable() {
                            public void run() {
                                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(stand.getId());

                                for (Player pp : list)
                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);
                            }
                        }.runTaskLater(UHC.getInstance(), 1L);
                    }
                }
            }
        }
    }
}