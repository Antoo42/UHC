package fr.anto42.emma.coreManager.customListeners;

import fr.anto42.emma.coreManager.players.UHCPlayer;

public class DeathEvent extends UHCEvent{
    private final UHCPlayer victim, killer;

    public DeathEvent(UHCPlayer victim, UHCPlayer killer) {
        this.victim = victim;
        this.killer = killer;
    }

    public UHCPlayer getVictim() {
        return victim;
    }

    public UHCPlayer getKiller() {
        return killer;
    }
}
