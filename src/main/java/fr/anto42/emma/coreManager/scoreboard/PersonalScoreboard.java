package fr.anto42.emma.coreManager.scoreboard;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scoreboard.tools.ObjectiveSign;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.utils.players.GameUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.materials.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "EmmaUHC");
        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData(){
    }

    private String getAliveTeamsNumber(){
        if (UHCTeamManager.getInstance().isActivated())
            return " §7(" + UHCTeamManager.getInstance().translateTeamAlivesNumber() + ")";
        else return "";
    }

    public void setLines(String ip){
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        objectiveSign.setDisplayName("§6" + UHC.getInstance().getUhcGame().getUhcConfig().getUHCName());

        switch (UHC.getInstance().getUhcGame().getGameState()){
            case STARTING:
            case WAITING:
                objectiveSign.setLine(0, "§f");
                objectiveSign.setLine(1, "§8§l» §e§lINFORMATIONS");
                objectiveSign.setLine(2, "  §8┃ §fHost: §6" + UHC.getInstance().getUhcGame().getUhcData().getHostName());
                objectiveSign.setLine(3, "  §8┃ §fWhiteList: §6" + isWl());
                objectiveSign.setLine(4, "  §8┃ §fJoueurs: §a" + UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().size() + "§7/" + UHC.getInstance().getUhcGame().getUhcConfig().getSlots());
                objectiveSign.setLine(5, "  §8┃ §fEquipes: §c" + UHCTeamManager.getInstance().getDisplayFormat());
                objectiveSign.setLine(6, "§c");
                objectiveSign.setLine(7, "§8§l» §e§lPARTIE");
                objectiveSign.setLine(8, "  §8┃ §fMode: §6" + UHC.getInstance().getUhcManager().getGamemode().getName());
                objectiveSign.setLine(9, "  §8┃ §fRègles: §e/rules");
                objectiveSign.setLine(10, "§b");
                objectiveSign.setLine(11, ip);
                break;
            case PLAYING:
                if (UHC.getInstance().getUhcManager().getGamemode().getUhcScoreboard() != null)
                    UHC.getInstance().getUhcManager().getGamemode().getUhcScoreboard().updateScoreboard(objectiveSign, uuid, ip);
                else {
                    objectiveSign.setLine(0, "§f");
                    objectiveSign.setLine(1, "§8§l» §e§lINFORMATIONS");
                    objectiveSign.setLine(2, "  §8┃ §fHost: §6" + UHC.getInstance().getUhcGame().getUhcData().getHostName());
                    objectiveSign.setLine(3, "  §8┃ §fJoueurs: §a" + UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().size() + getAliveTeamsNumber());
                    objectiveSign.setLine(4, "  §8┃ §fTimer: §c" + TimeUtils.getFormattedTime(UHC.getInstance().getUhcGame().getUhcData().getTimer()));
                    objectiveSign.setLine(5, "§c");
                    objectiveSign.setLine(6, "§8§l» §e§lPARTIE");
                    objectiveSign.setLine(7, "  §8┃ §fPvP: §c" + isPvp());
                    objectiveSign.setLine(8, "  §8┃ §fKills: §a" + uhcPlayer.getKills());
                    objectiveSign.setLine(9, "  §8┃ §fBordure: §e" + borderIsMove());
                    objectiveSign.setLine(10, "  §8┃ §fRayon: §e±" + GameUtils.getDecimalFormat(WorldManager.getGameWorld().getWorldBorder().getSize() / 2, true));
                    objectiveSign.setLine(11, "  §8┃ §fCentre: §3" + WorldUtils.distance(player.getLocation(), WorldManager.getCenterLoc()) + "m " + WorldUtils.Fleche(WorldUtils.Angle(player, WorldManager.getCenterLoc())));
                    objectiveSign.setLine(12, "§b");
                    objectiveSign.setLine(13, ip);
                }
                break;
            case FINISH:
                objectiveSign.setDisplayName("§6§lPartie terminée");
                objectiveSign.setLine(0, "§f");
                objectiveSign.setLine(1, "§8§l» §e§lINFORMATIONS");
                objectiveSign.setLine(2, "  §8┃ §fHost: §6" + UHC.getInstance().getUhcGame().getUhcData().getHostName());
                objectiveSign.setLine(3, "  §8┃ §fJoueurs: §a" + UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().size());
                objectiveSign.setLine(4, "  §8┃ §fTimer: §c" + TimeUtils.getFormattedTime(UHC.getInstance().getUhcGame().getUhcData().getTimer()));
                objectiveSign.setLine(5, "§c");
                objectiveSign.setLine(6, "§8§l» §e§lRESUME");
                objectiveSign.setLine(7, "  §8┃ §fKills: §a" + uhcPlayer.getKills());
                objectiveSign.setLine(8, "  §8┃ §fMorts: §c" + uhcPlayer.getDeath());
                objectiveSign.setLine(9, "  §8┃ §fDiamant(s) miné(s): §b" + uhcPlayer.getDiamondMined());
                objectiveSign.setLine(10, "  §8┃ §fOr(s) miné(s): §e"+ uhcPlayer.getGoldMined());
                objectiveSign.setLine(11, "§b");
                objectiveSign.setLine(12, ip);
                break;
        }

        objectiveSign.updateLines();
    }

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
    private String isWl(){
        if (UHC.getInstance().getUhcGame().getUhcData().isWhiteList())
            return "§a✔";
        else
            return "§c✘";
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}