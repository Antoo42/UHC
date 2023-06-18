package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.TryStartEvent;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DevCommand extends Command {
    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    public DevCommand() {
        super("dev");
    }


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!commandSender.getName().equals("Anto42_")) return false;
        if (args[0].equalsIgnoreCase("start")){
            TryStartEvent tryStartEvent = new TryStartEvent();
            Bukkit.getServer().getPluginManager().callEvent(tryStartEvent);
            if (!tryStartEvent.isCancelled()){
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aLancement en démarage forcé!");
                uhc.startGame();
            }
        }
        return false;
    }

}
