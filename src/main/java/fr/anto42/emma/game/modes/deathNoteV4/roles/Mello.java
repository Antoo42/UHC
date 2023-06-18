package fr.anto42.emma.game.modes.deathNoteV4.roles;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Random;

public class Mello extends DNRole {
    private final DNModule dn;
    public Mello(Module gamemode, DNModule dn){
        super("Mello", null, gamemode);
        this.dn = dn;
    }

    @Override
    public void setRole(){
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            if(dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()) == null) {
                int type = new Random().nextInt(dn.melloList.size());
                String role = dn.melloList.get(type);
                getUhcPlayer().sendClassicMessage("§cVous avez mis trop de temps pour choisir votre forme de mello !");
                SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
                dn.melloType.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), role);
                if (role.equals("gentil")) {
                    dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 1);
                } else if (role.equals("jaloux")) {
                    dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 2);
                    dn.canInvest.add(getUhcPlayer());
                    dn.melloInvest.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 0);
                } else if (role.equals("méchant")) {
                    dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 2);
                }
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    getUhcPlayer().getRole().sendDesc();
                }, 10);
            }
        }, 120*20);
    }

    @Override
    public void sendDesc() {
        if(dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()) == null){
            getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName());
            getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §b???.");

            getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
            getUhcPlayer().sendMessage("  §8• §fVous pouvez choisir votre forme à l'aide de la commande /mello.");
        }else if(dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("gentil")){
            getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName() + "§b" + dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()));
            getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bvotre équipe d'origine.");

            getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
            getUhcPlayer().sendMessage("  §8• §fVous pouvez enquêter un joueur de votre équipe grâce à la commande /enquête <pseudo>.");
            for(UHCPlayer near : getUhcPlayer().getUhcTeam().getUhcPlayerList()){
                if(near.getRole() instanceof Near){
                    getUhcPlayer().sendMessage("  §8• §fLe Near de votre équipe est: §2" + near.getName() + "§7.");
                }
            }
        }else if(dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("jaloux")){
            getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName() + "§b" + dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()));
            getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bvotre équipe d'origine.");

            getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
            getUhcPlayer().sendMessage("  §8• §fVous pouvez enquêter deux joueurs de votre équipe grâce à la commande /enquête <pseudo>.");
            getUhcPlayer().sendMessage("  §8• §fSi vous arrivez à démasquer le Kira, vous échopperer de tout les effets des enquêteurs, mais si vous échouez deux fois, vous viendrez alors à rejoindre votre tombe prématurémment.");
        }else if (dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("méchant")){
            getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName() + "§b" + dn.melloType.get(getUhcPlayer().getBukkitPlayer().getUniqueId()));
            getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bseul");

            getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
            getUhcPlayer().sendMessage("  §8• §fVous êtes insensible au pouvoir du DeathNote, et vous ralentissez la vite d'enquête du Near par deux.");
            getUhcPlayer().sendMessage("  §8• §fQuand le moment viendra, vous pourrez révéler votre identité à l'aide de la commande /reveal. Cette dernière vous permmettra de gagner cinq coeurs supplémentaires.");
            for(UHCPlayer near : getUhcPlayer().getUhcTeam().getUhcPlayerList()){
                if(near.getRole() instanceof Near){
                    getUhcPlayer().sendMessage("  §8• §fLe Near de votre équipe est: §2" + near.getName() + "§7.");
                }
            }
        }

    }
}
