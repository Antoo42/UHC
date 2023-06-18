package fr.anto42.emma.game.modes.stp.listeners;

import fr.anto42.emma.game.modes.stp.SwitchModule;
import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SwitchListeners implements Listener {

    private final UHCGame uhcGame = UHC.getInstance().getUhcGame();
    private final SwitchModule module;

    public SwitchListeners(SwitchModule module) {
        this.module = module;
    }

    @EventHandler
    public void onDeath(DeathEvent deathEvent) {
        UHCPlayer victim = deathEvent.getVictim();
        victim.setPlayerState(UHCPlayerStates.DEAD);

        if (!UHCTeamManager.getInstance().isActivated()) {
            if (deathEvent.getKiller() != null)
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c" + victim.getName() + "§7 est mort des mains de §a" + deathEvent.getKiller().getName() + "§7 !");
            else
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c" + victim.getName() + "§7 est mort seul !");
        } else {
            if (victim.getUhcTeam() != null) {
                UHCTeam uhcTeam = victim.getUhcTeam();
                if (deathEvent.getKiller() != null)
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " " + uhcTeam.getPrefix() + victim.getName() + "§7 est mort des mains de §a" + deathEvent.getKiller().getUhcTeam().getPrefix() + deathEvent.getKiller().getName() + "§7 !");
                else
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " " + uhcTeam.getPrefix() + victim.getName() + "§7 est mort !");
                UHCTeam uhcTeam1 = victim.getUhcTeam();
                victim.leaveTeam();
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    if (uhcTeam1.getAliveUhcPlayers().size() == 0) {
                        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), uhcTeam1::destroy, 2);
                    }
                }, 15);
            }else{
                if (deathEvent.getKiller() != null)
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " " + victim.getName() + "§7 est mort des mains de §a" + deathEvent.getKiller().getUhcTeam().getPrefix() + deathEvent.getKiller().getName() + "§7 !");
                else
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " " + victim.getName() + "§7 est mort !");
            }
        }

        SoundUtils.playSoundToAll(Sound.WITHER_SPAWN);
        uhcGame.getUhcData().getUhcPlayerList().remove(victim);

        Player player = victim.getBukkitPlayer();
        player.spigot().respawn();
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            if (uhcGame.getUhcConfig().getAllowSpec() != "nobody"){
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(WorldManager.getCenterLoc());
            } else {
                player.kickPlayer(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Je suis navré de devoir vous expulser car les spectateurs sont désactivés dans cette partie, néanmoins je vous attend pour revenir dès la prochaine partie !");
            }
        }, 5);
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), module::winTester, 10L);
    }
}
