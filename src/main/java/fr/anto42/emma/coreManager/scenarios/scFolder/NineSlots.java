package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.UniversalMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NineSlots extends UHCScenario {
    public NineSlots(ScenarioManager scenarioManager, int i) {
        super("NineSlots", new ItemStack(Material.BARRIER), scenarioManager, i);
        setScenarioType(ScenarioType.STUFF);
        setDesc("§8┃ §fVous ne pourrez jouer qu'avec les 9 slots de la hotbar");
    }

    private ItemStack fillItem;

    @Override
    public void onEnable(){
        fillItem = UniversalMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.getStack();
        ItemMeta meta = fillItem.getItemMeta();
        meta.setDisplayName("§cBloqué");
        fillItem.setItemMeta(meta);
    }

    @EventHandler
    public void onGameStarted(StartEvent e){
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            fillInventory(uhcPlayer.getBukkitPlayer());
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();

        // Only handle clicked items.
        if (item == null){
            return;
        }

        if (item.equals(fillItem)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        List<ItemStack> drops = e.getDrops();

        while (drops.remove(fillItem)){}
    }

    private void fillInventory(Player player){
        for (int i = 9; i <= 35; i++) {
            player.getInventory().setItem(i, fillItem);
        }
    }
}
