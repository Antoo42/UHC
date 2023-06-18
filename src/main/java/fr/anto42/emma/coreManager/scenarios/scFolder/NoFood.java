package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class NoFood extends UHCScenario {
    public NoFood(ScenarioManager scenarioManager, int page) {
        super("NoFood", new ItemStack(Material.COOKED_BEEF), scenarioManager, page);
        super.setDesc("§8┃ §fLes joueurs ne perdent plus de satiété");
        setScenarioType(ScenarioType.PVE);
    }

    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent event){
        if (!isActivated())
            return;
        event.setCancelled(true);
    }
}
