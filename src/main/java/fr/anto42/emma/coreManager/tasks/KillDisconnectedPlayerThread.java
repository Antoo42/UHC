package fr.anto42.emma.coreManager.tasks;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.UUID;

public class KillDisconnectedPlayerThread extends BukkitRunnable {

    private final UUID uuid;
    private int timeLeft;

    public KillDisconnectedPlayerThread(UUID playerUuid, int maxDisconnectPlayersTime){
        uuid = playerUuid;
        timeLeft = maxDisconnectPlayersTime;
    }

    @Override
    public void run() {
        UHCGame gm = UHC.getInstance().getUhcGame();

        if (!gm.getGameState().equals(GameState.PLAYING)) {
            this.cancel();
        }

        Player player = Bukkit.getPlayer(uuid);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);

        if (player.isOnline()){
            Optional<LivingEntity> zombie = WorldManager.getGameWorld().getLivingEntities()
                    .stream()
                    .filter(e -> e.getUniqueId().equals(uhcPlayer.getOfflineZombieUuid()))
                    .findFirst();

            if (zombie.isPresent()) {
                zombie.get().remove();
                uhcPlayer.setOfflineZombieUuid(null);
            }
            this.cancel();
        }

        /*if (timeLeft <= 0){
            DeathEvent deathEvent = new DeathEvent(uhcPlayer, null);
            Bukkit.getServer().getPluginManager().callEvent(deathEvent);
            if (uhcPlayer.getOfflineZombieUuid() != null){
                Optional<LivingEntity> zombie = WorldManager.getGameWorld().getLivingEntities()
                        .stream()
                        .filter(e -> e.getUniqueId().equals(uhcPlayer.getOfflineZombieUuid()))
                        .findFirst();

                if (zombie.isPresent()) {
                    for (int i = 0; i < InventoryUtils.getPlayerInventory(player).length; i++) {
                        ItemStack[] itemStacks = InventoryUtils.getPlayerInventory(player);
                        zombie.get().getLocation().getWorld().dropItemNaturally(zombie.get().getLocation(), itemStacks[i]);
                    }
                    zombie.get().remove();
                    uhcPlayer.setOfflineZombieUuid(null);
                }
            }
        }*/else{
            timeLeft-=1;
            Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(), this, 20);
        }
    }

}
