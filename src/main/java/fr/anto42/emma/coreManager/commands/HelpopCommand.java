package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.chat.InteractiveMessage;
import fr.anto42.emma.utils.chat.InteractiveMessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCommand extends Command {
    public HelpopCommand() {
        super("helpop");
    }

    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer((Player) sender);
        if(args.length == 0){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("helpopPrefix").replace("&", "§") + " §cVeuillez à inscrire un message à transmettre ! §3(/helpop <message>)");
            SoundUtils.playSoundToPlayer(uhcPlayer.getBukkitPlayer(), Sound.VILLAGER_NO);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for(String l : args){
            sb.append(l + " ");
        }
        uhc.getUhcData().getSpecList().forEach(uhcPlayer1 -> {
            uhcPlayer1.sendMessage(UHC.getInstance().getConfig().getString("helpopPrefix").replace("&", "§") + " §3" + uhcPlayer.getName() + "§7: " + sb.toString());
            (new InteractiveMessage().add(new InteractiveMessageBuilder("§e§l[SE TELEPORTER]")
                    .setHoverMessage("§8§l» §6Cliquez§f pour vous téléporter à " + uhcPlayer.getName()).setClickAction(ClickEvent.Action.RUN_COMMAND, "/spec tp " + uhcPlayer.getBukkitPlayer().getName())
                    .build()))
                    .add("   ")
                    .add(new InteractiveMessageBuilder("§c§l[ENVOYER UN MESSAGE]")
                            .setHoverMessage("§8§l» §6Cliquez §fpour envoyer un message privé à " + uhcPlayer.getBukkitPlayer().getDisplayName()).setClickAction(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + uhcPlayer + " ").build()).sendMessage(uhcPlayer1.getBukkitPlayer());
        });
        uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("helpopPrefix").replace("&", "§") + " §aVotre demande a été transmise avec succés !");
        return false;
    }
}