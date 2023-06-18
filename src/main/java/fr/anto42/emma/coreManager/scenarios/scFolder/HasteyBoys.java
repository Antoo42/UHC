package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HasteyBoys extends UHCScenario implements Listener {


    public HasteyBoys(ScenarioManager scenarioManager, int page) {
        super("HasteyBoys", new ItemCreator(Material.IRON_AXE).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLes outils sont directement enchantés efficacité III ainsi que durabilité III");
        setScenarioType(ScenarioType.STUFF);
    }


    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (!isActivated())
            return;

        Material itemType = event.getInventory().getResult().getType();
        Material[] tools = {
                Material.WOOD_PICKAXE, Material.WOOD_AXE, Material.WOOD_SPADE, Material.WOOD_HOE,
                Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SPADE, Material.STONE_HOE,
                Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SPADE, Material.IRON_HOE,
                Material.GOLD_PICKAXE, Material.GOLD_AXE, Material.GOLD_SPADE, Material.GOLD_HOE,
                Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE
        };

        for (Material tool : tools) {
            if (itemType == tool) {
                ItemStack item = new ItemStack(tool);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
                itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
                item.setItemMeta(itemMeta);
                event.getInventory().setResult(item);
                break;
            }
        }
    }

}
