package fr.anto42.emma.coreManager.customListeners;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class UHCEvent extends Event{

    private static HandlerList handlers;

    static{
        handlers = new HandlerList();
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public UHCGame getUHCGame(){
        return UHC.getInstance().getUhcGame();
    }
}