package fr.anto42.emma.game.modes.trueLove;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
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

public class TrueLoveListeners implements Listener {

    private final UHCGame uhcGame = UHC.getInstance().getUhcGame();
    private final TrueLoveModule module;

    public TrueLoveListeners(TrueLoveModule module) {
        this.module = module;
    }

    @EventHandler
    public void onDeath(DeathEvent deathEvent){
        UHCPlayer victim = deathEvent.getVictim();
        UHCTeam uhcTeam = victim.getUhcTeam();

        if (deathEvent.getKiller() != null)
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c" + victim.getName() + "§7 est mort des mains de §a" + deathEvent.getKiller().getName() + "§7 !");
        else
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c" + victim.getName() + "§7 est mort seul !");

        if(UHC.getUHCPlayer(deathEvent.getVictim().getBukkitPlayer()).getUhcTeam() != null){
            for (UHCPlayer uhcPlayer : uhcTeam.getAliveUhcPlayers()) {
                if(uhcPlayer != victim){
                    uhcPlayer.leaveTeam();
                }
            }
            victim.leaveTeam();
        }
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            for (UHCTeam team : UHCTeamManager.getInstance().getUhcTeams()) {
                if(team.getAliveUhcPlayers().size() == 0) {
                    team.destroy();
                }
            }
        }, 2L);


        SoundUtils.playSoundToAll(Sound.WITHER_SPAWN);
        uhcGame.getUhcData().getUhcPlayerList().remove(victim);
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), module::winTester, 3L);
        Player player = victim.getBukkitPlayer();
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            if (!uhcGame.getUhcConfig().getAllowSpec().equals("nobody")){
                player.spigot().respawn();
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(WorldManager.getCenterLoc());
            } else {
                player.kickPlayer(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Je suis navré de devoir vous expulser car les spectateurs sont désactivés dans cette partie, néanmoins je vous attend pour revenir dès la prochaine partie !");
            }
        }, 5);
    }
}
