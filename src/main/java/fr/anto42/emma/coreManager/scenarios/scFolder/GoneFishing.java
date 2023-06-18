package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GoneFishing extends UHCScenario {
    public GoneFishing(ScenarioManager scenarioManager, int page) {
        super("GoneFishing", new ItemStack(Material.FISHING_ROD), scenarioManager, page);
        setDesc("§8┃ §fVotre canne à pêche vous permettra de trouver des merveilles submérgées");
        setScenarioType(ScenarioType.MINNING);
    }

    private int lureLevel = 250;
    private int luckLevel = 250;

    @EventHandler
    public void onGameStarted(StartEvent e){
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addUnsafeEnchantment(Enchantment.LURE, lureLevel);
        rod.addUnsafeEnchantment(Enchantment.LUCK, luckLevel);

        ItemMeta meta = rod.getItemMeta();
        meta.spigot().setUnbreakable(true);
        rod.setItemMeta(meta);

        ItemStack anvils = new ItemStack(Material.ANVIL, 64);

        for (UHCPlayer uhcPlayer : getUhcGame().getUhcData().getUhcPlayerList()){
            uhcPlayer.safeGive(rod);
            uhcPlayer.getBukkitPlayer().setLevel(10000);
            uhcPlayer.safeGive(anvils);
        }
    }
}
