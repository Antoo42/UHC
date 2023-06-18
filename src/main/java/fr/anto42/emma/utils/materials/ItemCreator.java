package fr.anto42.emma.utils.materials;

import org.apache.commons.lang.Validate;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCreator {
    private final ItemStack item;

    public ItemCreator(ItemStack item) {
        this.item = item;
    }

    public ItemCreator(Material type) {
        this(new ItemStack(type));
    }

    public ItemCreator(Material type, int amount, int damage) {
        this(new ItemStack(type, amount, (short) damage));
    }

    public ItemCreator(Material type, int amount) {
        this(new ItemStack(type, amount));
    }

    public ItemStack get() {
        return item;
    }

    public ItemCreator amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemCreator damage(int damage) {
        item.setDurability((short) damage);
        return this;
    }

    public ItemCreator name(String name) {
        Validate.notNull(name, "Item name cannot be null.");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return this;
    }

    public ItemCreator lore(List<String> lore) {
        Validate.notNull(lore, "Item lore cannot be null.");

        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);

        return this;
    }

    public ItemCreator lore(String... lore) {
        Validate.notNull(lore, "Item lore cannot be null.");
        return lore(Arrays.asList(lore));
    }

    public ItemCreator enchant(Enchantment enchant, int level) {
        Validate.notNull(enchant, "Item enchantment cannot be null.");

        ItemMeta meta = item.getItemMeta();

        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta enchMeta = (EnchantmentStorageMeta) meta;
            enchMeta.addStoredEnchant(enchant, level, true);
        } else {
            meta.addEnchant(enchant, level, true);
        }

        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator flags(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);

        return this;
    }
    public ItemCreator glow(){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator owner(String name) {
        Validate.isTrue(item.getItemMeta() instanceof SkullMeta, "Item has to be a skull.");

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);

        return this;
    }

    public ItemCreator unbreakable(boolean active) {
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(active);
        item.setItemMeta(meta);

        return this;
    }

    public ItemCreator bannerColor(DyeColor dyeColor) {
        BannerMeta meta = (BannerMeta)this.item.getItemMeta();
        meta.setBaseColor(dyeColor);
        this.item.setItemMeta((ItemMeta)meta);
        return this;
    }

    public ItemCreator patterns(ArrayList<Pattern> patterns) {
        BannerMeta meta = (BannerMeta)this.item.getItemMeta();
        for (Pattern pattern : patterns) {
            meta.addPattern(pattern);
        }
        return this;
    }
}
