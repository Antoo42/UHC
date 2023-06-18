package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.NoCleanUPGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class NoCleanUP extends UHCScenario implements Listener {
    public NoCleanUP(ScenarioManager scenarioManager, int page) {
        super("NoCleanUP", new ItemCreator(SkullList.HEART.getItemStack()).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLors d'un kill, le tueur récupère x coeurs");
        super.setConfigurable(true);
        super.setkInventory(new NoCleanUPGUI(this).getkInventory());
        setScenarioType(ScenarioType.PVP);
    }

    private int life = 2;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDeath(PlayerDeathEvent event) {
        if (!isActivated())
            return;
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        killer.setHealth(Math.min(killer.getHealth() + life*2, killer.getMaxHealth()));
    }
}
