package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.impl.config.UHCConfig;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class TimerConfigGUI {
    private final KInventory kInventory;
    private final UHCConfig uhcConfig = UHC.getInstance().getUhcGame().getUhcConfig();

    final boolean perm;

    public TimerConfigGUI(boolean perm) {
        this.perm = perm;
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lGestion des timers");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 6).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 6).get());
            this.kInventory.setElement(i, glass);
        }
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) new RulesGUI().getkInventory().open(player);
            else UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem startGod = new KItem(new ItemCreator(Material.GOLDEN_APPLE).name("§8┃ §fInvincibilité de départ").lore("", "§8§l» §fStatut: §c" + uhcConfig.getGodStart() + "§f secondes", "", "§8┃ §6Pofinnez§f l'invicibilité de départ", "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 5." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 5.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        startGod.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhcConfig.getGodStart() == 600)
                    return;
                uhcConfig.setGodStart(uhcConfig.getGodStart() + 5);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhcConfig.getGodStart() == 0)
                    return;
                uhcConfig.setGodStart(uhcConfig.getGodStart()- 5);
            }
            startGod.setItem(new ItemCreator(Material.GOLDEN_APPLE).name("§8┃ §fInvincibilité de départ").lore("", "§8§l» §fStatut: §c" + uhcConfig.getGodStart() + "§f secondes", "", "§8┃ §6Pofinnez§f l'invicibilité de départ", "", "§8§l» §6Clique-gauche §fpour ajouter 5.", "§8§l» §6Clique-droit §fpour retirer 5.").get());

        });
        this.kInventory.setElement(20, startGod);

        KItem pvp = new KItem(new ItemCreator(Material.DIAMOND_SWORD).name("§8┃ §fPvP").lore("", "§8§l» §fStatut: §c" + uhcConfig.getPvp() + "§f minutes", "", "§8┃ §6Séléctionnez §fà partir de quand le ", "§8┃ §cPvP §fsera §aactivé", "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        pvp.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhcConfig.getPvp() == 120)
                    return;
                uhcConfig.setPvp(uhcConfig.getPvp() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhcConfig.getPvp() == 1)
                    return;
                uhcConfig.setPvp(uhcConfig.getPvp() - 1);
            }
            pvp.setItem(new ItemCreator(Material.DIAMOND_SWORD).name("§8┃ §fPvP").lore("", "§8§l» §fStatut: §c" + uhcConfig.getPvp() + "§f minutes", "", "§8┃ §6Séléctionnez §fà partir de quand le ", "§8┃ §cPvP §fsera §aactivé", "", "§8§l» §6Clique-gauche §fpour augmenter de 1.", "§8§l» §6Clique-droit §fpour réduire de 1.").get());
        });
        this.kInventory.setElement(21, pvp);

        KItem roles = new KItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fRôles").lore("", "§8§l» §fStatut: §c" + uhcConfig.getRoles() + "§f minutes", "", "§8┃ §6Séléctionnez §fle moment de séléction des rôles ", "§8┃ §fsi le mode de jeu le permet", "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        roles.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhcConfig.getRoles() == 120)
                    return;
                uhcConfig.setRoles(uhcConfig.getRoles() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhcConfig.getRoles() == 1)
                    return;
                uhcConfig.setRoles(uhcConfig.getRoles() - 1);
            }
            roles.setItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fRôles").lore("", "§8§l» §fStatut: §c" + uhcConfig.getRoles() + "§f minutes", "", "§8┃ §6Séléctionnez §fle moment de séléction des rôles ", "§8┃ §fsi le mode de jeu le permet", "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(23, roles);

        KItem borderTime = new KItem(new ItemCreator(SkullList.BEDROCK.getItemStack()).name("§8┃ §fBordure").lore("", "§8§l» §fStatut:§c "+ uhcConfig.getTimerBorder() + "§f minutes", "", "§8┃ §6Séléctionnez §fà partir de quand la", "§8┃ §cbordure §fsera §amise en mouvement", "", (perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        borderTime.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhcConfig.getTimerBorder() == 120)
                    return;
                uhcConfig.setTimerBorder(uhcConfig.getTimerBorder() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhcConfig.getTimerBorder() == 1)
                    return;
                uhcConfig.setTimerBorder(uhcConfig.getTimerBorder() - 1);
            }
            borderTime.setItem(new ItemCreator(SkullList.BEDROCK.getItemStack()).name("§8┃ §fBordure").lore("", "§8§l» §fStatut:§c "+ uhcConfig.getTimerBorder() + "§f minutes", "", "§8┃ §6Séléctionnez §fà partir de quand la", "§8┃ §cbordure §f sera §amise en mouvement", "", "§8§l» §6Clique-gauche §fpour augmenter de 1.", "§8§l» §6Clique-droit §fpour réduire de 1.").get());
        });
        this.kInventory.setElement(24, borderTime);

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
