package fr.anto42.emma.coreManager.listeners.customListeners;

import org.bukkit.event.Cancellable;

public class TryStartEvent extends UHCEvent implements Cancellable {
    private boolean cancel = false;
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        cancel = b;
    }
}
