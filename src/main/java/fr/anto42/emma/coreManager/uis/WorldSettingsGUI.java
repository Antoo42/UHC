package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.coreManager.worldManager.WorldPopulator;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.*;

public class WorldSettingsGUI {
    private final KInventory kInventory;
    private final UHCGame uhc = UHC.getInstance().getUhcGame();

    String translate(boolean b){
        if (b)
            return "§aactivé";
        else
            return "§cdésactivé";
    }

    public WorldSettingsGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lParamètres du monde");

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

        KItem nether = new KItem(new ItemCreator(Material.NETHERRACK).name("§8┃ §cNether").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isNether()), "", "§8┃ §fSouhaitez-vous activer le §cNether §f?", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        nether.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (uhc.getUhcConfig().isNether()){
                uhc.getUhcConfig().setNether(false);
                nether.setItem(new ItemCreator(Material.NETHERRACK).name("§8┃ §cNether").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isNether()), "", "§8┃ §fSouhaitez-vous activer le §cNether §f?", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            }
            else{
                uhc.getUhcConfig().setNether(true);
                nether.setItem(new ItemCreator(Material.NETHERRACK).name("§8┃ §cNether").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isNether()), "", "§8┃ §fSouhaitez-vous activer le §cNether §f?", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            }
        });
        this.kInventory.setElement(22, nether);
        KItem createWorld = new KItem(new ItemCreator(SkullList.EARTH.getItemStack()).name("§8┃ §fCréer un nouveau monde").lore("", "§8┃ §fOuvrez le menu de création de monde", "","§8§l» §6Cliquez §fpour ouvrir.").get());
        createWorld.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new CreateWorldGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(33, createWorld);

        KItem pregen = new KItem(new ItemCreator(SkullList.CYAN_BALL.getItemStack()).name("§8┃ §fPrégénération").lore("", "§8┃ §fAfin d'évitez les lags durant votre partie,", "§8┃ §fpré-chargez les mondes de la partie !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        pregen.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new GenerationGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(24, pregen);

        KItem cleanCenter = new KItem(new ItemCreator(SkullList.CYAN_BALL.getItemStack()).name("§8┃ §fNettoyer le centre").lore("", "§8┃ §fNettoyer le centre afin que ce dernier", "§8┃ §fsoit §asans eau","", "§c§o  Cette option n'est pas désactivable !", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        cleanCenter.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (WorldManager.isClean()){
                player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                return;
            }
            new WorldPopulator().cleanWorld(false);
        });
        this.kInventory.setElement(20, cleanCenter);


        KItem roofed = new KItem(new ItemCreator(Material.SAPLING, 1, (short) 5).name("§8┃ §fForêt noir").lore( "", "§8┃ §fGénérez une §cforêt §fartificielle §aau centre de la carte","", "§c§o  Cette option n'est pas désactivable !", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        roofed.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (WorldManager.isRoofed()){
                player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                return;
            }
            new WorldPopulator().addSapling();
        });
        this.kInventory.setElement(29, roofed);

        KItem end = new KItem(new ItemCreator(SkullList.ENDERDRAGON_BALL.getItemStack()).name("§8┃ §3End").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isEnd()), "", "§8┃ §fSouhaitez-vous activer l'§3End §f?", "", "§8§l» §6Cliquez §fpour séléctionner").get());
        end.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (uhc.getUhcConfig().isEnd()){
                uhc.getUhcConfig().setEnd(false);
                end.setItem(new ItemCreator(SkullList.ENDERDRAGON_BALL.getItemStack()).name("§8┃ §3End").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isEnd()), "", "§8┃ §fSouhaitez-vous activer l'§3End §f?", "", "§8§l» §6Cliquez §fpour séléctionner").get());
            }
            else{
                uhc.getUhcConfig().setEnd(true);
                end.setItem(new ItemCreator(SkullList.ENDERDRAGON_BALL.getItemStack()).name("§8┃ §3End").lore("", "§8§l» §fStatut: " + translate(uhc.getUhcConfig().isEnd()), "", "§8┃ §fSouhaitez-vous activer l'§3End §f?", "", "§8§l» §6Cliquez §fpour séléctionner").get());
            }
        });
        this.kInventory.setElement(31, end);

        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(47, settings);

        KItem border = new KItem(new ItemCreator(Material.BEDROCK).name("§8┃ §fBordure").lore("", "§8┃ §fOuvrez le menu de gestion de la bordure", "§8┃ §fafin de configurer cette dernière", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        border.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getBorderConfigGUI().open(player);
        });
        this.kInventory.setElement(51, border);

    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
