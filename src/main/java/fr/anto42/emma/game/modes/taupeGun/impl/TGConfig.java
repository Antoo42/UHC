package fr.anto42.emma.game.modes.taupeGun.impl;

public class TGConfig {
    int taupeSlots = 3;
    int taupePerTeams = 2;
    boolean superTaupe = true;
    int timerSuperTaupe = 10;

    public int getTaupeSlots() {
        return taupeSlots;
    }

    public void setTaupeSlots(int taupeSlots) {
        this.taupeSlots = taupeSlots;
    }

    public int getTaupePerTeams() {
        return taupePerTeams;
    }

    public void setTaupePerTeams(int taupePerTeams) {
        this.taupePerTeams = taupePerTeams;
    }

    public boolean isSuperTaupe() {
        return superTaupe;
    }

    public void setSuperTaupe(boolean superTaupe) {
        this.superTaupe = superTaupe;
    }

    public int getTimerSuperTaupe() {
        return timerSuperTaupe;
    }

    public void setTimerSuperTaupe(int timerSuperTaupe) {
        this.timerSuperTaupe = timerSuperTaupe;
    }
}
