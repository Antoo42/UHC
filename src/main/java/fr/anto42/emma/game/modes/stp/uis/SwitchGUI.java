package fr.anto42.emma.game.modes.stp.uis;

import fr.anto42.emma.game.modes.stp.SwitchModule;
import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.uis.GameModeGUI;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class SwitchGUI {
    private final KInventory kInventory;

    public SwitchGUI(SwitchModule switchModule){
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §e§lSwitchThePatrick");
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
            new GameModeGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);


        KItem timer = new KItem(new ItemCreator(SkullList.TIMER.getItemStack()).name("§8┃ §fTimer").lore("", "§8§l» §fStatut: §c" + switchModule.getTimer()/60 + "§f minutes", "","§8┃ §6Configurez §fla latence entre §achaque §eswitchs", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());
        timer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick())
                switchModule.addTimer();
            else if (kInventoryClickContext.getClickType().isRightClick())
                switchModule.removeTimer();
            timer.setItem(new ItemCreator(SkullList.TIMER.getItemStack()).name("§8┃ §fTimer").lore("", "§8§l» §fStatut: §c" + switchModule.getTimer()/60 + "§f minutes", "","§8┃ §6Configurez §fla latence entre §achaque §eswitchs", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour retirer 1.").get());
        });
        this.kInventory.setElement(22, timer);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
