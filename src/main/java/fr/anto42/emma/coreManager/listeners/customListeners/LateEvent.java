package fr.anto42.emma.coreManager.listeners.customListeners;

import fr.anto42.emma.coreManager.players.UHCPlayer;

public class LateEvent extends UHCEvent {

    private final UHCPlayer uhcPlayer;

    public LateEvent(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
    }

    public UHCPlayer getUhcPlayer() {
        return uhcPlayer;
    }
}
