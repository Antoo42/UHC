package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.VanillaPlusGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class VanillaPlus extends UHCScenario implements Listener {

    public VanillaPlus(ScenarioManager scenarioManager, int page) {
        super("VanillaPlus", new ItemCreator(Material.APPLE).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLe taux de drop des pommes et du flint sont boostés");
        setConfigurable(true);
        setkInventory(new VanillaPlusGUI(this).getkInventory());
        setScenarioType(ScenarioType.MINNING);
    }

    private int appleRate = 2;
    private int flintRate = 10;

    @EventHandler
    private void onGravelBreak(BlockBreakEvent event) {
        if (!isActivated())
            return;
        Block block = event.getBlock();
        Location loc = new Location(block.getWorld(),
                block.getLocation().getBlockX() + 0.5,
                block.getLocation().getBlockY() + 0.5,
                block.getLocation().getBlockZ() + 0.5);

        if (block.getType().equals(Material.GRAVEL)) {
            block.setType(Material.AIR);
            if (Math.random() * 100 < flintRate) {
                block.getWorld().dropItem(loc, new ItemStack(Material.FLINT, 1));
            } else
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
                        new ItemStack(Material.GRAVEL));
        }
    }

    @EventHandler
    public void onLeaveDecay(LeavesDecayEvent event) {
        if (!isActivated())
            return;
        event.getBlock().setType(Material.AIR);
        if (Math.random() * 100 < appleRate) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
                    new ItemStack(Material.APPLE));
        }
    }

    public int getAppleRate() {
        return appleRate;
    }

    public void setAppleRate(int appleRate) {
        this.appleRate = appleRate;
    }

    public void removeApple(){
        if (appleRate == 0)
            return;
        setAppleRate(appleRate - 1);
    }

    public void addApple(){
        if (appleRate == 100)
            return;
        setAppleRate(appleRate + 1);
    }

    public int getFlintRate() {
        return flintRate;
    }

    public void setFlintRate(int flintRate) {
        this.flintRate = flintRate;
    }

    public void addFlint(){
        if (flintRate == 100)
            return;
        setFlintRate(flintRate + 1);
    }
    public void removeFlint(){
        if (flintRate == 0)
            return;
        setFlintRate(flintRate - 1);
    }
}
