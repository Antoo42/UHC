package fr.anto42.emma.game.modes.deathNoteV4.gameManager;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathNoteListener implements Listener {

    private final DNModule deathNoteModule;
    private int episodeNumber = 1;
    private int min = 0;

    public DeathNoteListener(DNModule deathNoteModule) {
        this.deathNoteModule = deathNoteModule;
    }

    @EventHandler
    public void onStart(StartEvent startEvent){
        new BukkitRunnable() {
            @Override
            public void run() {
                min++;
                if(min > 4 && min < 16)
                    deathNoteModule.setDeathNoteCanBeUsed(true);
                else
                    deathNoteModule.setDeathNoteCanBeUsed(false);
            }
        }.runTaskTimer(UHC.getInstance(), 0, TimeUtils.minutes(1));

        new BukkitRunnable() {
            @Override
            public void run() {
                onEpisode();
            }
        }.runTaskTimer(UHC.getInstance(), TimeUtils.minutes(20), TimeUtils.minutes(20));
    }

    public void onEpisode(){
        episodeNumber++;
        PlayersUtils.broadcastMessage("§7Début de l'épisode §e" + episodeNumber);

        UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer -> uhcPlayer.getRole() instanceof Kira).forEach(uhcPlayer -> {
            ((Kira) uhcPlayer.getRole()).getKiraInv().setCanUse(true);
            SoundUtils.playSoundToAll(Sound.ORB_PICKUP);

            if(uhcPlayer.getBukkitPlayer().getMaxHealth() < 20)
                uhcPlayer.getBukkitPlayer().setMaxHealth(uhcPlayer.getBukkitPlayer().getMaxHealth() + 2);

        });

        this.min = 0;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }
}