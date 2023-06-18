package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.LuckyLeavesGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LuckyLeaves extends UHCScenario {
    public LuckyLeaves(ScenarioManager scenarioManager, int page) {
        super("LuckyLeaves", new ItemStack(Material.LEAVES), scenarioManager, page);
        setkInventory(new LuckyLeavesGUI(this).getkInventory());
        setScenarioType(ScenarioType.MINNING);
        setDesc("§8┃ §fVous avez une chance de récupperer une pomme d'or lorsque vous cassez une feuille");
    }
    int chance;
    public void addChance() {
        if (chance == 100) {
            return;
        }
        chance++;
    }
    public void increaseChance() {
        if (chance == 0) {
            return;
        }
        chance--;
    }

    public int getChance() {
        return chance;
    }


    Random random = new Random();
    @EventHandler
    public void onLeavesDeacay(LeavesDecayEvent event) {
        if(!isActivated())
            return;
        int l = random.nextInt(100);
        if (l <= getChance()) {
            event.getBlock().getDrops().add(new ItemStack(Material.GOLDEN_APPLE));
        }
    }
}
