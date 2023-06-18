package fr.anto42.emma.coreManager.scenarios.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.scFolder.SkyHigh;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class SkyHighGUI {
    private final KInventory kInventory;


    public SkyHighGUI(SkyHigh skyHigh) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration de NoCleanUP");

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

        KItem start = new KItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §fDélai d'activation").lore("", "§8§l» §fStatut: §c" + skyHigh.getDelay() + "§f minutes", "", "§8┃ §6Configurez §fà partir de combien de minutes", "§8┃ §fle scénario s'active t'il", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        start.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (skyHigh.getDelay() == 120)
                    return;
                skyHigh.setDelay(skyHigh.getDelay() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (skyHigh.getDelay() == 1)
                    return;
                skyHigh.setPeriod(skyHigh.getDelay() - 1);
            }
            start.setItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §fDélai d'activation").lore("", "§8§l» §fStatut: §c" + skyHigh.getDelay() + "§f minutes", "", "§8┃ §6Configurez §fà partir de combien de minutes", "§8┃ §fle scénario s'active t'il", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        });
        this.kInventory.setElement(22, start);

        KItem layer = new KItem(new ItemCreator(SkullList.ORANGE_BALL.getItemStack()).name("§8┃ §fCouche limite").lore("", "§8§l» §fStatut: §c" + skyHigh.getyLayer(), "", "§8┃ §6Configurez §fla couche §eY§f limite", "§8┃ §fsous laquelle les joueurs prendront des dégâts", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        layer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (skyHigh.getyLayer() == 256)
                    return;
                skyHigh.setDelay(skyHigh.getyLayer() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (skyHigh.getyLayer() == 0)
                    return;
                skyHigh.setPeriod(skyHigh.getyLayer() - 1);
            }
            layer.setItem(new ItemCreator(SkullList.ORANGE_BALL.getItemStack()).name("§8┃ §fCouche limite").lore("", "§8§l» §fStatut: §c" + skyHigh.getyLayer(), "", "§8┃ §6Configurez §fla couche §eY§f limite", "§8┃ §fsous laquelle les joueurs prendront des dégâts", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        });
        this.kInventory.setElement(13, layer);

        KItem ping = new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §fLatence entre chaque dégâts").lore("", "§8§l» §fStatut: §c" + skyHigh.getDelay() + "§f secondes", "", "§8┃ §6Configurez §fle temps entre chaque dégâts", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        ping.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (skyHigh.getPeriod() == 120)
                    return;
                skyHigh.setDelay(skyHigh.getPeriod() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (skyHigh.getPeriod() == 1)
                    return;
                skyHigh.setPeriod(skyHigh.getPeriod() - 1);
            }
            ping.setItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §fLatence entre chaque dégâts").lore("", "§8§l» §fStatut: §c" + skyHigh.getDelay() + "§f secondes", "", "§8┃ §6Configurez §fle temps entre chaque dégâts", "", "§8§l» §6Clique-gauche §fpour ajouter 1.", "§8§l» §6Clique-droit §fpour enlever 1.").get());
        });
        this.kInventory.setElement(15, ping);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
