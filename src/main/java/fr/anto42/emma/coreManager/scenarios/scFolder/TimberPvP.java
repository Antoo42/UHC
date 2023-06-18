package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.materials.UniversalMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TimberPvP extends UHCScenario implements Listener{
    public TimberPvP(ScenarioManager scenarioManager, int page) {
        super("TimberPvP", new ItemCreator(Material.IRON_AXE).get(), scenarioManager, page);
        setDesc("§8┃ §fLes arbres sont coupés au premier coup de poing jusqu'au PvP");
        setScenarioType(ScenarioType.MINNING);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (getUhcGame().getUhcData().isPvp())
            return;
        if (!isActivated())
            return;
        Block block = e.getBlock();

        if (UniversalMaterial.isLog(block.getType())) {
            int brokenLogs = breakTree(block, 2);
            ItemStack tool = e.getPlayer().getItemInHand();
            if (UniversalMaterial.isAxe(tool.getType())) {
                tool.setDurability((short) (tool.getDurability() + brokenLogs));
            }
        }
    }

    private int breakTree(Block block, int i) {
        int broken = 0;
        if (UniversalMaterial.isLog(block.getType())){
            block.breakNaturally();
            broken++;
            i = 2;
        }else {
            i--;
        }
        if (i > 0){
            for (BlockFace face : BlockFace.values()) {
                if (face.equals(BlockFace.DOWN) || face.equals(BlockFace.UP) || face.equals(BlockFace.NORTH) ||
                        face.equals(BlockFace.EAST) || face.equals(BlockFace.SOUTH) || face.equals(BlockFace.WEST)) {
                    broken += breakTree(block.getRelative(face), i);
                }
            }
        }

        return broken;
    }
}
