package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.impl.config.StarterStuffConfig;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StarterInvGUI {
    private final KInventory kInventory;
    private final StarterStuffConfig stuffConfig = UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig();

    public StarterInvGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lInventaire de départ");
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 4).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 4).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new RulesGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);

        if(stuffConfig.getHead() != null) {
            KItem kItem = new KItem(stuffConfig.getHead());
            kInventory.setElement(0, kItem);
        }
        if(stuffConfig.getBody() != null) {
            KItem kItem = new KItem(stuffConfig.getBody());
            kInventory.setElement(1, kItem);
        }if(stuffConfig.getLeggins() != null) {
            KItem kItem = new KItem(stuffConfig.getLeggins());
            kInventory.setElement(2, kItem);
        }if(stuffConfig.getBoots() != null) {
            KItem kItem = new KItem(stuffConfig.getBoots());
            kInventory.setElement(3, kItem);
        }

        if (stuffConfig.getStartInv().length == 0) {
            kInventory.setElement(22, new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §cInventaire manquant").lore("", "§8┃ §fL'inventaire de départ §cn'a pas été configuré", "§8┃ §fpar l'Host de la partie").get()));
        } else {
            final int[] slot = {9};
            for (ItemStack itemStack : stuffConfig.getStartInv()) {
                if (itemStack == null) {
                    slot[0]++;
                } else {
                    this.kInventory.setElement(slot[0], new KItem(itemStack));
                    slot[0]++;
                }
            }
        }

    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
