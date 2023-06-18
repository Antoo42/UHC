package fr.anto42.emma.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {
    public static void playSoundToPlayer(Player player, Sound sound){
        player.playSound(player.getLocation(), sound, 1F, 1F);
    }
    public  static void playSoundToPlayer(Player player, String soundPath){
        player.playSound(player.getLocation(), soundPath,1F,1F);
    }
    public  static void playSoundToPlayer(Player player, String soundPath, Float volume, Float pitch){
        player.playSound(player.getLocation(), soundPath,volume,pitch);
    }
    public static void playSoundToAll(Sound sound){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation(), sound, 1F, 1F);
        }
    }
}
