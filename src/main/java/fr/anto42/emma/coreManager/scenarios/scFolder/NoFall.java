package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFall extends UHCScenario implements Listener {
    public NoFall(ScenarioManager scenarioManager, int page) {
        super("NoFall", new ItemCreator(Material.GOLD_BOOTS).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLes dégats de chute sont désormais d'un autre temps");
        setScenarioType(ScenarioType.PVE);
    }

    @EventHandler
    private void onPlayerFall(EntityDamageEvent event) {
        if (!isActivated())
            return;
        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }

}
