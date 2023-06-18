package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.OreType;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DiamondBlood extends UHCScenario {

    public DiamondBlood(ScenarioManager scenarioManager, int page) {
        super("DiamondBlood", new ItemStack(Material.DIAMOND), scenarioManager, page);
        super.setDesc("§8┃ §fMiné des diamants fait perdre un demi-coeur");
        setScenarioType(ScenarioType.MINNING);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if (!isActivated())
            return;
        if (!OreType.DIAMOND.equals(e.getBlock().getType())){
            return;
        }

        Player p = e.getPlayer();
        SoundUtils.playSoundToPlayer(p, Sound.HURT_FLESH);

        if (p.getHealth() < 1){
            p.setHealth(0);
        }else {
            p.setHealth(p.getHealth() - 1);
        }
    }
}
