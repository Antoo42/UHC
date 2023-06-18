package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class RodLess extends UHCScenario implements Listener {
    public RodLess(ScenarioManager scenarioManager, int page) {
        super("RodLess", new ItemCreator(Material.FISHING_ROD).get(), scenarioManager, page);
        super.setDesc("§8┃ §fIl est maintenant §cimpossible §fde craft des cannes à pêche");
        setScenarioType(ScenarioType.PVP);
    }
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (!isActivated())
            return;
        CraftingInventory inv = event.getInventory();

        if (inv.getResult() == null) return;

        if (inv.getResult().getType().equals(Material.FISHING_ROD)) {
            inv.setResult(new ItemStack(Material.AIR));
        }
    }
}
