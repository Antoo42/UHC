package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FastSmelting extends UHCScenario implements Listener {
    public FastSmelting(ScenarioManager scenarioManager, int page) {
        super("FastSmelting", new ItemCreator(Material.FURNACE).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLes minérais cuissent beaucoup plus vites");
        setScenarioType(ScenarioType.MINNING);
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent event) {
        if (!isActivated())
            return;
        Furnace block = (Furnace) event.getBlock().getState();

        new BukkitRunnable() {
            public void run() {
                if (block.getCookTime() > 0 || block.getBurnTime() > 0) {
                    block.setCookTime((short) (block.getCookTime() + 8));
                    block.update();
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(UHC.getInstance(), 1L, 1L);
    }
}
