package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.chat.InteractiveMessage;
import fr.anto42.emma.utils.chat.InteractiveMessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DocCommand extends Command {
    public DocCommand() {
        super("doc");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if(UHC.getInstance().getUhcManager().getGamemode().getDocLink() != null)
            (new InteractiveMessage().add(new InteractiveMessageBuilder(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Veuillez cliquer §eici §7pour ouvrir le document du mode de jeu.").setHoverMessage("§8§l» §6Cliquez§f pour ouvrir.").setClickAction(ClickEvent.Action.OPEN_URL, UHC.getInstance().getUhcManager().getGamemode().getDocLink()).build())).sendMessage(((Player) sender));
        else {
            sender.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cAucun document n'est renseigné à propos de ce mode de jeu...");
            SoundUtils.playSoundToPlayer(Bukkit.getPlayer(sender.getName()), Sound.VILLAGER_NO);
        }
        return false;
    }
}
