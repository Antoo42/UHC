package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.votes.VoteSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand extends Command {
    public VoteCommand() {
        super("vote");
    }

    VoteSystem voteSystem = VoteSystem.getInstance();

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(voteSystem.getVotedPlayer().contains(uhcPlayer)){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous avez déjà voté !");
            return true;
        }
        if(args.length == 0){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVeuillez indiquer votre vote ! §3(/vote <yes/no>)");
            return true;
        }
        uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Votre vote a bien été enregistré.");
        if(args[0].equalsIgnoreCase("yes")){
            voteSystem.addYes();
            voteSystem.getVotedPlayer().add(uhcPlayer);
        }else if(args[0].equalsIgnoreCase("no")){
            voteSystem.addNo();
            voteSystem.getVotedPlayer().add(uhcPlayer);
        }
        else {
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVeuillez indiquer votre vote ! §3(/vote <yes/no>)");
        }
        return false;
    }
}
