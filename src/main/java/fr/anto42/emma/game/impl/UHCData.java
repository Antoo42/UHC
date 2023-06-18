package fr.anto42.emma.game.impl;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import org.bukkit.Location;

import java.util.*;

public class UHCData {

    private UHCPlayer hostPlayer;

    public UHCPlayer getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(UHCPlayer hostPlayer) {
        this.hostPlayer = hostPlayer;
    }
    public String getHostName(){
        if (hostPlayer != null)
            return hostPlayer.getName();
        else
            return "§cAucun";
    }

    private boolean discordSend = false;

    public boolean isDiscordSend() {
        return discordSend;
    }

    public void setDiscordSend(boolean discordSend) {
        this.discordSend = discordSend;
    }

    private final List<UHCPlayer> coHostList = new ArrayList<>();

    public List<UHCPlayer> getCoHostList() {
        return coHostList;
    }

    private boolean netherPreload = false;
    private boolean endPreload = false;

    public boolean isNetherPreload() {
        return netherPreload;
    }

    public void setNetherPreload(boolean netherPreload) {
        this.netherPreload = netherPreload;
    }

    public boolean isEndPreload() {
        return endPreload;
    }

    public void setEndPreload(boolean endPreload) {
        this.endPreload = endPreload;
    }

    private boolean preloadFinished = false;

    public boolean isPreloadFinished() {
        return preloadFinished;
    }

    public void setPreloadFinished(boolean preloadFinished) {
        this.preloadFinished = preloadFinished;
    }

    public boolean isRoles() {
        return roles;
    }

    public void setRoles(boolean roles) {
        this.roles = roles;
    }

    private boolean roles = false;

    private int timer = 0;

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    private final List<UHCPlayer> uhcPlayerList = new ArrayList<>();

    public List<UHCPlayer> getUhcPlayerList() {
        return uhcPlayerList;
    }
    private final List<UHCTeam> uhcTeamList = new ArrayList<>();

    public List<UHCTeam> getUhcTeamList() {
        return uhcTeamList;
    }

    private final List<Location> locationSpawnList = new ArrayList<>();

    public List<Location> getLocationSpawnList() {
        return locationSpawnList;
    }

    private final List<UHCPlayer> specList = new ArrayList<>();

    public List<UHCPlayer> getSpecList() {
        return specList;
    }

    private final List<UHCPlayer> alerts = new ArrayList<>();

    public List<UHCPlayer> getAlerts() {
        return alerts;
    }

    private boolean pvp = false;

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    private boolean whiteList = UHC.getInstance().getConfig().getBoolean("whiteListOnTheStart");

    private final List<String> preWhitelist = new ArrayList<>();

    public List<String> getPreWhitelist() {
        return preWhitelist;
    }

    public boolean isWhiteList() {
        return whiteList;
    }

    public void setWhiteList(boolean whiteList) {
        this.whiteList = whiteList;
    }

    private final List<UUID> whiteListPlayer = new ArrayList<>();

    public List<UUID> getWhiteListPlayer() {
        return whiteListPlayer;
    }

    private boolean chat = true;

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    private boolean borderMove = false;

    public boolean isBorderMove() {
        return borderMove;
    }

    public void setBorderMove(boolean borderMove) {
        this.borderMove = borderMove;
    }

    private int borderRaduis;

    public int getBorderRaduis() {
        return borderRaduis;
    }

    public void setBorderRaduis(int borderRaduis) {
        this.borderRaduis = borderRaduis;
    }

    private final List<UHCPlayer> spyList = new ArrayList<>();

    public List<UHCPlayer> getSpyList() {
        return spyList;
    }
}
