package fr.anto42.emma.coreManager.roles;

import fr.anto42.emma.coreManager.players.UHCPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class Camp {
    private final String name;
    public Camp(String name) {
        this.name = name;
    }

    private final List<UHCPlayer> alivePlayers = new ArrayList<>();

    public List<UHCPlayer> getAlivePlayers() {
        return alivePlayers;
    }

    public String getName() {
        return name;
    }
}
