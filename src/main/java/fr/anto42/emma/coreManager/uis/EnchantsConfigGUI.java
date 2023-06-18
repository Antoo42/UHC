package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.enchants.EnchantsManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class EnchantsConfigGUI {
    String translate(int i) {
        if (i <= 0) {
            return "§6désactivé";
        } else {
            return String.valueOf(i);
        }
    }

    private final KInventory kInventory;
    private final EnchantsManager enchantsManager = UHC.getInstance().getEnchantsManager();

    public EnchantsConfigGUI(boolean perm) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration des enchantements");

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
            if (!perm) {
                new RulesGUI().getkInventory().open(player);
                return;
            }
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem diamondSword = new KItem(new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, enchantsManager.getDiamondSharpness()).name("§8┃ §fSharpness Diamant").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getDiamondSharpness()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        diamondSword.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addDiamondSharpness(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeDiamondSharpness(1);
            diamondSword.setItem(new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, enchantsManager.getDiamondSharpness()).name("§8┃ §fSharpness Diamant").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getDiamondSharpness()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(11, diamondSword);

        KItem ironSword = new KItem(new ItemCreator(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, enchantsManager.getIronSharpness()).name("§8┃ §fSharpness Fer").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getIronSharpness()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        ironSword.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addIronSharpness(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeIronSharpness(1);
            ironSword.setItem(new ItemCreator(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, enchantsManager.getIronSharpness()).name("§8┃ §fSharpness Fer").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getIronSharpness()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(15, ironSword);

        KItem diamondProt = new KItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantsManager.getDiamondArmor()).name("§8┃ §fProtection Diamant").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getDiamondArmor()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        diamondProt.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addDiamondArmor(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeDiamondArmor(1);
            diamondProt.setItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantsManager.getDiamondArmor()).name("§8┃ §fProtection Diamant").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getDiamondArmor()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(12, diamondProt);

        KItem ironProt = new KItem(new ItemCreator(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantsManager.getIronArmor()).name("§8┃ §fProtection Fer").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getIronArmor()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        ironProt.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addIronArmor(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeIronArmor(1);
            ironProt.setItem(new ItemCreator(Material.IRON_CHESTPLATE).name("§8┃ §fProtection Fer").enchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantsManager.getIronArmor()).lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getIronArmor()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(14, ironProt);

        KItem infinity = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fInfinity").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getInfinity()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        infinity.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addInfinity(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeInfinity(1);
            infinity.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fInfinity").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getInfinity()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(28, infinity);

        KItem power = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fPower").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getPower()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        power.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addPower(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removePower(1);
            power.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fPower").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getPower()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(29, power);

        KItem punch = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fPunch").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getPunch()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        punch.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addPunch(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removePunch(1);
            punch.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fPunch").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getPunch()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(30, punch);

        KItem kb = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fKnockBack").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getKb()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        kb.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addKb(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeKb(1);
            kb.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fKnockBack").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getKb()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(31, kb);

        KItem fire = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fFireAspect").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getFireAspect()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        fire.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addFireAspect(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeFireAspect(1);
            fire.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fFireAspect").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getFireAspect()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(32, fire);

        KItem flame = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fFlame").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getFlame()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        flame.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addFlame(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeFlame(1);
            flame.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fFlame").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getFlame()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(33, flame);

        KItem thorns = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fThorns").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getThorns()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        thorns.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick())
                enchantsManager.addThorns(1);
            else if (kInventoryClickContext.getClickType().isRightClick())
                enchantsManager.removeThorns(1);
            thorns.setItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fThorns").lore("", "§8§l» §fStatut: §c" + translate(enchantsManager.getThorns()), "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());

        });
        this.kInventory.setElement(34, thorns);

        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) new SettingsConfigGUI(false).getkInventory().open(player);
            else UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(47, settings);

        KItem scenarios = new KItem(new ItemCreator(SkullList.BOOKSHELF.getItemStack()).name("§8┃ §fScénarios").lore("", "§8┃ §fUne partie est toujours plus §aextravagante", "§8┃ §favec §aces derniers", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        scenarios.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) new ScenariosActivatedGUI().getkInventory().open(player);
            else UHC.getInstance().getUhcManager().getScenariosConfigGUI().open(player);
        });
        this.kInventory.setElement(51, scenarios);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
