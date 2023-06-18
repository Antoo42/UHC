package fr.anto42.emma.coreManager.teams;

import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class UHCTeam {
    private final String name;
    private String prefix;
    private final String color;
    private final DyeColor dyeColor;
    private final int colorNumber;
    private final String uuid;
    private final Team team;
    private int killsTeam = 0;
    private Location startLoc;

    public UHCTeam(String name, String prefix, String color, DyeColor dyeColor, int colorNumber, String uuid, Team team) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.dyeColor = dyeColor;
        this.colorNumber = colorNumber;
        this.uuid = uuid;
        this.team = team;
    }

    public String getDisplayName(){
        return this.color + this.name;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getColor() {
        return color;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public String getUuid() {
        return uuid;
    }

    public Team getTeam() {
        return team;
    }


    private final List<UHCPlayer> uhcPlayerList = new ArrayList<>();

    public List<UHCPlayer> getUhcPlayerList() {
        return uhcPlayerList;
    }

    public List<UHCPlayer> getAliveUhcPlayers(){
        List<UHCPlayer> list = new ArrayList<>(uhcPlayerList);
        list.stream().filter(uhcPlayer -> uhcPlayer.getPlayerState() != UHCPlayerStates.ALIVE).forEach(list::remove);
        return list;
    }

    public int getAlivePlayersAmount(){
        if (uhcPlayerList.isEmpty())
            return 0;
        else
            return (int) uhcPlayerList.stream().filter(uhcPlayer -> uhcPlayer.getPlayerState() == UHCPlayerStates.ALIVE).count();
    }

    public int getPlayersAmount(){
        if (uhcPlayerList.isEmpty())
            return 0;
        else
            return uhcPlayerList.size();
    }

    public boolean isAlive(){
        return getAlivePlayersAmount() != 0;
    }

    public void destroy(){
        UHCTeamManager.getInstance().getUhcTeams().remove(this);
    }

    public int getKillsTeam() {
        return killsTeam;
    }

    public void setKillsTeam(int killsTeam) {
        this.killsTeam = killsTeam;
    }

    public Location getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(Location startLoc) {
        this.startLoc = startLoc;
    }

    public int getColorNumber() {
        return colorNumber;
    }
}
