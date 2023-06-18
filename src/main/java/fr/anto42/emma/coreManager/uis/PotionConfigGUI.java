package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class PotionConfigGUI {
    private final KInventory kInventory;

    public PotionConfigGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lGestion de la bordure");

        KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 3).get());
        this.kInventory.setElement(0, glass);
        this.kInventory.setElement(1, glass);
        this.kInventory.setElement(9, glass);
        this.kInventory.setElement(8, glass);
        this.kInventory.setElement(7, glass);
        this.kInventory.setElement(17, glass);
        this.kInventory.setElement(36, glass);
        this.kInventory.setElement(44, glass);
        this.kInventory.setElement(45, glass);
        this.kInventory.setElement(46, glass);
        this.kInventory.setElement(52, glass);
        this.kInventory.setElement(53, glass);
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(49, back);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
