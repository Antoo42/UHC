package fr.anto42.emma.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryUtils {
    private static final Map<UUID, ItemStack[]> playerInventoryMap = new HashMap<>();

    public static void registerInventory(UUID uuid, ItemStack[] inventory){
        playerInventoryMap.put(uuid, inventory);
    }

    public static ItemStack[] getPlayerInventory(UUID uuid){
        return playerInventoryMap.get(uuid);
    }

    public static void restoreInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setContents(playerInventoryMap.get(player.getUniqueId()));
    }
}
