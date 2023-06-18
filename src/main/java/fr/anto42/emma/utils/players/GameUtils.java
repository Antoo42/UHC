package fr.anto42.emma.utils.players;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.roles.Camp;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class GameUtils {

    static int kills = 0;
    public static void onWin(Object object){
        if (object == null){
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Oh mince, je n'ai pas regarder la partie... §3Qui a gagner ?");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }else if (object instanceof Player){
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications au joueur " + ((Player) object).getDisplayName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + UHC.getUHCPlayer(((Player) object).getPlayer()).getKills() + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }else if (object instanceof Camp){
            ((Camp) object).getPlayers().forEach(uhcPlayer -> {
                kills = kills + uhcPlayer.getKills();
            });
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications au camp des §e" + ((Camp) object).getName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + kills + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }else if (object instanceof UHCTeam){
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications à l'équipe " + ((UHCTeam) object).getDisplayName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + ((UHCTeam) object).getKillsTeam() + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }
    }


    private static int groupsLimit = 5;

    public static int getGroupsLimit() {
        return groupsLimit;
    }

    public static void setGroupsLimit(int a) {
        groupsLimit = a;
    }

    public static void warnGroups(){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Title.sendFullTitle(onlinePlayer, 0, TimeUtils.seconds(2), 20, "§4Groupes de " + getGroupsLimit() + "", "§c§o⚠Veuillez à respecter les limites de groupes !");
        }
        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cPensez à respecter les groupes ! §3(" + getGroupsLimit() + ")");
        SoundUtils.playSoundToAll(Sound.GHAST_SCREAM);
    }

    public static boolean isDiamondArmor(ItemStack var1) {
        Material var2 = var1.getType();
        return var2 == Material.DIAMOND_HELMET || var2 == Material.DIAMOND_CHESTPLATE || var2 == Material.DIAMOND_LEGGINGS || var2 == Material.DIAMOND_BOOTS;
    }

    public static boolean isIronArmor(ItemStack var1) {
        Material var2 = var1.getType();
        return var2 == Material.IRON_HELMET || var2 == Material.IRON_CHESTPLATE || var2 == Material.IRON_LEGGINGS || var2 == Material.IRON_BOOTS;
    }

    public static String getDecimalFormat(double i, boolean format) {
        if (format) {
            String dFormat = new DecimalFormat("##.#").format(i);
            if (!dFormat.contains(".")) {
                return dFormat + ".0";
            } else {
                return dFormat;
            }
        } else {
            return new DecimalFormat("##.#").format(i);
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
