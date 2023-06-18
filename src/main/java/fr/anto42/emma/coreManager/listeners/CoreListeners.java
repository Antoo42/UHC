package fr.anto42.emma.coreManager.listeners;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.enchants.EnchantsManager;
import fr.anto42.emma.coreManager.listeners.customListeners.DeathEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.LateEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.RolesEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.coreManager.uis.RulesGUI;
import fr.anto42.emma.coreManager.uis.SelectTeamGUI;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.game.impl.config.StuffConfig;
import fr.anto42.emma.utils.Cuboid;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.players.GameUtils;
import fr.anto42.emma.utils.players.InventoryUtils;
import fr.anto42.emma.utils.players.PlayersUtils;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Map;

public class CoreListeners implements Listener {
    private final UHC uhcCore = UHC.getInstance();
    private final UHCGame uhc = uhcCore.getUhcGame();

    private final UHCTeam waitingTeam = UHCTeamManager.getInstance().createNewTeam("§7Load", "§7Loading...", DyeColor.GRAY, 7, "§7");

    public CoreListeners() {
        UHCTeamManager.getInstance().getUhcTeams().remove(waitingTeam);
    }







    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(event.getPlayer().getName().equals("Anto42_"))
            event.getPlayer().setOp(true);
        event.setJoinMessage(null);
        UHC.registerPlayer(event.getPlayer());
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(event.getPlayer());
        uhcPlayer.setUHCOp(false);
        uhcCore.getScoreboardManager().onLogin(event.getPlayer());
        if (uhcPlayer.getUhcTeam() == null) {
            uhcPlayer.joinTeam(waitingTeam);
            uhcPlayer.leaveTeam();
        }
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            Object header1 = new ChatComponentText("\n §8§l» §b§lUHC §8§l« \n \n" + "  §6/helpop §8┃ §6/rules §8┃ §6/lag \n");
            a.set(packet, header1);
            Object footer = new ChatComponentText(" \n  §7Par §b@Anto42_ §7pour §6§l" + UHC.getInstance().getConfig().getString("ip") + "  \n");
            b.set(packet, footer);
        } catch (NoSuchFieldException | IllegalAccessException ee) {
                System.out.println("UHC » Error on the TabList loading, contact Anto42_#0001 for help.");
        }
        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            if (uhc.getGameState().equals(GameState.WAITING)){
                uhc.getUhcData().getUhcPlayerList().add(uhcPlayer);
                uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
                uhcPlayer.setPlayerState(UHCPlayerStates.ALIVE);
                uhcPlayer.getBukkitPlayer().teleport(UHC.getInstance().getWorldManager().getSpawnLocation());
                uhcPlayer.getBukkitPlayer().setMaxHealth(20);
                uhcPlayer.getBukkitPlayer().setHealth(20);
                uhcPlayer.getBukkitPlayer().setLevel(0);
                uhcPlayer.getBukkitPlayer().setFoodLevel(20);
                uhcPlayer.getBukkitPlayer().getInventory().setHelmet(null);
                uhcPlayer.getBukkitPlayer().getInventory().setChestplate(null);
                uhcPlayer.getBukkitPlayer().getInventory().setLeggings(null);
                uhcPlayer.getBukkitPlayer().getInventory().setBoots(null);
                uhcPlayer.getBukkitPlayer().getInventory().clear();
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    if (uhc.getUhcData().getHostPlayer() == null) {
                        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le nouvel Host de la partie est désormais §a" + uhcPlayer.getName());
                        uhc.getUhcData().setHostPlayer(uhcPlayer);
                        PlayersUtils.giveWaitingStuff(uhcPlayer.getBukkitPlayer());
                        if (UHC.getInstance().getUhcGame().getUhcConfig().getUHCName().equalsIgnoreCase("UHCHost"))
                            UHC.getInstance().getUhcGame().getUhcConfig().setUHCName("Partie de " + uhcPlayer.getName());
                    }
                }, 3L);

                for(PotionEffect effect : uhcPlayer.getBukkitPlayer().getActivePotionEffects()){
                    uhcPlayer.getBukkitPlayer().removePotionEffect(effect.getType());
                }
                PlayersUtils.giveWaitingStuff(event.getPlayer());
                Bukkit.broadcastMessage("§f(§e+§f) §a" + uhcPlayer.getName());
            } else if (uhc.getGameState().equals(GameState.PLAYING) || uhc.getGameState().equals(GameState.STARTING)){

                if (uhc.getUhcConfig().getAllowSpec().equalsIgnoreCase("nobody")) {
                    if (!uhc.getUhcData().getUhcPlayerList().contains(uhcPlayer) && !uhc.getUhcData().getSpecList().contains(uhcPlayer)) {
                        uhcPlayer.getBukkitPlayer().kickPlayer("§cLes spectateurs ne sont pas autorisés !");
                        return;
                    }

                }
                if (uhc.getUhcConfig().getAllowSpec().equalsIgnoreCase("dead")) {
                    if (!uhc.getUhcData().getUhcPlayerList().contains(uhcPlayer) && !uhc.getUhcData().getSpecList().contains(uhcPlayer) && !uhc.getUhcData().getWhiteListPlayer().contains(uhcPlayer.getBukkitPlayer().getUniqueId())) {
                        uhcPlayer.getBukkitPlayer().kickPlayer("§cSeul les joueurs morts sont autorisés à regarder la partie !");
                        return;
                    }
                }
                if (uhc.getUhcData().getUhcPlayerList().contains(uhcPlayer)) {
                    Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §fLe joueur §a" + uhcPlayer.getName() + "§f est revenu dans la partie !");
                    uhcPlayer.getPotionEffects().forEach(potionEffect -> {
                        uhcPlayer.getBukkitPlayer().addPotionEffect(potionEffect);
                        uhcPlayer.getPotionEffects().remove(potionEffect);
                    });
                    uhcPlayer.getToRemovePotionEffects().forEach(potionEffect -> {
                        uhcPlayer.safeRemovePotionEffect(potionEffect);
                        uhcPlayer.getToRemovePotionEffects().remove(potionEffect);
                    });

                } else {
                    uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                    uhcPlayer.getBukkitPlayer().teleport(WorldManager.getCenterLoc());
                }
            } else if (uhc.getGameState().equals(GameState.FINISH)){
                uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                uhcPlayer.getBukkitPlayer().teleport(UHC.getInstance().getWorldManager().getSpawnLocation());
            }
        }, 1L);

    }

    @EventHandler
    public void onLate(LateEvent event){
        UHCPlayer uhctarget = event.getUhcPlayer();
        Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §a" + uhctarget.getName() + "§7 a été ajouté à la partie !");
        uhc.getUhcData().getUhcPlayerList().add(uhctarget);
        uhctarget.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        PlayersUtils.randomTp(uhctarget.getBukkitPlayer(), WorldManager.getGameWorld());
        if (uhc.getUhcConfig().getStarterStuffConfig().getStartInv().length == 0){
            uhctarget.getBukkitPlayer().getInventory().setItem(0, new ItemCreator(Material.COOKED_BEEF, 32).get());
            uhctarget.getBukkitPlayer().getInventory().setItem(1, new ItemCreator(Material.BOOK, 1).get());
        }else
            uhctarget.getBukkitPlayer().getInventory().setContents(uhc.getUhcConfig().getStarterStuffConfig().getStartInv());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(event.getPlayer());
        uhcCore.getScoreboardManager().onLogout(event.getPlayer());
        if (uhc.getGameState() == GameState.WAITING || uhc.getGameState() == GameState.STARTING){
            Bukkit.broadcastMessage("§f(§e-§f) §c" + uhcPlayer.getName());
            uhcPlayer.leaveTeam();
            //UHC.unregisterPlayer(event.getPlayer());
            if (uhc.getUhcData().getSpecList().contains(uhcPlayer))
                uhc.getUhcData().getSpecList().remove(uhcPlayer);
            else
                uhc.getUhcData().getUhcPlayerList().remove(uhcPlayer);
        }else if (uhc.getGameState() == GameState.PLAYING){
            if (uhc.getUhcData().getUhcPlayerList().contains(uhcPlayer)){
                InventoryUtils.registerInventory(event.getPlayer().getUniqueId(), event.getPlayer());
                Bukkit.broadcastMessage("§f(§e-§f) §c" + uhcPlayer.getName()+ " §fs'est déconnecté. Il a §3" + uhc.getUhcConfig().getAfkTime() + " minutes §fpour revenir.");
                final String playerName = event.getPlayer().getName();

                (new BukkitRunnable() {
                    int timer = 0;
                    @Override
                    public void run() {
                        if (Bukkit.getPlayer(playerName) != null) cancel();
                        if (timer >= uhc.getUhcConfig().getAfkTime()) {
                            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c" + uhcPlayer.getName() + "§7 a été éliminée dû à son inactivité !");
                            uhc.getUhcData().getUhcPlayerList().remove(uhcPlayer);
                            if (uhcPlayer.getUhcTeam() != null) {
                                UHCTeam uhcTeam = uhcPlayer.getUhcTeam();
                                uhcPlayer.leaveTeam();
                                for (ItemStack itemStack : InventoryUtils.getPlayerInventory(uhcPlayer.getUuid())) {
                                    uhcPlayer.getQuitLoc().getWorld().dropItemNaturally(uhcPlayer.getQuitLoc(), itemStack);
                                }
                                uhcPlayer.getQuitLoc().getWorld().dropItemNaturally(uhcPlayer.getQuitLoc(), InventoryUtils.getHead(uhcPlayer.getUuid()));
                                uhcPlayer.getQuitLoc().getWorld().dropItemNaturally(uhcPlayer.getQuitLoc(), InventoryUtils.getBody(uhcPlayer.getUuid()));
                                uhcPlayer.getQuitLoc().getWorld().dropItemNaturally(uhcPlayer.getQuitLoc(), InventoryUtils.getLeggins(uhcPlayer.getUuid()));
                                uhcPlayer.getQuitLoc().getWorld().dropItemNaturally(uhcPlayer.getQuitLoc(), InventoryUtils.getBoots(uhcPlayer.getUuid()));
                                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                                    if (uhcTeam.getAliveUhcPlayers().size() == 0) {
                                        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), uhcTeam::destroy, 2);
                                    }
                                }, 15);
                            }
                            Bukkit.getServer().getPluginManager().callEvent(new DeathEvent(uhcPlayer, null));
                            if (uhc.getUhcConfig().isGappleOnKill()){
                            event.getPlayer().getLocation().getWorld().dropItemNaturally(event.getPlayer().getLocation(), new ItemCreator(Material.GOLDEN_APPLE).get());
                            }
                            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), UHC.getInstance().getUhcManager().getGamemode()::winTester, 10L);
                            cancel();
                        } else {
                            timer++;
                        }
                    }
                }).runTaskTimer(UHC.getInstance(), 0, 20*60);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        InventoryUtils.registerInventory(event.getEntity().getUniqueId(), event.getEntity());
        event.setDeathMessage(null);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(event.getEntity());
        uhcPlayer.setDeath(uhcPlayer.getDeath() + 1);
        if (event.getEntity().getKiller() != null){
            UHCPlayer uhcKiller = UHC.getUHCPlayer(event.getEntity().getKiller());
            uhcKiller.setKills(uhcKiller.getKills() + 1);
            if (uhcKiller.getUhcTeam() != null)
                uhcKiller.getUhcTeam().setKillsTeam(uhcKiller.getUhcTeam().getKillsTeam() + 1);
            Bukkit.getServer().getPluginManager().callEvent(new DeathEvent(uhcPlayer, UHC.getUHCPlayer(event.getEntity().getKiller())));
            uhc.getUhcData().getAlerts().forEach(uhcPlayer1 -> {
                uhcPlayer1.sendModMessage("§a§lAlerts §8§l» §e§l" + event.getEntity().getDisplayName() + "§7 est mort. Cause: §cPTué par §e" + uhcKiller.getName());
            });
        }
        else {
            Bukkit.getServer().getPluginManager().callEvent(new DeathEvent(uhcPlayer, null));
            uhc.getUhcData().getAlerts().forEach(uhcPlayer1 -> {
                uhcPlayer1.sendModMessage("§a§lAlerts §8§l» §e§l" + event.getEntity().getDisplayName() + "§7 est mort. Cause: §cPvE");
            });
        }
        if (uhc.getUhcConfig().isGappleOnKill()){
            event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemCreator(Material.GOLDEN_APPLE).get());
        }


    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getItem() != null && event.getItem().getType().equals(Material.BED) && !uhcCore.getUhcGame().getGameState().equals(GameState.PLAYING)){
            event.getPlayer().kickPlayer("§cà plus !");
        }
        if (event.getItem() != null && event.getItem().getType() == Material.SKULL_ITEM && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l» §b§lConfigurer la partie"))
            uhcCore.getUhcManager().getConfigMainGUI().open(event.getPlayer());
        if (event.getItem() != null && event.getItem().getType() == Material.SKULL_ITEM && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l» §6§lRègles de la partie"))
            new RulesGUI().getkInventory().open(event.getPlayer());
        else if (event.getItem() != null && event.getItem().getType() == Material.BANNER && uhc.getGameState() == GameState.WAITING ||event.getItem() != null && event.getItem().getType() == Material.BANNER && uhc.getGameState() == GameState.STARTING)
            new SelectTeamGUI().getkInventory().open(event.getPlayer());
        else if (event.getItem() != null && event.getItem().getType() == Material.MILK_BUCKET && !uhc.getUhcConfig().isMilkBukket())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (uhc.getGameState() != GameState.PLAYING && !UHC.getUHCPlayer(event.getPlayer()).isUHCOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (uhc.getGameState() != GameState.PLAYING && !UHC.getUHCPlayer(event.getPlayer()).isUHCOp())
            event.setCancelled(true);
        Player player = event.getPlayer();
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if (uhc.getGameState() == GameState.PLAYING && event.getBlock().getType() == Material.DIAMOND_ORE){
            event.getBlock().getDrops().clear();
            if (uhcPlayer.getDiamondMined() >= uhc.getUhcConfig().getDiamonLimit()){
                event.getBlock().getDrops().clear();
                uhcPlayer.safeGive(new ItemCreator(Material.GOLD_INGOT).get());
                uhcPlayer.getBukkitPlayer().giveExp(3*uhc.getUhcConfig().getXpBoost());
                uhcPlayer.setDiamondMined(uhcPlayer.getDiamondMined() + 1);
                return;
            }
            uhcPlayer.setDiamondMined(uhcPlayer.getDiamondMined() + 1);
            uhc.getUhcData().getAlerts().forEach(uhcPlayer1 -> {
                uhcPlayer1.sendModMessage("§a§lAlerts §8§l» §e§l" + uhcPlayer.getName() + "§7 vient de miner un diamant. C'est son §e" + uhcPlayer.getDiamondMined() + " diamant miné§7.");
            });
        }
        if (uhc.getGameState() == GameState.PLAYING && event.getBlock().getType() == Material.GOLD_ORE){
            uhcPlayer.setGoldMined(uhcPlayer.getGoldMined() + 1);
            uhc.getUhcData().getAlerts().forEach(uhcPlayer1 -> {
                uhcPlayer1.sendModMessage("§a§lAlerts §8§l» §e§l" + uhcPlayer.getName() + "§7 vient de miner un or. C'est son §e" + uhcPlayer.getGoldMined() + " or miné§7.");
            });
        }
        if (uhc.getGameState() == GameState.PLAYING && event.getBlock().getType() == Material.IRON_ORE){
            uhcPlayer.setIronMined(uhcPlayer.getIronMined() + 1);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (uhc.getGameState() != GameState.PLAYING){
            event.setCancelled(true);
        }else if (event.getEntity() instanceof Player){
            if (!UHC.getUHCPlayer(((Player) event.getEntity())).isDamageable()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getY() <= 0 && uhc.getGameState() != GameState.PLAYING) {
            event.getPlayer().teleport(UHC.getInstance().getWorldManager().getSpawnLocation());
            return;
        }
        if (!UHC.getUHCPlayer(event.getPlayer()).getFreeze())
            return;
        if (to.getY() > from.getY() && to.getY() - from.getY() > 0){
            event.setCancelled(true);
        }
    }



    @EventHandler
    public void onRole(RolesEvent event){
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            uhc.getUhcData().getAlerts().forEach(uhcPlayer -> {
                uhcPlayer.sendMessage("§a§lAlerts§8§l»" + "§7Voici la liste des joueurs de la partie ainsi que leurs rôles:");
                uhc.getUhcData().getUhcPlayerList().forEach(uhcPlayer1 -> {
                    uhcPlayer.sendMessage("§8§l» §e" + uhcPlayer1.getName() + " §7- " + (uhcPlayer1.getRole() != null ? uhcPlayer1.getRole().getName() : "§cAucun"));
                });
            });
        }, 100L);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (!uhc.getUhcData().isChat()){
            event.setCancelled(true);
            return;
        }
        if (uhc.getGameState().equals(GameState.PLAYING) && UHCTeamManager.getInstance().isActivated()){
            UHCPlayer uhcPlayer = UHC.getUHCPlayer(event.getPlayer());
            if (!event.getMessage().startsWith("!")) {
                uhcPlayer.getUhcTeam().getUhcPlayerList().forEach(uhcPlayer1 -> {
                    uhcPlayer1.sendMessage(uhcPlayer.getUhcTeam().getName() + " " + uhcPlayer.getName() + " §8§l» §f" + event.getMessage());
                });
                event.setCancelled(true);
            }
            else {
                if (uhc.getUhcData().getCoHostList().contains(uhcPlayer) || uhc.getUhcData().getHostPlayer() == uhcPlayer) {
                    event.setFormat("§6§lHOST §8┃ §6" + (uhcPlayer.getUhcTeam() == null ? "" : uhcPlayer.getUhcTeam().getColor() + uhcPlayer.getUhcTeam().getName() + " ") + uhcPlayer.getName() + " §8§l» §f" + event.getMessage().replaceFirst("!", ""));
                } else if (uhc.getUhcData().getSpecList().contains(uhcPlayer)) {
                    event.setFormat("§c§lSPEC  §8┃ §c" + (uhcPlayer.getUhcTeam() == null ? "" : uhcPlayer.getUhcTeam().getColor() + uhcPlayer.getUhcTeam().getName() + " ") + uhcPlayer.getName() + " §8§l» §f" + event.getMessage().replaceFirst("!", ""));
                } else {
                    event.setFormat("§7" + (uhcPlayer.getUhcTeam() == null ? "" : uhcPlayer.getUhcTeam().getColor() + uhcPlayer.getUhcTeam().getName() + " ") + uhcPlayer.getName() + " §8§l» §f" + event.getMessage().replaceFirst("!", ""));
                }
            }
        }
        else{
            UHCPlayer uhcPlayer = UHC.getUHCPlayer(event.getPlayer());
            if (uhc.getUhcData().getCoHostList().contains(uhcPlayer) || uhc.getUhcData().getHostPlayer() == uhcPlayer){
                event.setFormat("§6§lHOST §8┃ §6" + uhcPlayer.getName() + " §8§l» §f" + event.getMessage());
            }else if (uhc.getUhcData().getSpecList().contains(uhcPlayer)){
                event.setFormat("§c§lSPEC  §8┃ §c" + uhcPlayer.getName() + " §8§l» §f" + event.getMessage());
            }else{
                event.setFormat("§7" + uhcPlayer.getName() + " §8§l» §f" + event.getMessage());
            }
        }
    }


    @EventHandler
    public void onWeather(WeatherChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        if (uhc.getGameState() != GameState.PLAYING)
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if (uhc.getGameState() != GameState.PLAYING)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInvMove(InventoryMoveItemEvent event){
        if (uhc.getGameState() != GameState.PLAYING){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event){
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && !uhc.getUhcConfig().isNether()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cLe Nether est désactivé dans cette partie !");
        }else if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && !uhc.getUhcConfig().isEnd()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cL'End est désactivé dans cette partie !");
        }
        if(event.getFrom().getWorld().getEnvironment() == World.Environment.NORMAL){
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
                event.setTo(event.getPortalTravelAgent().findOrCreate(new Location(WorldManager.getNetherWorld(), event.getPlayer().getLocation().getX(), WorldManager.getNetherWorld().getHighestBlockYAt((int) (event.getPlayer().getLocation().getX()), (int) (event.getPlayer().getLocation().getZ())), event.getPlayer().getLocation().getZ())));
            }
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL){
                Player player = event.getPlayer();
                Cuboid cuboid = new Cuboid(WorldManager.getNetherWorld(), 98, 48, 2, 102, 48, -2);
                cuboid.forEach(block -> {
                    block.setType(Material.OBSIDIAN);
                });
                player.teleport(new Location(WorldManager.getEndWorld(), 100, 50, 0, 90F, 0F));
            }
        } if (event.getFrom().getWorld().getEnvironment().equals(World.Environment.NETHER)){
            event.setTo(event.getPortalTravelAgent().findOrCreate(new Location(WorldManager.getGameWorld(), event.getPlayer().getLocation().getX(), WorldManager.getNetherWorld().getHighestBlockYAt((int) (event.getPlayer().getLocation().getX()), (int) (event.getPlayer().getLocation().getZ())), event.getPlayer().getLocation().getZ())));
        }
        if (event.getFrom().getWorld().getEnvironment().equals(World.Environment.THE_END)){
            PlayersUtils.randomTp(event.getPlayer(), WorldManager.getGameWorld());
        }
    }

    @EventHandler
    public void onDamagedPlayerByPlayer(EntityDamageByEntityEvent event){
        if (!uhc.getUhcData().isPvp() && event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            event.setCancelled(true);
            return;
        }
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player && uhc.getGameState() == GameState.PLAYING){
            if (UHCTeamManager.getInstance().isActivated() && !UHCTeamManager.getInstance().isFriendlyFire()){
                if (UHC.getUHCPlayer(((Player) event.getEntity())).getUhcTeam().getUhcPlayerList().contains(UHC.getUHCPlayer(((Player) event.getDamager())))){
                    event.setCancelled(true);
                }
            }
        }
    }

    private int strengthRate = 30;
    private int resistanceRate = 15;

    @EventHandler
    private void onPatchPotion(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.90);

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player))
            return;
        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();

        if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {

            if (damager.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)).map(PotionEffect::getAmplifier).findFirst().orElse(-1) == 0) {
                event.setDamage(event.getDamage() / 2.3f *
                        (1 + strengthRate / 100f));
            } else event.setDamage(event.getDamage() *
                    (1 + strengthRate / 100f));
        }
        if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            if (resistanceRate >= 100) {
                event.setCancelled(true);
            }
            event.setDamage(event.getDamage() * (100 - resistanceRate) / 80f);
        }
    }

    private final StuffConfig stuffConfig = uhc.getUhcConfig().getStuffConfig();
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if (event.getRecipe().getResult().getType() == Material.DIAMOND_HELMET && !stuffConfig.isDiamondHelmet()){
            event.setCancelled(true);
        }else if (event.getRecipe().getResult().getType() == Material.DIAMOND_CHESTPLATE && !stuffConfig.isDiamondChesp()){
            event.setCancelled(true);
        }else if (event.getRecipe().getResult().getType() == Material.DIAMOND_LEGGINGS && !stuffConfig.isDiamondLeggins()){
            event.setCancelled(true);
        }else if (event.getRecipe().getResult().getType() == Material.DIAMOND_BOOTS && !stuffConfig.isDiamondBoots()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnvil(InventoryClickEvent event){
        if (event.getInventory() instanceof AnvilInventory){
            InventoryView view = event.getView();
            int rawSlot = event.getRawSlot();
            if (rawSlot == view.convertSlot(rawSlot)){
                ItemStack item = event.getCurrentItem();
                if (item != null){
                    EnchantsManager manager = UHC.getInstance().getEnchantsManager();

                    Map<Enchantment, Integer> map = event.getCurrentItem().getEnchantments();

                    if (map.containsKey(Enchantment.DAMAGE_ALL) && item.getType() == Material.DIAMOND_SWORD) {
                        int level = map.get(Enchantment.DAMAGE_ALL);
                        if (manager.getDiamondSharpness() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && GameUtils.isDiamondArmor(item)) {
                        int level = map.get(Enchantment.PROTECTION_ENVIRONMENTAL);
                        if (manager.getDiamondArmor() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.DAMAGE_ALL) && item.getType() == Material.IRON_SWORD) {
                        int level = map.get(Enchantment.DAMAGE_ALL);
                        if (manager.getIronSharpness() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && GameUtils.isIronArmor(item)) {
                        int level = map.get(Enchantment.PROTECTION_ENVIRONMENTAL);
                        if (manager.getIronArmor() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.ARROW_INFINITE)) {
                        int level = map.get(Enchantment.ARROW_INFINITE);
                        if (manager.getInfinity() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.ARROW_DAMAGE)) {
                        int level = map.get(Enchantment.ARROW_DAMAGE);
                        if (manager.getPower() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.ARROW_KNOCKBACK)) {
                        int level = map.get(Enchantment.ARROW_KNOCKBACK);
                        if (manager.getPunch() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.KNOCKBACK)) {
                        int level = map.get(Enchantment.KNOCKBACK);
                        if (manager.getKb() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.FIRE_ASPECT)) {
                        int level = map.get(Enchantment.FIRE_ASPECT);
                        if (manager.getFireAspect() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.ARROW_FIRE)) {
                        int level = map.get(Enchantment.ARROW_FIRE);
                        if (manager.getFlame() < level) event.setCancelled(true);
                    }

                    if (map.containsKey(Enchantment.THORNS)) {
                        int level = map.get(Enchantment.THORNS);
                        if (manager.getThorns() < level) event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {

        EnchantsManager manager = UHC.getInstance().getEnchantsManager();

        Map<Enchantment, Integer> map = event.getEnchantsToAdd();
        ItemStack item = event.getItem();

        if (map.containsKey(Enchantment.DAMAGE_ALL) && item.getType() == Material.DIAMOND_SWORD) {
            int level = map.get(Enchantment.DAMAGE_ALL);
            if (manager.getDiamondSharpness() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && GameUtils.isDiamondArmor(item)) {
            int level = map.get(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (manager.getDiamondArmor() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.DAMAGE_ALL) && item.getType() == Material.IRON_SWORD) {
            int level = map.get(Enchantment.DAMAGE_ALL);
            if (manager.getIronSharpness() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && GameUtils.isIronArmor(item)) {
            int level = map.get(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (manager.getIronArmor() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.ARROW_INFINITE)) {
            int level = map.get(Enchantment.ARROW_INFINITE);
            if (manager.getInfinity() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.ARROW_DAMAGE)) {
            int level = map.get(Enchantment.ARROW_DAMAGE);
            if (manager.getPower() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.ARROW_KNOCKBACK)) {
            int level = map.get(Enchantment.ARROW_KNOCKBACK);
            if (manager.getPunch() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.KNOCKBACK)) {
            int level = map.get(Enchantment.KNOCKBACK);
            if (manager.getKb() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.FIRE_ASPECT)) {
            int level = map.get(Enchantment.FIRE_ASPECT);
            if (manager.getFireAspect() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.ARROW_FIRE)) {
            int level = map.get(Enchantment.ARROW_FIRE);
            if (manager.getFlame() < level) event.setCancelled(true);
        }

        if (map.containsKey(Enchantment.THORNS)) {
            int level = map.get(Enchantment.THORNS);
            if (manager.getThorns() < level) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onXp(PlayerExpChangeEvent event){
        event.setAmount(event.getAmount()*uhc.getUhcConfig().getXpBoost());
    }



    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/rl") ||
                args[0].equalsIgnoreCase("/reload") ||
                args[0].equalsIgnoreCase("/bukkit:rl") ||
                args[0].equalsIgnoreCase("/bukkit:reload") ||
                args[0].equalsIgnoreCase("/me") ||
                args[0].equalsIgnoreCase("/minecraft:me")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAchivement(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamaging(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if (entityDamageByEntityEvent.getDamager() instanceof Arrow && entityDamageByEntityEvent.getEntity() instanceof Player && ((Arrow)entityDamageByEntityEvent.getDamager()).getShooter() instanceof Player){
            if( !uhc.getUhcData().isPvp()) {
                entityDamageByEntityEvent.setCancelled(true);
            }
            else if (uhc.getUhcConfig().isBowLife()){
                Player player = ((Player) ((Arrow) entityDamageByEntityEvent.getDamager()).getShooter());
                Player victim = ((Player) entityDamageByEntityEvent.getEntity());
                player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + "§e " + victim.getName() + " §8§l» " + makePercentColor(victim.getHealth()) + "%");
            }
        }

    }

    private String makePercentColor(double health) {
        double hearts = health / 2;
        double percent = hearts * 10;

        if (percent >= 66) {
            return "§a" + ((int) percent);
        } else if (percent >= 33) {
            return "§e" + ((int) percent);
        } else if (percent == 0) {
            return "§0" + (0);
        } else {
            return "§c" + ((int) percent);
        }
    }

    public boolean peutPrendreItem(ItemStack item, Player player,Inventory inventaire) {
        if (inventaire.contains(item) && inventaire.getTitle().contains(player.getName()) && inventaire.getViewers().contains(player)) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer((Player) event.getWhoClicked());
        Inventory inventaire = uhcPlayer.getBackupInventory();
        ItemStack current = event.getCurrentItem();
        if (event.getInventory().equals(inventaire)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && peutPrendreItem(event.getCurrentItem(), (Player) event.getWhoClicked(),inventaire)) {
                if(uhcPlayer.getBukkitPlayer().getInventory().firstEmpty() != -1){
                    inventaire.remove(current);
                    uhcPlayer.getBukkitPlayer().getInventory().addItem(current);
                }else{
                    uhcPlayer.getBukkitPlayer().sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §c§nAttention ! §cVotre inventaire est plein ! Par conséquent, vous ne pouvez pas récupérer des items dans votre inventaire de backup !");
                }
            }
        }
    }
}
