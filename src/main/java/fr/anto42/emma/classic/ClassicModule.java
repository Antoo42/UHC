package fr.anto42.emma.classic;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.GameUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ClassicModule extends Module {
    public ClassicModule() {
        super("Classique", "Classique", new ItemCreator(Material.GOLDEN_APPLE).get());
        super.setConfigurable(false);
    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new ClassicListeners(this), UHC.getInstance());
    }

    private final UHCGame uhcGame = UHC.getInstance().getUhcGame();
    public void winTester(){
        if(!UHCTeamManager.getInstance().isActivated()){
            if(uhcGame.getUhcData().getUhcPlayerList().size() == 1){
                uhcGame.setGameState(GameState.FINISH);
                SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
                Bukkit.broadcastMessage("§7");
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélécitations au joueur " + uhcGame.getUhcData().getUhcPlayerList().get(0).getName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + uhcGame.getUhcData().getUhcPlayerList().get(0).getKills() + "§a !");
                Bukkit.broadcastMessage("§7");
                for(Player player : Bukkit.getOnlinePlayers()){
                    UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                    player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                    player.sendMessage("§7");
                    player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                    player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                    if(uhcPlayer.getRole() != null)
                        player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
                }

                Bukkit.broadcastMessage("§7");
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
                Bukkit.broadcastMessage("§7");
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                    Bukkit.shutdown();
                }, TimeUtils.minutes(5));            }
        }else if(UHCTeamManager.getInstance().getUhcTeams().size() == 1){
            uhcGame.setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            UHCTeam uhcTeam = UHCTeamManager.getInstance().getUhcTeams().get(0);
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélécitations à l'équipe " + uhcTeam.getDisplayName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + uhcTeam.getKillsTeam() + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if(uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        } else if(uhcGame.getUhcData().getUhcPlayerList().size() == 0){
            uhcGame.setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Oh mince, je n'ai pas regarder la partie... §3Qui a gagner ?");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if(uhcPlayer.getRole() != null)
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
}
