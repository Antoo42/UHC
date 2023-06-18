package fr.anto42.emma.coreManager.tasks;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.Cuboid;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarterTask {
    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    private final List<Location> locationList = new ArrayList<>();
    private final List<UHCTeam> uhcTeamList = new ArrayList<>();

    int b = 0;
    public void startUHC(){
        int a = uhc.getUhcData().getUhcPlayerList().size();
        int borderSize = UHC.getInstance().getUhcGame().getUhcConfig().getStartBorderSize();
        if (UHCTeamManager.getInstance().isActivated()){
            uhc.getUhcData().getUhcPlayerList().stream().filter(uhcPlayer -> uhcPlayer.getUhcTeam() == null).forEach(uhcPlayer -> uhcPlayer.joinTeam(UHCTeamManager.getInstance().getRandomFreeTeam()));
            UHCTeamManager.getInstance().getUhcTeams().forEach(uhcTeam -> {
                if (uhcTeam.isAlive()){
                    uhcTeamList.add(uhcTeam);
                }
            });
            UHCTeamManager.getInstance().setUhcTeams(uhcTeamList);
            List<UHCTeam> uhcTeams = new ArrayList<>(UHCTeamManager.getInstance().getUhcTeams());
            (new BukkitRunnable() {
                @Override
                public void run() {
                    if (MinecraftServer.getServer().recentTps[0] <= 17.5D){
                        return;
                    }
                    UHCTeam uhcTeam = uhcTeams.get(0);
                    Location loc = new Location(WorldManager.getGameWorld(), new Random().nextInt((borderSize-20)*2)-(borderSize-20), 200, new Random().nextInt((borderSize-20)*2)-(borderSize-20));
                    uhcTeam.setStartLoc(loc);
                    locationList.add(loc);
                    Cuboid cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 200, loc.getBlockZ()-5, loc.getBlockX()+5, 200, loc.getBlockZ()+5);
                    cuboid.forEach(block -> {
                        block.setType(new ItemCreator(Material.STAINED_GLASS).get().getType());
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 201, loc.getBlockZ()+5, loc.getBlockX()-5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()+5, 201, loc.getBlockZ()+5, loc.getBlockX()+5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 201, loc.getBlockZ()+5, loc.getBlockX()+5, 203, loc.getBlockZ()+5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()+5, 201, loc.getBlockZ()-5, loc.getBlockX()-5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    uhcTeam.getAliveUhcPlayers().stream().filter(uhcPlayer -> uhcPlayer.getBukkitPlayer() != null).forEach(uhcPlayer -> {
                        uhc.getUhcData().getWhiteListPlayer().add(uhcPlayer.getUuid());
                        uhcPlayer.getBukkitPlayer().teleport(loc.add(0, 1, 0));
                        uhcPlayer.getBukkitPlayer().setLevel(0);
                        uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
                        uhcPlayer.setEditing(false);
                        b++;
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Téléportation de §6" + uhcPlayer.getName() + " §7(" + b + "/" + a + ")");
                        Bukkit.broadcastMessage("");
                        uhcPlayer.getBukkitPlayer().getInventory().clear();
                    });
                    uhcTeams.remove(uhcTeam);
                    if (b == a) {
                        preStart();
                        this.cancel();
                    }
                }
            }).runTaskTimer(UHC.getInstance(), 5L, 15L);
        }else{

            List<UHCPlayer> uhcPlayers = new ArrayList<>(uhc.getUhcData().getUhcPlayerList());
            (new BukkitRunnable() {
                @Override
                public void run() {
                    if (MinecraftServer.getServer().recentTps[0] <= 17.5D){
                        return;
                    }
                    UHCPlayer uhcPlayer = uhcPlayers.get(0);
                    Location loc = new Location(WorldManager.getGameWorld(), new Random().nextInt((borderSize-20)*2)-(borderSize-20), 200, new Random().nextInt((borderSize-20)*2)-(borderSize-20));
                    locationList.add(loc);
                    Cuboid cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 200, loc.getBlockZ()-5, loc.getBlockX()+5, 200, loc.getBlockZ()+5);
                    cuboid.forEach(block -> {
                        int r = new Random().nextInt(14);
                        block.setType(new ItemCreator(Material.STAINED_GLASS, 1, (short) r).get().getType());
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 201, loc.getBlockZ()+5, loc.getBlockX()-5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()+5, 201, loc.getBlockZ()+5, loc.getBlockX()+5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()-5, 201, loc.getBlockZ()+5, loc.getBlockX()+5, 203, loc.getBlockZ()+5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    cuboid = new Cuboid(WorldManager.getGameWorld(), loc.getBlockX()+5, 201, loc.getBlockZ()-5, loc.getBlockX()-5, 203, loc.getBlockZ()-5);
                    cuboid.forEach(block -> {
                        block.setType(Material.BARRIER);
                    });
                    b++;
                    if (uhcPlayer.getBukkitPlayer() != null){
                        uhc.getUhcData().getWhiteListPlayer().add(uhcPlayer.getUuid());
                        uhcPlayer.getBukkitPlayer().teleport(loc.add(0, 1, 0));
                        uhcPlayer.getBukkitPlayer().setLevel(0);
                        uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
                        uhcPlayer.setEditing(false);
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Téléportation de §6" + uhcPlayer.getName() + " §7(" + b + "/" + a + ")");
                        Bukkit.broadcastMessage("");
                        uhcPlayer.getBukkitPlayer().getInventory().clear();
                    }
                    uhcPlayers.remove(uhcPlayer);
                    if (a == b) {
                        preStart();
                        this.cancel();
                    }
                };
            }).runTaskTimer(UHC.getInstance(), 5L, 15L);
        }

    }

    private void preStart(){
        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Stabilisation des §aTPS §7en cours...");
        (new BukkitRunnable() {
            @Override
            public void run() {
                if (MinecraftServer.getServer().recentTps[0] >= 17.5D){
                    start();
                    this.cancel();
                }
            }
        }).runTaskTimer(UHC.getInstance(), 10L, 10L);
    }

    int t = 10;
    private void start(){
        (new BukkitRunnable() {
            @Override
            public void run() {
                if (t == 0){
                    locationList.forEach(location -> {
                        Cuboid cuboid = new Cuboid(location.getWorld(), location.getBlockX()-5, 200, location.getBlockZ()-5, location.getBlockX()+5, 204, location.getBlockZ()+5);
                        cuboid.forEach(block -> {
                            block.setType(Material.AIR);
                        });
                    });
                    Cuboid cuboid = new Cuboid(WorldManager.getGameWorld(), -20, 200, 20, 20, 204, -20);
                    cuboid.forEach(block -> {
                        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                            block.setType(Material.AIR);
                        },2L);
                    });
                    SoundUtils.playSoundToAll(Sound.ITEM_BREAK);
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aDébut de la partie !");
                    uhc.getUhcTimer().runTaskTimer(UHC.getInstance(), 0L, 20L);
                    uhc.getUhcData().getSpecList().forEach(uhcPlayer -> {
                        if (uhcPlayer.getBukkitPlayer() != null){
                            uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                        }
                    });

                    uhc.getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
                        uhcPlayer.getBukkitPlayer().setLevel(0);
                        uhcPlayer.setDamageable(false);
                        if (uhc.getUhcConfig().getStarterStuffConfig().getStartInv().length == 0){
                            uhcPlayer.getBukkitPlayer().getInventory().setItem(0, new ItemCreator(Material.COOKED_BEEF, 32).get());
                            uhcPlayer.getBukkitPlayer().getInventory().setItem(1, new ItemCreator(Material.BOOK, 1).get());
                        }else {
                            uhcPlayer.getBukkitPlayer().getInventory().setContents(uhc.getUhcConfig().getStarterStuffConfig().getStartInv());
                            uhcPlayer.getBukkitPlayer().getInventory().setHelmet(uhc.getUhcConfig().getStarterStuffConfig().getHead());
                            uhcPlayer.getBukkitPlayer().getInventory().setChestplate(uhc.getUhcConfig().getStarterStuffConfig().getBody());
                            uhcPlayer.getBukkitPlayer().getInventory().setLeggings(uhc.getUhcConfig().getStarterStuffConfig().getLeggins());
                            uhcPlayer.getBukkitPlayer().getInventory().setBoots(uhc.getUhcConfig().getStarterStuffConfig().getBoots());
                        }
                    });
                    Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Les §cdegâts (PvE) §7sont désormais §cactifs §7!");
                        uhc.getUhcData().getUhcPlayerList().forEach(uhcPlayer -> {
                            uhcPlayer.setDamageable(true);
                            SoundUtils.playSoundToAll(Sound.ORB_PICKUP);
                        });
                    }, TimeUtils.seconds(uhc.getUhcConfig().getGodStart()));
                    WorldManager.getGameWorld().getWorldBorder().setSize(uhc.getUhcConfig().getStartBorderSize()*2);
                    WorldManager.getNetherWorld().getWorldBorder().setSize(uhc.getUhcConfig().getStartBorderSize()*2);
                    uhc.getUhcData().setBorderRaduis(((int) WorldManager.getGameWorld().getWorldBorder().getSize())/2);
                    uhc.setGameState(GameState.PLAYING);
                    UHC.getInstance().getUhcManager().getGamemode().onStart();
                    UHC.getInstance().getUhcManager().getScenarioManager().getActivatedScenarios().forEach(UHCScenario::onStart);
                    Bukkit.getServer().getPluginManager().callEvent(new StartEvent());
                    new ArrowTask().runTaskTimer(UHC.getInstance(), 0L, 3L);
                    new NoSpecLagTask().runTaskTimer(UHC.getInstance(), 0L, 2L);
                    this.cancel();
                }
                if (t == 10 || t == 5 || t <= 3 && t != 0){
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Début de la partie dans §a" + t + " §7seconde" + (t != 1 ? "s" : "") + " !");
                    SoundUtils.playSoundToAll(Sound.NOTE_PLING);
                }

                t--;
            }
        }).runTaskTimer(UHC.getInstance(), 40L, 20L);
    }
}
