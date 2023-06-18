package fr.anto42.emma.game.modes.taupeGun.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.taupeGun.roles.Taupe;
import fr.anto42.emma.game.modes.taupeGun.impl.TRole;
import fr.anto42.emma.game.modes.taupeGun.roles.SuperTaupe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand extends Command {
    public ClaimCommand(){
        super("claim");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(uhcPlayer.getRole() instanceof Taupe || uhcPlayer.getRole() instanceof SuperTaupe){
            ((TRole) uhcPlayer.getRole()).claim();
        }else{
            uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça !");
        }
        return false;
    }
}
