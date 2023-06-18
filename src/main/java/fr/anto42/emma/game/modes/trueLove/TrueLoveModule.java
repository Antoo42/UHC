package fr.anto42.emma.game.modes.trueLove;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.game.modes.trueLove.uis.TrueLoveGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TrueLoveModule extends Module {
    public TrueLoveModule() {
        super("§5§lTRUE-LOVE UHC", "True Love UHC", new ItemCreator(Material.DIAMOND).get());
        super.setDev("Anto42_");
        setConfigurable(true);
        setkInventory(new TrueLoveGUI(this).getkInventory());
        setUhcScoreboard(new TrueLoveScoreboard());
        super.getDesc().add("§8┃ §fDès l'activation du §aPvP§f, les joueurs seront désormais");
        super.getDesc().add("§8┃ §fen §déquipe avec le premier joueur qu'ils rencontreront§f.");
    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new TrueLoveListeners(this), UHC.getInstance());
        new LoveManager(this).runTaskTimer(UHC.getInstance(), 0, 2);
        UHCTeamManager.getInstance().setActivated(true);
        UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer -> uhcPlayer.getUhcTeam() != null).forEach(UHCPlayer::leaveTeam);
        UHCTeamManager.getInstance().getUhcTeams().clear();
        UHCTeamManager.getInstance().setSlots(2);
    }

    private int radius = 5;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void addRadius() {
        if (radius == 20) return;
        radius++;
    }

    public void reduceRadius() {
        if (radius == 1) return;
        radius--;
    }

    private final UHCGame uhcGame = UHC.getInstance().getUhcGame();

    @Override
    public void winTester() {
        if (UHCTeamManager.getInstance().getUhcTeams().size() == 1 && UHCTeamManager.getInstance().getUhcTeams().get(0).getPlayersAmount() == uhcGame.getUhcData().getUhcPlayerList().size()) {
            uhcGame.setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            UHCTeam uhcTeam = UHCTeamManager.getInstance().getUhcTeams().get(0);
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications à l'équipe " + uhcTeam.getDisplayName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + "§a avec §b" + uhcTeam.getKillsTeam() + "§a kills !");
            Bukkit.broadcastMessage("§7");
            for (Player player : Bukkit.getOnlinePlayers()) {
                UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
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
            PlayersUtils.finishToSpawn();
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        } else if (uhcGame.getUhcData().getUhcPlayerList().size() == 0) {
            uhcGame.setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Oh mince, je n'ai pas regarder la partie... §3Qui a gagner ?");
            Bukkit.broadcastMessage("§7");
            for (Player player : Bukkit.getOnlinePlayers()) {
                UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
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
            PlayersUtils.finishToSpawn();
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }
    }
}
