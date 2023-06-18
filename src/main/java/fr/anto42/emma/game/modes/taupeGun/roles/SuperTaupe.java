package fr.anto42.emma.game.modes.taupeGun.roles;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.modes.taupeGun.impl.TRole;
import fr.anto42.emma.game.modes.taupeGun.utils.GameUtils;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class SuperTaupe extends TRole {
    public SuperTaupe(Module gamemode) {
        super("Super Taupe", null, gamemode);
    }


    @Override
    public void sendDesc() {
        getUhcPlayer().sendMessage("§6══════════════════════════════");
        getUhcPlayer().sendMessage("§eRôle : §bSuper Taupe");
        getUhcPlayer().sendMessage("§eObjectif : §bGagner sans équipe");

        getUhcPlayer().sendMessage("§6══════════════════════════════");
        getUhcPlayer().sendMessage("§eEffets & Commandes :");
        getUhcPlayer().sendMessage("  §8⦿ §fUtilisez /t <message> pour communiquer avec votre équipe de taupes.");
        getUhcPlayer().sendMessage("  §8⦿ §fRévélez votre identité aux autres joueurs en utilisant /reveal. Vous obtiendrez une pomme d'or en conséquence.");
        getUhcPlayer().sendMessage("§6══════════════════════════════");
    }


    @Override
    public void reveal() {
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
        getUhcPlayer().joinTeam(UHCTeamManager.getInstance().createNewTeam("Super-Taupe", "§4§lS-TAUPE §4☠ ", DyeColor.RED, 14, "§4"));
        getUhcPlayer().safeGive(new ItemCreator(Material.GOLDEN_APPLE).get());
        Bukkit.broadcastMessage("§6§lUHC §8§l» §c§l" + getUhcPlayer().getName() + "§7 se rèvéle être une §4§lsuper taupe §7!");
        SoundUtils.playSoundToAll(Sound.GHAST_SCREAM);
        GameUtils.getModule().getData().getRevealPlayers().add(getUhcPlayer());

        int l = 0;
        for (UHCPlayer aliveUhcPlayer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()) {
            if(aliveUhcPlayer.getRole() instanceof Taupe && ((Taupe) aliveUhcPlayer.getRole()).getTaupeTeam() == getTaupeTeam() && getTaupeTeam() != null) {
                l++;

            }
        }
        if(l == 0){
            GameUtils.getModule().getData().getTeamList().remove(getTaupeTeam());
        }
        GameUtils.getModule().winTester();
    }


    boolean hasClaim = false;

    public void setHasClaim(boolean hasClaim) {
        this.hasClaim = hasClaim;
    }

    public boolean isHasClaim() {
        return hasClaim;
    }

    @Override
    public void claim() {
        if(hasClaim){
            getUhcPlayer().sendClassicMessage("§cVous avez déjà réclamé vos items !");
            return;
        }
        if (UHC.getInstance().getUhcGame().getGameState() != GameState.PLAYING) {
            getUhcPlayer().sendMessage("§3§lPeijin §8§l» §cVous ne pouvez pas faire ça maintenant !");
            SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.VILLAGER_NO);
            return;
        }
        getUhcPlayer().sendClassicMessage("§7Le kit §a" + getKit().getName() + " §7vous a bien été donné !");
        hasClaim = true;
        SoundUtils.playSoundToPlayer(getUhcPlayer().getBukkitPlayer(), Sound.LEVEL_UP);
        if(getKit().equals(Kits.MINEUR)){
            getUhcPlayer().safeGive(new ItemCreator(Material.DIAMOND_PICKAXE).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.IRON_SPADE).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.OBSIDIAN, 4).get());
            getUhcPlayer().safeGive(new ItemCreator(Material.ENCHANTED_BOOK).enchant(Enchantment.DIG_SPEED, 3).get());
        }else if(getKit().equals(Kits.WIZARD)){
            /*ItemStack potion = new ItemStack(Material.POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30*20, 1), true);
            potion.setItemMeta(meta);
            getUhcPlayer().safeGive(potion);

            ItemStack potion1 = new ItemStack(Material.POTION);
            PotionMeta meta1 = (PotionMeta) potion1.getItemMeta();
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 30*20, 1), true);
            potion.setItemMeta(meta1);
            getUhcPlayer().safeGive(potion1);

            ItemStack potion2 = new ItemStack(Material.POTION);
            PotionMeta meta2 = (PotionMeta) potion.getItemMeta();
            meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20*18, 1), true);
            potion.setItemMeta(meta2);
            getUhcPlayer().safeGive(potion2);*/

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
}