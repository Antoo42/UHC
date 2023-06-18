package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.game.impl.UHCData;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CutClean extends UHCScenario implements Listener {
    public CutClean(ScenarioManager scenarioManager, int page) {
        super("CutClean", new ItemCreator(SkullList.DIAMOND_BALL.getItemStack()).get(), scenarioManager, page);
        setDesc("§8┃ §fLes minerais sont directement cuits");
        setScenarioType(ScenarioType.MINNING);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        if (!isActivated())
            return;

        Block block = e.getBlock();
        Player player = e.getPlayer();
        UHCGame uhcGame = UHC.getInstance().getUhcGame();
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if (block.getType() == Material.IRON_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.IRON_INGOT, 1));
            player.giveExp(2 * uhcGame.getUhcConfig().getXpBoost());
        }
        else if (block.getType() == Material.COAL_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.COAL, 1));
            player.giveExp(1 * uhcGame.getUhcConfig().getXpBoost());
        }
        else if (block.getType() == Material.GOLD_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.GOLD_INGOT, 1));
            player.giveExp(3 * uhcGame.getUhcConfig().getXpBoost());
        }
        else if (block.getType() == Material.DIAMOND_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.DIAMOND, 1));
            player.giveExp(4 * uhcGame.getUhcConfig().getXpBoost());
        }
        else if (block.getType() == Material.QUARTZ_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.QUARTZ, 3));
            player.giveExp(6 * uhcGame.getUhcConfig().getXpBoost());
        }
        else if (block.getType() == Material.EMERALD_ORE) {
            e.setCancelled(true);
            block.setType(Material.AIR);
            uhcPlayer.safeGiveOrDrop(new ItemStack(Material.EMERALD, 3));
            player.giveExp(10 * uhcGame.getUhcConfig().getXpBoost());
        }
    }


    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.PIG) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.GRILLED_PORK, 2));
        }

        if (e.getEntityType() == EntityType.COW) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
            e.getDrops().add(new ItemStack(Material.LEATHER));
        }

        if (e.getEntityType() == EntityType.CHICKEN) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 2));
            e.getDrops().add(new ItemStack(Material.FEATHER, 2));
        }

        if (e.getEntityType() == EntityType.SHEEP) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.COOKED_MUTTON, 2));
        }

        if (e.getEntityType() == EntityType.RABBIT) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.COOKED_RABBIT, 1));
            int random = new Random().nextInt(100);
            if (random < 5) {
                e.getDrops().add(new ItemStack(Material.RABBIT_FOOT));
            }
        }

        if (e.getEntityType() == EntityType.SPIDER) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.SPIDER_EYE, 1));
            e.getDrops().add(new ItemStack(Material.STRING, 2));
        }

        if (e.getEntityType() == EntityType.SKELETON) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.BONE, 2));
            e.getDrops().add(new ItemStack(Material.ARROW, 3));
        }
    }
}
