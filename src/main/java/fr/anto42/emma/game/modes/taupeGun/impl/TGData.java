package fr.anto42.emma.game.modes.taupeGun.impl;


import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TGData {
    private final List<UHCPlayer> revealPlayers = new ArrayList<>();
    private final HashMap<UHCTeam, Integer> uhcTeamIntegerHashMap = new HashMap<>();


    public List<UHCPlayer> getRevealPlayers() {
        return revealPlayers;
    }

    private final List<UHCTeam> teamList = new ArrayList<>();



    public HashMap<UHCTeam, Integer> getUhcTeamIntegerHashMap() {
        return uhcTeamIntegerHashMap;
    }

    public List<UHCTeam> getTeamList() {
        return teamList;
    }
}