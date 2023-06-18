package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BowSwap extends UHCScenario {
    public BowSwap(ScenarioManager scenarioManager, int page) {
        super("BowSwap", new ItemStack(Material.ARROW), scenarioManager, page);
        super.setDesc("§8┃ §fLorsqu'un joueur tire sur un autre, leurs positions sont échangés");
        setScenarioType(ScenarioType.PVP);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e){
        if (!isActivated())
            return;
        if (!(e.getEntity() instanceof Player)){
            return;
        }

        Player player = ((Player) e.getEntity()).getPlayer();

        if (!(e.getDamager() instanceof Arrow)){
            return;
        }

        Arrow arrow = (Arrow) e.getDamager();

        if (!(arrow.getShooter() instanceof  Player)){
            return;
        }

        Player shooter = ((Player) arrow.getShooter()).getPlayer();

        if (player.equals(shooter)){
            return;
        }

        arrow.remove();

        Location playerLoc = player.getLocation();
        Location shooterLoc = shooter.getLocation();

        player.teleport(shooterLoc);
        shooter.teleport(playerLoc);

        SoundUtils.playSoundToPlayer(player, Sound.ENDERMAN_TELEPORT);
        SoundUtils.playSoundToPlayer(shooter, Sound.ENDERMAN_TELEPORT);

        UHC.getUHCPlayer(player).sendClassicMessage("§7Votre position a été inversée avec celle de §a" + shooter.getDisplayName() + "§7 !");
        UHC.getUHCPlayer(shooter).sendClassicMessage("§7Votre position a été inversée avec celle de §a" + player.getDisplayName() + "§7 !");
    }
}