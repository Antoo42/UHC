package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.LateEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.MasterLevelGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.event.EventHandler;

public class MasterLevel extends UHCScenario {
    public MasterLevel(ScenarioManager scenarioManager, int page) {
        super("MasterLevel", new ItemCreator(SkullList.XP.getItemStack()).get(), scenarioManager, page);
        setDesc("§8┃ §fCommencez la partie avec un nombre précis de niveaux d'expérience");
        setConfigurable(true);
        setkInventory(new MasterLevelGUI(this).getkInventory());
        setScenarioType(ScenarioType.STUFF);
    }

    private int level = 10;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @EventHandler
    public void onStart(StartEvent event){
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            uhcPlayer.getBukkitPlayer().setLevel(level);
        });
    }

    @EventHandler
    public void onLate(LateEvent event) {
        event.getUhcPlayer().getBukkitPlayer().setLevel(level);
    }
}
