package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SelectTeamGUI {
    private final KInventory kInventory;
    private final UHCTeamManager uhcTeamManager = UHCTeamManager.getInstance();

    public SelectTeamGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lSéléction de l'équipe");

        KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 4).get());
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
        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cFermer le menu").lore("", "§8§l» §6Cliquez §fpour fermer.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
        });
        this.kInventory.setElement(48, back);

        KItem leaveTeam = new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §cQuitter votre équipe").lore("", "§8┃ §fVotre équipe actuelle §cne vout plaît pas §f?", "§8┃ §aAucun soucis§f, quiiter cette dernière !", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        leaveTeam.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getUHCPlayer(player).leaveTeam();
            player.getInventory().clear();
            PlayersUtils.giveWaitingStuff(player);
        });
        this.kInventory.setElement(50, leaveTeam);

        final int[] slot = {10};
        uhcTeamManager.getUhcTeams().forEach(uhcTeam -> {
            List<String> strings = new ArrayList<>();
            strings.add("");
            strings.add("§8§l» §fJoueurs de cette équipe:");
            if (uhcTeam.getUhcPlayerList().size() == 0) strings.add("§8┃ §cAucun");
            else {
                uhcTeam.getUhcPlayerList().forEach(uhcPlayer -> {
                    strings.add("§8┃ §7" + uhcPlayer.getName());
                });
            }
            strings.add("");
            strings.add("§8§l» §6Cliquez §fpour rejoindre.");
            KItem kItem = new KItem(new ItemCreator(Material.BANNER).bannerColor(uhcTeam.getDyeColor()).name(uhcTeam.getDisplayName()).lore(strings).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
                if (uhcTeam.getUhcPlayerList().contains(uhcPlayer) || uhcTeam.getPlayersAmount() >= uhcTeamManager.getSlots())
                    return;
                uhcPlayer.joinTeam(uhcTeam);
                new SelectTeamGUI().getkInventory().open(player);
                player.getInventory().clear();
                PlayersUtils.giveWaitingStuff(player);
            });
            this.kInventory.setElement(slot[0], kItem);
            slot[0]++;
            while (slot[0] == 17 || slot[0] == 18 || slot[0] == 26 || slot[0] == 27 || slot[0] == 35 || slot[0] == 36|| slot[0] == 44)
                slot[0]++;
        });
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
