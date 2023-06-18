package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

import static fr.anto42.emma.coreManager.scenarios.ScenarioType.*;

public class ScenariosConfigGUI {
    private final KInventory kInventory;
    String translate(boolean b){
        if (b)
            return "§aactivé";
        else
            return "§cdésactivé";
    }

    String isActivate(boolean b){
        if (b)
            return "§cnon visibles";
        else
            return "§avisibles";
    }

    public ScenariosConfigGUI(ScenarioManager scenarioManager, int page, ScenarioType actualType) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lScénarios");
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
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem number = new KItem(new ItemCreator(Material.PAPER).name("§8┃ §fConfiguration des scénarios").lore("", "§8§l» §fScénarios cachés: " + translate(UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()), "", "§8§l» §6Clique-droit §fpour rendre les scénarios " + isActivate(UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()) + " §f.").get());
        number.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcGame().getUhcConfig().setHideScenarios(!UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios());
            number.setItem(new ItemCreator(Material.PAPER).name("§8┃ §fConfiguration des scénarios").lore("", "§8§l» §fScénarios cachés: " + translate(UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()), "", "§8§l» §6Clique-droit §fpour rendre les scénarios " + isActivate(UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()) + " §f.").get());
        });
        kInventory.setElement(5, number);

        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(47, settings);

        KItem timer = new KItem(new ItemCreator(SkullList.TIMER.getItemStack()).name("§8┃ §fTimers").lore("", "§8┃ §6Affinez §fles timers (temps)", "§8┃ §fde §avotre superbe partie !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        timer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getTimerConfigGUI().open(player);
        });
        this.kInventory.setElement(51, timer);

        KItem sign = new KItem(new ItemCreator(Material.SIGN).name("§8┃ §fTrieur de scénarios").lore("", "§8┃ §fTrie les scénarios afin de mieux t'y retrouver", "",
                (actualType == ALL ? "  §8§l» §a" + "Tous" : "    §7" + "Tous"),
                (actualType == PVP ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + PVP.getTypeName()),
                (actualType == PVE ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + PVE.getTypeName()),
                (actualType == STUFF ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + STUFF.getTypeName()),
                (actualType == MINNING ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + MINNING.getTypeName()),
                (actualType == WORLD ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + WORLD.getTypeName()),
                (actualType == FUN ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + FUN.getTypeName()),
                (actualType == OTHER ? "  §8§l» §a" + actualType.getTypeName() : "    §7" + OTHER.getTypeName()),
                "", "§8§l» §6Cliquez §fpour changer.").get());
        sign.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (actualType == ALL) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 101, PVP).getkInventory().open(player);
            } if (actualType == PVP) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 102, PVE).getkInventory().open(player);
            } if (actualType == PVE) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 103, STUFF).getkInventory().open(player);
            } if (actualType == STUFF) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 104, MINNING).getkInventory().open(player);
            } if (actualType == MINNING) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 105, WORLD).getkInventory().open(player);
            } if (actualType == WORLD) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 106, FUN).getkInventory().open(player);
            } if (actualType == FUN) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 107, OTHER).getkInventory().open(player);
            } if (actualType == OTHER) {
                new ScenariosConfigGUI(UHC.getInstance().getUhcManager().getScenarioManager(), 1, ALL).getkInventory().open(player);
            }
        });
        this.kInventory.setElement(3, sign);

        final int[] slot = {9};
        if (actualType == ALL) {
            scenarioManager.getInitialScenarioList().stream().filter(scenario -> scenario.getPage() == page).forEach(uhcScenario -> {
                KItem kItem;
                if (uhcScenario.isConfigurable()){
                    kItem = new KItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §6Clique-gauche §fpour activer.", "§8§l» §6Clique-droit §fpour configurer.").get());
                    kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                        if (kInventoryClickContext.getClickType().isRightClick())
                            uhcScenario.getkInventory().open(player);
                        else if (kInventoryClickContext.getClickType().isLeftClick()) {
                            if (scenarioManager.getActivatedScenarios().contains(uhcScenario)) {
                                scenarioManager.disableScenario(uhcScenario);
                                UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().remove(uhcScenario.getName());
                                kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Clique-gauche §fpour activer.", "§8§l» §6Clique-droit §fpour configurer.").get());
                            } else {
                                scenarioManager.activateScenerio(uhcScenario);
                                UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().add(uhcScenario.getName());
                                kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Clique-gauche §fpour désactiver.", "§8§l» §6Clique-droit §fpour configurer.").get());
                            }
                        }
                    });
                } else {
                    kItem = new KItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                    kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                        if (scenarioManager.getActivatedScenarios().contains(uhcScenario)){
                            scenarioManager.disableScenario(uhcScenario);
                            UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().remove(uhcScenario.getName());
                            kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(),"", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                        }else{
                            scenarioManager.activateScenerio(uhcScenario);
                            UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().add(uhcScenario.getName());
                            kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "" , uhcScenario.getDesc() , "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                        }
                    });
                }
                this.kInventory.setElement(slot[0], kItem);
                slot[0]++;
            });
        }else {
            scenarioManager.getInitialScenarioList().stream().filter(scenario -> scenario.getScenarioType() == actualType).forEach(uhcScenario -> {
                KItem kItem;
                if (uhcScenario.isConfigurable()){
                    kItem = new KItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §6Clique-gauche §fpour activer.", "§8§l» §6Clique-droit §fpour configurer.").get());
                    kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                        if (kInventoryClickContext.getClickType().isRightClick())
                            uhcScenario.getkInventory().open(player);
                        else if (kInventoryClickContext.getClickType().isLeftClick()) {
                            if (scenarioManager.getActivatedScenarios().contains(uhcScenario)) {
                                scenarioManager.disableScenario(uhcScenario);
                                UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().remove(uhcScenario.getName());
                                kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Clique-gauche §fpour activer.", "§8§l» §6Clique-droit §fpour configurer.").get());
                            } else {
                                scenarioManager.activateScenerio(uhcScenario);
                                UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().add(uhcScenario.getName());
                                kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Clique-gauche §fpour désactiver.", "§8§l» §6Clique-droit §fpour configurer.").get());
                            }
                        }
                    });
                } else {
                    kItem = new KItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(), "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                    kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                        if (scenarioManager.getActivatedScenarios().contains(uhcScenario)){
                            scenarioManager.disableScenario(uhcScenario);
                            UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().remove(uhcScenario.getName());
                            kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "", uhcScenario.getDesc(),"", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                        }else{
                            scenarioManager.activateScenerio(uhcScenario);
                            UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().add(uhcScenario.getName());
                            kItem.setItem(new ItemCreator(uhcScenario.getItemStack()).name("§8┃ §f" + uhcScenario.getName()).lore("", "§8§l» §fStatut: " + (uhcScenario.isActivated() ? "§aactivé" : "§cdésactivé"), "" , uhcScenario.getDesc() , "", "§8§l» §fType: §6" + uhcScenario.getScenarioType().getTypeName(), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
                        }
                    });
                }
                this.kInventory.setElement(slot[0], kItem);
                slot[0]++;
            });
        }


        if (page == 1) {
            KItem next = new KItem(new ItemCreator(Material.ARROW).name("§8┃ §fPage suivante").lore("", "§8┃ §fDécouvrez la suite des scénarios ici", "","§8§l» §6Cliquez §fpour ouvrir.").get());
            next.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                new ScenariosConfigGUI(scenarioManager, 2, ALL).getkInventory().open(player);
            });
            kInventory.setElement(6, next);
        }
        if (page == 2) {
            KItem previous = new KItem(new ItemCreator(Material.ARROW).name("§8┃ §fPage précédente").lore("", "§8┃ §fDécouvrez la suite des scénarios ici", "","§8§l» §6Cliquez §fpour ouvrir.").get());
            previous.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                new ScenariosConfigGUI(scenarioManager, 1, ALL).getkInventory().open(player);
            });
            kInventory.setElement(2, previous);
        }

    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
