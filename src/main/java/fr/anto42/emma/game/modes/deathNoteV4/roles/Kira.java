package fr.anto42.emma.game.modes.deathNoteV4.roles;


import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import fr.anto42.emma.game.modes.deathNoteV4.uis.KiraMainInventory;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;

public class Kira extends DNRole {
    private final KiraMainInventory kiraMainInventory;
    private final Module gamemode;
    private final DNModule dn;
    public Kira(Module gamemode, DNModule dn){
        super("Kira", ((DNModule) gamemode).getDnData().getKiraCamp(), gamemode);
        this.dn = dn;
        this.kiraMainInventory = new KiraMainInventory(this);
        this.gamemode = gamemode;
    }

    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§8▎ §7Vous êtes: §b" + getName());
        getUhcPlayer().sendMessage("§8▎ §7Votre objectif est de gagner avec: §bles autres Kira de la partie.");

        getUhcPlayer().sendMessage("§8▎ §7Effets & Commandes");
        getUhcPlayer().sendMessage("  §8• §fVous disposez de la commande /k <message>, qui vous permet de discuter de manière privée avec les autres kiras de la partie.");
        getUhcPlayer().sendMessage("  §8• §fVous disposez de la commande /reveal, qui vous permettra de révéler votre identitée de Kira et d'obtenir une pomme d'or.");
        getUhcPlayer().sendMessage("  §8• §fVous pouvez faire un clique-droit avec votre DeathNote en main, afin d'acquérir divers pouvoirs.");
    }

    @Override
    public void setRole() {
        dn.aura.put(getUhcPlayer().getBukkitPlayer().getUniqueId(), 3);
        dn.canSeeRealDeath.add(getUhcPlayer());
        dn.canSeeKiraChat.add(getUhcPlayer());
        getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).name("§4§lDEATH NOTE").lore("", "§8§l» §7Clic droit pour ouvrir votre §4§lDeath Note").get());
    }

    public KiraMainInventory getKiraInv() {
        return kiraMainInventory;
    }
    public Module getModule() {
        return gamemode;
    }
}
