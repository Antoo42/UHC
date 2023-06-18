package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RandomLoots extends UHCScenario {
    public RandomLoots(ScenarioManager scenarioManager, int page) {
        super("RandomLoots", new ItemStack(Material.IRON_INGOT), scenarioManager, page);
        super.setDesc("§8┃ §fLes loots sont tous aléatoires");
        setScenarioType(ScenarioType.WORLD);
        dropList = new HashMap<>();
    }

    private List<Material> items;
    private final Map<Material, ItemStack> dropList;

    @Override
    public void onEnable() {
        items = Arrays.asList(Material.values());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!isActivated())
            return;
        Block block = event.getBlock();

        ItemStack blockDrop;
        if (dropList.containsKey(block.getType())) {
            blockDrop = dropList.get(block.getType());
        }
        else {
            int itemindex  = randomInteger(1, items.size())-1;
            Material material = items.get(itemindex);

            blockDrop = new ItemStack(material);
            dropList.put(block.getType(), blockDrop);
            blockDrop = dropList.get(block.getType());

//            items.remove(material);
        }

        event.setCancelled(true);
        block.setType(Material.AIR);
        Location dropLocation = block.getLocation().add(.5, 0, .5);
        dropLocation.getWorld().dropItemNaturally(dropLocation, blockDrop);

        Player player = event.getPlayer();
        ItemStack tool = player.getItemInHand();

        if (tool != null && tool.hasItemMeta() && tool.getDurability() > 1) {
            tool.setDurability((short) (tool.getDurability()-1));
            player.setItemInHand(tool);
        }
    }

    public static int randomInteger(int min, int max){
        Random r = new Random();
        int realMin = Math.min(min, max);
        int realMax = Math.max(min, max);
        int exclusiveSize = realMax-realMin;
        return r.nextInt(exclusiveSize+1)+min;
    }
}
