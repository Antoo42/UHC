package fr.anto42.emma.utils.players;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.blendman974.kinventory.inventories.ContainerElement;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryUtils {
    private static final Map<UUID, ItemStack[]> playerInventoryMap = new HashMap<>();
    private static final Map<UUID, ItemStack> headMap = new HashMap<>();
    private static final Map<UUID, ItemStack> bodyMap = new HashMap<>();
    private static final Map<UUID, ItemStack> legginsMap = new HashMap<>();
    private static final Map<UUID, ItemStack> bootsMap = new HashMap<>();

    public static void registerInventory(UUID uuid, Player player){
        playerInventoryMap.put(uuid, player.getInventory().getContents());
        headMap.put(uuid, player.getInventory().getHelmet());
        bodyMap.put(uuid, player.getInventory().getChestplate());
        legginsMap.put(uuid, player.getInventory().getLeggings());
        bootsMap.put(uuid, player.getInventory().getBoots());
    }

    public static ItemStack[] getPlayerInventory(UUID uuid){
        return playerInventoryMap.get(uuid);
    }

    public static ItemStack getHead(UUID uuid) {
        return headMap.get(uuid);
    }
    public static ItemStack getBody(UUID uuid) {
        return bodyMap.get(uuid);
    }
    public static ItemStack getLeggins(UUID uuid) {
        return legginsMap.get(uuid);
    }
    public static ItemStack getBoots(UUID uuid) {
        return bootsMap.get(uuid);
    }


    public static void restoreInventory(Player player){
        player.getInventory().clear();
        if (!playerInventoryMap.containsKey(player.getUniqueId())) {
            UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
            uhcPlayer.getBukkitPlayer().setLevel(0);
            UHCGame uhc = UHC.getInstance().getUhcGame();
            if (uhc.getUhcConfig().getStarterStuffConfig().getStartInv().length == 0){
                uhcPlayer.getBukkitPlayer().getInventory().setItem(0, new ItemCreator(Material.COOKED_BEEF, 32).get());
                uhcPlayer.getBukkitPlayer().getInventory().setItem(1, new ItemCreator(Material.BOOK, 1).get());
            }else {
                uhcPlayer.getBukkitPlayer().getInventory().setContents(uhc.getUhcConfig().getStarterStuffConfig().getStartInv());
                uhcPlayer.getBukkitPlayer().getInventory().setHelmet(uhc.getUhcConfig().getStarterStuffConfig().getHead());
                uhcPlayer.getBukkitPlayer().getInventory().setChestplate(uhc.getUhcConfig().getStarterStuffConfig().getBody());
                uhcPlayer.getBukkitPlayer().getInventory().setLeggings(uhc.getUhcConfig().getStarterStuffConfig().getLeggins());
                uhcPlayer.getBukkitPlayer().getInventory().setBoots(uhc.getUhcConfig().getStarterStuffConfig().getBoots());
            }
        }
        player.getInventory().setContents(playerInventoryMap.get(player.getUniqueId()));
        player.getInventory().setHelmet(headMap.get(player.getUniqueId()));
        player.getInventory().setChestplate(bodyMap.get(player.getUniqueId()));
        player.getInventory().setLeggings(legginsMap.get(player.getUniqueId()));
        player.getInventory().setBoots(bootsMap.get(player.getUniqueId()));
    }


    public static void dropInventoryOf(UUID uuid, Location location) {
        for (ItemStack itemStack : playerInventoryMap.get(uuid)) {
            if (itemStack == null)
                return;
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        location.getWorld().dropItemNaturally(location, getHead(uuid));
        location.getWorld().dropItemNaturally(location, getBody(uuid));
        location.getWorld().dropItemNaturally(location, getLeggins(uuid));
        location.getWorld().dropItemNaturally(location, getBoots(uuid));

    }

}
