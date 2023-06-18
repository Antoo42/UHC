package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScenariosCommand extends Command {
    public ScenariosCommand() {
        super("scenarios");
        super.getAliases().add("sc");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(UHC.getInstance().getUhcManager().getScenarioManager().getActivatedScenarios().size() == 0){
            uhcPlayer.sendMessage("§aAucun scénario n'est activé !");
            return true;
        }

        if(UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()){
            uhcPlayer.sendClassicMessage("§cZut ! Vous ne pouvez pas voir les scénarios activés !");
            SoundUtils.playSoundToPlayer(uhcPlayer.getBukkitPlayer(), Sound.VILLAGER_NO);
            return true;
        }

        uhcPlayer.sendClassicMessage("§7Voici la liste des scénarios §aactivés§7:");
        UHC.getInstance().getUhcManager().getScenarioManager().getActivatedScenarios().forEach(uhcScenario -> {
            uhcPlayer.sendMessage("§7- §e" + uhcScenario.getName());
        });


        return false;
    }
}
