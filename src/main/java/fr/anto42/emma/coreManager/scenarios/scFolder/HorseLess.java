package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class HorseLess extends UHCScenario implements Listener {
    public HorseLess(ScenarioManager scenarioManager, int page) {
        super("Horseless", new ItemCreator(Material.SADDLE).get(), scenarioManager, page);
        super.setDesc("§8┃ §fIl est impossible de monter des cheveaux");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    public void onEntityMount(EntityMountEvent event) {
        if (!isActivated())
            return;
        if (event.getEntity() instanceof Player) {
            if (event.getMount() instanceof Horse) {
                event.setCancelled(true);
            }
        }
    }
}
