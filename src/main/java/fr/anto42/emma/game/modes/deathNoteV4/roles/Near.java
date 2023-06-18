package fr.anto42.emma.game.modes.deathNoteV4.roles;


import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;

public class Near extends DNRole {
    private final DNModule dn;
    public Near(Module gamemode, DNModule dn){
        super("Near", null, gamemode);
        this.dn = dn;
    }

    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName());
        getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bvotre équipe d'origine.");

        getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
        getUhcPlayer().sendMessage("  §8• §fVous pouvez mener une enquêtes sur une personne de votre équipe avec l'aide de la commande /enquête <pesudo>, en échange de 3 coeurs permanents");
        getUhcPlayer().sendMessage("  §8• §fVous disposez également du KiraKiller. Si vous frapper un Kira avec cette arme, il perdra la moitié de sa vie. Néanmoins si vous-vous trompez à deux reprises, ce sera vous qui perdrez votre vie.");
    }

    @Override
    public void setRole() {
        dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 1);
        dn.getKiraHasEyesList().add(getUhcPlayer());
        dn.canInvest.add(getUhcPlayer());
        dn.canKira.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), true);
        dn.kiraNum.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 0);
        getUhcPlayer().safeGive(new ItemCreator(Material.GOLD_HOE).unbreakable(true).lore("", "§8§l» §7Frappez un kira.").name("§e§lKira killer").get());
    }

    @Override
    public void onEpisode() {
        dn.canKira.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), true);
    }
}
