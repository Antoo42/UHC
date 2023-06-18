package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.event.EventHandler;

public class DoubleHealth extends UHCScenario {
    public DoubleHealth(ScenarioManager scenarioManager, int page) {
        super("DoubleHealth", new ItemCreator(SkullList.HEART.getItemStack()).get(), scenarioManager, page);
        setDesc("§8┃ §fJouez la partie avec §d20 coeurs §fau lieu de 10");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    public void onStart(StartEvent event){
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            uhcPlayer.getBukkitPlayer().setMaxHealth(40);
            uhcPlayer.getBukkitPlayer().setHealth(40);
        });
    }
}
