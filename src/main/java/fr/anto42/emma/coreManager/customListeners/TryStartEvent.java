package fr.anto42.emma.coreManager.customListeners;

import org.bukkit.event.Cancellable;

public class TryStartEvent extends UHCEvent implements Cancellable {
    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {
    }
}
