package fr.anto42.emma.coreManager.players.roles;

import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;

import java.util.ArrayList;
import java.util.List;

public abstract class Camp {
    private final String name;
    private final String color;
    public Camp(String name, String color) {
        this.name = name;
        this.color = color;
    }

    private final List<UHCPlayer> players = new ArrayList<>();

    public List<UHCPlayer> getPlayers() {
        players.stream().filter(uhcPlayer -> uhcPlayer.getPlayerState() != UHCPlayerStates.ALIVE).forEach(players::remove);
        return players;
    }

    public String getName() {
        return name;
    }

    public String getColor(){return color;}
}
