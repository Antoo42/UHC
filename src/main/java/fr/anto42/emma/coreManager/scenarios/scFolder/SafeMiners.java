package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.SafeMinersGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class SafeMiners extends UHCScenario implements Listener {
    public SafeMiners(ScenarioManager scenarioManager, int page) {
        super("SafeMiners", new ItemStack(Material.DIAMOND_PICKAXE), scenarioManager, page);
        super.setDesc("§8┃ §fVous ne prenez aucun degâts tant que la bordure ne bouge pas sous une hauteur x");
        super.setConfigurable(true);
        super.setkInventory(new SafeMinersGUI(this).getkInventory());
        setScenarioType(ScenarioType.MINNING);
    }

    private int y = 30;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addY(){
        if (y == 100)
            return;
        y++;
    }
    public void removeY(){
        if (y == 0)
            return;
        y--;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (!isActivated())
            return;
        if (getUhcGame().getUhcData().isBorderMove()){
            return;
        }
        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
            return;
        }

        if (event.getEntity() instanceof Player){
            int y1 = event.getEntity().getLocation().getBlockY();
            if (y1> 0 && y1 < y){
                event.setCancelled(true);
            }
        }
    }
}
