package fr.anto42.emma.game.modes.deathNoteV4.uis;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Shinigami;
import fr.anto42.emma.game.modes.deathNoteV4.utils.TimerActionbarTask;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KiraDnInventory {

    private final KInventory kInventory;
    private final KiraMainInventory kiraMainInventory;
    public int p = 0;
    private final DNModule dn;

    public KiraDnInventory(KiraMainInventory kiraMainInventory){
        this.kInventory = new KInventory(27, UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cAttaque");
        this.kiraMainInventory = kiraMainInventory;
        dn = GameUtils.getModule();
    }

    public void open(Player player){
        p = 0;

        for(Player online : getNearby(player.getLocation(), ((DNModule) kiraMainInventory.getKira().getModule()).getDistanceDeathNote())){
            if(online == player)
                continue;
            if (online.getGameMode() != GameMode.SURVIVAL)
                return;

            KItem kItem = new KItem(new ItemCreator(Material.SKULL_ITEM, 1, (short) 3).owner(online.getName()).name("§8┃ §c" + online.getName()).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player1, kInventoryClickContext) -> {
                player1.closeInventory();
                kiraMainInventory.setCanUse(false);
                UHC.getUHCPlayer(player1).sendClassicMessage("§7Pouvoir appliqué sur §c" + online.getName() + "§7 !");
                UHC.getUHCPlayer(player1).sendClassicMessage("§7Ce joueur perdra §cla moitié de sa vie §7dans 40 secondes.");
                player1.playSound(player1.getLocation(), Sound.LEVEL_UP, 1, 1);
                new TimerActionbarTask("§8§l» §3Application du DeathNote", 40, "§aDeathNote appliqué").sendToPlayer(player1);
                for(UHCPlayer viewer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()){
                    UHCPlayer kira = UHC.getUHCPlayer(player);
                    if(viewer.getUhcTeam() == kira.getUhcTeam() && dn.seeWhenDN.contains(viewer)){
                        viewer.sendClassicMessage("§cLe Kira de votre équipe vient d'utiliser son DeathNote !");
                    }
                }


                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), ()->{
                    player1.playSound(player1.getLocation(), Sound.LEVEL_UP, 1, 1);
                    UHCPlayer target = UHC.getUHCPlayer(online);
                    SoundUtils.playSoundToPlayer(target.getBukkitPlayer(), Sound.WOLF_WHINE);
                    if(target.getRole() instanceof Shinigami || target.getRole() instanceof Mello && dn.melloType.get(target.getBukkitPlayer().getUniqueId()) .equals("méchant")){
                        target.sendClassicMessage("§cVous avez été attaqué(e) par le DeathNote, néanmoins vous y êtes immunisé(e) !");
                    } else if(target.getBukkitPlayer().getGameMode() != GameMode.SPECTATOR) {
                        target.sendClassicMessage("§cVous avez été attaqué(e) par le pouvoir du DeathNote !");
                        int p = ((DNModule) kiraMainInventory.getKira().getModule()).getDeathNoteListener().getEpisodeNumber();
                        int j = (p == 2 ? 10 : (p == 3 ? 8 : 6));

                        target.getBukkitPlayer().setMaxHealth(target.getBukkitPlayer().getMaxHealth() - j);
                    }
                }, TimeUtils.seconds(40));
            });
            kInventory.setElement(p, kItem);
            p++;
        }

        kInventory.open(player);
    }

    private List<Player> getNearby(Location loc, int distance) {
        List<Player> list = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getWorld() == loc.getWorld() && loc.distance(online.getLocation()) <= distance)
                list.add(online);
        }

        return list;
    }

}