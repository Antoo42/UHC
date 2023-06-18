package fr.anto42.emma.game.modes.trueLove.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.uis.GameModeGUI;
import fr.anto42.emma.game.modes.trueLove.TrueLoveModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class TrueLoveGUI {
    private final KInventory kInventory;

    public TrueLoveGUI(TrueLoveModule switchModule){
        this.kInventory = new KInventory(27, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §5§lTrueLove");
        KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 6).get());
        this.kInventory.setElement(0, glass);
        this.kInventory.setElement(1, glass);
        this.kInventory.setElement(9, glass);
        this.kInventory.setElement(8, glass);
        this.kInventory.setElement(7, glass);
        this.kInventory.setElement(17, glass);
        this.kInventory.setElement(18, glass);
        this.kInventory.setElement(19, glass);
        this.kInventory.setElement(25, glass);
        this.kInventory.setElement(26, glass);
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> new GameModeGUI().getkInventory().open(player));
        this.kInventory.setElement(22, back);


        KItem timer = new KItem(new ItemCreator(SkullList.PINK_BALL.getItemStack()).name("§8┃ §fRayon de cupidon").lore("", "§8§l» §fStatut: §c" + switchModule.getRadius() + "§f blocks", "","§8┃ §6Configurez §fle champ d'action du §dcupidon", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());
        timer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick())
                switchModule.addRadius();
            else if (kInventoryClickContext.getClickType().isRightClick())
                switchModule.reduceRadius();
            timer.setItem(new ItemCreator(SkullList.PINK_BALL.getItemStack()).name("§8┃ §fRayon de cupidon").lore("", "§8§l» §fStatut: §c" + switchModule.getRadius() + "§f blocks", "","§8┃ §6Configurez §fle champ d'action du §dcupidon", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());
        });
        this.kInventory.setElement(13, timer);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
