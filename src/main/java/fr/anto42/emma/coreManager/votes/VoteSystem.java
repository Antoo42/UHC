package fr.anto42.emma.coreManager.votes;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VoteSystem {
    private static final VoteSystem voteSystem = new VoteSystem();
    private static VoteSystem instance;

    public VoteSystem() {
        instance = this;
    }

    private int yes = 0;
    private int no = 0;
    private int t = 0;
    private boolean vote = false;

    final List<UHCPlayer> votedPlayer = new ArrayList<>();

    public void startVote(String message){
        votedPlayer.clear();
        yes = 0;
        no = 0;
        t = 0;
        vote = true;
        SoundUtils.playSoundToAll(Sound.LEVEL_UP);
        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §eUn nouveau vote est en cours sur le sujet suivant: §3" + message + " §e! §b(/vote)");
        (new BukkitRunnable() {
            @Override
            public void run() {
                Title.sendActionBar("§8§l» §6" + message + " §8┃ §aoui: §e" + yes + " §8┃ §cnon: §e" + no + " §7(Temps restant: §b" + (30-t) + " secondes§7)");
                if (t == 30){
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") +  " §7Voici le résultat au vote §3" + message +"§7:");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("§8§l» §aoui: §e" + yes);
                    Bukkit.broadcastMessage("§8§l» §cnon: §e" + no);
                    vote = false;
                    this.cancel();
                }
                t++;
            }
        }).runTaskTimer(UHC.getInstance(), 0L, 20L);
    }

    public static VoteSystem getInstance() {
        return instance;
    }

    public List<UHCPlayer> getVotedPlayer() {
        return votedPlayer;
    }


    public void addYes(){
        yes = yes + 1;
    }

    public void addNo(){
        no = no + 1;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
