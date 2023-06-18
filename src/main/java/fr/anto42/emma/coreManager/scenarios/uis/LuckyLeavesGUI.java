package fr.anto42.emma.coreManager.scenarios.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.scFolder.LuckyLeaves;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class LuckyLeavesGUI {
    private KInventory kInventory;

    public LuckyLeavesGUI (LuckyLeaves luckyLeaves){
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration de LuckyLeaves");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 3).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 3).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getScenariosConfigGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem chance = new KItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fChance d'avoir une pomme dorée").lore("", "§8§l» §fStatut: §c" + luckyLeaves.getChance() + "%", "", "§8┃ §6Configurez §fle taux de drop", "§8┃ §fdes pommes d'or lorsqu'un joueur casse une feuille", "", "§8§l» §6Clique-gauche §fpour augmenter de 1.", "§8§l» §6Clique-droit pour retirer 1.").get());
        chance.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick())
                luckyLeaves.addChance();
            else if (kInventoryClickContext.getClickType().isRightClick())
                luckyLeaves.increaseChance();
        });
        this.kInventory.setElement(22, chance);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
