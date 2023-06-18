package fr.anto42.emma.game.modes.deathNoteV4.gameManager;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.TryStartEvent;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TryStartListener implements Listener {
    @EventHandler
    public void tryStartEvent(TryStartEvent startEvent){
        if(!UHCTeamManager.getInstance().isActivated() && UHC.getInstance().getUhcManager().getGamemode() == GameUtils.getModule()){
            startEvent.setCancelled(true);
            PlayersUtils.broadcastMessage("§cVeuillez activer le système d'équipes pour jouer dans ce mode de jeu !");
            SoundUtils.playSoundToAll(Sound.VILLAGER_NO);
        }
    }
}
