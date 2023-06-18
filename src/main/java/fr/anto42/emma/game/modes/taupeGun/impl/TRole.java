package fr.anto42.emma.game.modes.taupeGun.impl;


import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.roles.Camp;
import fr.anto42.emma.coreManager.players.roles.Role;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.game.modes.taupeGun.roles.Kits;

public class TRole extends Role {
    private Kits kit;
    private UHCTeam taupeTeam;
    public TRole(String name, Camp camp, Module gamemode) {
        super(name, camp, gamemode);
    }

    public void setTaupeTeam(UHCTeam taupeTeam) {
        this.taupeTeam = taupeTeam;
    }

    public void claim(){
    }

    public void reveal(){}

    public UHCTeam getTaupeTeam() {
        return taupeTeam;
    }

    public void setKit(Kits kit) {
        this.kit = kit;
    }

    public Kits getKit() {
        return kit;
    }

    private boolean hasClaim = false;

    public void setHasClaim(boolean hasClaim) {
        this.hasClaim = hasClaim;
    }

    public boolean isHasClaim() {
        return hasClaim;
    }
}
