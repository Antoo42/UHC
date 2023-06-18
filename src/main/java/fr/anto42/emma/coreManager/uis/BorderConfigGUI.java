package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.impl.config.UHCConfig;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class BorderConfigGUI {
    private final KInventory kInventory;
    private final UHCConfig config = UHC.getInstance().getUhcGame().getUhcConfig();

    public BorderConfigGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lGestion de la bordure");
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 5).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 5).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem initialSize = new KItem(new ItemCreator(SkullList.EARTH.getItemStack()).name("§8┃ §fTaille initiale").lore("", "§8§l» §fStatut: §c" + config.getStartBorderSize() + "§f blocks", "", "§8┃ §fParamètrez le rayon initial de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 50.", "§8§l» §6Clique-droit §fpour retirer 50.").get());
        initialSize.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (config.getStartBorderSize() == 1000)
                    return;
                config.setStartBorderSize(config.getStartBorderSize() + 50);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (config.getStartBorderSize() == 50)
                    return;
                config.setStartBorderSize(config.getStartBorderSize() - 50);
            }
            initialSize.setItem(new ItemCreator(SkullList.EARTH.getItemStack()).name("§8┃ §fTaille initiale").lore("", "§8§l» §fStatut: §c" + config.getStartBorderSize() + "§f blocks", "", "§8┃ §fParamètrez le rayon initial de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 50.", "§8§l» §6Clique-droit §fpour retirer 50.").get());
        });
        this.kInventory.setElement(20, initialSize);

        KItem blockPerS = new KItem(new ItemCreator(Material.RABBIT_FOOT).name("§8┃ §fVitesse de la bordure").lore("", "§8§l» §fStatut: §c" + config.getBlockPerS() + "§f blocks/s", "", "§8┃ §fConfigurez la vitesse de réduction de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 0.5.", "§8§l» §6Clique-droit §fpour retirer 0.5.").get());
        blockPerS.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (config.getBlockPerS() == 15)
                    return;
                config.setBlockPerS((float) (config.getBlockPerS() + 0.5));
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (config.getBlockPerS() == 0.5)
                    return;
                config.setBlockPerS((float) (config.getBlockPerS() - 0.5));
            }
            blockPerS.setItem(new ItemCreator(Material.RABBIT_FOOT).name("§8┃ §fVitesse").lore("", "§8§l» §fStatut: §c" + config.getBlockPerS() + "§f blocks/s", "", "§8┃ §fConfigurez la vitesse de réduction de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 0.5.", "§8§l» §6Clique-droit §fpour retirer 0.5.").get());
        });
        this.kInventory.setElement(22, blockPerS);

        KItem finalSize = new KItem(new ItemCreator(SkullList.EARTH_2.getItemStack()).name("§8┃ §fTaille finale").lore("", "§8§l» §fStatut: §c" + config.getFinalBorderSize() + "§f blocks", "", "§8┃ §fParamètrez le rayon final de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 50.", "§8§l» §6Clique-droit §fpour retirer 50.").get());
        finalSize.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (config.getFinalBorderSize() == 1000)
                    return;
                config.setFinalBorderSize(config.getFinalBorderSize() + 50);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (config.getFinalBorderSize() == 50)
                    return;
                config.setFinalBorderSize(config.getFinalBorderSize() - 50);
            }
            finalSize.setItem(new ItemCreator(SkullList.EARTH_2.getItemStack()).name("§8┃ §fTaille finale").lore("", "§8§l» §fStatut: §c" + config.getFinalBorderSize() + "§f blocks", "", "§8┃ §fParamètrez le rayon final de la bordure", "", "§8§l» §6Clique-gauche §fpour ajouter 50.", "§8§l» §6Clique-droit §fpour retirer 50.").get());
        });
        this.kInventory.setElement(24, finalSize);



        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(47, settings);

        KItem scenarios = new KItem(new ItemCreator(SkullList.BOOKSHELF.getItemStack()).name("§8┃ §fScénarios").lore("", "§8┃ §fUne partie est toujours plus §aextravagante", "§8┃ §favec §aces derniers", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        scenarios.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getScenariosConfigGUI().open(player);
        });
        this.kInventory.setElement(51, scenarios);
    }


    public KInventory getkInventory() {
        return kInventory;
    }
}
