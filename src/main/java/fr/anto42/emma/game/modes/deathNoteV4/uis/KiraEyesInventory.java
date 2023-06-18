package fr.anto42.emma.game.modes.deathNoteV4.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KiraEyesInventory {

    private KInventory kInventory;
    private final DNModule dn;

    public KiraEyesInventory(KiraMainInventory kiraMainInventory, DNModule dn){
        this.dn = dn;
        this.kInventory = new KInventory(KItem.DEFAULT, 9, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §8Yeux de la mort");

        KItem kItem = new KItem(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13)).name("§aAccepter").lore("§7Vous permet de §cvoir la vie §7au dessus des joueurs.", "§7Coût: §c3 coeurs permanents§7.", "").get());
        kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
            player.setMaxHealth(player.getMaxHealth() - 6);
            uhcPlayer.sendClassicMessage("§aVous avez débloqué les Yeux du dieu de la mort.");

            dn.getKiraHasEyesList().add(uhcPlayer);
        });

        KItem kItem2 = new KItem(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14)).name("§cAnnuler").lore("§7Vous permet de fermer ce menu.", "").get());
        kItem2.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
            UHC.getUHCPlayer(player).sendClassicMessage("§cVous avez annulé l'échange.");
        });
        KItem kItem1 = new KItem(new ItemCreator(Material.EYE_OF_ENDER).name("§cPouvoir des Yeux").lore("§7Le pouvoir des §cyeux du dieu de la mort", "§7Vous permet de voir la vie des autres joueurs de", "§7la partie en échange de §c3 coeures §7permanents.").get());

        this.kInventory.setElement(2, kItem);
        this.kInventory.setElement(4, kItem1);
        this.kInventory.setElement(6, kItem2);

    }

    public void open(Player player){
        this.kInventory.open(player);
    }

}