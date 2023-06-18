package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class SpecialGenerationGUI {

    private final KInventory kInventory;

    public SpecialGenerationGUI() {
        this.kInventory = new KInventory(27, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lCréer un nouveau monde");

        KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 5).get());
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
            UHC.getInstance().getUhcManager().getWorldConfgGUI().open(player);
        });
        this.kInventory.setElement(22, back);



        KItem createWorld = new KItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §fCréer un nouveau monde").lore("", "§8┃ §fLe monde de jeu actuel §cne vous plaît pas §f?", "§8┃ §aRe-créez en un facilement §fque vous pouvez configuré", "§8┃ §fau préalable avec l'option §eparamètres du monde", "", "§8§l» §6Cliquez §fpour séléctionner").get());
        createWorld.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cCréation d'un nouveau monde ! Le serveur peut par conséquant subir des ralentissements.");
            WorldManager.setRoofed(false);
            WorldManager.setClean(false);
            UHC.getInstance().getWorldManager().createGameWorld();
        });
        this.kInventory.setElement(14, createWorld);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
