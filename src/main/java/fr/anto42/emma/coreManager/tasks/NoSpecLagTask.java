package fr.anto42.emma.coreManager.tasks;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NoSpecLagTask extends BukkitRunnable {
    @Override
    public void run() {

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getGameMode() == GameMode.SPECTATOR || onlinePlayer.getGameMode() == GameMode.CREATIVE) {
                WorldBorder worldBorder = onlinePlayer.getWorld().getWorldBorder();
                double borderSize = worldBorder.getSize();
                Location playerLocation = onlinePlayer.getLocation();

                if (playerLocation.getX() < -(borderSize / 2) || playerLocation.getX() > (borderSize / 2)
                        || playerLocation.getZ() < -(borderSize / 2) || playerLocation.getZ() > (borderSize / 2)) {
                    onlinePlayer.teleport(WorldManager.getCenterLoc());
                    UHC.getUHCPlayer(onlinePlayer).sendClassicMessage("§cHop hop hop, reviens par ici toi !");
                    SoundUtils.playSoundToPlayer(onlinePlayer, Sound.VILLAGER_NO);
                }
            }
        }
    }
}
