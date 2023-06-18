package fr.anto42.emma.coreManager.roles;

import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayer;

public abstract class Role {
    private final String name;
    private final Camp camp;
    private final Module gamemode;
    private UHCPlayer uhcPlayer;

    public UHCPlayer getUhcPlayer() {
        return uhcPlayer;
    }

    public void setUhcPlayer(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
    }

    public Role(String name, Camp camp, Module gamemode) {
        this.name = name;
        this.camp = camp;
        this.gamemode = gamemode;
    }

    public String getName() {
        return name;
    }

    public Camp getCamp() {
        return camp;
    }

    public Module getGamemode() {
        return gamemode;
    }

    public void sendDesc(){}

    public void setRole(){}
}
