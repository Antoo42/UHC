package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BigCrack extends UHCScenario {
    public BigCrack(ScenarioManager scenarioManager, int page) {
        super("BigCrack", new ItemStack(Material.BEDROCK), scenarioManager, page);
        setScenarioType(ScenarioType.WORLD);
        setDesc("§8┃ §fCréez un gouffre au milieu de la carte de jeu");
    }

    private static final int CHUNK_HEIGHT_LIMIT = 128;
    private static final int BLOCKS_PER_CHUNK = 16;

    private boolean generation = false;

    @EventHandler
    public void onFlow(BlockFromToEvent event) {
        if (generation) {
            event.setCancelled(true);
        }
    }


    @Override
    public void onEnable() {
        super.onEnable();

        int width = 25;
        int length = 1200;
        int speed = 2;

        generate(WorldManager.getGameWorld(), length, width, speed);
    }

    public void generate(final World world, final int length, final int width, int speed) {
        generation = true;

        int xChunk;
        if (length % BLOCKS_PER_CHUNK == 0) {
            xChunk = length / BLOCKS_PER_CHUNK;
        } else {
            xChunk = (length / BLOCKS_PER_CHUNK) + 1;
        }

        int xMaxChunk = xChunk;
        xChunk = xChunk * -1;

        int zChunk;
        if (width % BLOCKS_PER_CHUNK == 0) {
            zChunk = (width) / BLOCKS_PER_CHUNK;
        } else {
            zChunk = ((width) / BLOCKS_PER_CHUNK) + 1;
        }

        int zMaxChunk = zChunk;
        zChunk = zChunk * -1;

        int delayMultiplier = 0;
        for (int x = xChunk; x <= xMaxChunk; x++) {
            for (int z = zChunk; z <= zMaxChunk; z++) {
                final Chunk chunk = world.getChunkAt(x, z);
                new BukkitRunnable() {
                    public void run() {
                        populate(world, chunk, width, length);

                        for (Player online : Bukkit.getOnlinePlayers()) {
                            Title.sendActionBar(online, "§8§l» §3BigCrack §a" + "§3: §e" + "Chunk effacé en x= " + chunk.getX() + ", z= " + chunk.getZ());
                        }
                    }
                }.runTaskLater(UHC.getInstance(), delayMultiplier * speed);
                delayMultiplier++;
            }
        }

        new BukkitRunnable() {
            public void run() {
                generation = false;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    Title.sendActionBar(online, "§8§l» §3BigCrack §a" + "§3: §e" + "Remplacement terminé");
                }            }
        }.runTaskLater(UHC.getInstance(), delayMultiplier * speed);
    }

    public void populate(World world, Chunk chunk, int width, int length) {
        chunk.load();
        for (int x = 0; x < BLOCKS_PER_CHUNK; x++) {
            for (int z = 0; z < BLOCKS_PER_CHUNK; z++) {
                for (int y = CHUNK_HEIGHT_LIMIT - 1; y >= 0; y--) {
                    Block block = chunk.getBlock(x, y, z);
                    Location location = block.getLocation();

                    int xLocation = location.getBlockX();
                    int zLocation = location.getBlockZ();

                    if (zLocation >= (width * -1) && zLocation <= width && xLocation <= length && xLocation >= (length * -1)) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
