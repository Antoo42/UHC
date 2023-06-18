package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProgressiveSpeed extends UHCScenario {
    public ProgressiveSpeed(ScenarioManager scenarioManager, int page) {
        super("ProgressiveSpeed", new ItemStack(Material.RABBIT_FOOT), scenarioManager, page);
        setDesc("§8┃ §fA chaque kills, vous gagnez un niveau de vitesse");
        setScenarioType(ScenarioType.PVP);
    }


    Map<UUID, Integer> map = new HashMap<>();
    @EventHandler
    public void onDeath (DeathEvent event){
        if(!isActivated())
            return;
        event.getKiller().getBukkitPlayer().removePotionEffect(PotionEffectType.SPEED);
        if (!map.containsKey(event.getKiller().getUuid()))
            map.put(event.getKiller().getUuid(), 0);
        else map.put(event.getKiller().getUuid(), map.get(event.getKiller().getUuid())+ 1);
        event.getKiller().getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE, map.get(event.getKiller().getUuid()), false, false));
        event.getKiller().sendClassicMessage("§7Vous avez désormais §aSpeed de niveau " + (map.get(event.getKiller().getUuid()) + 1) + "§7 !");
    }
}
