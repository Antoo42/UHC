package fr.anto42.emma.coreManager.tasks;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.BorderMovementEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.PvPEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.RolesEvent;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCTimer extends BukkitRunnable {
    private final UHCGame uhc;

    public UHCTimer(UHCGame uhc) {
        this.uhc = uhc;
    }

    @Override
    public void run() {
        if (uhc.getGameState() == GameState.FINISH)
            this.cancel();
        uhc.getUhcData().setTimer(uhc.getUhcData().getTimer() + 1);
        if (uhc.getUhcConfig().getPvp()*60 == uhc.getUhcData().getTimer() && !uhc.getUhcData().isPvp()){
            if (!uhc.getUhcData().isPvp()){
                uhc.getUhcData().setPvp(true);
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le PvP est désormais §aactif§7 !");
                SoundUtils.playSoundToAll(Sound.WOLF_GROWL);
                Bukkit.getServer().getPluginManager().callEvent(new PvPEvent());
            }
        }
        if (uhc.getUhcConfig().getRoles()*60 == uhc.getUhcData().getTimer() && !uhc.getUhcData().isRoles()) {
            uhc.getUhcData().setRoles(true);
            Bukkit.getServer().getPluginManager().callEvent(new RolesEvent());
        }
        if (uhc.getUhcConfig().getTimerBorder()*60 == uhc.getUhcData().getTimer() && !uhc.getUhcData().isBorderMove()){
            uhc.getUhcData().setBorderMove(true);
            long a = uhc.getUhcConfig().getStartBorderSize() - uhc.getUhcConfig().getFinalBorderSize();
            a = (long) (a/uhc.getUhcConfig().getBlockPerS());
            WorldManager.getGameWorld().getWorldBorder().setSize(uhc.getUhcConfig().getFinalBorderSize()*2, a);
            WorldManager.getNetherWorld().getWorldBorder().setSize(uhc.getUhcConfig().getFinalBorderSize()*2, a);
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7La bordure est en §amouvement§7 !");
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            Bukkit.getServer().getPluginManager().callEvent(new BorderMovementEvent());
        }
    }
}
