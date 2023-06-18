package fr.anto42.emma.coreManager.enchants.config;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class EnchantGUI {
    private final KInventory kInventory;

    public EnchantGUI(ItemStack itemStack) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lEnchanter un item");
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 9).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 9).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cFermer le menu").lore("", "§8§l» §6Cliquez §fpour fermer.").get());
        back.addCallback((kInventoryRepresentation, itemStack1, player, kInventoryClickContext) -> player.closeInventory());
        this.kInventory.setElement(49, back);

        /*KItem unbreakable = new KItem(new ItemCreator(Material.BLAZE_ROD).name("§8┃ §fIncassable").lore("", "§8§l» §fStatut: " + (itemStack.getItemMeta().spigot().isUnbreakable() ? "§aactivé" : "§cdésactivé"), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        unbreakable.addCallback((kInventoryRepresentation, itemStack1, player, kInventoryClickContext) -> {
            itemStack.getItemMeta().spigot().setUnbreakable((!itemStack.getItemMeta().spigot().isUnbreakable()));
            unbreakable.setItem(new ItemCreator(Material.BLAZE_ROD).name("§8┃ §fIncassable").lore("", "§8§l» §fStatut: " + (itemStack.getItemMeta().spigot().isUnbreakable() ? "§aactivé" : "§cdésactivé"), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        });
        this.kInventory.setElement(4, unbreakable);*/

        final int[] slot = {9};
        int level;
        AtomicInteger l = new AtomicInteger();
        for (EnchantConfiguration.EnchantementEnum value : EnchantConfiguration.EnchantementEnum.values()) {
            level = 0;
            if (itemStack.getEnchantments().containsKey(value.getEnchantment()))
                level = itemStack.getEnchantmentLevel(value.getEnchantment());
            KItem kItem = new KItem(new ItemCreator(Material.ENCHANTED_BOOK, level).name(value.getName()).lore("", "§8§l» §fStatut: §c" + level, "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());
            kItem.addCallback((kInventoryRepresentation, itemStack1, player, kInventoryClickContext) -> {
                if (kInventoryClickContext.getClickType().isLeftClick())
                    for(EnchantConfiguration.EnchantementEnum enchantementEnum : EnchantConfiguration.EnchantementEnum.values()){
                        if (itemStack1.getItemMeta().getDisplayName().equalsIgnoreCase(enchantementEnum.getName())){
                            int lvl = itemStack.getEnchantmentLevel(enchantementEnum.getEnchantment());
                            if (lvl < enchantementEnum.getMax())
                                itemStack.addUnsafeEnchantment(enchantementEnum.getEnchantment(), lvl + 1);
                        }
                    }
                else if (kInventoryClickContext.getClickType().isRightClick())
                    for(EnchantConfiguration.EnchantementEnum enchantementEnum : EnchantConfiguration.EnchantementEnum.values()){
                        if (itemStack1.getItemMeta().getDisplayName().equalsIgnoreCase(enchantementEnum.getName())){
                            int lvl = itemStack.getEnchantmentLevel(enchantementEnum.getEnchantment());
                            if (lvl > enchantementEnum.getMin())
                                if (lvl == 1)
                                    itemStack.removeEnchantment(enchantementEnum.getEnchantment());
                            else {
                                itemStack.addUnsafeEnchantment(enchantementEnum.getEnchantment(), lvl - 1);
                            }
                        }
                    }
                l.set(0);
                if (itemStack.getEnchantments().containsKey(value.getEnchantment()))
                    l.set(itemStack.getEnchantmentLevel(value.getEnchantment()));
                kItem.setItem(new ItemCreator(Material.ENCHANTED_BOOK, l.get()).name(value.getName()).lore("", "§8§l» §fStatut: §c" + l, "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());

            });
            this.kInventory.setElement(slot[0], kItem);
            slot[0]++;
        }
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
