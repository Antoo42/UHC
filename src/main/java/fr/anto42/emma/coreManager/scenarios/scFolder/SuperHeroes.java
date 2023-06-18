package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SuperHeroes extends UHCScenario {
    public SuperHeroes(ScenarioManager scenarioManager, int page) {
        super("SuperHeroes", new ItemStack(Material.POTION), scenarioManager, page);
        super.setDesc("§8┃ §fDevenez un héro (à défaut d'être un zéro) et obtenez des pouvoirs aléatoirs au début de la partie");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    public void onStart(StartEvent event){
        getUhcGame().getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
            addHeroesEffect(uhcPlayer, new Random().nextInt(5));
        });
    }

    private void addHeroesEffect(UHCPlayer uhcPlayer, int effect){
        if (!isActivated())
            return;
        Player player = uhcPlayer.getBukkitPlayer();

        switch (effect){
            case 0:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999,0));
                break;
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999,0));
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999,1));
                break;
            case 3:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999,0));
                break;
            case 4:
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999,3));
                break;
            case 5:
                player.setMaxHealth(40);
                player.setHealth(40);
                break;
            default:
                System.out.println("No effect for: " + effect);
                break;
        }
    }
}
