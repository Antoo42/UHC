package fr.anto42.emma.utils;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.anto42.emma.utils.versionsUtils.VersionUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayersUtils {
    public static void giveWaitingStuff(Player player){
        player.getInventory().clear();
        if(UHCTeamManager.getInstance().isActivated())
            if(UHC.getUHCPlayer(player).getUhcTeam() == null) {
                player.getInventory().setItem(0, new ItemCreator(Material.BANNER).bannerColor(DyeColor.WHITE).name("§8§l» §e§lSéléction des équipes").get());
            }else{
                player.getInventory().setItem(0, new ItemCreator(Material.BANNER).bannerColor(UHC.getUHCPlayer(player).getUhcTeam().getDyeColor()).name("§8§l» §e§lSéléction des équipes").get());
            }
        player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§8§l» §c§lRetourner au Hub").get());
        if(UHC.getInstance().getUhcGame().getUhcData().getHostPlayer() == UHC.getUHCPlayer(player) || UHC.getInstance().getUhcGame().getUhcData().getCoHostList().contains(UHC.getUHCPlayer(player)))
            player.getInventory().setItem(4, new ItemCreator(SkullList.BLOCK_COMMANDBLOCK_DEFAULT.getItemStack()).name("§8§l» §b§lConfigurer la partie").get());
    }

    public static void randomTp(Player player, World world, int xMax, int zMax){
        Random random = new Random();
        int x = random.nextInt(xMax*2);
        x = x-xMax;
        int z = random.nextInt(zMax*2);
        z = z-zMax;
        player.teleport(new Location(world, x, world.getHighestBlockYAt(x, z) + 100, z, 0F, 0F));
        UHC.getUHCPlayer(player).setDamageable(false);
        player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aVous êtes invincibles pour les 30 prochaines secondes.");
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous pouvez de nouveau subir des dégâts !");
            SoundUtils.playSoundToPlayer(player, Sound.ANVIL_LAND);
            UHC.getUHCPlayer(player).setDamageable(true);
        }, TimeUtils.seconds(30));
    }

    public static void spawnOfflineZombieFor(Player player){
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);

        Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
        VersionUtils.getVersionUtils().setEntityAI(zombie, false);
        zombie.setCustomName(uhcPlayer.getName());
        zombie.setCustomNameVisible(true);
        zombie.setBaby(false);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 1, true, true));
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 9999999, true, true));

        EntityEquipment equipment = zombie.getEquipment();
        equipment.setHelmet(new ItemCreator(Material.SKULL_ITEM,1, (short) 3).owner(player.getName()).get());
        equipment.setChestplate(player.getInventory().getChestplate());
        equipment.setLeggings(player.getInventory().getLeggings());
        equipment.setBoots(player.getInventory().getBoots());
        equipment.setItemInHand(player.getItemInHand());

        uhcPlayer.setOfflineZombieUuid(zombie.getUniqueId());
    }

}
