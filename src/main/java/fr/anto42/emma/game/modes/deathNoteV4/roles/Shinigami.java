package fr.anto42.emma.game.modes.deathNoteV4.roles;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Shinigami extends DNRole implements Listener {
    private final DNModule dn;
    public Shinigami(Module gamemode, DNModule dn){
        super("Shinigami", null, gamemode);

        this.dn = dn;
    }

    private String kiraName = "Aucun Kira";

    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName());
        getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bseul(e).");

        getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
        getUhcPlayer().sendMessage("  §8• §fVous disposez de la commande /reveal, qui vous permettra de révéler votre identitée et vous permet de gagner une pomme d'or ainsi que l'effet de Résistance I permanent.");
        getUhcPlayer().sendMessage("  §8• §fVous connaissez également le pseudonyme du Kira de votre équipe:");

        if(kiraName.equals("Aucun Kira")){
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                for(UHCPlayer shini : getUhcPlayer().getUhcTeam().getUhcPlayerList()) {
                    if (shini.getRole() instanceof Kira) {
                        getUhcPlayer().sendMessage("  §8• §fLe Kira de votre équipe est: §c" + shini.getName());
                        this.kiraName = shini.getName();
                    }
                }
            }, 10);
        } else {
            getUhcPlayer().sendMessage("  §8• §fLe Kira de votre équipe est: §c" + kiraName);
        }
    }

    @Override
    public void setRole() {
        dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 3);
    }

    @EventHandler
    public void onPlayerDeath(DeathEvent deathEvent){
        if(deathEvent.getKiller() == getUhcPlayer()){
            Player player = deathEvent.getKiller().getBukkitPlayer();
            player.setMaxHealth(player.getMaxHealth() + 1);
        }
        UHCPlayer uhcplayer = deathEvent.getVictim();
        if(uhcplayer.getUhcTeam() == getUhcPlayer().getUhcTeam() && uhcplayer.getRole() instanceof Kira){
            if(!dn.isReveal.get(uhcplayer.getBukkitPlayer().getUniqueId())){
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    dn.reveal(uhcplayer);
                }, 20*60);
            }
        }
    }

}
