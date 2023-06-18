package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SavesGUI {
    private final KInventory kInventory;
    public SavesGUI(Player player, boolean priv) {
        priv = true;
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lSauvegardes");
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
        back.addCallback((kInventoryRepresentation, itemStack, player5, kInventoryClickContext) -> {
            UHC.getInstance().getUhcManager().getConfigMainGUI().open(player5);
        });
        this.kInventory.setElement(49, back);

        KItem save = new KItem(new ItemCreator(SkullList.LIME_BALL.getItemStack()).name("§8┃ §aCréer une nouvelle sauvegarde").lore("", "§8┃ §6Cliquez §fpour ajouter une nouvelle sauvegarde", "§8┃ §fet ainsi vous §aépargner du temps §fde configuration", "", "§8§l» §6Cliquez §fpour sauvegarder.").get());
        save.addCallback((kInventoryRepresentation, itemStack, player4, kInventoryClickContext) -> {
            UHC.getUHCPlayer(player4).sendClassicMessage("§cIndisponible dans cette version !");
            return;
            /*if (!UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().equals(UHC.getUHCPlayer(player4))) {
                UHC.getUHCPlayer(player4).sendClassicMessage("§cSeul l'Host de la partie peut créer une nouvelle sauvegarde !");
                SoundUtils.playSoundToPlayer(player4, Sound.VILLAGER_NO);
                return;
            }
            UHC.getInstance().getUhcGame().getUhcConfig().setCreator(UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().getName());
            try (Connection conn = HikariConnector.get().getConnection()) {
                UUID uuid = UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().getUuid();
                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO saves (owner, json) VALUES (?, ?)")) {
                    stmt.setObject(1, uuid);
                    stmt.setString(2, UHC.getInstance().getSaveSerializationManager().serialize(UHC.getInstance().getUhcGame().getUhcConfig()));
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                new SavesGUI(player4, false).getkInventory().open(player4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.kInventory.setElement(4, save);

        /*KItem my = new KItem(new ItemCreator(SkullList.MASCOTTE_COMPUTER.getItemStack()).name("§8┃ §fMes configurations").lore("", "§8§l» §fStatut: " + (priv ? "§ames créations" : "§ctoutes les créations"), "", "§8┃ §fAffichez uniquement vos configurations", "", "§8§l» §6Cliquez §fpour sélectionner.").get());
        my.addCallback((kInventoryRepresentation, itemStack, player1, kInventoryClickContext) -> {
            if (priv) new SavesGUI(player, false).getkInventory().open(player1);

            else new SavesGUI(player, true).getkInventory().open(player1);
        });
        this.kInventory.setElement(3, my);

        int slot = 9;
        try (Connection conn = HikariConnector.get().getConnection()) {
            if (conn == null) {
                UHC.getInstance().getLogger().warning("Failed to connect to database.");
                return;
            }
            String query = "SELECT id, owner, json FROM saves";
            if (priv) {
                query += " WHERE owner = ?";
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                if (priv) {
                    stmt.setObject(1, player.getUniqueId());
                }
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (!resultSet.next()) {
                        KItem anySave = new KItem(new ItemCreator(SkullList.COMMANDBLOCK_RED.getItemStack()).name("§8┃ §cAucune sauvegarde trouvée").lore("", "§8┃ §fAucune sauvegarde n'a été trouvée.", "", "§8§l» §6Cliquez §fpour rafraîchir.").get());
                        anySave.addCallback((kInventoryRepresentation, itemStack, player2, kInventoryClickContext) -> {
                            new SavesGUI(player, false).getkInventory().open(player2);
                        });
                        this.kInventory.setElement(22, anySave);
                    } else {
                        do {
                            int id = resultSet.getInt("id");
                            UUID owner = UUID.fromString(resultSet.getString("owner"));
                            String json = resultSet.getString("json");
                            UHCConfig config = UHC.getInstance().getSaveSerializationManager().deserialize(resultSet.getString("json"));
                            if (!owner.equals(player.getUniqueId())) {
                                continue;
                            }
                            KItem saveItem = new KItem(new ItemCreator(SkullList.BLOCK_COMMANDBLOCK_DEFAULT.getItemStack()).name("§8┃ §6" + config.getUHCName()).lore("", "§8§l» §6Cliquez §fpour charger.", "§8§l» §6Jetez §fpour supprimer.").get());
                            saveItem.addCallback((kInventoryRepresentation, itemStack, player3, kInventoryClickContext) -> {
                                if(kInventoryClickContext.getInventoryAction().equals(InventoryAction.DROP_ONE_SLOT) || kInventoryClickContext.getInventoryAction().equals(InventoryAction.DROP_ALL_SLOT)) {
                                    if (!owner.equals(player3.getUniqueId())) {
                                        UHC.getUHCPlayer(player3).sendClassicMessage("§cSeul le créateur de la configuration peut supprimer cette dernière !");
                                        SoundUtils.playSoundToPlayer(player3, Sound.VILLAGER_NO);
                                        return;
                                    }
                                    new DeleteFileGUI(id, new SavesGUI(player, true).getkInventory()).getkInventory().open(player);
                                } else if (!UHC.getInstance().getUhcGame().getUhcData().getHostPlayer().equals(UHC.getUHCPlayer(player))) {
                                    UHC.getUHCPlayer(player).sendClassicMessage("§cSeul l'Host de la partie peut charger une sauvegarde !");
                                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                                    return;
                                }
                                try {
                                    UHCConfig loadedConfig = UHC.getInstance().getSaveSerializationManager().deserialize(json);
                                    UHC.getInstance().getUhcGame().setUhcConfig(loadedConfig);
                                    UHC.getInstance().getUhcManager().setScenarioManager(new ScenarioManager());
                                    PlayersUtils.broadcastMessage("§7La nouvelle configuration chargée est " + loadedConfig.getUHCName() + "§7.");
                                } catch (SerializationException e) {
                                    UHC.getInstance().getLogger().warning("Failed to deserialize saved UHCConfig from database.");
                                    e.printStackTrace();
                                }
                            });
                            this.kInventory.setElement(slot, saveItem);
                            slot++;
                        } while (resultSet.next());
                    }
                }
            } catch (SQLException e) {
                UHC.getInstance().getLogger().warning("Failed to execute database query.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            UHC.getInstance().getLogger().warning("Failed to connect to database.");
            e.printStackTrace();
        }*/
    });
    }
public KInventory getkInventory() {
        return kInventory;

    }
}




