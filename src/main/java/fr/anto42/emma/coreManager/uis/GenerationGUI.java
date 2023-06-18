package fr.anto42.emma.coreManager.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.worldManager.Generator;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.*;

public class GenerationGUI {
    private final KInventory kInventory;

    String translate (boolean b, World world) {
        if (b) return "§aprégénéré";
        else if (WorldManager.getInGeneration() == world) return "§6en cours de génération";
        else if (WorldManager.getToGenerate().contains(world)) return "§6dans la file d'attente";
        else return "§cpas prégénéré";
    }

    public GenerationGUI() {
        this.kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §6§lGénérer un monde");

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
            UHC.getInstance().getUhcManager().getWorldConfgGUI().open(player);
        });
        this.kInventory.setElement(49, back);
        KItem nether = new KItem(new ItemCreator(Material.NETHERRACK).name("§8┃ §cNether §7(" + WorldManager.getNetherWorld().getName() + ")").lore("", "§8§l» §fStatut: " + translate(UHC.getInstance().getUhcGame().getUhcData().isNetherPreload(), WorldManager.getNetherWorld()) , "", "§8┃ §fVisitez le nether et §aprégénerez§f-le §f!", "", "§8§l» §6Clique-gauche §fpour vous téléporter.", "§8§l» §6Clique-droit §fpour prégénérer.").get());
        nether.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()) {
                if (UHC.getInstance().getUhcGame().getGameState().equals(GameState.PLAYING)){
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous ne pouvez pas faire cela maintenant !");
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                    return;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(WorldManager.getNetherWorld(), 0, 80, 0));
                UHC.getUHCPlayer(player).sendClassicMessage("§7Vous êtes dans le monde §a" + player.getLocation().getWorld().getName() + "§7. Afin de revenir au spawn, vous pouvez effectuer la commande §3/spawn§7.");
            } else if (kInventoryClickContext.getClickType().isRightClick()) {
                if (UHC.getInstance().getUhcGame().getUhcData().isNetherPreload()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous avez déjà généré le monde " + WorldManager.getNetherWorld().getName());
                    return;
                }
                if (WorldManager.getToGenerate().contains(WorldManager.getNetherWorld())) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cLe monde §a" + WorldManager.getNetherWorld().getName() + "§cest déjà dans la file d'attente des mondes en génération !");
                    return;
                }
                if (WorldManager.getInGeneration() == WorldManager.getNetherWorld()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getNetherWorld().getName() + "§7 est déjà en cours de génération !");
                    WorldManager.getToGenerate().add(WorldManager.getNetherWorld());
                    return;
                }
                if (WorldManager.getInGeneration() != null) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getNetherWorld().getName() + "§7 a été ajouté dans la file d'attente des mondes en génération !");
                    WorldManager.getToGenerate().add(WorldManager.getNetherWorld());
                    return;
                }

                new Generator(WorldManager.getNetherWorld());
            }
            new GenerationGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(20, nether);

        KItem world = new KItem(new ItemCreator(SkullList.EARTH.getItemStack()).name("§8┃ §bMonde naturel §7(" + WorldManager.getGameWorld().getName() + ")").lore("", "§8§l» §fStatut: " + translate(UHC.getInstance().getUhcGame().getUhcData().isPreloadFinished(), WorldManager.getGameWorld()) , "", "§8┃ §fVisitez le monde de jeu et §aprégénerez§f-le §f!", "", "§8§l» §6Clique-gauche §fpour vous téléporter.", "§8§l» §6Clique-droit §fpour prégénérer.").get());
        world.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()) {
                if (UHC.getInstance().getUhcGame().getGameState().equals(GameState.PLAYING)){
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous ne pouvez pas faire cela maintenant !");
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                    return;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(WorldManager.getGameWorld(), 0, 201, 0));
                UHC.getUHCPlayer(player).sendClassicMessage("§7Vous êtes dans le monde §a" + player.getLocation().getWorld().getName() + "§7. Afin de revenir au spawn, vous pouvez effectuer la commande §3/spawn§7.");
            } else if (kInventoryClickContext.getClickType().isRightClick()) {
                if (UHC.getInstance().getUhcGame().getUhcData().isPreloadFinished()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous avez déjà généré le monde " + WorldManager.getGameWorld().getName());
                    return;
                }
                if (WorldManager.getToGenerate().contains(WorldManager.getGameWorld())) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cLe monde §a" + WorldManager.getGameWorld().getName() + "§cest déjà dans la file d'attente des mondes en génération !");
                    return;
                }
                if (WorldManager.getInGeneration() == WorldManager.getGameWorld()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getGameWorld().getName() + "§7 est déjà en cours de génération !");
                    WorldManager.getToGenerate().add(WorldManager.getGameWorld());
                    return;
                }
                if (WorldManager.getInGeneration() != null) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getGameWorld().getName() + "§7 a été ajouté dans la file d'attente des mondes en génération !");
                    WorldManager.getToGenerate().add(WorldManager.getGameWorld());
                    return;
                }
                new Generator(WorldManager.getGameWorld());
            }
            new GenerationGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(22, world);

        KItem end = new KItem(new ItemCreator(SkullList.ENDERDRAGON_BALL.getItemStack()).name("§8┃ §3End §7(" + WorldManager.getEndWorld().getName() + ")").lore("", "§8§l» §fStatut: " + translate(UHC.getInstance().getUhcGame().getUhcData().isEndPreload(), WorldManager.getEndWorld()) , "", "§8┃ §fVisitez l'end et §aprégénerez§f-le §f!", "", "§8§l» §6Clique-gauche §fpour vous téléporter.", "§8§l» §6Clique-droit §fpour prégénérer.").get());
        end.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if (kInventoryClickContext.getClickType().isLeftClick()) {
                if (UHC.getInstance().getUhcGame().getGameState().equals(GameState.PLAYING)){
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous ne pouvez pas faire cela maintenant !");
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                    return;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(WorldManager.getEndWorld(), 0, 80, 0));
                UHC.getUHCPlayer(player).sendClassicMessage("§7Vous êtes dans le monde §a" + player.getLocation().getWorld().getName() + "§7. Afin de revenir au spawn, vous pouvez effectuer la commande §3/spawn§7.");
            } else if (kInventoryClickContext.getClickType().isRightClick()) {
                if (UHC.getInstance().getUhcGame().getUhcData().isEndPreload()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cVous avez déjà généré le monde " + WorldManager.getEndWorld().getName());
                    return;
                }
                if (WorldManager.getToGenerate().contains(WorldManager.getEndWorld())) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§cLe monde §a" + WorldManager.getEndWorld().getName() + "§cest déjà dans la file d'attente des mondes en génération !");
                    return;
                }
                if (WorldManager.getInGeneration() == WorldManager.getEndWorld()) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getEndWorld().getName() + "§7 est déjà en cours de génération !");
                    WorldManager.getToGenerate().add(WorldManager.getEndWorld());
                    return;
                }
                if (WorldManager.getInGeneration() != null) {
                    UHC.getUHCPlayer(player).sendClassicMessage("§7Le monde §a" + WorldManager.getEndWorld().getName() + "§7 a été ajouté dans la file d'attente des mondes en génération !");
                    WorldManager.getToGenerate().add(WorldManager.getEndWorld());
                    return;
                }
                new Generator(WorldManager.getEndWorld());
            }
            new GenerationGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(24, end);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
