package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;

public class DeleteFileGUI {

    private final KInventory kInventory;
    public DeleteFileGUI(int id, KInventory previousKinv) {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lSupprimer le fichier " + id);
        for (int i = 0; i < 9; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 7).get());
            this.kInventory.setElement(i, glass);
            this.kInventory.setElement(45 + i, glass);
        }
        for (int i = 36; i < 45; i++) {
            KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 7).get());
            this.kInventory.setElement(i, glass);
        }

        KItem back = new KItem(new ItemCreator(SkullList.LEFT_AROOW.getItemStack()).name("§8┃ §cRevenir en arrière").lore("", "§8┃ §cVous ne trouvez pas §fce que vous souhaitez ?", "§8┃ §aPas de soucis§f, revenez en arrière !", "", "§8§l» §6Cliquez §fpour ouvrir.").get());
        back.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem green = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 5).get());
        placeGlass(0, green);
        KItem kitem = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (byte) 14).get());
        placeGlass(4, kitem);

        KItem delete = new KItem(new ItemCreator(SkullList.GREEN_BALL.getItemStack()).name("§8┃ §aEcraser le fichier").lore("", "§8§l» §6Cliquez §fpour §csupprimer définitivement ce fichier§f.").get());
        delete.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            UHC.getUHCPlayer(player).sendClassicMessage("§cIndisponible dans cette version !");
            return;
            /*try (Connection conn = HikariConnector.get().getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM saves WHERE id = '" + id + "'")) {
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UHC.getUHCPlayer(player).sendClassicMessage("§aVous avez supprimé le fichier " + id + " avec succès.");
            player.closeInventory();*/
        });
        this.kInventory.setElement(20, delete);

        KItem b = new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8┃ §cAnnuler").lore("", "§8┃ §fVous souhaitez §crevenir en arrière§f ?", "", "§8§l» §6Cliquez §fpour revenir en arrière.").get());
        b.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            previousKinv.open(player);
        });
        this.kInventory.setElement(24, b);
    }

    private void placeGlass (int i, KItem kItem) {
        this.kInventory.setElement(10+i, kItem);
        this.kInventory.setElement(11+i, kItem);
        this.kInventory.setElement(12+i, kItem);
        this.kInventory.setElement(19+i, kItem);
        this.kInventory.setElement(21+i, kItem);
        this.kInventory.setElement(30+i, kItem);
        this.kInventory.setElement(29+i, kItem);
        this.kInventory.setElement(28+i, kItem);
    }

    public KInventory getkInventory() {
        return kInventory;

    }
}
