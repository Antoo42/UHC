package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlowerPower extends UHCScenario {
    public FlowerPower(ScenarioManager scenarioManager, int page) {
        super("FlowerPower", new ItemStack(Material.RED_ROSE), scenarioManager, page);
        setDesc("§8┃ §fLes fleurs ont désormais 4 chances sur 5 de §adrop §fun §aitem aléatoire");
        setScenarioType(ScenarioType.FUN);
        materials.add(Material.RED_ROSE);
        materials.add(Material.YELLOW_FLOWER);
    }

    List<Material> materials = new ArrayList<>();
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!isActivated())
            return;
        if (materials.contains(e.getBlock().getType())) {
            e.getBlock().setType(Material.AIR);
            double r = Math.random();
            if (r <= 0.2D)
                return;
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(getRandomMaterial()));
        }
    }

    private Material getRandomMaterial() {
        int index = (new Random()).nextInt((Material.values()).length);
        return Material.values()[index];
    }

}