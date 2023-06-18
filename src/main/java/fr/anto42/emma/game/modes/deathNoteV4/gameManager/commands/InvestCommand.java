package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Near;
import fr.anto42.emma.game.modes.deathNoteV4.roles.investigator.InvestTask;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvestCommand extends Command {
    private final DNModule dn;
    public InvestCommand(DNModule dn){
        super("enquête");
        super.getAliases().add("invest");
        this.dn = dn;
    }
    int time = 20;
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(!dn.canInvest.contains(uhcPlayer)){
            uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça !");
        }else {
            if(args.length < 1){
                uhcPlayer.sendMessage("§c/enquête <joueur>");
            } else {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if(targetPlayer == null){
                    uhcPlayer.sendClassicMessage("§c/enquête <joueur>");
                    return true;
                }
                if(targetPlayer.getName().equals(uhcPlayer.getName())){
                    uhcPlayer.sendMessage("§cAvez-vous un problême mental ? Apprends à lire ton rôle (/dn)");
                    return true;
                }
                UHCPlayer target = UHC.getUHCPlayer(targetPlayer);


                if(target.getUhcTeam() != uhcPlayer.getUhcTeam()){
                    uhcPlayer.sendMessage("");
                    return true;
                }

                if(uhcPlayer.getRole() instanceof Mello){
                    InvestTask task = new InvestTask(uhcPlayer, GameUtils.getModule(), target);
                    task.runTaskTimer(UHC.getInstance(), 0, 10);
                } else{

                    dn.canInvest.remove(uhcPlayer);
                    if(uhcPlayer.getRole() instanceof Near){
                        uhcPlayer.getBukkitPlayer().setMaxHealth(14);
                        for(UHCPlayer mello : uhcPlayer.getUhcTeam().getUhcPlayerList()){
                            if(mello.getRole() instanceof Mello && dn.melloType.get(mello.getBukkitPlayer().getUniqueId()) == "méchant")
                                time = 40;
                            else
                                time = 20;
                        }

                        InvestTask task = new InvestTask(uhcPlayer, GameUtils.getModule(), target);
                        task.runTaskTimer(UHC.getInstance(), 0, time);
                    }else{
                        InvestTask task = new InvestTask(uhcPlayer, GameUtils.getModule(), target);
                        task.runTaskTimer(UHC.getInstance(), 0, 20);
                    }
                }
            }
        }
        return false;
    }
}