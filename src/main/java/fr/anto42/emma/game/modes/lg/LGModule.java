package fr.anto42.emma.game.modes.lg;

import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;

public class LGModule extends Module {
    public LGModule() {
        super("§cLG", "Loup-Garou", new ItemCreator(Material.BONE).get());
        setUhcScoreboard(new LGScoreboard());
    }
}
