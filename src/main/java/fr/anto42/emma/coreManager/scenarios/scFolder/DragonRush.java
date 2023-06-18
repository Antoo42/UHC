package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.utils.*;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.materials.UniversalMaterial;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.versionsUtils.VersionUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DragonRush extends UHCScenario {
    private final List<Block> portalBlocks;
    public DragonRush(ScenarioManager scenarioManager, int page) {
        super("DragonRush", new ItemCreator(Material.DRAGON_EGG).get(), scenarioManager, page);
        portalBlocks = new ArrayList<>();
        setScenarioType(ScenarioType.PVE);
    }





    @Override
    public void onStart(){
        if (!getUhcGame().getUhcConfig().isEnd()){
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + "§c Veuillez à activer l'end pour pouvoir jouer dans ce mode de jeu !");
            getScenarioManager().disableScenario(this);
            return;
        }

        Location portalLoc = getPortalLocation();

        portalBlocks.add(portalLoc.clone().add(1, 0, 2).getBlock());
        portalBlocks.add(portalLoc.clone().add(0, 0, 2).getBlock());
        portalBlocks.add(portalLoc.clone().add(-1, 0, 2).getBlock());

        portalBlocks.add(portalLoc.clone().add(-2, 0, 1).getBlock());
        portalBlocks.add(portalLoc.clone().add(-2, 0, 0).getBlock());
        portalBlocks.add(portalLoc.clone().add(-2, 0, -1).getBlock());

        portalBlocks.add(portalLoc.clone().add(1, 0, -2).getBlock());
        portalBlocks.add(portalLoc.clone().add(0, 0, -2).getBlock());
        portalBlocks.add(portalLoc.clone().add(-1, 0, -2).getBlock());

        portalBlocks.add(portalLoc.clone().add(2, 0, 1).getBlock());
        portalBlocks.add(portalLoc.clone().add(2, 0, 0).getBlock());
        portalBlocks.add(portalLoc.clone().add(2, 0, -1).getBlock());

        int i = 0;
        BlockFace blockFace = BlockFace.NORTH;
        for (Block block : portalBlocks){
            block.setType(UniversalMaterial.END_PORTAL_FRAME.getType());
            VersionUtils.getVersionUtils().setEndPortalFrameOrientation(block, blockFace);
            if (new Random().nextInt(2) == 1){
                VersionUtils.getVersionUtils().setEye(block, true);
            }
            i++;
            if (i == 3){
                i = 0;
                if (blockFace == BlockFace.NORTH){
                    blockFace = BlockFace.EAST;
                }else if (blockFace == BlockFace.EAST){
                    blockFace = BlockFace.SOUTH;
                }else if (blockFace == BlockFace.SOUTH){
                    blockFace = BlockFace.WEST;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        for (Block block : portalBlocks){
            block.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if (!isActivated())
            return;
        if (e.getEntityType() != EntityType.ENDER_DRAGON){
            return;
        }

        if (e.getEntity().getKiller() == null) {
            return;
        }

        Player killer = e.getEntity().getKiller();
        UHCPlayer uhcKiller = UHC.getUHCPlayer(killer);

        List<UHCPlayer> spectators = new ArrayList<>();

        for (UHCPlayer playingPlayer : getUhcGame().getUhcData().getUhcPlayerList()){

            if (!playingPlayer.isInTeamWith(uhcKiller)){
                spectators.add(playingPlayer);
            }
        }

        for (UHCPlayer spectator : spectators){
            spectator.setPlayerState(UHCPlayerStates.DEAD);
            Player all = spectator.getBukkitPlayer();
            all.setGameMode(GameMode.SPECTATOR);
            all.teleport(killer);
        }
        if (uhcKiller.getUhcTeam() == null){
            getUhcGame().setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications au joueur " + uhcKiller.getName() + "§a pour sa victoire en §6" + UHC.getInstance().getUhcManager().getGamemode().getName() + " §3(DragonRush) §aavec §b" + uhcKiller.getKills() + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }
            PlayersUtils.finishToSpawn();

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));
        }
        else{
            getUhcGame().setGameState(GameState.FINISH);
            SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
            UHCTeam uhcTeam = uhcKiller.getUhcTeam();
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aFélications à l'équipe " + uhcTeam.getDisplayName() + "§a pour sa victoire en " + UHC.getInstance().getUhcManager().getGamemode().getName() + " §3(DragonRush)§a avec §b" + uhcTeam.getKillsTeam() + "§a !");
            Bukkit.broadcastMessage("§7");
            for(Player player : Bukkit.getOnlinePlayers()){
                UHCPlayer uhcPlayer  = UHC.getUHCPlayer(player);
                player.sendMessage("§8┃ §fRécapitulatif de votre partie:");
                player.sendMessage("§7");
                player.sendMessage("§8§l» §3Kills: §e" + uhcPlayer.getKills());
                player.sendMessage("§8§l» §3Morts: §e" + uhcPlayer.getDeath());
                if (uhcPlayer.getRole() != null)
                    player.sendMessage("§8§l» §3Rôle: §e" + uhcPlayer.getRole().getName());
            }
            PlayersUtils.finishToSpawn();

            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cArrêt automatique du serveur dans 5 minutes !");
            Bukkit.broadcastMessage("§7");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(),  () -> {
                
                Bukkit.shutdown();
            }, TimeUtils.minutes(5));


    }
    }

    private Location getPortalLocation(){
        World world = WorldManager.getGameWorld();
        int portalY = 0;

        for (int x = -4; x < 4; x++) {
            for (int z = -4; z < 4; z++) {
                int y = getHighestBlock(world, x, z);
                if (y > portalY){
                    portalY = y;
                }
            }
        }

        return new Location(world, 0, portalY+1, 0);
    }

    private int getHighestBlock(World world, int x, int z){
        int y = 150;
        while (world.getBlockAt(x, y, z).getType() == Material.AIR){
            y--;
        }

        return y;
    }

}
