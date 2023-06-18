package fr.anto42.emma.coreManager.worldManager;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.Cuboid;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldManager {
    private static World gameWorld;
    private static World netherWorld;
    private static World endWorld;
    private static Location centerLoc;

    public static int random;

    private static boolean clean = false;
    private static boolean roofed = false;

    public static List<World> worldList = new ArrayList<>();

    private static World inGeneration = null;

    private static List<World> toGenerate = new ArrayList<>();


    public void init() {
        if (UHC.getInstance().getConfig().getBoolean("customSpawn")){
            createCustomSpawn();
        }
        createGameWorld();

    }

    private Location spawnLocation;
    private final FileConfiguration fileConfiguration = UHC.getInstance().getConfig();
    public void createCustomSpawn(){
        World world = Bukkit.getWorld((String) fileConfiguration.get("worldName"));
        Bukkit.getLogger().info("UHC: Custom spawn set to the world: " + world.getName());
        double x = fileConfiguration.getDouble("x");
        double y = fileConfiguration.getDouble("y");
        double z = fileConfiguration.getDouble("z");
        int yaw = fileConfiguration.getInt("yaw");
        int pitch = fileConfiguration.getInt("pitch");
        setSpawnLocation(new Location(world, x, y, z, yaw, pitch));
    }

    public void createGameWorld(){
        random = new Random().nextInt(9999);
        String worldName = "world-" + random;
        World world = new WorldCreator(worldName).createWorld();
        gameWorld = world;
        worldList.add(world);
        netherWorld = new WorldCreator("nether-" + random).environment(World.Environment.NETHER).createWorld();
        worldList.add(netherWorld);
        endWorld = new WorldCreator("end-" + random).environment(World.Environment.THE_END).createWorld();
        worldList.add(endWorld);
        setInGeneration(null);
        getToGenerate().clear();
        setCenterLoc(new Location(world, 0.5 , 80, 0.5, 0F, 0F));
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        netherWorld.setGameRuleValue("naturalRegeneration", "false");
        endWorld.setGameRuleValue("naturalRegeneration", "false");
        netherWorld.setGameRuleValue("doFireTick", "false");
        endWorld.setGameRuleValue("doFireTick", "false");
        gameWorld.setTime(6000);
        world.getWorldBorder().setCenter(0, 0);
        if (!UHC.getInstance().getConfig().getBoolean("customSpawn")) {
            setSpawnLocation(new Location(gameWorld, 0.5, 201, 0.5, 0F, 0F));
            Cuboid cuboid = new Cuboid(gameWorld, -20, 200, 20, 20, 200, -20);
            cuboid.forEach(block -> {
                block.setType(Material.BARRIER);
            });
            cuboid = new Cuboid(gameWorld, -20, 201, 20, 20, 203, 20);
            cuboid.forEach(block -> {
                block.setType(Material.STAINED_GLASS_PANE);
            });
            cuboid = new Cuboid(gameWorld, 20, 201, -20, 20, 203, 20);
            cuboid.forEach(block -> {
                block.setType(Material.STAINED_GLASS_PANE);
            });
            cuboid = new Cuboid(gameWorld, 20, 201, -20, -20, 203, -20);
            cuboid.forEach(block -> {
                block.setType(Material.STAINED_GLASS_PANE);
            });
            cuboid = new Cuboid(gameWorld, -20, 201, -20, -20, 203, 20);
            cuboid.forEach(block -> {
                block.setType(Material.STAINED_GLASS_PANE);
            });
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(this.getSpawnLocation());
        }
    }

    public static World getGameWorld() {
        return gameWorld;
    }

    public static void setGameWorld(World gameWorld) {
        WorldManager.gameWorld = gameWorld;
    }

    public static World getNetherWorld() {
        return netherWorld;
    }

    public static void setNetherWorld(World netherWorld) {
        WorldManager.netherWorld = netherWorld;
    }

    public static World getEndWorld() {
        return endWorld;
    }

    public static void setEndWorld(World endWorld) {
        WorldManager.endWorld = endWorld;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location s) {
        this.spawnLocation = s;
    }

    public static Location getCenterLoc() {
        return centerLoc;
    }

    public static void setCenterLoc(Location centerLoc) {
        WorldManager.centerLoc = centerLoc;
    }

    public static boolean isClean() {
        return clean;
    }

    public static void setClean(boolean clean) {
        WorldManager.clean = clean;
    }

    public static boolean isRoofed() {
        return roofed;
    }

    public static void setRoofed(boolean roofed) {
        WorldManager.roofed = roofed;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public static World getInGeneration() {
        return inGeneration;
    }

    public static void setInGeneration(World inGeneration) {
        WorldManager.inGeneration = inGeneration;
    }

    public static List<World> getToGenerate() {
        return toGenerate;
    }

    public static void setToGenerate(List<World> toGenerate) {
        WorldManager.toGenerate = toGenerate;
    }
}
