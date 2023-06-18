package fr.anto42.emma.game.modes.deathNoteV4.gameManager.commands;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.uis.MelloGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MelloCommand extends Command {
    private final DNModule dn;
    private final MelloGUI melloGUI;
    public MelloCommand(DNModule dn, MelloGUI melloGUI){
        super("mello");
        this.dn = dn;
        this.melloGUI = melloGUI;
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(uhcPlayer.getRole() instanceof Mello && dn.melloType.get(uhcPlayer.getBukkitPlayer().getUniqueId()) == null){
            melloGUI.open((Player) sender);
        }else {
            uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça !");
        }
        return false;
    }
}
