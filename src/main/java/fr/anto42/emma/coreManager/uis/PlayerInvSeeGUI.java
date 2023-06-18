package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInvSeeGUI {

    private final KInventory kInventory;

    public PlayerInvSeeGUI(Player p) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lInventaire de " + p.getName());
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 14).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 14).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new SpecInfoGUI(p).getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);

        if(p.getInventory().getHelmet() != null) {
            KItem kItem = new KItem(p.getInventory().getHelmet());
            kInventory.setElement(0, kItem);
        }
        if(p.getInventory().getChestplate() != null) {
            KItem kItem = new KItem(p.getInventory().getChestplate());
            kInventory.setElement(1, kItem);
        }if(p.getInventory().getLeggings() != null) {
            KItem kItem = new KItem(p.getInventory().getLeggings());
            kInventory.setElement(2, kItem);
        }if(p.getInventory().getBoots() != null) {
            KItem kItem = new KItem(p.getInventory().getBoots());
            kInventory.setElement(3, kItem);
        }
        final int[] slot = {9};
        for (ItemStack itemStack : p.getInventory()) {
            if (itemStack == null) {
                slot[0]++;
            } else {
                this.kInventory.setElement(slot[0], new KItem(itemStack));
                slot[0]++;
            }
        }

        KItem health = new KItem(new ItemCreator(SkullList.HEART.getItemStack()).name("§8┃ §dVie: " + p.getHealth() + "/" + p.getMaxHealth()).get());
        this.kInventory.setElement(5, health);

        KItem exp =new KItem(new ItemCreator(SkullList.XP.getItemStack()).name("§8┃ §eXP: " + p.getLevel() + " levels").get());
        this.kInventory.setElement(6, exp);


    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
