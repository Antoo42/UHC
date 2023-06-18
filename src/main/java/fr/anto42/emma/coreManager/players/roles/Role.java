package fr.anto42.emma.coreManager.players.roles;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.UHCManager;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.Bukkit;

public abstract class Role {
    private final String name;
    private final Camp camp;
    private UHCPlayer uhcPlayer;
    private final Module gameMode;

    public UHCPlayer getUhcPlayer() {
        return uhcPlayer;
    }

    public void setUhcPlayer(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
    }

    public Role(String name, Camp camp, Module gamemode) {
        this.name = name;
        this.camp = camp;
        this.gameMode = gamemode;
    }

    public String getName() {
        return name;
    }

    public Camp getCamp() {
        return camp;
    }


    public Module getGameMode() {
        return gameMode;
    }

    public void sendDesc(){}

    public void setRole(){
    }
    public void onDeath() {}
}
