package fr.anto42.emma.game.modes.deathNoteV4.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class DNConfigGUI {
    private final KInventory kInventory;
    private final DNModule dn;


    public DNConfigGUI(DNModule dn) {
        this.dn = dn;
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") +  " §3Death§6Note§7-§eV4");


        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 11).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 11).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);


        KItem kItem = new KItem(new ItemCreator(Material.EXP_BOTTLE).name("§8┃ §fEnquête").lore("", "§8» §fStatut: §c" + dn.getPointsForEnquete() + " §fpoints", "", "§8┃ §fDéfinnissez le nombre de §apoints recquis §fpour §cune enquête", "", "§8» §6Clique-Droit §fpour diminuer de 100.","§8» §6Clique-Gauche§f pour augmenter de 100.").get());
        KItem kItem1 = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fDistance du §3Death§6Note").lore("", "§8» §fStatut: §c" + dn.getDistanceDeathNote() + " §fmètres", "", "§8┃ §fChoisissez le champ d'action du §3Death§6Note", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(kInventoryClickContext.getClickType().isLeftClick()){
                if(dn.getPointsForEnquete() == 5000)
                    return;
                dn.setPointsForEnquete((int) (dn.getPointsForEnquete() + 100));
            }else if(kInventoryClickContext.getClickType().isRightClick()){
                if(dn.getPointsForEnquete() == 100)
                    return;
                dn.setPointsForEnquete((int) dn.getPointsForEnquete() - 100);
            }
            kItem.setItem(new ItemCreator(Material.EXP_BOTTLE).name("§8┃ §fEnquête").lore("", "§8» §fStatut: §c" + dn.getPointsForEnquete() + " §fpoints", "", "§8┃ §fDéfinnissez le nombre de §apoints recquis §fpour §cune enquête", "", "§8» §6Clique-Droit §fpour diminuer de 100.","§8» §6Clique-Gauche§f pour augmenter de 100.").get());
        });
        kItem1.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(kInventoryClickContext.getClickType().isLeftClick()){
                if(dn.getDistanceDeathNote() == 50)
                    return;
                dn.setDistanceDeathNote(dn.getDistanceDeathNote() + 1);
            }else if(kInventoryClickContext.getClickType().isRightClick()){
                if(dn.getPointsForEnquete() == 5)
                    return;
                dn.setDistanceDeathNote(dn.getDistanceDeathNote() - 1);
            }
            kItem1.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fDistance du §3Death§6Note").lore("", "§8» §fStatut: §c" + dn.getDistanceDeathNote() + " §fmètres", "", "§8┃ §fChoisissez le champ d'action du §3Death§6Note", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        });

        this.kInventory.setElement(23, kItem);
        this.kInventory.setElement(21, kItem1);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
