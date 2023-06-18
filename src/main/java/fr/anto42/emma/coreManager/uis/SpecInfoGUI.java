package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.chat.InteractiveMessage;
import fr.anto42.emma.utils.chat.InteractiveMessageBuilder;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SpecInfoGUI {
    private final KInventory kInventory;

    public SpecInfoGUI(Player p) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(p);
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §6§lMenu de modération");
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 14).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 14).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
        });
        this.kInventory.setElement(49, back);

        KItem playerHead = new KItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §f" + uhcPlayer.getName()).lore("",
                "§8§l» §fEquipe: " + (uhcPlayer.getUhcTeam() != null ? uhcPlayer.getUhcTeam().getDisplayName() : "§cAucune"),
                "§8§l» §fRôle: " + (uhcPlayer.getRole() == null ? "§cAucun" : "§a" + uhcPlayer.getRole().getName()),
                "",
                "",
                "§8§l» §fFer(s) miné(s):§e " + uhcPlayer.getIronMined(), "§8§l» §fOr(s) miné(s): §e" + uhcPlayer.getGoldMined(), "§8§l» §fDiamant(s) miné(s): §e" + uhcPlayer.getDiamondMined(), "",
                "§8§l» §6Cliquez §fpour vous téléporter à §e" + uhcPlayer.getName() + "§f.").get());
        this.kInventory.setElement(22, playerHead);
        playerHead.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.teleport(p.getLocation());
            player.closeInventory();
        });
        KItem message = new KItem(new ItemCreator(SkullList.BLUE_BALL.getItemStack()).name("§8┃ §fEnvoyer un message").lore("", "§8§l» §6Cliquez§f pour envoyer un message à §e" + p.getName() + "§f.").get());
        message.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            InteractiveMessage interactiveMessage = new InteractiveMessage();
            interactiveMessage.add(new InteractiveMessageBuilder("§8§l» §6Cliquez§f pour envoyer un message à §e" + p.getName() + "§f.")
                    .setHoverMessage("§8§l» §6Cliquez §fpour envoyer un message privé à " + uhcPlayer.getBukkitPlayer().getDisplayName()).setClickAction(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + uhcPlayer.getName() + " ").build());
            interactiveMessage.sendMessage(player);
        });
        this.kInventory.setElement(20, message);

        KItem inv = new KItem(new ItemCreator(SkullList.CHEST.getItemStack()).name("§8┃ §fInventaire").lore("", "§8§l» §6Cliquez§f pour voir l'inventaire de §e" + p.getName()+ "§f.").get());
        inv.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            new PlayerInvSeeGUI(p).getkInventory().open(player);
        });
        this.kInventory.setElement(24, inv);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
