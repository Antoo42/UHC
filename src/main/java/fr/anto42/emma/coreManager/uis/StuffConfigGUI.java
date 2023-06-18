package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.impl.config.StuffConfig;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class StuffConfigGUI {

    String translate(boolean b) {
        if (!b) {
            return "§cdésactivé";
        } else {
            return "§aactivé";
        }
    }

    private final KInventory kInventory;
    private final StuffConfig stuffConfig = UHC.getInstance().getUhcGame().getUhcConfig().getStuffConfig();
    public StuffConfigGUI(boolean perm) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration de l'équipement");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 9).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 9).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm){
               new RulesGUI().getkInventory().open(player);
               return;
            }
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem helmet = new KItem(new ItemCreator(Material.DIAMOND_HELMET).name("§8┃ §fCasque en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondHelmet()), "", "§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        helmet.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (stuffConfig.isDiamondHelmet()) {
                stuffConfig.setDiamondHelmet(false);
                helmet.setItem(new ItemCreator(Material.DIAMOND_HELMET).name("§8┃ §fCasque en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondHelmet()), "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            } else {
                stuffConfig.setDiamondHelmet(true);
                helmet.setItem(new ItemCreator(Material.DIAMOND_HELMET).name("§8┃ §fCasque en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondHelmet()), "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(20, helmet);

        KItem chesplate = new KItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).name("§8┃ §fPlastron en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondChesp()), "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        chesplate.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (stuffConfig.isDiamondChesp()) {
                stuffConfig.setDiamondChesp(false);
                chesplate.setItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).name("§8┃ §fPlastron en diamant").lore("", "§8§l» §fStatut: §cdésactivé", "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            } else {
                stuffConfig.setDiamondChesp(true);
                chesplate.setItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).name("§8┃ §fPlastron en diamant").lore("", "§8§l» §fStatut: §aactivé", "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(21, chesplate);

        KItem leggs = new KItem(new ItemCreator(Material.DIAMOND_LEGGINGS).name("§8┃ §fJambières en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondLeggins()), "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        leggs.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (stuffConfig.isDiamondLeggins()) {
                stuffConfig.setDiamondLeggins(false);
                leggs.setItem(new ItemCreator(Material.DIAMOND_LEGGINGS).name("§8┃ §fJambières en diamant").lore("", "§8§l» §fStatut: §cdésactivé","", "§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            } else {
                stuffConfig.setDiamondLeggins(true);
                leggs.setItem(new ItemCreator(Material.DIAMOND_LEGGINGS).name("§8┃ §fJambières en diamant").lore("", "§8§l» §fStatut: §aactivé","", "§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(22, leggs);

        KItem boots = new KItem(new ItemCreator(Material.DIAMOND_BOOTS).name("§8┃ §fBottes en diamant").lore("", "§8§l» §fStatut: " + translate(stuffConfig.isDiamondBoots()),"", "§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        boots.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (stuffConfig.isDiamondBoots()) {
                stuffConfig.setDiamondBoots(false);
                boots.setItem(new ItemCreator(Material.DIAMOND_BOOTS).name("§8┃ §fBottes en diamant").lore("", "§8§l» §fStatut: §cdésactivé","", "§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            } else {
                stuffConfig.setDiamondBoots(true);
                boots.setItem(new ItemCreator(Material.DIAMOND_BOOTS).name("§8┃ §fBottes en diamant").lore("", "§8§l» §fStatut: §aactivé", "","§8┃ §aActivez §cou non§f la possibilté de craft avec", "§8┃ §fcette pièce d'armure !", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(23, boots);

        KItem enchants = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fEnchantements").lore("", "§8┃ §6Paramétrez §fles enchantements maximums de la partie","", "§8§l» §6Cliquez §fpour ouvrir.").get());
        enchants.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                new EnchantsConfigGUI(false).getkInventory().open(player);
            else
                UHC.getInstance().getUhcManager().getEnchantsConfigGUI().open(player);
        });
        this.kInventory.setElement(24, enchants);

        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new SettingsConfigGUI(false).getkInventory().open(player);
        });
        this.kInventory.setElement(47, settings);

        KItem scenarios = new KItem(new ItemCreator(SkullList.BOOKSHELF.getItemStack()).name("§8┃ §fScénarios").lore("", "§8┃ §fUne partie est toujours plus §aextravagante", "§8┃ §favec §aces derniers", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        scenarios.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new ScenariosActivatedGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(51, scenarios);


    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
