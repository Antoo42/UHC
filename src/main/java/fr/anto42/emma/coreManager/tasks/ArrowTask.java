package fr.anto42.emma.coreManager.tasks;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.chat.Title;
import fr.anto42.emma.utils.materials.WorldUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowTask extends BukkitRunnable {
    private final UHCGame uhc = UHC.getInstance().getUhcGame();

    @Override
    public void run() {
        if (!UHCTeamManager.getInstance().isActivated() || !UHCTeamManager.getInstance().isDirectionalArrow())
            return;
        uhc.getUhcData().getUhcPlayerList().stream().filter(uhcPlayer -> uhcPlayer.getBukkitPlayer() != null && uhcPlayer.getUhcTeam() != null).forEach(uhcPlayer -> {
            StringBuilder stringBuilder = new StringBuilder();
            uhcPlayer.getUhcTeam().getUhcPlayerList().stream().filter(uhcPlayer1 -> !uhcPlayer.getName().equals(uhcPlayer1.getName()) && uhcPlayer1.getBukkitPlayer() != null && uhcPlayer.getBukkitPlayer().getWorld() == uhcPlayer1.getBukkitPlayer().getWorld()).forEach(uhcPlayer1 -> {
                String string = uhcPlayer1.getUhcTeam().getColor() + uhcPlayer1.getName() + " §c" + WorldUtils.distance(uhcPlayer.getBukkitPlayer().getLocation(), uhcPlayer1.getBukkitPlayer().getLocation())+ "§cm " + WorldUtils.Fleche(WorldUtils.Angle(uhcPlayer.getBukkitPlayer(), uhcPlayer1.getBukkitPlayer().getLocation()));
                stringBuilder.append(" §8┃ ").append(string);
            });
            Title.sendActionBar(uhcPlayer.getBukkitPlayer(), stringBuilder.toString());
        });
    }
}
