package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.XHealthsGUI;
import fr.anto42.emma.utils.skulls.SkullList;

public class XHealths extends UHCScenario {
    public XHealths(ScenarioManager scenarioManager, int page) {
        super("XHealths", SkullList.HEART.getItemStack(), scenarioManager, page);
        setConfigurable(true);
        setDesc("§8┃ §fDébutez la partie avec un certain montant de vie");
        setkInventory(new XHealthsGUI(this).getkInventory());
        setScenarioType(ScenarioType.PVE);
    }

    int h = 50;
    @Override
    public void onStart() {
        if(!isActivated())
            return;
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            if (uhcPlayer.getBukkitPlayer() != null) {
                uhcPlayer.getBukkitPlayer().setMaxHealth(h);uhcPlayer.getBukkitPlayer().setHealth(h);
            }
        });
    }

    public void addH() {
        if(h == 500)
            return;
        h++;
    }

    public void increaseH() {
        if (h == 1)
            return;
        h--;
    }

    public int getH() {
        return h;
    }
}
