package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.PvPEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinalHeal extends UHCScenario implements Listener {
    public FinalHeal(ScenarioManager scenarioManager, int page) {
        super("FinalHeal", new ItemCreator(SkullList.HEART.getItemStack()).get(), scenarioManager, page);
        super.setDesc("§8┃ §fAu PvP, tout les joueurs sont soignés");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    public void onPvP(PvPEvent event){
        if (!isActivated())
            return;
        Bukkit.getOnlinePlayers().forEach(player -> player.setHealth(player.getMaxHealth()));
        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + "§7 FinalHeal effectué !");
    }
}
