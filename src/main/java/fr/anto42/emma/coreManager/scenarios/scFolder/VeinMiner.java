package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.OreType;
import fr.anto42.emma.utils.materials.UniversalMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class VeinMiner extends UHCScenario {
    public VeinMiner(ScenarioManager scenarioManager, int page) {
        super("VeinMiner", new ItemStack(Material.GOLD_ORE), scenarioManager, page);
        super.setDesc("§8┃ §fAccroupissez-vous lorsque vous minez pour détruire tout un filon d'un coup de pioche");
        setScenarioType(ScenarioType.MINNING);
    }

    private static final BlockFace[] BLOCK_FACES = new BlockFace[]{
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.SOUTH,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

    private boolean calculateToolDamage = true;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if (!isActivated())
            return;
        Player player = e.getPlayer();

        if (!player.isSneaking()){
            return;
        }

        Block block = e.getBlock();
        ItemStack tool = player.getItemInHand();

        if (block.getType() == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()){
            block.setType(Material.REDSTONE_ORE);
        }

        Optional<OreType> oreType = OreType.valueOf(block.getType());
        if (!oreType.isPresent() || !oreType.get().isCorrectTool(tool.getType())) {
            return;
        }

        Vein vein = new Vein(block);
        vein.process();

        int amount = vein.getOres() /* getVeinMultiplier(oreType.get())*/;
        ItemStack drops = new ItemStack(oreType.get().getDrop(), amount);
        Location loc = player.getLocation().getBlock().getLocation().add(.5,.5,.5);
        loc.getWorld().dropItem(loc, drops);

        int xp = oreType.get().getXpPerBlock() * vein.getOres();
        if (xp != 0) {
            ExperienceOrb orb = (ExperienceOrb) block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.EXPERIENCE_ORB);
            orb.setExperience(amount);
        }


        if (calculateToolDamage) {
            tool.setDurability((short) (tool.getDurability() + vein.getOres()));
        }
    }

    /*private int getVeinMultiplier(OreType oreType) {
        int multiplier = 1;
        if (getScenarioManager().isEnabled(Scenario.TRIPLE_ORES)) {
            multiplier *= 3;
        }
        if (getScenarioManager().isEnabled(Scenario.DOUBLE_ORES)) {
            multiplier *= 2;
        }
        if ((oreType == OreType.GOLD || oreType == OreType.NETHER_GOLD) && getScenarioManager().isEnabled(Scenario.DOUBLE_GOLD)) {
            multiplier *= 2;
        }
        return multiplier;
    }*/

    private static class Vein {
        private final Block startBlock;
        private final Material type;
        private int ores;

        public Vein(Block startBlock) {
            this.startBlock = startBlock;
            this.type = startBlock.getType();
            ores = 0;
        }

        public void process() {
            getVeinBlocks(startBlock, type, 2, 10);
        }

        public int getOres() {
            return ores;
        }

        private void getVeinBlocks(Block block, Material type, int i, int maxBlocks) {
            if (maxBlocks == 0) return;

            if (block.getType() == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()) {
                block.setType(Material.REDSTONE_ORE);
            }

            if (block.getType() == type) {
                block.setType(Material.AIR);
                ores++;
                i = 2;
            }else {
                i--;
            }
            if (i > 0 && ores < 20) {
                for (BlockFace face : BLOCK_FACES) {
                    getVeinBlocks(block.getRelative(face), type, i, maxBlocks-1);
                }
            }
        }
    }
}
