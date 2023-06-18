package fr.anto42.emma.game.modes.trueLove;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scoreboard.UHCScoreboard;
import fr.anto42.emma.coreManager.scoreboard.tools.ObjectiveSign;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.utils.players.GameUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.materials.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class TrueLoveScoreboard implements UHCScoreboard {
    private String isPvp(){
        if (UHC.getInstance().getUhcGame().getUhcData().isPvp())
            return "§a✔";
        else
            return TimeUtils.getEcartTimer(UHC.getInstance().getUhcGame().getUhcConfig().getPvp()* 60L, UHC.getInstance().getUhcGame().getUhcData().getTimer());
    }

    private String borderIsMove(){
        if (UHC.getInstance().getUhcGame().getUhcData().isBorderMove())
            return "§a✔";
        else
            return TimeUtils.getEcartTimer(UHC.getInstance().getUhcGame().getUhcConfig().getTimerBorder()*60L, UHC.getInstance().getUhcGame().getUhcData().getTimer());
    }
    String getCouple(UHCPlayer uhcPlayer) {
        AtomicReference<String> s = new AtomicReference<>();
        if (uhcPlayer.getUhcTeam() != null) {
            uhcPlayer.getUhcTeam().getUhcPlayerList().stream().filter(uhcPlayer1 -> !Objects.equals(uhcPlayer1.getName(), uhcPlayer.getName())).forEach(uhcPlayer1 -> {
                s.set(uhcPlayer1.getName());
            });
            return s.get();
        } else return "§cAucun";
    }

    @Override
    public void updateScoreboard(ObjectiveSign objectiveSign, UUID uuid, String ip) {
        Player player = Bukkit.getPlayer(uuid);
        objectiveSign.setLine(0, "§f");
        objectiveSign.setLine(1, "§8§l» §eINFORMATIONS");
        objectiveSign.setLine(2, "  §8┃ §fHost: §6" + UHC.getInstance().getUhcGame().getUhcData().getHostName());
        objectiveSign.setLine(3, "  §8┃ §fJoueurs: §a" + UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().size() + " §7(" + UHCTeamManager.getInstance().translateTeamAlivesNumber() + ")");
        objectiveSign.setLine(4, "  §8┃ §fTimer: §c" + TimeUtils.getFormattedTime(UHC.getInstance().getUhcGame().getUhcData().getTimer()));
        objectiveSign.setLine(5, "§c");
        objectiveSign.setLine(6, "§8§l» §ePARTIE");
        objectiveSign.setLine(7, "  §8┃ §fCouple: §d" + getCouple(UHC.getUHCPlayer(player)));
        objectiveSign.setLine(8, "  §8┃ §fPvP: §c" + isPvp());
        objectiveSign.setLine(9, "  §8┃ §fBordure: §e" + borderIsMove());
        objectiveSign.setLine(10, "  §8┃ §fRayon: §e±" + GameUtils.getDecimalFormat(WorldManager.getGameWorld().getWorldBorder().getSize() / 2, true));
        objectiveSign.setLine(11, "  §8┃ §fCentre: §3" + WorldUtils.distance(player.getLocation(), WorldManager.getCenterLoc()) + "m " + WorldUtils.Fleche(WorldUtils.Angle(player, WorldManager.getCenterLoc())));
        objectiveSign.setLine(12, "§b");
        objectiveSign.setLine(13, ip);
    }
}
