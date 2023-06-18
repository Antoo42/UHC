package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WinTest extends Command {
    public WinTest() {
        super("wintest");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if(!sender.isOp())
            return true;
        if(UHC.getInstance().getUhcGame().getGameState() != GameState.PLAYING)
            return true;
        UHC.getInstance().getUhcManager().getGamemode().winTester();
        return false;
    }
}
