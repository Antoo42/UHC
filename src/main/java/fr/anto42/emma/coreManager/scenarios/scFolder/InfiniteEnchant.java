package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.UniversalMaterial;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class InfiniteEnchant extends UHCScenario {
    public InfiniteEnchant(ScenarioManager scenarioManager, int page) {
        super("InfiniteEnchanter", new ItemStack(Material.EXP_BOTTLE), scenarioManager, page);
        super.setDesc("§8┃ §fPrenez vous pour Merlin l'enchanteur et débarquez avec vos outils de magie au début de la partie");
        setScenarioType(ScenarioType.STUFF);
    }

    @EventHandler
    public void onStart(StartEvent event){
        if (!isActivated())
            return;
        ItemStack enchantingTables = UniversalMaterial.ENCHANTING_TABLE.getStack(64);
        ItemStack anvils = new ItemStack(Material.ANVIL, 64);
        ItemStack lapisBlocks = new ItemStack(Material.LAPIS_BLOCK, 64);
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            uhcPlayer.safeGive(enchantingTables);
            uhcPlayer.safeGive(anvils);
            uhcPlayer.safeGive(lapisBlocks);
        });
    }
}
