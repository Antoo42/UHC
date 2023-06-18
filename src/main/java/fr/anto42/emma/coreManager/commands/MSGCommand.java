package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSGCommand extends Command {
    public MSGCommand() {
        super("msg");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(!UHC.getInstance().getConfig().getBoolean("privatemessages")){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cLe système de message est désactivés sur ce serveur !");
            return true;
        }
        if (strings.length == 0 || strings.length == 1) {
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cMerci de préciser un joueur ainsi que le message que vous souhaitez lui envoyer §3(/msg <Player> <message>)§c.");
            return true;
        }
        Player target = Bukkit.getPlayer(strings[0]);
        if (target == null) {
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cMerci de préciser un joueur connecté.");
            return true;
        }
        if(target == uhcPlayer.getBukkitPlayer()){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("messagesPrefix").replace("&", "§") + " §cAttendez... Vous ne pouvez pas faire ça !");
            return true;
        }
        UHCPlayer uhcTarget = UHC.getUHCPlayer(target);

        StringBuilder stringBuilder = new StringBuilder();
        strings[0] = strings[0].replace(uhcTarget.getName(), "");
        for (String l : strings) {
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