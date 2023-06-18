package fr.anto42.emma.coreManager.worldManager;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.chat.Title;
import fr.anto42.emma.utils.players.PlayersUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.bukkit.Bukkit.getScheduler;

public class Generator {
    private final World world;

    private final int size;

    private BukkitTask task;

    private int nChunk;

    private int last;

    private long startTime;

    public Generator(World world) {
        this.size = UHC.getInstance().getUhcGame().getUhcConfig().getStartBorderSize() + 200;
        this.world = world;
        world.setGameRuleValue("randomTickSpeed", "0");
        load();
    }

    private SurgarCanePopulator surgarCanePopulator = new SurgarCanePopulator(200);

    private void load() {
        if (WorldManager.getInGeneration() != null) {
            return;
        }
        System.out.println("Starting pregeneration");
        PlayersUtils.broadcastMessage("§7Lancement de la génération du monde §a" + world.getName() + "§7. §cLe serveur peut subir des ralentissements ! Par précautions, ne lancez pas la partie durant la prégénération.");
        WorldManager.setInGeneration(world);
        Random random = new Random();
        (new Thread(() -> {
            this.startTime = System.currentTimeMillis();
            this.task = getScheduler().runTaskTimer(UHC.getInstance(), new Runnable() {
                private int todo = Generator.this.size * 2 * Generator.this.size * 2 / 256;

                private int x = -Generator.this.size;

                private int z = -Generator.this.size;

                public void run() {
                    if (MinecraftServer.getServer().recentTps[0] <= 15D){
                        return;
                    }
                    for (int i = 0; i < 50; i++) {
                        Chunk chunk = Generator.this.world.getChunkAt(Generator.this.world.getBlockAt(this.x, 64, this.z));
                        chunk.load(true);
                        chunk.load(false);
                        if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                            surgarCanePopulator.populate(world, random, chunk);
                        }
                        int percentage = Generator.this.nChunk * 100 / this.todo;
                        if (percentage > Generator.this.last) {
                            Generator.this.last = percentage;
                            Generator.this.sendMessage(percentage, todo);
                        }
                        this.z += 16;
                        if (this.z >= Generator.this.size) {
                            this.z = -Generator.this.size;
                            this.x += 16;
                        }
                        if (this.x >= Generator.this.size) {
                            Generator.this.task.cancel();
                            int calculedTime = Math.round((float)((System.currentTimeMillis() - Generator.this.startTime) / 1000L));
                            System.out.println("Finished the preload of world " +  world.getName() +" after " + calculedTime + "s");
                            WorldManager.setInGeneration(null);
                            if (world.getEnvironment().equals(World.Environment.NORMAL))
                                UHC.getInstance().getUhcGame().getUhcData().setPreloadFinished(true);
                            else if (world.getEnvironment().equals(World.Environment.NETHER))
                                UHC.getInstance().getUhcGame().getUhcData().setNetherPreload(true);
                            else if (world.getEnvironment().equals(World.Environment.THE_END)) {
                                UHC.getInstance().getUhcGame().getUhcData().setEndPreload(true);
                            }
                            if (WorldManager.getToGenerate().size() != 0) {
                                World world = WorldManager.getToGenerate().get(0);
                                WorldManager.getToGenerate().remove(world);
                                getScheduler().runTaskLater(UHC.getInstance(), () -> {
                                    new Generator(world);
                                }, TimeUtils.seconds(3));
                            }
                            return;
                        }
                        Generator.this.nChunk++;
                    }
                }
            }, 1L, 1L);
        })).start();
    }
    private void sendMessage(int percentage, int todo) {
        for (Player player : Bukkit.getOnlinePlayers())
            Title.sendActionBar(player, "§8§l» §3Prégéneration du monde §a" + world.getName() + "§3: §e" + percentage +"§3%" + " §7(§e" + nChunk + "§7/" + "§a" + todo + " §7chunks)");
    }
}