package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.WeakestLinkGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.concurrent.atomic.AtomicReference;

public class WeakestLink extends UHCScenario {
    public WeakestLink(ScenarioManager scenarioManager, int page) {
        super("WeakestLink", new ItemCreator(Material.COAL).get(), scenarioManager, page);
        setDesc("§8┃ §fA une intervalle définie, le joueur ayant le moins de vie est éliminé");
        setConfigurable(true);
        setkInventory(new WeakestLinkGUI(this).getkInventory());
        setScenarioType(ScenarioType.FUN);
    }

    private int timer = 5;

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @EventHandler
    public void onStart(StartEvent event){
        AtomicReference<UHCPlayer> uhcPlayer = new AtomicReference<>(getUhcGame().getUhcData().getUhcPlayerList().get(0));
        Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), () -> {
            if (!getScenarioManager().isEnabled("WeakestLink"))
                return;
            getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer1 -> {
                if (uhcPlayer1.getBukkitPlayer().getHealth() <= uhcPlayer.get().getBukkitPlayer().getHealth()) {
                    uhcPlayer.set(uhcPlayer1);
                }
            });
            uhcPlayer.get().getBukkitPlayer().setHealth(0);
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §9WeakestLink: " + " §c" + uhcPlayer.get().getName() + "§7 est mort car il avait le moins de vie !");
        }, TimeUtils.minutes(timer), TimeUtils.minutes(timer));
    }
}
