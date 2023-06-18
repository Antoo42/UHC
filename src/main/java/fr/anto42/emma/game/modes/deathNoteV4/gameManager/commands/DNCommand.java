package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DNCommand extends Command {
    public DNCommand(){
        super("dn");
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player player = ((Player) sender);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if(uhcPlayer.getRole() == null){
            uhcPlayer.sendClassicMessage("§cVous n'avez pas de rôle.");
        }else {
            uhcPlayer.getRole().sendDesc();
        }
        return false;
    }
}
