package fr.anto42.emma.game.modes.taupeGun.uis;

/**
  @author Anto42_
 */

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.uis.GameModeGUI;
import fr.anto42.emma.game.modes.taupeGun.TGModule;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.Sound;

public class TGConfigGUI {
    private final KInventory kInventory;
    private final TGModule module;

    public TGConfigGUI(TGModule module) {
        this.module = module;
        kInventory = new KInventory(54, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c§lTaupe§7-§e§lGun");

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
            new GameModeGUI().getkInventory().open(player);
        });
        this.kInventory.setElement(49, back);

        KItem teamsSlots = new KItem(new ItemCreator(Material.BANNER).name("§8┃ §fTaille des équipes de taupe").lore("", "§8» §fStatut: §c" + module.getConfig().getTaupeSlots() + "§f slots", "", "§8┃ §fDéfinnissez la taille des §céquipes de taupe", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        teamsSlots.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(kInventoryClickContext.getClickType().isLeftClick()){
                if(module.getConfig().getTaupeSlots() == 10){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTaupeSlots(module.getConfig().getTaupeSlots() + 1);
            } else if (kInventoryClickContext.getClickType().isRightClick()){
                if(module.getConfig().getTaupeSlots() == 1){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTaupeSlots(module.getConfig().getTaupeSlots() - 1);
            }
            teamsSlots.setItem(new ItemCreator(Material.BANNER).name("§8┃ §fTaille des équipes de taupe").lore("", "§8» §fStatut: §c" + module.getConfig().getTaupeSlots() + "§f slots", "", "§8┃ §fDéfinnissez la taille des §céquipes de taupe", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        });
        this.kInventory.setElement(20, teamsSlots);

        KItem perTeamTaupe = new KItem(new ItemCreator(Material.MONSTER_EGG).name("§8┃ §fNombre de taupes par équipe").lore("", "§8» §fStatut: §c" + module.getConfig().getTaupePerTeams() + "§f joueur(s)", "", "§8┃ §fDéfinnissez le nombre de §ctraître §fpar équipe", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        perTeamTaupe.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(kInventoryClickContext.getClickType().isLeftClick()){
                if(module.getConfig().getTaupePerTeams() == 10){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTaupePerTeams(module.getConfig().getTaupePerTeams() + 1);
            } else if (kInventoryClickContext.getClickType().isRightClick()){
                if(module.getConfig().getTaupePerTeams() == 1){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTaupePerTeams(module.getConfig().getTaupePerTeams() - 1);
            }
            perTeamTaupe.setItem(new ItemCreator(Material.MONSTER_EGG).name("§8┃ §fNombre de taupes par équipe").lore("", "§8» §fStatut: §c" + module.getConfig().getTaupePerTeams() + "§f joueur(s)", "", "§8┃ §fDéfinnissez le nombre de §ctraître §fpar équipe", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        });
        this.kInventory.setElement(21, perTeamTaupe);

        KItem superTaupe = new KItem(new ItemCreator(Material.GOLD_SPADE).name("§8┃ §fSuper Taupes").lore("", "§8» §fStatut: §aactivé", "", "§8┃ §fChoisissez d'activer ou non les §aSuper Taupes", "§8┃ §fCes derniers sont des §ctraîtes §fau sein ", "§8┃ §fde leurs équipes de Taupe et doivent §cgagner seul", "", "§8» §6Cliquez §fpour désactiver.").get());
        superTaupe.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(module.getConfig().isSuperTaupe()){
                superTaupe.setItem(new ItemCreator(Material.GOLD_SPADE).name("§8┃ §fSuper Taupes").lore("", "§8» §fStatut: §cdésactivé", "", "§8┃ §fChoisissez d'activer ou non les §aSuper Taupes", "§8┃ §fCes derniers sont des §ctraîtes §fau sein", "§8┃ §fde leurs équipes de Taupe et doivent §cgagner seul", "", "§8» §6Cliquez §fpour activer.").get());
                module.getConfig().setSuperTaupe(false);}
            else{
                superTaupe.setItem(new ItemCreator(Material.GOLD_SPADE).name("§8┃ §fSuper Taupes").lore("", "§8» §fStatut: §aactivé", "", "§8┃ §fChoisissez d'activer ou non les §aSuper Taupes", "§8┃ §fCes derniers sont des §ctraîtes §fau sein", "§8┃ §fde leurs équipes de Taupe et doivent §cgagner seul", "", "§8» §6Cliquez §fpour désactiver.").get());
                module.getConfig().setSuperTaupe(true);
            }
        });
        this.kInventory.setElement(23, superTaupe);

        KItem durationSuperTaupe = new KItem(new ItemCreator(Material.WATCH).name("§8┃ §fSéléction des Super Taupes").lore("", "§8» §fStatut: §c" + module.getConfig().getTimerSuperTaupe() + "§f minutes après le PvP", "", "§8┃ §fChsoississez le temps d'activation des §aSuper Taupes", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        durationSuperTaupe.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            if(kInventoryClickContext.getClickType().isLeftClick()){
                if(module.getConfig().getTimerSuperTaupe() == 20){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTimerSuperTaupe(module.getConfig().getTimerSuperTaupe() + 1);
            } else if (kInventoryClickContext.getClickType().isRightClick()){
                if(module.getConfig().getTimerSuperTaupe() == 1){
                    SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                }else
                    module.getConfig().setTimerSuperTaupe(module.getConfig().getTimerSuperTaupe() - 1);
            }
            durationSuperTaupe.setItem(new ItemCreator(Material.WATCH).name("§8┃ §fSéléction des Super Taupes").lore("", "§8» §fStatut: §c" + module.getConfig().getTimerSuperTaupe() + "§f minutes après le PvP", "", "§8┃ §fChsoississez le temps d'activation des §aSuper Taupes", "", "§8» §6Clique-Droit §fpour diminuer de 1.","§8» §6Clique-Gauche§f pour augmenter de 1.").get());
        });
        this.kInventory.setElement(24, durationSuperTaupe);
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
