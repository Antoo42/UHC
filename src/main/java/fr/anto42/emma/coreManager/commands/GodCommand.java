package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand extends Command {
    public GodCommand() {
        super("god");
        super.getAliases().add("bypass");
        super.getAliases().add("operator");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(UHC.getInstance().getUhcGame().getUhcData().getHostPlayer() == uhcPlayer || UHC.getInstance().getUhcGame().getUhcData().getSpecList().contains(uhcPlayer) || uhcPlayer.isUHCOp() || UHC.getInstance().getUhcGame().getUhcData().getCoHostList().contains(uhcPlayer)){
            if(uhcPlayer.isUHCOp()){
                uhcPlayer.setUHCOp(false);
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §7Vous n'êtes plus opérateur de la partie !");
            }else{
                uhcPlayer.setUHCOp(true);
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §7Vous êtes désormais opérateur de la partie !");
            }
        }else
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §7Vous n'avez pas les permissions afin d'effectuer cette commande.");

        return false;
    }
}
