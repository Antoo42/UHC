package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DevCommand extends Command {
    private final DNModule dn;
    public DevCommand(DNModule dn){
        super("dev");
        this.dn = dn;
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        Player player = (Player) sender;
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if(player.getName().equals("Anto42_")){
            if(args.length < 1){
                player.sendMessage("/dev <type>");
            }else{
                if(uhcPlayer.getRole() == null){
                    String st = args[0];
                    int type = Integer.parseInt(st);
                    DNRole role = dn.getDnConfig().roleList.get(type);
                    uhcPlayer.setRole(role);
                }else{
                    player.sendMessage("Impossible action.");
                }
            }
        }else {
            uhcPlayer.sendClassicMessage("§cAlors oui mais non tu touches pas à ça toi");
        }
        return false;
    }
}
