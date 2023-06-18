package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Shinigami;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RevealCommand extends Command {
    private final DNModule dn;
    public RevealCommand(DNModule dn){
        super("reveal");
        this.dn = dn;
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        Player player = ((Player) sender);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if(uhcPlayer.getRole() instanceof Shinigami || uhcPlayer.getRole() instanceof Kira || (uhcPlayer.getRole() instanceof Mello && dn.melloType.get(uhcPlayer.getBukkitPlayer().getUniqueId()).equals("méchant"))){
            if(!dn.isReveal.get(player.getUniqueId())){
                dn.reveal(uhcPlayer);
            }
        }else {
            uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça !");
        }
        return false;
    }
}