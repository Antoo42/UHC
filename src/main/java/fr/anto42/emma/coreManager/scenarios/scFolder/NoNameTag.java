package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class NoNameTag extends UHCScenario {
    public NoNameTag(ScenarioManager scenarioManager, int page) {
        super("NoNameTag", new ItemCreator(Material.NAME_TAG).get(), scenarioManager, page);
        super.setDesc("§8┃ §fLes pseudos sont cachés");
        setScenarioType(ScenarioType.FUN);
    }


    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), () -> {
            if (!isActivated())
                return;
            Bukkit.getOnlinePlayers().forEach(player -> player.setDisplayName(null));
        }, 0L, 30L);
    }
}
