package fr.anto42.emma.game.modes.taupeGun.listeners;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.RolesEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.impl.UHCData;
import fr.anto42.emma.game.modes.taupeGun.TGModule;
import fr.anto42.emma.game.modes.taupeGun.impl.TRole;
import fr.anto42.emma.game.modes.taupeGun.roles.Kits;
import fr.anto42.emma.game.modes.taupeGun.roles.SuperTaupe;
import fr.anto42.emma.game.modes.taupeGun.roles.Taupe;
import fr.anto42.emma.utils.players.InventoryUtils;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

import static fr.anto42.emma.coreManager.players.UHCPlayerStates.DEAD;

public class TGListeners implements Listener {
    private final TGModule module;

    public TGListeners(TGModule module) {
        this.module = module;
    }


    List<UHCTeam> teamList = new ArrayList<>();
    @EventHandler
    public void onRoles(RolesEvent event) {
        PlayersUtils.broadcastMessage("§7Séléction des taupes en cours...");
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            List<UHCTeam> taupesTeams = module.getData().getTeamList();
            List<UHCTeam> aliveTeams = UHCTeamManager.getInstance().getUhcTeams();
            taupesTeams.forEach(uhcTeam -> {
                System.out.println(module.getData().getUhcTeamIntegerHashMap().get(uhcTeam));
            });
            Collections.shuffle(aliveTeams);
            final int[] l = {1};
            final UHCTeam[] team = {UHCTeamManager.getInstance().createNewTeam("taupe-" + l[0], "§c§lTaupe " + l[0] + " ", DyeColor.RED, 14, "§c")};
            module.getData().getUhcTeamIntegerHashMap().put(team[0], 0);
            module.getData().getTeamList().add(team[0]);
            for (UHCTeam uhcTeam : aliveTeams) {
                List<UHCPlayer> players = uhcTeam.getUhcPlayerList();
                List<UHCPlayer> playersToRemove = new ArrayList<>();
                for (int i = 0; i < module.getConfig().getTaupePerTeams() && players.size() > 0; i++) {
                    if (module.getData().getUhcTeamIntegerHashMap().get(team[0]) == module.getConfig().getTaupeSlots()) {
                        l[0]++;
                        team[0] = UHCTeamManager.getInstance().createNewTeam("taupe-" + l[0], "§c§lTaupe " + l[0] + " ", DyeColor.RED, 14, "§c");
                        this.teamList.add(team[0]);
                        module.getData().getTeamList().add(team[0]);
                    }
                    UHCPlayer taupe = players.get(new Random().nextInt(players.size()));
                    playersToRemove.add(taupe);
                    taupe.setRole(new Taupe(module));
                    module.getData().getUhcTeamIntegerHashMap().put(team[0], l[0]);
                    System.out.println(module.getData().getUhcTeamIntegerHashMap().get(team[0]));
                    ((TRole) taupe.getRole()).setTaupeTeam(team[0]);
                    Title.sendTitle(taupe.getBukkitPlayer(), 0, 20 * 5, 15, "§cVous êtes la Taupe !", "§6§oNe le dîtes à personne !");
                    SoundUtils.playSoundToPlayer(taupe.getBukkitPlayer(), Sound.ANVIL_LAND);
                    taupe.getRole().sendDesc();
                    taupe.getRole().setRole();
                }
                players.removeAll(playersToRemove);
            }
        }, 10);

        if(module.getConfig().isSuperTaupe()){
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                PlayersUtils.broadcastMessage("§7Séléction des super taupes en cours...");
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    module.getData().getTeamList().forEach(uhcTeam -> {
                        System.out.println("Found a Taupe team: " + uhcTeam.getName());
                        List<UHCPlayer> list = new ArrayList<>();
                        list.clear();
                        UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer1 -> uhcPlayer1.getRole() instanceof TRole && ((TRole) uhcPlayer1.getRole()).getTaupeTeam() == uhcTeam).forEach(list::add);
                        UHCPlayer uhcPlayer = list.get(new Random().nextInt(list.size()));
                        Kits kit = ((TRole) uhcPlayer.getRole()).getKit();
                        boolean hasClaim = ((TRole) uhcPlayer.getRole()).isHasClaim();
                        uhcPlayer.setRole(new SuperTaupe(module));
                        ((SuperTaupe) uhcPlayer.getRole()).setHasClaim(hasClaim);
                        ((SuperTaupe) uhcPlayer.getRole()).setKit(kit);
                        ((SuperTaupe) uhcPlayer.getRole()).sendDesc();
                        if(module.getData().getRevealPlayers().contains(uhcPlayer))
                            module.getData().getRevealPlayers().remove(uhcPlayer);
                        SoundUtils.playSoundToPlayer(uhcPlayer.getBukkitPlayer(), Sound.ANVIL_LAND);
                        Title.sendTitle(uhcPlayer.getBukkitPlayer(), 0, 20*5, 15, "§cVous êtes une Super Taupe !", "§6§oNe le dîtes à personne !");
                    });
                }, 15L);
            }, 20L*60*module.getConfig().getTimerSuperTaupe());}
    }

    private final Map<UUID, Location> deathLocationMap = new HashMap<>();

    @EventHandler
    public void onDeathP(PlayerDeathEvent playerDeathEvent){
        deathLocationMap.put(playerDeathEvent.getEntity().getUniqueId(), playerDeathEvent.getEntity().getLocation());
        InventoryUtils.registerInventory(playerDeathEvent.getEntity().getUniqueId(), playerDeathEvent.getEntity());
    }

    @EventHandler
    public void onDeath(DeathEvent deathEvent){
        UHCData gameManager = UHC.getInstance().getUhcGame().getUhcData();
        UHCPlayer victim = deathEvent.getVictim();
        UHCTeam uhcTeam = victim.getUhcTeam();
        Location loc = deathEvent.getVictim().getBukkitPlayer().getLocation();
        PlayersUtils.broadcastMessage("§c" + uhcTeam.getPrefix() + victim.getName() + "§7 est mort !");

        SoundUtils.playSoundToAll(Sound.WITHER_SPAWN);

        deathEvent.getVictim().leaveTeam();
        gameManager.getUhcPlayerList().remove(victim);
        victim.setPlayerState(DEAD);

        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), ()->{
            if(!uhcTeam.isAlive()){
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), uhcTeam::destroy, 2);
            }
        }, 15);


        if(victim.getRole() instanceof Taupe){
            int l = 0;
            for (UHCPlayer aliveUhcPlayer : gameManager.getUhcPlayerList()) {
                if(aliveUhcPlayer.getRole() instanceof Taupe && ((Taupe) aliveUhcPlayer.getRole()).getTaupeTeam() == ((Taupe) victim.getRole()).getTaupeTeam() && ((Taupe) victim.getRole()).getTaupeTeam() != null) {
                    l++;

                }
            }
            if(l == 0){
                module.getData().getTeamList().remove(((Taupe) victim.getRole()).getTaupeTeam());
                ((Taupe) victim.getRole()).getTaupeTeam().destroy();

            }
        }

        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), module::winTester, 17);


        Player player = victim.getBukkitPlayer();
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
}
