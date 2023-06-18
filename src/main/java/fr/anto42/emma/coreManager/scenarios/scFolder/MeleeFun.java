package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class MeleeFun extends UHCScenario {
    public MeleeFun(ScenarioManager scenarioManager, int i) {
        super("MeleeFun", new ItemStack(Material.IRON_SWORD), scenarioManager, i);
        setDesc("§8┃ §fLes délais entre chaque coups sont annulés (les degâts sont réduits de 90%)");
        setScenarioType(ScenarioType.PVP);
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        final Player player = (Player) event.getEntity();
        event.setDamage(event.getDamage() * 0.5);

        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), new Runnable() {
            public void run() {
                player.setNoDamageTicks(0);
            }
        }, 1L);
    }
}
