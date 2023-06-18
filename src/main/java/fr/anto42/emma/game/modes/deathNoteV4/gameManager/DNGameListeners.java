package fr.anto42.emma.game.modes.deathNoteV4.gameManager;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.RolesEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.impl.UHCData;
import fr.anto42.emma.game.modes.deathNoteV4.DNModule;
import fr.anto42.emma.game.modes.deathNoteV4.impl.DNRole;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Near;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;
import fr.anto42.emma.game.modes.deathNoteV4.utils.NearHealthView;
import fr.anto42.emma.game.modes.deathNoteV4.roles.investigator.Inverstigator;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collections;
import java.util.List;

import static fr.anto42.emma.coreManager.players.UHCPlayerStates.DEAD;

public class DNGameListeners implements Listener {
    private final DNModule dn;
    public UHCTeam kira;
    public UHCTeam shini;
    public UHCTeam badMello;
    public DNGameListeners(DNModule dn) {
        this.dn = dn;
    }

    @EventHandler
    public void onStart(StartEvent startEvent){
        for(UHCPlayer uhcPlayer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()){
            dn.isReveal.put(uhcPlayer.getBukkitPlayer().getUniqueId(), false);
        }
        for(UHCTeam uhcTeam : UHCTeamManager.getInstance().getUhcTeams()){
            if(uhcTeam.getUhcPlayerList().size() == 0){
                uhcTeam.destroy();
            }
        }
        shini = UHCTeamManager.getInstance().createNewTeam("Shinigami", "§eShinigami ", DyeColor.YELLOW, 4, "§e");
        badMello = UHCTeamManager.getInstance().createNewTeam("Mello", "§6Mello ", DyeColor.ORANGE, 1, "§6");
        kira = UHCTeamManager.getInstance().createNewTeam("Kira", "§cKira ", DyeColor.RED, 14, "§c");
        dn.melloList.add("gentil");
        dn.melloList.add("jaloux");
        dn.melloList.add("méchant");
    }

    @EventHandler
    public void onRoles(RolesEvent event){
        UHCData gameManager = UHC.getInstance().getUhcGame().getUhcData();
        for(UHCTeam uhcTeam : UHCTeamManager.getInstance().getUhcTeams()){
            List<DNRole> roleList = dn.getDnConfig().getRoleList();
            Collections.shuffle(roleList);
            uhcTeam.getUhcPlayerList().stream().filter(uhcPlayer -> uhcPlayer.getRole() == null).forEach(uhcPlayer -> {
                if(roleList.size() != 0){
                    uhcPlayer.setRole(roleList.get(0));
                    roleList.remove(roleList.get(0));
                }else
                    uhcPlayer.setRole(new Inverstigator(GameUtils.getModule(), GameUtils.getModule()));
            });
        }
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), ()->{
            gameManager.getUhcPlayerList().forEach(uhcPlayer -> {
                if(uhcPlayer.getRole() != null){
                    uhcPlayer.getRole().setRole();
                    uhcPlayer.getRole().sendDesc();
                }
            });
        }, 20);
        NearHealthView nearHealthView = new NearHealthView(dn);
        nearHealthView.runTaskTimer(UHC.getInstance(), 0, 1);
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), dn::winTester, 20*5);
    }

    @EventHandler
    public void onDeath(DeathEvent deathEvent){
        if(deathEvent.getVictim().getRole() == null){
            PlayersUtils.broadcastMessage("§c" + deathEvent.getVictim().getName() + "§7 est mort.");
            SoundUtils.playSoundToAll(Sound.WITHER_SPAWN);
            deathEvent.getVictim().getBukkitPlayer().spigot().respawn();
            return;
        }
        deathEvent.getVictim().setPlayerState(DEAD);
        dn.canSeeRealDeath.add(UHC.getUHCPlayer(deathEvent.getVictim().getBukkitPlayer()));
        for(UHCPlayer uhcPlayer : UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList()){
            SoundUtils.playSoundToAll(Sound.WITHER_SPAWN);
            if(!dn.canSeeRealDeath.contains(uhcPlayer) && uhcPlayer.getBukkitPlayer().getGameMode() == GameMode.SURVIVAL){
                uhcPlayer.sendClassicMessage("§c" + deathEvent.getVictim().getName() + "§7 est mort.");
            } else if (deathEvent.getKiller() != null && dn.canSeeRealDeath.contains(uhcPlayer)){
                uhcPlayer.sendClassicMessage("§c" + deathEvent.getVictim().getName() + " §7s'est fait tué(e) par §c" + deathEvent.getKiller().getName());
            }else{
                uhcPlayer.sendClassicMessage("§c" + deathEvent.getVictim().getName() + "§7 est mort seul.");
            }
        }
        dn.isReveal.put(deathEvent.getVictim().getBukkitPlayer().getUniqueId(), true);
        UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().remove(deathEvent.getVictim());
        UHCTeam uhcTeam = deathEvent.getVictim().getUhcTeam();
        deathEvent.getVictim().leaveTeam();
        if(!uhcTeam.isAlive()){
            uhcTeam.destroy();
        }
        dn.winTester();
        Player player = deathEvent.getVictim().getBukkitPlayer();
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            if (!UHC.getInstance().getUhcGame().getUhcConfig().getAllowSpec().equals("nobody")){
                player.spigot().respawn();
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(WorldManager.getCenterLoc());
            } else {
                player.kickPlayer(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Je suis navré de devoir vous expulser car les spectateurs sont désactivés dans cette partie, néanmoins je vous attend pour revenir dès la prochaine partie !");
            }
        }, 5);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent){
        if(interactEvent.getItem() != null && interactEvent.getItem().getItemMeta().hasDisplayName()){
            if(interactEvent.getItem().getType() == Material.ENCHANTED_BOOK && interactEvent.getItem().getItemMeta().getDisplayName().contains("§4§lDEATH NOTE")){
                UHCPlayer uhcPlayer = UHC.getUHCPlayer(interactEvent.getPlayer());
                if(uhcPlayer.getRole() instanceof Kira){
                    ((Kira) uhcPlayer.getRole()).getKiraInv().open(uhcPlayer.getBukkitPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
            return;
        UHCPlayer hitter = UHC.getUHCPlayer(((Player) event.getDamager()));
        UHCPlayer target = UHC.getUHCPlayer(((Player) event.getEntity()));
        if(hitter.getRole() instanceof Near && hitter.getBukkitPlayer().getItemInHand().getType() == Material.GOLD_HOE){
            if(target.getRole() instanceof Kira && dn.canKira.get(hitter.getBukkitPlayer().getUniqueId())){
                dn.canKira.put(hitter.getBukkitPlayer().getUniqueId(), false);
                target.getBukkitPlayer().setMaxHealth(10);
                hitter.getBukkitPlayer().playSound(hitter.getBukkitPlayer().getLocation(), Sound.WITHER_DEATH, 1f, 1f);
            }else if (dn.canKira.get(hitter.getBukkitPlayer().getUniqueId()) && !(target.getRole() instanceof Kira)) {
                dn.canKira.put(hitter.getBukkitPlayer().getUniqueId(), false);
                dn.kiraNum.put(hitter.getBukkitPlayer().getUniqueId(), dn.kiraNum.get(hitter.getBukkitPlayer().getUniqueId()) + 1);
                hitter.getBukkitPlayer().setMaxHealth(10);
                hitter.getBukkitPlayer().playSound(hitter.getBukkitPlayer().getLocation(), Sound.ANVIL_LAND, 1f, 1f);
            } else if(!(dn.canKira.get(hitter.getBukkitPlayer().getUniqueId()))){
                hitter.sendClassicMessage("§cVous avez déjà essayé de §e§lKira-Killer ce joueur !");
                SoundUtils.playSoundToPlayer(hitter.getBukkitPlayer(), Sound.VILLAGER_NO);
            }
            if(dn.kiraNum.get(hitter.getBukkitPlayer().getUniqueId()) == 2){
                hitter.getBukkitPlayer().setHealth(0);
            }
        }
    }
    public UHCTeam getKiraTeam() {
        return kira;
    }

    public UHCTeam getShini() {
        return shini;
    }

    public UHCTeam getBadMello() {
        return badMello;
    }
}
