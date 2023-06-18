package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

public class ReplyCommand extends Command {
    public ReplyCommand() {
        super("reply");
        super.getAliases().add("r");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(UHC.getInstance().getMessageManager().recentlyMessaged.get(uhcPlayer.getBukkitPlayer()) == null){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cVous n'avez personne à qui répondre !");
            return true;
        }
        if(args.length == 0){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cVeuillez indiquer un message à transmettre ! §3(/r <message>)");
            return true;
        }

        Player target = UHC.getInstance().getMessageManager().recentlyMessaged.get(uhcPlayer.getBukkitPlayer());
        UHCPlayer uhcTarget = UHC.getUHCPlayer(target);

        StringBuilder stringBuilder = new StringBuilder();
        for (String l : args) {
            stringBuilder.append(l + " ");
        }

        uhcTarget.sendMessage("§b§l" + uhcPlayer.getName() + " §8§l» §eVous§7: " + stringBuilder.toString());
        uhcPlayer.sendMessage("§b§lVous §8§l» §e" + uhcTarget.getName() + "§7: " + stringBuilder.toString());
        UHC.getInstance().getUhcGame().getUhcData().getSpyList().forEach(uhcPlayer1 -> {
            uhcPlayer1.sendMessage("§b§lMSG §e(" + uhcPlayer + " §8§l» §e" + uhcTarget + ")§7: "+ stringBuilder.toString());
        });
        UHC.getInstance().getMessageManager().recentlyMessaged.put(uhcTarget.getBukkitPlayer(), uhcPlayer.getBukkitPlayer());
        return false;
    }
}
