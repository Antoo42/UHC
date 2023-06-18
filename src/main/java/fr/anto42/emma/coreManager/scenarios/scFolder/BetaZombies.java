package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class BetaZombies extends UHCScenario {
    public BetaZombies(ScenarioManager scenarioManager, int page) {
        super("BetaZombies", new ItemCreator(Material.FEATHER).get(), scenarioManager, page);
        setDesc("§8┃ §fLes zombies donnent des plumes à leur mort");
        setScenarioType(ScenarioType.PVE);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if (!getScenarioManager().isEnabled("BetaZombies"))
            return;
        if (!(event.getEntity() instanceof Zombie))
            return;
        event.getDrops().add(new ItemCreator(Material.FEATHER, new Random().nextInt(3)).get());
    }
}
