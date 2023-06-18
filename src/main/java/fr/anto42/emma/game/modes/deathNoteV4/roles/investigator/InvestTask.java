package fr.anto42.emma.game.modes.deathNoteV4.roles.investigator;

import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.scheduler.BukkitRunnable;

public class InvestTask extends BukkitRunnable {
    private final UHCPlayer investigator;
    private final DNModule dn;
    private final UHCPlayer target;

    public InvestTask(UHCPlayer investigator, DNModule dn, UHCPlayer target) {
        this.investigator = investigator;
        this.dn = dn;
        this.target = target;
    }

    double points = GameUtils.getModule().getPointsForEnquete();
    double multi = 1;
    @Override
    public void run() {
        for(UHCPlayer mello : investigator.getUhcTeam().getUhcPlayerList()){
            if(mello.getRole() instanceof Mello){
                if(dn.melloType.get(mello.getBukkitPlayer().getUniqueId()).equals("gentil")){
                    multi = 1.5;
                }
            }
        }
        Title.sendActionBar(investigator.getBukkitPlayer(), "§8§l» §7Points restants pour l'enquête : §b" + points);
        if(investigator.getBukkitPlayer().getLocation().distance(target.getBukkitPlayer().getLocation()) >= 7){
            points = points - 1*multi;
        }else if(investigator.getBukkitPlayer().getLocation().distance(target.getBukkitPlayer().getLocation()) <= 6 && investigator.getBukkitPlayer().getLocation().distance(target.getBukkitPlayer().getLocation()) > 1){
            points = points - 5*multi;
        }else if (investigator.getBukkitPlayer().getLocation().distance(target.getBukkitPlayer().getLocation()) <= 1){
            points = points - 10*multi;
        }
        if(points <= 1){
            if(investigator.getRole() instanceof Mello){
                if(target.getRole() instanceof Kira){
                    dn.seeWhenDN.add(target);
                    dn.canSeeKiraChat.add(target);
                    dn.canSeeRealDeath.add(target);
                }else {
                    dn.melloInvest.put(investigator.getBukkitPlayer().getUniqueId(), dn.melloInvest.get(investigator.getBukkitPlayer().getUniqueId()) + 1);
                    if(dn.melloInvest.get(investigator.getBukkitPlayer().getUniqueId()) == 2){
                        investigator.getBukkitPlayer().setMaxHealth(0);
                    }
                }
            }
            if(dn.aura.get(target.getBukkitPlayer().getUniqueId()) == 1){
                investigator.sendClassicMessage("§7Le joueur §b" + target.getName() + " §7est §a§lInnocent§7.");
            }else if (dn.aura.get(target.getBukkitPlayer().getUniqueId()) == 2){
                investigator.sendClassicMessage("§7Le joueur §b" + target.getName() + " §7est §6§lSuspect§7.");
            }else {
                investigator.sendClassicMessage("§7Le joueur §b" + target.getName() + " §7est un §4§lTraitre§7.");
            }
            this.cancel();
        }
    }
}
