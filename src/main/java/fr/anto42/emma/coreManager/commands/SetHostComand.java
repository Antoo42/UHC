package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.utils.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHostComand extends Command {

    public SetHostComand() {
        super("sethost");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player player = ((Player) sender);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if(player.isOp() || player.getName().equalsIgnoreCase("Anto42_")){
            if(strings.length == 0){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur.");
                return true;
            }
            Player target = Bukkit.getPlayer(strings[0]);
            if(target == null){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur connecté.");
                return true;
            }
            UHCPlayer uhctarget = UHC.getUHCPlayer(target);
            if(uhctarget == UHC.getInstance().getUhcGame().getUhcData().getHostPlayer()){
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
                return true;
            }
            if(UHC.getInstance().getUhcGame().getUhcData().getHostPlayer() != null){
                UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().getBukkitPlayer().getInventory().remove(Material.COMMAND);
            }
            SoundUtils.playSoundToPlayer(uhctarget.getBukkitPlayer(), Sound.LEVEL_UP);
            UHC.getInstance().getUhcGame().getUhcData().setHostPlayer(uhctarget);
            if(UHC.getInstance().getUhcGame().getGameState() == GameState.WAITING) {
                uhctarget.getBukkitPlayer().getInventory().clear();
                PlayersUtils.giveWaitingStuff(uhctarget.getBukkitPlayer());
            }
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le nouvel Host de la partie est désormais §a" + uhctarget.getName());
            if(UHC.getInstance().getUhcGame().getUhcConfig().getUHCName().equalsIgnoreCase("UHCHost"))
                UHC.getInstance().getUhcGame().getUhcConfig().setUHCName("Partie de " + uhctarget.getName());
        }
        return false;
    }
}