package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends Command {
    public SpawnCommand() {
        super("spawn");
    }





    @Override
    public boolean execute(CommandSender sender, String s, String[] strings)
    {if (!UHC.getInstance().getUhcGame().getGameState().equals(GameState.WAITING)){
        UHC.getUHCPlayer(((Player) sender)).sendClassicMessage("§cVous ne pouvez pas effectuer cette commande maintenant !");
        SoundUtils.playSoundToPlayer(((Player) sender).getPlayer(), Sound.VILLAGER_NO);
        return true;
    }
        ((Player) sender).teleport(UHC.getInstance().getWorldManager().getSpawnLocation());
        ((Player) sender).setGameMode(GameMode.SURVIVAL);
        return false;
    }
}
