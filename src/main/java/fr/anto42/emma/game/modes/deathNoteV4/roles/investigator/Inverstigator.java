package fr.anto42.emma.game.modes.deathNoteV4.roles.investigator;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.Random;

public class Inverstigator extends DNRole {
    private final DNModule dn;
    public Inverstigator(Module gamemode, DNModule dn){
        super("Enquêteur", null, gamemode);
        this.dn = dn;
    }

    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName());
        getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bvotre équipe d'origine");

        getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> getUhcPlayer().sendMessage(dn.powerDesc.get(getUhcPlayer().getBukkitPlayer().getUniqueId())), 10);
    }

    @Override
    public void setRole() {
        dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 1);
        Random random = new Random();
        int r = random.nextInt(Arrays.asList(InvestPowers.values()).size());
        String power = Arrays.asList(InvestPowers.values()).get(r).getPowerName();
        dn.investPower.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), power);
        String powerdesc = Arrays.asList(InvestPowers.values()).get(r).getDesc();
        dn.powerDesc.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), powerdesc);
        if(dn.investPower.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("morts")){
            dn.canSeeRealDeath.add(getUhcPlayer());
        }else if(dn.investPower.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("chat")){
            dn.canSeeKiraChat.add(getUhcPlayer());
        }else if(dn.investPower.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("dn")){
            dn.seeWhenDN.add(getUhcPlayer());
        }else if(dn.investPower.get(getUhcPlayer().getBukkitPlayer().getUniqueId()).equals("enquete")){
            dn.canInvest.add(getUhcPlayer());
        }else{
            System.out.println("");
            System.out.println("ERREUR POUVOIR INVESTIGATOR");
            System.out.println("");
        }
    }

    @Override
    public void onEpisode() {
        if(getUhcPlayer().getBukkitPlayer().getMaxHealth() != 20){
            getUhcPlayer().getBukkitPlayer().setMaxHealth(getUhcPlayer().getBukkitPlayer().getMaxHealth() + 2);
        }
    }
}
