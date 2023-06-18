package fr.anto42.emma.game.modes.taupeGun.listeners;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.TryStartEvent;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.modes.taupeGun.utils.GameUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.players.PlayersUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TryStartListeners implements Listener {
    @EventHandler
    public void onTryStart(TryStartEvent event){
        if(UHC.getInstance().getUhcManager().getGamemode() == GameUtils.getModule()){
            if(!UHCTeamManager.getInstance().isActivated()){
                event.setCancelled(true);
                PlayersUtils.broadcastMessage("§cLe système d'équipes doit être activé pour jouer dans ce mode de jeu !");
                SoundUtils.playSoundToAll(Sound.VILLAGER_NO);
            }
        }
    }
}
