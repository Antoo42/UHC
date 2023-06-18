package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.TimeBombGUI;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.versionsUtils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TimeBomb extends UHCScenario {
    public TimeBomb(ScenarioManager scenarioManager, int page) {
        super("TimeBomb", new ItemStack(Material.CHEST), scenarioManager, page);
        setConfigurable(true);
        setkInventory(new TimeBombGUI(this).getkInventory());
        setScenarioType(ScenarioType.PVP);
    }

    private long delay = 30;
    private int radius = 10;

    public int getRadius() {
        return radius;
    }

    public void addRadius(){
        if (radius == 30)
            return;
        radius++;
    }

    public void removeRadius(){
        if (radius == 0)
            return;
        radius--;
    }

    public long getDelay() {
        return delay;
    }


    public void addDelay(){
        if (delay == 300)
            return;
        delay++;
    }

    public void removeDelay(){
        if (delay == 0)
            return;
        delay--;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!isActivated())
            return;
        Player p = e.getEntity().getPlayer();
        List<ItemStack> drops = new ArrayList<>(e.getDrops());
        e.getDrops().removeAll(e.getDrops());

        TimebombThread timebombThread = new TimebombThread(drops, p.getLocation().getBlock().getLocation(), p.getName(), delay);
        Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(), timebombThread,1L);
    }

    public static class TimebombThread implements Runnable{

        private ArmorStand armorStand;
        private Block block1, block2;
        private long timeLeft;
        private Location loc;
        private List<ItemStack> drops;
        private String name;
        private boolean spawned;

        public TimebombThread(List<ItemStack> drops, Location loc, String name, long delay){
            this.drops = drops;
            this.loc = loc;
            this.name = name;
            timeLeft = delay;
            spawned = false;
        }

        @Override
        public void run() {
            if (!spawned){
                spawnChest();
            }

            if (timeLeft > 0){
                armorStand.setCustomName(ChatColor.RED + "Secondes restantes: " + timeLeft);
                timeLeft--;
                Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(),this,20L);
            } else{
                armorStand.remove();
                block1.setType(Material.AIR);
                block2.setType(Material.AIR);
                block1.getWorld().createExplosion(block1.getLocation(), 10, false);
            }

        }

        private void spawnChest(){
            spawned = true;

            block1 = loc.getBlock();
            loc.add(-1, 0, 0);
            block2 = loc.getBlock();

            block1.setType(Material.CHEST);
            block2.setType(Material.CHEST);

            Chest chest1 = (Chest) block1.getState();
            Chest chest2 = (Chest) block2.getState();

            String chestName = "Coffre de mort de " + name;
            VersionUtils.getVersionUtils().setChestName(chest1, chestName);
            VersionUtils.getVersionUtils().setChestName(chest2, chestName);

            Inventory inv = chest1.getInventory();

            for (ItemStack drop : drops){
                inv.addItem(drop);
            }

            loc.add(1,-1,.5);

            armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setCustomName("");
        }
    }
}
