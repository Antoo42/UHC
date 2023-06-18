package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.Sound;

public class SettingsConfigGUI {
    private final KInventory kInventory;
    private final UHCGame uhc = UHC.getInstance().getUhcGame();

    String translateBoolean(Boolean b){
        if (b)
            return "§aactivé";
        else
            return "§cdésactivé";
    }

    String translateSpec (String string) {
        if (string.equals("everyone")) return "§atout le monde";
        if (string.equals("dead")) return "§6joueurs morts";
        if (string.equals("nobody")) return "§6personne";
        return string;
    }

    public SettingsConfigGUI(boolean perm) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lParamètres de la partie");

        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 1).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 1).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (perm)
                UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
            else
                new RulesGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem spectators = new KItem(new ItemCreator(SkullList.EYE.getItemStack()).name("§8┃ §fSpectateurs").lore("", "§8§l» §fStatut: §atout le monde", "", "§8┃ §6Configurez §fqui pourra §aregarder votre partie", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        spectators.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (uhc.getUhcConfig().getAllowSpec().equalsIgnoreCase("everyone")){
                uhc.getUhcConfig().setAllowSpec("dead");
                spectators.setItem(new ItemCreator(SkullList.EYE.getItemStack()).name("§8┃ §fSpectateurs").lore("", "§8§l» §fStatut: " + translateSpec(uhc.getUhcConfig().getAllowSpec()), "", "§8┃ §6Configurez §fqui pourra §aregarder votre partie", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }else if (uhc.getUhcConfig().getAllowSpec().equalsIgnoreCase("dead")){
                uhc.getUhcConfig().setAllowSpec("nobody");
                spectators.setItem(new ItemCreator(SkullList.EYE.getItemStack()).name("§8┃ §fSpectateurs").lore("", "§8§l» §fStatut: " + translateSpec(uhc.getUhcConfig().getAllowSpec()), "", "§8┃ §6Configurez §fqui pourra §aregarder votre partie", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }else if (uhc.getUhcConfig().getAllowSpec().equalsIgnoreCase("nobody")){
                uhc.getUhcConfig().setAllowSpec("everyone");
                spectators.setItem(new ItemCreator(SkullList.EYE.getItemStack()).name("§8┃ §fSpectateurs").lore("", "§8§l» §fStatut: " + translateSpec(uhc.getUhcConfig().getAllowSpec()), "", "§8┃ §6Configurez §fqui pourra §aregarder votre partie", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        kInventory.setElement(16, spectators);
        KItem slots = new KItem(new ItemCreator(Material.PAPER).name("§8┃ §fSlots").lore("", "§8§l» §fStatut: §c" + uhc.getUhcConfig().getSlots() + "§f places", "", "§8┃ §6Choisissez §f le nombre", "§8┃ §fde places de votre partie","",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ?"§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        slots.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhc.getUhcConfig().getSlots() == 150)
                    return;
                uhc.getUhcConfig().setSlots(uhc.getUhcConfig().getSlots() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhc.getUhcConfig().getSlots() == 1)
                    return;
                uhc.getUhcConfig().setSlots(uhc.getUhcConfig().getSlots() - 1);
            }
            slots.setItem(new ItemCreator(Material.PAPER).name("§8┃ §fSlots").lore("", "§8§l» §fStatut: §c" + uhc.getUhcConfig().getSlots() + "§f places", "", "§8┃ §6Choisissez §f le nombre", "§8┃ §fde places de votre partie", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(10, slots);

        KItem gappleOnKill = new KItem(new ItemCreator(Material.GOLDEN_APPLE).name("§8┃ §fPomme d'or à la mort").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGappleOnKill()), "", "§8┃ §aActiver §cou non§f le drop d'une", "§8┃ §epomme en or§f lors d'un kill", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        gappleOnKill.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (uhc.getUhcConfig().isGappleOnKill()){
                uhc.getUhcConfig().setGappleOnKill(false);
                gappleOnKill.setItem(new ItemCreator(Material.GOLDEN_APPLE).name("§8┃ §fPomme d'or à la mort").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGappleOnKill()), "", "§8┃ §aActiver §cou non§f le drop d'une", "§8┃ §epomme en or§f lors d'un kill", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }else{
                uhc.getUhcConfig().setGappleOnKill(true);
                gappleOnKill.setItem(new ItemCreator(Material.GOLDEN_APPLE).name("§8┃ §fPomme d'or à la mort").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGappleOnKill()), "", "§8┃ §aActiver §cou non§f le drop d'une", "§8┃ §epomme en or§f lors d'un kill", "",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(12, gappleOnKill);

        KItem afkTime = new KItem(new ItemCreator(SkullList.DEAD_STEVE.getItemStack()).name("§8┃ §fAFK time").lore("", "§8§l» §fSatut: §c" + uhc.getUhcConfig().getAfkTime() + "§f minutes", "", "§8┃ §6Choisissez §fcombien de temps", "§8┃ §fles joueurs peuvent rester §cdéconnectés §f avant de §cmourrir", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1.": "" ), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        afkTime.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhc.getUhcConfig().getAfkTime() == 30)
                    return;
                uhc.getUhcConfig().setAfkTime(uhc.getUhcConfig().getAfkTime() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhc.getUhcConfig().getAfkTime() == 0)
                    return;
                uhc.getUhcConfig().setAfkTime(uhc.getUhcConfig().getAfkTime() - 1);
            }

            afkTime.setItem(new ItemCreator(SkullList.DEAD_STEVE.getItemStack()).name("§8┃ §fAFK time").lore("", "§8§l» §fSatut: §c" + uhc.getUhcConfig().getAfkTime() + "§f minutes", "", "§8┃ §6Choisissez §fcombien de temps", "§8┃ §fles joueurs peuvent rester §cdéconnectés §f avant de §cmourrir", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(14, afkTime);

        KItem xpBooster = new KItem(new ItemCreator(SkullList.XP.getItemStack()).name("§8┃ §fBoost d'experience").lore("", "§8§l» §fStatut: §cx" + uhc.getUhcConfig().getXpBoost(), "", "§8┃ §fVous en avez marre de ne pas pouvoir faire votre équipement tranquilement ?", "§8┃ §fCette fonctionnalité §aest faîte pour vous", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        xpBooster.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhc.getUhcConfig().getXpBoost() == 3)
                    return;
                uhc.getUhcConfig().setXpBoost(uhc.getUhcConfig().getXpBoost() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhc.getUhcConfig().getXpBoost() == 1)
                    return;
                uhc.getUhcConfig().setXpBoost(uhc.getUhcConfig().getXpBoost() - 1);
            }
            xpBooster.setItem(new ItemCreator(SkullList.XP.getItemStack()).name("§8┃ §fBoost d'experience").lore("", "§8§l» §fStatut: §cx" + uhc.getUhcConfig().getXpBoost(), "", "§8┃ §fVous en avez marre de ne pas pouvoir faire votre équipement tranquilement ?", "§8┃ §fCette fonctionnalité §aest faîte pour vous", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(22, xpBooster);

        KItem groupSystem = new KItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fSystème de groupes").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGroupSystem()), "", "§8┃ §fCe système est §aparfait §fpour §crappeler", "§8┃ §cà l'ordre les joueurs §fdans certains modes de jeu","",(perm ? "§8§l» §6Cliquez §fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        groupSystem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (uhc.getUhcConfig().isGroupSystem()){
                uhc.getUhcConfig().setGroupSystem(false);
                groupSystem.setItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fSystème de groupes").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGroupSystem()), "", "§8┃ §fCe système est §aparfait §fpour §crappeler", "§8┃ §cà l'ordre les joueurs §fdans certains modes de jeu", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }else{
                uhc.getUhcConfig().setGroupSystem(true);
                groupSystem.setItem(new ItemCreator(SkullList.LUCKYBLOCK.getItemStack()).name("§8┃ §fSystème de groupes").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isGroupSystem()), "", "§8┃ §fCe système est §aparfait §fpour §crappeler", "§8┃ §cà l'ordre les joueurs §fdans certains modes de jeu", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
            }
        });
        this.kInventory.setElement(20, groupSystem);

        KItem diamondLimit = new KItem(new ItemCreator(SkullList.DIAMOND_BALL.getItemStack()).name("§8┃ §fLimite de diamants").lore("", "§8§l» §fStatut: §c" + uhc.getUhcConfig().getDiamonLimit(), "", "§8┃ §6Définnissez§f une limite après laquelle les joueurs", "§8┃ §fmineront auront de l'§eor §flors du minage de §bdiamants", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        diamondLimit.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            if (kInventoryClickContext.getClickType().isLeftClick()){
                if (uhc.getUhcConfig().getDiamonLimit() == 100)
                    return;
                uhc.getUhcConfig().setDiamonLimit(uhc.getUhcConfig().getDiamonLimit() + 1);
            }else if (kInventoryClickContext.getClickType().isRightClick()){
                if (uhc.getUhcConfig().getDiamonLimit() == 0)
                    return;
                uhc.getUhcConfig().setDiamonLimit(uhc.getUhcConfig().getDiamonLimit() - 1);
            }
            diamondLimit.setItem(new ItemCreator(SkullList.DIAMOND_BALL.getItemStack()).name("§8┃ §fLimite de diamants").lore("", "§8§l» §fStatut: §c" + uhc.getUhcConfig().getDiamonLimit(), "", "§8┃ §6Définnissez§f une limite après laquelle les joueurs", "§8┃ §fmineront auront de l'§eor §flors du minage de §bdiamants", "",(perm ? "§8§l» §6Clique-gauche §fpour ajouter 1." : ""), (perm ? "§8§l» §6Clique-droit §fpour retirer 1.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(24, diamondLimit);

        KItem potion = new KItem(new ItemCreator(Material.POTION).name("§8┃ §fGestion des potions").lore("", "§8┃ §6Configurez §fles potions dans votre partie", "", "§c§o  Cette option n'est pas encore disponible !", "","§8§l» §6Cliquez §fpour ouvrir.").get());
        potion.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
            //UHC.getInstance().getUhcManager().getPotionConfigGUI().open(player);
        });
        this.kInventory.setElement(34, potion);

        KItem bowLife = new KItem(new ItemCreator(Material.BOW).name("§8┃ §fAffichage de la vie au contact d'une flèche").lore("", "§8§l» §fStatut: " + (uhc.getUhcConfig().isBowLife() ? "§aactivé" : "§cdésactivé"), "", "§8┃ §fLorsque cette option est §aactivé§f,", "§8┃ §florsqu'un joueur touchera quelqu'un en §6lui tirant dessus§f,", "§8┃ §fil §3aconnaîtra la vie §fde ce dernier", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        bowLife.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            uhc.getUhcConfig().setBowLife(!uhc.getUhcConfig().isBowLife());
            bowLife.setItem(new ItemCreator(Material.BOW).name("§8┃ §fAffichage de la vie au contact d'une flèche").lore("", "§8§l» §fStatut: " + (uhc.getUhcConfig().isBowLife() ? "§aactivé" : "§cdésactivé"), "", "§8┃ §fLorsque cette option est §aactivé§f,", "§8┃ §florsqu'un joueur touchera quelqu'un en §6lui tirant dessus§f,", "§8┃ §fil §3aconnaîtra la vie §fde ce dernier", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(32, bowLife);

        KItem milk = new KItem(new ItemCreator(Material.MILK_BUCKET).name("§8┃ §fSceaux de lait").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isMilkBukket()), "", "§8┃ §6Configurez §fla possibilité de §bboire", "§8┃ §bdes sceaux de laits§f, ces derniers annulant les effets de §aMinecraft", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        milk.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm)
                return;
            uhc.getUhcConfig().setMilkBukket(!uhc.getUhcConfig().isMilkBukket());
            milk.setItem(new ItemCreator(Material.MILK_BUCKET).name("§8┃ §fSceaux de lait").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isMilkBukket()), "", "§8┃ §6Configurez §fla possibilité de §bboire", "§8┃ §bdes sceaux de laits§f, ces derniers annulant les effets de §aMinecraft", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(28, milk);

        KItem lifeTab = new KItem(new ItemCreator(SkullList.HEART.getItemStack()).name("§8┃ §fVie dans le tab").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isLifeTab()), "", "§8┃ §6Configurez §fl'affichage de la", "§8┃ §dvie des joueurs §fdans le tab", "", (perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        lifeTab.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            uhc.getUhcConfig().setLifeTab(!uhc.getUhcConfig().isLifeTab());
            lifeTab.setItem(new ItemCreator(SkullList.HEART.getItemStack()).name("§8┃ §fVie dans le tab").lore("", "§8§l» §fStatut: " + translateBoolean(uhc.getUhcConfig().isLifeTab()), "", "§8┃ §6Configurez §fl'affichage de la", "§8┃ §dvie des joueurs §fdans le tab", "",(perm ? "§8§l» §6Cliquez§fpour séléctionner.": "§8§l» §cVous ne pouvez pas modifié cela.")).get());
        });
        this.kInventory.setElement(30, lifeTab);

        KItem timer = new KItem(new ItemCreator(SkullList.TIMER.getItemStack()).name("§8┃ §fTimers").lore("", "§8┃ §6Affinez §fles timers (temps)", "§8┃ §fde §avotre superbe partie !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        timer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!perm) new TimerConfigGUI(false).getkInventory().open(player);
            else UHC.getInstance().getUhcManager().getTimerConfigGUI().open(player);
        });
        this.kInventory.setElement(47, timer);

        KItem scenarios = new KItem(new ItemCreator(SkullList.BOOKSHELF.getItemStack()).name("§8┃ §fScénarios").lore("", "§8┃ §fUne partie est toujours plus §aextravagante", "§8┃ §favec §aces derniers", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        scenarios.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (perm)
                UHC.getInstance().getUhcManager().getScenariosConfigGUI().open(player);
            else{
                if (UHC.getInstance().getUhcManager().getScenarioManager().getActivatedScenarios().size() == 0){
                UHC.getUHCPlayer(player).sendClassicMessage("§cAucun scénario n'est activé !");
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                return;
                }
                new ScenariosActivatedGUI().getkInventory().open(player);
            }
        });
        this.kInventory.setElement(51, scenarios);
    }
    public KInventory getkInventory() {
        return kInventory;
    }
}
