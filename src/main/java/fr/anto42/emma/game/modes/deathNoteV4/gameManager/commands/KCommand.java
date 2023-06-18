package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;

import com.google.common.base.Joiner;
import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KCommand extends Command {
    private final DNModule dn;
    public KCommand(DNModule dn){
        super("k");
        this.dn = dn;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(!(uhcPlayer.getRole() instanceof Kira) || uhcPlayer.getRole() == null){
            uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça !");
        } else {
            if(args.length < 1){
                uhcPlayer.sendClassicMessage("§cL'utilisation correcte de cette commande est: /k <message>");
            }else{
                for (UHCPlayer viewer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()){
                    if(dn.canSeeKiraChat.contains(viewer)){
                        if(viewer.getRole() instanceof Kira){
                            viewer.sendMessage(uhcPlayer.getUhcTeam().getPrefix() + "§cKira §l" + uhcPlayer.getName() + " §8» §6" + Joiner.on(" ").join(args));
                        }else {
                            viewer.sendMessage("§cKira §l??? §8» §6" + Joiner.on(" ").join(args));
                        }
                    }
                }
            }
        }
        return false;
    }
}
