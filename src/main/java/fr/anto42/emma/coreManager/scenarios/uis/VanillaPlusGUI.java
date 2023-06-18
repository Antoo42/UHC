package fr.anto42.emma.coreManager.scenarios.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.scFolder.VanillaPlus;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class VanillaPlusGUI {
    private final KInventory kInventory;

    String translate(int number){
        if (number <= 0)
            return "§cdésactivé";
        else
            return "§c" + number;
    }

    public VanillaPlusGUI(VanillaPlus vanillaPlus) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration de VanillaPlus");

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
        KItem apple = new KItem(new ItemCreator(Material.APPLE).name("§8┃ §fTaux de drop des pommes").lore("", "§8§l» §fStatut: " + translate(vanillaPlus.getAppleRate()), "", "§8┃ §6Configurez §fle taux de drop des pommes", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        apple.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick())
                vanillaPlus.addApple();
            else if (kInventoryClickContext.getClickType().isRightClick())
                vanillaPlus.removeApple();
            apple.setItem(new ItemCreator(Material.APPLE).name("§8┃ §fTaux de drop des pommes").lore("", "§8§l» §fStatut: " + translate(vanillaPlus.getAppleRate()), "", "§8┃ §6Configurez §fle taux de drop des pommes", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        });
        this.kInventory.setElement(21, apple);

        KItem flint = new KItem(new ItemCreator(Material.FLINT).name("§8┃ §fTaux de drop du gravier").lore("", "§8§l» §fStatut: " + translate(vanillaPlus.getFlintRate()), "", "§8┃ §6Configurez §fle taux de drop du gravier", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        flint.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick())
                vanillaPlus.addFlint();
            else if (kInventoryClickContext.getClickType().isRightClick())
                vanillaPlus.removeFlint();
            flint.setItem(new ItemCreator(Material.FLINT).name("§8┃ §fTaux de drop du gravier").lore("", "§8§l» §fStatut: " + translate(vanillaPlus.getFlintRate()), "", "§8┃ §6Configurez §fle taux de drop du gravier", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        });
        this.kInventory.setElement(23, flint);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
