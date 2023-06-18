package fr.anto42.emma.game.modes.trueLove;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class LoveManager extends BukkitRunnable {

    private final TrueLoveModule trueLoveModule;

    public LoveManager(TrueLoveModule trueLoveModule) {
        this.trueLoveModule = trueLoveModule;
    }

    @Override
    public void run() {
        if(!UHC.getInstance().getUhcGame().getUhcData().isPvp()) return;
        if(!UHC.getInstance().getUhcGame().getGameState().equals(GameState.PLAYING)) return;
        for(UHCPlayer uhcPlayer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()){
            if(uhcPlayer.getUhcTeam() == null && uhcPlayer.getBukkitPlayer() != null && uhcPlayer.getBukkitPlayer().getGameMode() == GameMode.SURVIVAL){
                UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer1 -> uhcPlayer1.getBukkitPlayer() != null && uhcPlayer.getBukkitPlayer().getWorld() == uhcPlayer1.getBukkitPlayer().getWorld() && uhcPlayer.getBukkitPlayer().getLocation().distance(uhcPlayer1.getBukkitPlayer().getLocation()) <= trueLoveModule.getRadius() && uhcPlayer1.getBukkitPlayer().getGameMode() == GameMode.SURVIVAL && uhcPlayer.getBukkitPlayer().getGameMode() == GameMode.SURVIVAL).forEach(uhcPlayers -> {
                    if(uhcPlayer != uhcPlayers && uhcPlayers.getUhcTeam() == null && uhcPlayers.getBukkitPlayer() != null){
                        UHCTeam uhcTeam = UHCTeamManager.getInstance().getRandomFreeTeam();
                        UHCTeamManager.getInstance().getUhcTeams().add(uhcTeam);
                        uhcPlayer.joinTeam(uhcTeam);
                        uhcPlayers.joinTeam(uhcTeam);
                        uhcPlayer.sendClassicMessage("§7Vous êtes tombé amoureux(se) de §d§l" + uhcPlayers.getName() + " §7!");
                        uhcPlayers.sendClassicMessage("§7Vous êtes tombé amoureux(se) de §d§l" + uhcPlayer.getName() + " §7!");
                        UHC.getInstance().getUhcManager().getGamemode().winTester();
                    }
                });

            }
        }
    }
}
