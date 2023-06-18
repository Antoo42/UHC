package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class ScenariosActivatedGUI {

    private final KInventory kInventory;

    public ScenariosActivatedGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lScénarios activés");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 2).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 2).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new RulesGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);
        final int[] slot = {9};
        if (UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()) {
            this.kInventory.setElement(22, new KItem(new ItemCreator(SkullList.COMMANDBLOCK_RED.getItemStack()).name("§8┃ §cListe cachée").lore("", "§8┃ §fL'Host de la partie a décider de", "§8┃ §ccaché §f les scénarios activés !").get()));
        } else{
            ScenarioManager scenarioManager = UHC.getInstance().getUhcManager().getScenarioManager();
            scenarioManager.getActivatedScenarios().forEach(uhcScenario -> {
                KItem kItem = new KItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("" , "§8§l» §fStatut: §aactivé", "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName()).get());
                this.kInventory.setElement(slot[0], kItem);
                slot[0]++;
            });
        }

    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
