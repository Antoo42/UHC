package fr.anto42.emma.game.modes.taupeGun.roles;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.modes.taupeGun.impl.TRole;
import fr.anto42.emma.game.modes.taupeGun.utils.GameUtils;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.Random;

public class Taupe extends TRole {
    public Taupe(Module gamemode) {
        super("Taupe", null, gamemode);
    }

    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§6══════════════════════════════");
        getUhcPlayer().sendMessage("§eRôle : §bTaupe");
        getUhcPlayer().sendMessage("§eObjectif : §bGagner avec " + getTaupeTeam().getName());

        getUhcPlayer().sendMessage("§6══════════════════════════════");
        getUhcPlayer().sendMessage("§eEffets & Commandes :");
        getUhcPlayer().sendMessage("  §8⦿ §fUtilisez /t <message> pour communiquer avec votre équipe.");
        getUhcPlayer().sendMessage("  §8⦿ §fRécupérez les objets de votre kit avec /claim.");
        getUhcPlayer().sendMessage("  §8⦿ §fRévélez votre identité aux autres joueurs en utilisant /reveal. Vous obtiendrez une pomme d'or en conséquence.");
        getUhcPlayer().sendMessage("§6══════════════════════════════");
    }


    @Override
    public void reveal() {
        UHCTeam uhcTeam = getUhcPlayer().getUhcTeam();
        if(GameUtils.getModule().getData().getRevealPlayers().contains(getUhcPlayer())){
            getUhcPlayer().sendMessage("§3§lPeijin §8§l» §cVous avez déjà révélé votre identité !");
            SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
            return;
        }
        if (getUhcPlayer().getPlayerState() != UHCPlayerStates.ALIVE) {
            getUhcPlayer().sendMessage("§3§lPeijin §8§l» §cVous ne pouvez pas faire ça en étant mort !");
            SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
            return;
        }
        if (UHC.getInstance().getUhcGame().getGameState() != GameState.PLAYING) {
            getUhcPlayer().sendMessage("§3§lPeijin §8§l» §cVous ne pouvez pas faire ça maintenant !");
            SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
            return;
        }
        getUhcPlayer().leaveTeam();
        getUhcPlayer().joinTeam(getTaupeTeam());
        getUhcPlayer().safeGive(new ItemCreator(Material.GOLDEN_APPLE).get());
        Bukkit.broadcastMessage("§6§lUHC §8§l» §c§l" + getUhcPlayer().getName() + "§7 se rèvéle être une §c§ltaupe §7!");
        SoundUtils.playSoundToAll(Sound.GHAST_SCREAM);
        GameUtils.getModule().getData().getRevealPlayers().add(getUhcPlayer());
        if(uhcTeam.getAlivePlayersAmount() == 0)
            uhcTeam.destroy();
        GameUtils.getModule().winTester();
    }


    @Override
    public void claim() {
        if(isHasClaim()){
            getUhcPlayer().sendClassicMessage("§cVous avez déjà réclamé vos items !");
            return;
        }
        if (UHC.getInstance().getUhcGame().getGameState() != GameState.PLAYING) {
            getUhcPlayer().sendMessage("§3§lPeijin §8§l» §cVous ne pouvez pas faire ça maintenant !");
            SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
            return;
        }
        getUhcPlayer().sendClassicMessage("§7Le kit §a" + getKit().getName() + " §7vous a bien été donné !");
        setHasClaim(true);
        SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.LEVEL_UP);
        if(getKit().equals(Kits.MINEUR)){
            getUhcPlayer().safeGive(new ItemCreator(Material.DIAMOND_PICKAXE).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.IRON_SPADE).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.OBSIDIAN, 4).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.DIG_SPEED, 3).get());
        }else if(getKit().equals(Kits.WIZARD)){
            ItemStack potion = new ItemStack(Material.POTION);
            Potion pot = new Potion(PotionType.WEAKNESS, 1).splash();
            pot.apply(potion);
            getUhcPlayer().safeGive(potion);

            ItemStack potion1 = new ItemStack(Material.POTION);
            Potion pot1 = new Potion(PotionType.SLOWNESS, 1).splash();
            pot1.apply(potion1);
            getUhcPlayer().safeGive(potion1);

            ItemStack potion2 = new ItemStack(Material.POTION);
            Potion pot2 = new Potion(PotionType.POISON, 1).splash();
            pot2.apply(potion2);
            getUhcPlayer().safeGive(potion2);


        }/*else if (getKit().equals(Kits.MAGICIEN)){
            getUhcPlayer().getBukkitPlayer().getInventory().addItem(new com.pyralia.core.common.ItemCreator(Material.MONSTER_EGG, 5, (short) 5).get());
            getUhcPlayer().getBukkitPlayer().getInventory().addItem(new com.pyralia.core.common.ItemCreator(Material.MONSTER_EGG, 3, (short) 50).get());
            getUhcPlayer().getBukkitPlayer().getInventory().addItem(new com.pyralia.core.common.ItemCreator(Material.MONSTER_EGG, 2, (short) 61).get());
        }*/else if (getKit().equals(Kits.AIGLE)){
            getUhcPlayer().safeGive(new ItemCreator(Material.ENDER_PEARL, 3).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ARROW, 16).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.PROTECTION_FALL, 3).get());
        }else if (getKit().equals(Kits.ENCHANTEUR)){
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.DAMAGE_ALL, 2).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.DIG_SPEED, 2).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        }
    }

    @Override
    public void setRole() {
        int l = new Random().nextInt(Kits.values().length);
        setKit(Arrays.asList(Kits.values()).get(l));
    }
}
