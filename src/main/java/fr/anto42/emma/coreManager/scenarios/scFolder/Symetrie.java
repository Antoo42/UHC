package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class Symetrie extends UHCScenario {
    public Symetrie(ScenarioManager scenarioManager, int page) {
        super("Symetrie", new ItemStack(Material.PAINTING), scenarioManager, page);
        setDesc("§8┃ §fChaque blocks cassés ou posés se vera attribuer le même sort à ses coordonnés inverses");
        setScenarioType(ScenarioType.WORLD);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!isActivated())
            return;
        if (event.isCancelled())
            return;
        event.getBlock().getWorld().getBlockAt((int) -event.getBlock().getLocation().getX(), (int) event.getBlock().getLocation().getY(), (int) -event.getBlock().getLocation().getZ()).setType(Material.AIR);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!isActivated())
            return;
        if (event.isCancelled())
            return;
        event.getBlock().getWorld().getBlockAt((int) -event.getBlock().getLocation().getX(), (int) event.getBlock().getLocation().getY(), (int) -event.getBlock().getLocation().getZ()).setType(event.getBlock().getType());
    }
}
