package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.TryStartEvent;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.discord.DiscordManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigMainGUI {
    private final KInventory kInventory;
    private final UHCGame uhc;

    public ConfigMainGUI(UHCGame uhc) {
        this.uhc = uhc;
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lConfiguration");


        KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 3).get());
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

        KItem license = new KItem(new ItemCreator(SkullList.BLOCK_COMMANDBLOCK_DEFAULT.getItemStack()).name("§8┃ §6§lUHC").lore("", "§8§l» §fVersion: §c0.9.3", "", "§8┃ §fCette license d'§6§lUHC §fvous a été distribué par §b§nAnto42_","§8┃ §fPour tout incident, veuillez le signaler au plus vite.","", "§8§l» §6Cliquez §fpour ouvrir le §cmenu d'administration§f.").get());
        license.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (!player.isOp() && !player.hasPermission("uhc.*")){
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                return;
            }
            UHC.getInstance().getUhcManager().getAdminGUI().open(player);
        });
        //this.kInventory.setElement(18, license);

        KItem saves = new KItem(new ItemCreator(SkullList.MASCOTTE_COMPUTER.getItemStack()).name("§8┃ §fSauvegardes").lore("", "§8┃ §fOuvrez ce menu pour séléctionner","§8┃ §fune sauvegarde existante", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        saves.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new SavesGUI(player, false).getkInventory().open(player);
        });
        this.kInventory.setElement(18, saves);

        KItem gamemode = new KItem(new ItemCreator(Material.NETHER_STAR).name("§8┃ §fModes de jeu").lore("", "§8┃ §fOuvrez ce menu pour séléctionner","§8┃ §fle §amode de jeu §fde la partie", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        gamemode.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new GameModeGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(22, gamemode);

        KItem worldSettings = new KItem(new ItemCreator(SkullList.EARTH.getItemStack()).name("§8┃ §fGestion des mondes").lore("", "§8┃ §fC'est §6ici §fque vous pouvez gérer les §adifférents", "§8┃ §amondes§f de votre partie !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        worldSettings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getWorldConfgGUI().open(player);
        });
        this.kInventory.setElement(4, worldSettings);

        KItem border = new KItem(new ItemCreator(Material.BEDROCK).name("§8┃ §fBordure").lore("", "§8┃ §fOuvrez le menu de gestion de la bordure", "§8┃ §fafin de configurer cette dernière", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        border.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getBorderConfigGUI().open(player);
        });
        this.kInventory.setElement(6, border);

        KItem teams = new KItem(new ItemCreator(Material.BANNER).bannerColor(DyeColor.RED).name("§8┃ §fEquipes").lore("", "§8┃ §fParfois, la vie est mieux avec des alliés !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        teams.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getTeamsConfigGUi().open(player);
        });
        this.kInventory.setElement(12, teams);

        KItem scenarios = new KItem(new ItemCreator(SkullList.BOOKSHELF.getItemStack()).name("§8┃ §fScénarios").lore("", "§8┃ §fUne partie est toujours plus §aextravagante", "§8┃ §favec §aces derniers", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        scenarios.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getScenariosConfigGUI().open(player);
        });
        this.kInventory.setElement(20, scenarios);

        KItem settings = new KItem(new ItemCreator(Material.REDSTONE_COMPARATOR).name("§8┃ §fParamètres").lore("", "§8┃ §fVous cherchez à §6paramétrer §fle déroulement de votre partie ?", "§8┃ §fVous êtes §aau bon endroit§f !", "","§8§l» §6Cliquez §fpour ouvrir").get());
        settings.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getSettingsConfigGUI().open(player);
        });
        this.kInventory.setElement(2, settings);

        KItem equipements = new KItem(new ItemCreator(Material.DIAMOND_CHESTPLATE).name("§8┃ §fEquipements").lore("", "§8┃ §6Séléctionnez §fchaque éléments du futur §3inventaire des joueurs", "","§8§l» §6Cliquez §fpour ouvrir").get());
        equipements.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getStuffConfigGUI().open(player);
        });
        this.kInventory.setElement(14, equipements);

        KItem enchants = new KItem(new ItemCreator(Material.ENCHANTED_BOOK).name("§8┃ §fEnchantements").lore("", "§8┃ §6Paramétrez §fles enchantements maximums de la partie","", "§8§l» §6Cliquez §fpour ouvrir.").get());
        enchants.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getEnchantsConfigGUI().open(player);
        });
        this.kInventory.setElement(24, enchants);

        KItem timer = new KItem(new ItemCreator(SkullList.TIMER.getItemStack()).name("§8┃ §fTimers").lore("", "§8┃ §6Affinez §fles timers (temps)", "§8┃ §fde §avotre superbe partie !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        timer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getTimerConfigGUI().open(player);
        });
        this.kInventory.setElement(30, timer);

        KItem starterStuff = new KItem(new ItemCreator(Material.CHEST).name("§8┃ §fStuff de départ").lore("", "§8┃ §fDonnez un petit coup de pouce", "§8┃ §faux joueurs au début de la partie", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        starterStuff.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (uhc.getGameState() != GameState.WAITING){
                player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cC'est trop tard pour faire cette action !");
                return;
            }
            UHC.getUHCPlayer(player).setEditing(true);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            if (uhc.getUhcConfig().getStarterStuffConfig().getStartInv().length != 0)
                player.getInventory().setContents(uhc.getUhcConfig().getStarterStuffConfig().getStartInv());
            else{
                player.getInventory().setItem(0, new ItemCreator(Material.COOKED_BEEF, 32).get());
                player.getInventory().setItem(1, new ItemCreator(Material.BOOK, 1).get());
            }
            player.getInventory().setHelmet(uhc.getUhcConfig().getStarterStuffConfig().getHead());
            player.getInventory().setChestplate(uhc.getUhcConfig().getStarterStuffConfig().getBody());
            player.getInventory().setLeggings(uhc.getUhcConfig().getStarterStuffConfig().getLeggins());
            player.getInventory().setBoots(uhc.getUhcConfig().getStarterStuffConfig().getBoots());
            player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Vous éditez désormais l'§ainventaire de départ §7!");
            player.sendMessage("");
            player.sendMessage("§8┃ §7Vous pouvez à tout moment enchanter l'item en main à l'aide de la commande §b/enchant§7.");
            player.sendMessage("");
            TextComponent msg = new TextComponent("§8┃ §7Lorsque vous aurez terminé votre §6configuration§7, §7vous §7pourrez sauvegarder l'inventaire en cliquant sur §e§nici§7 ou par §7le biais de la commande §b/save§7.");
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8§l» §6Cliquez§f pour sauvegarder.").create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/save"));
            player.spigot().sendMessage(msg);
            player.sendMessage("");
        });
        this.kInventory.setElement(32, starterStuff);

        KItem whitelist = new KItem(new ItemCreator(Material.PAPER).name("§8┃ §fWhiteList").lore("", "§8§l» §fStatut: " + (uhc.getUhcData().isWhiteList() ? "§aactivé" : "§cdésactivé"), "", "§8┃ §aAutorisez §cou non§f les joueurs", "§8┃ §fà rentrer dans votre partie", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        whitelist.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (uhc.getUhcData().isWhiteList()){
                uhc.getUhcData().setWhiteList(false);
                whitelist.setItem(new ItemCreator(Material.PAPER).name("§8┃ §fWhiteList").lore("", "§8§l» §fStatut: §cdésactivé", "", "§8┃ §aAutorisez §cou non§f les joueurs", "§8┃ §fà rentrer dans votre partie", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            }else{
                uhc.getUhcData().setWhiteList(true);
                whitelist.setItem(new ItemCreator(Material.PAPER).name("§8┃ §fWhiteList").lore("", "§8§l» §fStatut: §aactivé", "", "§8┃ §aAutorisez §cou non§f les joueurs", "§8┃ §fà rentrer dans votre partie", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            }
        });
        this.kInventory.setElement(16, whitelist);

        AtomicBoolean can = new AtomicBoolean(uhc.getUhcData().isDiscordSend());
        KItem discordGame = new KItem(new ItemCreator(SkullList.DISCORD.getItemStack()).name("§8┃ §9Discord").lore("", "§8┃ §aAnnoncer §fvotre partie sur §9Discord", "", "§c§o Cette action ne peut être effectuée qu'une fois !", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        discordGame.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (can.get()){
                player.sendMessage(UHC.getInstance().getConfig().getString("discordPrefix").replace("&", "§") + " §cVous avez déjà envoyer une annonce !");
                SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                return;
            }
            uhc.getUhcData().setDiscordSend(true);
            can.set(uhc.getUhcData().isDiscordSend());
            UHC.getInstance().getDiscordManager().sendAnounce();
            player.sendMessage(UHC.getInstance().getConfig().getString("discordPrefix").replace("&", "§") + " §aVotre partie a été annoncé sur le serveur §9Discord§a !");
            SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_YES);
        });
        if (!DiscordManager.getAnnounceURL().equalsIgnoreCase(""))
            this.kInventory.setElement(35, discordGame);


        KItem rules = new KItem(new ItemCreator(Material.BOOK).name("§8┃ §fRègles de votre partie").lore("", "§8┃ §6Consultez §fles règles actuelles de votre partie", "","§8§l» §6Cliquez §fpour ouvrir.").get());
        rules.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new RulesGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(51, rules);

        KItem start = new KItem(new ItemCreator((uhc.isStart() ? SkullList.RED_BALL.getItemStack() : SkullList.GREEN_BALL.getItemStack())).name("§8┃ §fLancer la partie").lore("", "§8§l» §fStatut: " + (uhc.isStart() ? "§cEn cours" : "§aEn attente"), "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        start.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            TryStartEvent tryStartEvent = new TryStartEvent();
            Bukkit.getServer().getPluginManager().callEvent(tryStartEvent);
            if(tryStartEvent.isCancelled()) {
                return;
            }
            if (uhc.isStart()) {
                if (uhc.getGameState() != GameState.WAITING) {
                    player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça ! Si vous souhaitez arrêter votre partie, utilisez la commande §a/host stop§c.");
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                    return;
                }
                uhc.setStart(false);
                start.setItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §fLancer la partie").lore("", "§8§l» §fStatut: §aEn attente", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            } else {
                uhc.startGame();
                start.setItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §cArrêter la partie").lore("", "§8§l» §fStatut: §cEn cours", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
            }

        });
        this.kInventory.setElement(49, start);

    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
