package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.UHCManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameModeGUI {
    private final KInventory kInventory;
    private final UHCManager uhcManager = UHC.getInstance().getUhcManager();

    public GameModeGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lModes de jeu");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1).get());
            this.kInventory.setElement(i, glass);
        }


        final int[] slot = {9};
        uhcManager.getModuleList().forEach(module -> {
            if (!module.isAvaible())
                return;
            List<String> strings = new ArrayList<>();
            strings.add("");
            if (module.getDesc().size() != 0) {
                strings.addAll(module.getDesc());
            } else strings.add("§8┃ §cAucune information.");
            strings.add("");
            strings.add("§8§l» §fStatut: " + (module == UHC.getInstance().getUhcManager().getGamemode() ? "§aactivé" : "§cdésactivé"));
            strings.add("");
            if (module.getDev() != null) {
                strings.add("§8┃ §fDéveloppeur: §b" + module.getDev());
                strings.add("");
            }
            if (module.isConfigurable()){
                strings.add("§8§l» §6Clique-gauche §fpour séléctionner.");
                strings.add("§8§l» §6Clique-droit §fpour §bconfigurer§f.");
            } else {
                strings.add("§8§l» §6Clique-gauche §fpour séléctionner.");
            }
            KItem kItem = new KItem(new ItemCreator(module.getItemStack()).name("§8┃ §6" + module.getName() + (module.isAvaible() ? "" : " §c(exclusif aux staffs)")).lore(strings).get());
            if (module.isConfigurable()){
                kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                    if (kInventoryClickContext.getClickType().isLeftClick()){
                        if (Objects.equals(module.getName(), uhcManager.getGamemode().getName()))
                            return;
                        uhcManager.getGamemode().onUnLoad();
                        uhcManager.setGamemode(module);
                        uhcManager.getGamemode().onLoad();
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le nouveau mode de jeu séléctionné par l'Host est " + module.getName() + "§7 !");
                        new GameModeGUI().getkInventory().open(player);
                    }
                    else if (kInventoryClickContext.getClickType().isRightClick())
                        module.getkInventory().open(player);
                });
            }
            else {
                kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                    if (Objects.equals(module.getName(), uhcManager.getGamemode().getName()))
                        return;
                    if (kInventoryClickContext.getClickType().isLeftClick()){
                        uhcManager.getGamemode().onUnLoad();
                        uhcManager.setGamemode(module);
                        uhcManager.getGamemode().onLoad();
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le nouveau mode de jeu séléctionné par l'Host est " + module.getName() + "§7 !");
                        new GameModeGUI().getkInventory().open(player);
                    }
                });
            }
            this.kInventory.setElement(slot[0], kItem);
            slot[0]++;
        });





        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
