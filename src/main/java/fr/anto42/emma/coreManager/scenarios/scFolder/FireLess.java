package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class FireLess extends UHCScenario {
    public FireLess(ScenarioManager scenarioManager, int page) {
        super("FireLess", new ItemCreator(Material.FLINT_AND_STEEL).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLes degâts du feu sont désactivés");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    private void onPlayerDamage(EntityDamageEvent event) {
        if (!isActivated())
            return;
        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) ||
                event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) ||
                event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            event.setCancelled(true);
        }

    }
}