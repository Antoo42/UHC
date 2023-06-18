package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackupCommand extends Command {
    public BackupCommand() {
        super("backup");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer((Player) sender);
        uhcPlayer.getBukkitPlayer().openInventory(uhcPlayer.getBackupInventory());
        return false;
    }
}
