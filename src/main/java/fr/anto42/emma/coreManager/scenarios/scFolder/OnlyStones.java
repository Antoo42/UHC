package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OnlyStones extends UHCScenario {
    public OnlyStones(ScenarioManager scenarioManager, int page) {
        super("OnlyStones", new ItemStack(Material.COBBLESTONE), scenarioManager, page);
        setDesc("§8┃ §fL'andésite et la diorite deviennent de la cobblestone quand elles sont cassées");
        setScenarioType(ScenarioType.MINNING);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;
        if(!isActivated())
            return;
        if (event.getBlock().getType() == Material.STONE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
        }
    }
}
