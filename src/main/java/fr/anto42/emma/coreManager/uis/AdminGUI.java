package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class AdminGUI {
    private final KInventory kInventory;

    String translate(boolean b){
        if (b)
            return "§aactivé";
        else
            return "§cdésactivé";
    }

    public AdminGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c§lPanel Administrateur");
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
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> UHC.getInstance().getUhcManager().getConfigMainGUI().open(player));
        this.kInventory.setElement(49, back);


        KItem messages = new KItem(new ItemCreator(Material.PAPER).name("§8┃ §fMessages privés").lore("", "§8§l» §fStatut: " + translate(UHC.getInstance().getConfig().getBoolean("privatemessages")), "", "§8┃ §fAutorisez les joueurs à communiquer entre eux ou non", "", "§8§l» §6Cliquez§f pour séléctionner.").get());
        messages.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (UHC.getInstance().getConfig().getBoolean("privatemessages"))
                UHC.getInstance().getConfig().set("privatemessages", false);
            else
                UHC.getInstance().getConfig().set("privatemessages", true);
            messages.setItem(new ItemCreator(Material.PAPER).name("§8┃ §fMessages privés").lore("", "§8§l» §fStatut: " + translate(UHC.getInstance().getConfig().getBoolean("privatemessages")), "", "§8┃ §fAutorisez les joueurs à communiquer entre eux ou non", "", "§8§l» §6Cliquez§f pour séléctionner.").get());
        });

        this.kInventory.setElement(20, messages);



        KItem stop = new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §fFermer le serveur §7(" + Bukkit.getServerName() + ")").lore("", "§8┃ §fFermez simplement le serveur par le biais de cet item", "", "§8§l» §6Cliquez §fpour séléctionner.").get());
        stop.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                Bukkit.shutdown();
        });
        this.kInventory.setElement(22, stop);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
