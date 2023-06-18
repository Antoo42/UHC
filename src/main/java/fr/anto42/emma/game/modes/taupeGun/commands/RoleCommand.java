package fr.anto42.emma.game.modes.taupeGun.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoleCommand extends Command {
    public RoleCommand() {
        super("role");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer((Player) sender);
        if(uhcPlayer.getRole() != null){
            uhcPlayer.getRole().sendDesc();
        } else uhcPlayer.sendClassicMessage("§cVous n'avez pas de rôle !");
        return false;
    }
}
