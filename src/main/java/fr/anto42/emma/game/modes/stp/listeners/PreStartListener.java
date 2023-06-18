package fr.anto42.emma.game.modes.stp.listeners;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.TryStartEvent;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PreStartListener implements Listener {
    @EventHandler
    public void onTryStart(TryStartEvent event){
        if (UHC.getInstance().getUhcManager().getGamemode().getDiscordName().equals("SwitchThePatrick") && !UHCTeamManager.getInstance().isActivated()){
            event.setCancelled(true);
            UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().sendClassicMessage("§cVeuillez activer le système d'équipe pour jouer dans ce mode de jeu.");
        }
    }
}
