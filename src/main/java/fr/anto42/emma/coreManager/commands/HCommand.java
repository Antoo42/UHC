package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.UHCManager;
import fr.anto42.emma.coreManager.listeners.customListeners.BorderMovementEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.LateEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.PvPEvent;
import fr.anto42.emma.coreManager.listeners.customListeners.RolesEvent;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.players.UHCPlayerStates;
import fr.anto42.emma.coreManager.votes.VoteSystem;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.UHCGame;
import fr.anto42.emma.utils.*;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.players.PlayersUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HCommand extends Command {
    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    private final UHCManager uhcManager = UHC.getInstance().getUhcManager();
    List<String> aliases = new ArrayList<>();
    public HCommand() {
        super("host");
        aliases.add("h");
        aliases.add("uhc");
        super.setAliases(aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        Player player = ((Player) sender);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if(!uhcPlayer.equals(uhc.getUhcData().getHostPlayer()) && !uhc.getUhcData().getCoHostList().contains(uhcPlayer) && !uhcPlayer.isUHCOp()){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
            return true;
        }
        if(args.length == 0 || args[0].equalsIgnoreCase("help")){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Liste des commandes disponibles:");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h config§7: Ouvrez, par le biais d'une commande, le menu de configuration.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h add/remove/list§7: Ajoutez, retirez ou consultez à votre guise les Hosts de la partie.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h name <nom>: §7Renomez votre partie comme bon vous le semble §a(Fonctionne avec les codes couleurs !)");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h say <message>: §7Faîtes votre plus belle annonce en destination des joueurs de votre partie.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h force <PvP/border>: §7Forcez l'activation du PvP ou la mise en mouvement de la bordure.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h give <item> <montant>: §7Donnez à tout les joueurs un item ciblé.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h chat: §7Activez/désactivez le chat.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h late <Player>:§7 Ajoutez un joueur à la partie en cours de route.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h revive <Player>:§7 Ramenez un joueur à la vie !");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h heal:§7 Remettez tout les joueurs sur le même pied d'estale en les soignant au maxiumum.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h groupes set <montant>/warn: §7Le système parfait pour rappeler à l'ordre les joueurs de la partie qui sont tous groupés !");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h vote <sujet>: §7Démarrez un vote/sondage durant 30 secondes sur le sujet que vous souhaitez !");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §c/spec help: §7Consultez les commandes de spectateur.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h stop: §7Stoppez le serveur de jeu. §c(⚠Irréversible !)");
            uhcPlayer.sendMessage("");
        }
        else if (args[0].equalsIgnoreCase("add")){
            if (uhcPlayer != uhc.getUhcData().getHostPlayer() && !uhcPlayer.getBukkitPlayer().isOp()){
                uhcPlayer.sendClassicMessage("§cSeul l'host de la partie peut faire ça !");
            }
            if (args.length == 1){
                uhcPlayer.sendClassicMessage("§cMerci de préciser un joueur.");

            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                uhcPlayer.sendClassicMessage("§cMerci de préciser un joueur connecté.");
                return true;
            }
            if (target == player && uhc.getUhcData().getHostPlayer() == uhcPlayer) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça.");
                return true;
            }
            UHCPlayer uhctarget = UHC.getUHCPlayer(target);
            uhc.getUhcData().getCoHostList().add(uhctarget);
            uhcPlayer.sendClassicMessage("§7Vous avez §aajouté §a" + uhctarget.getName() + " §7à liste des co-Hosts.");
            uhctarget.sendClassicMessage("§aVous êtes maintenant co-Host de la partie !");
            uhctarget.sendClassicMessage("§7Vous pouvez consulter toutes les commandes à votre disposition à l'aide de la commande §3/h help§7.");
            SoundUtils.playSoundToPlayer(target, Sound.LEVEL_UP);
            if (uhc.getGameState() == GameState.WAITING || uhc.getGameState() == GameState.STARTING)
                fr.anto42.emma.utils.players.PlayersUtils.giveWaitingStuff(uhctarget.getBukkitPlayer());
        }
        else if (args[0].equalsIgnoreCase("remove")){
            if (uhcPlayer != uhc.getUhcData().getHostPlayer() && !uhcPlayer.getBukkitPlayer().isOp()){
                uhcPlayer.sendClassicMessage("§cSeul l'host de la partie peut faire ça !");
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                uhcPlayer.sendClassicMessage("§cMerci de préciser un joueur connecté.");
                return true;
            }
            UHCPlayer uhctarget = UHC.getUHCPlayer(target);
            if (!uhc.getUhcData().getCoHostList().contains(uhctarget)){
                uhcPlayer.sendClassicMessage("§cCe joueur n'est pas co-Host.");
                return true;
            }
            if (target == player) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça.");
                return true;
            }
            uhc.getUhcData().getCoHostList().remove(uhctarget);
            if (uhc.getGameState() == GameState.WAITING || uhc.getGameState() == GameState.STARTING)
                fr.anto42.emma.utils.players.PlayersUtils.giveWaitingStuff(uhctarget.getBukkitPlayer());
            uhcPlayer.sendClassicMessage("§7Vous avez retiré §a" + uhctarget.getName() + " §7des Hosts de la partie.");
            uhcPlayer.sendClassicMessage("§cVous avez été retiré de la liste des Hosts de la partie.");
        }
        else if (args[0].equalsIgnoreCase("list")){
            uhcPlayer.sendClassicMessage("§7Voici la liste des Hosts de la partie:");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §e" + uhc.getUhcData().getHostPlayer().getName() +" §3" + uhc.getUhcData().getHostPlayer().getUuid() + ") §7- §aHost principal");
            uhc.getUhcData().getCoHostList().forEach(uhcPlayer1 -> {
                uhcPlayer.sendMessage("§8§l» §e" + uhcPlayer1.getName() + " §3(" + uhcPlayer1.getUuid() + ")");
            });
        }
        else if (args[0].equalsIgnoreCase("force")){
            if (!uhc.getGameState().equals(GameState.PLAYING)) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça maintenant !");
                return true;
            }
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cErreur de syntaxe ! §3(/h force <pvp/border/roles>)");
            } else if (args[1].equalsIgnoreCase("pvp")){
                if (uhc.getUhcData().isPvp())
                    return true;
                uhc.getUhcData().setPvp(true);
                Bukkit.getServer().getPluginManager().callEvent(new PvPEvent());
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le PvP est désormais §aactif §7!");
                SoundUtils.playSoundToAll(Sound.WOLF_GROWL);
            }else if (args[1].equalsIgnoreCase("border")){
                if (uhc.getUhcData().isBorderMove())
                    return true;
                uhc.getUhcData().setBorderMove(true);
                long a = uhc.getUhcConfig().getStartBorderSize() - uhc.getUhcConfig().getFinalBorderSize();
                a = (long) (a/uhc.getUhcConfig().getBlockPerS());
                WorldManager.getGameWorld().getWorldBorder().setSize(uhc.getUhcConfig().getFinalBorderSize()*2, a);
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7La bordure est en §amouvement§7 !");
                SoundUtils.playSoundToAll(Sound.ENDERDRAGON_GROWL);
                Bukkit.getServer().getPluginManager().callEvent(new BorderMovementEvent());
            } else if (args[1].equalsIgnoreCase("roles" ) || args[1].equalsIgnoreCase("rôles")){
                if (uhc.getUhcData().isRoles())
                    return true;
                uhc.getUhcData().setRoles(true);
                Bukkit.getServer().getPluginManager().callEvent(new RolesEvent());

            }
        } else if (args[0].equalsIgnoreCase("playerlist")){
            uhcPlayer.sendClassicMessage("§7Voici la liste des joueurs de la partie:");
            uhcPlayer.sendMessage("");
            uhc.getUhcData().getUhcPlayerList().forEach(uhcPlayer1 -> {
                uhcPlayer.sendMessage("§8§l» §e" + uhcPlayer1.getName() + " §3(" + uhcPlayer1.getUuid() + ") " + (uhcPlayer1.getBukkitPlayer() != null ? "§aConnecté" : ""));
            });
        } else if (args[0].equalsIgnoreCase("give")){
            if (!uhc.getGameState().equals(GameState.PLAYING)) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça maintenant !");
                return true;
            }
            if (args.length == 1){
                uhcPlayer.sendClassicMessage("§cMerci de préciser un item ainsi qu'un montant ! §3(/h give <item> <montant>)");
                return true;
            }
            Material mat = Bukkit.getUnsafe().getMaterialFromInternalName(args[1]);

            boolean exist = false;

            for(Material m : Material.values()) {
                if (m == mat) {
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                player.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + "§c Cet item n'existe pas !");
                return true;
            }

            int amount = 1;

            if (args.length == 3 && fr.anto42.emma.utils.players.GameUtils.isInteger(args[2])) {
                amount = Integer.parseInt(args[2]);
            }

            final int newAmount = amount;
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §e" + uhcPlayer.getBukkitPlayer().getDisplayName() + " §7a give §3" + amount+ " " +args[1].replace("minecraft:", ""));
            uhc.getUhcData().getUhcPlayerList().stream().filter(uhcPlayer1 -> uhcPlayer1.getBukkitPlayer() != null).forEach(uhcPlayer1 -> uhcPlayer1.safeGive(new ItemCreator(mat, newAmount).get()));
            SoundUtils.playSoundToAll(Sound.ORB_PICKUP);
        }
        else if (args[0].equalsIgnoreCase("config")){
            uhcManager.getConfigMainGUI().open(uhcPlayer.getBukkitPlayer());
        }
        else if (args[0].equalsIgnoreCase("chat")){
            if (uhc.getUhcData().isChat()){
                uhc.getUhcData().setChat(false);
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §3" + uhcPlayer.getName() + "§7 vient de §cdésativer§7 le chat.");
            }else{
                uhc.getUhcData().setChat(true);
                Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("modPrefix").replace("&", "§") + " §3" + uhcPlayer.getName() + "§7 vient d'§aactiver§7 le chat.");
            }
        }
        else if (args[0].equalsIgnoreCase("stop")){
            if (uhcPlayer != uhc.getUhcData().getHostPlayer() && !uhcPlayer.isUHCOp()){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cCette commande est restreinte à l'Host de la partie.");
                return true;
            }
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Le serveur §e" + Bukkit.getServerName() + "§7 a été arrêté par §c" + uhcPlayer.getName() + "§7.");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), Bukkit::shutdown, 15L);
        }
        else if (args[0].equalsIgnoreCase("late")){
            if (!uhc.getGameState().equals(GameState.PLAYING)) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça maintenant !");
                return true;
            }
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur connecté.");
                return true;
            }
            UHCPlayer uhctarget = UHC.getUHCPlayer(target);
            if (uhc.getUhcData().getUhcPlayerList().contains(uhctarget)){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas ajouter un joueur qui est déjà dans la partie !");
                return true;
            }
            Bukkit.getPluginManager().callEvent(new LateEvent(uhcPlayer));
        }
        else if (args[0].equalsIgnoreCase("revive")){
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur connecté.");
                return true;
            }
            UHCPlayer uhctarget = UHC.getUHCPlayer(target);
            if (uhctarget.getPlayerState() == UHCPlayerStates.ALIVE){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas réssucité un joueur qui n'est pas encore mort !");
                return true;
            }
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §a" + uhctarget.getName() + "§7 a été réssucité !");
            uhc.getUhcData().getUhcPlayerList().add(uhctarget);
            uhctarget.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
            fr.anto42.emma.utils.players.PlayersUtils.randomTp(uhctarget.getBukkitPlayer(), WorldManager.getGameWorld());
            fr.anto42.emma.utils.players.InventoryUtils.restoreInventory(target);
            if (uhctarget.getPreviousTeam() != null && uhctarget.getPreviousTeam().isAlive()) {
                uhctarget.joinTeam(uhctarget.getPreviousTeam());
            }
        }
        else if (args[0].equalsIgnoreCase("heal")){
            if (!uhc.getGameState().equals(GameState.PLAYING)) {
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas faire ça maintenant !");
                return true;
            }
            uhc.getUhcData().getUhcPlayerList().stream().filter(uhcPlayer1 -> uhcPlayer1.getBukkitPlayer() != null).forEach(uhcPlayer1 -> {
                uhcPlayer1.getBukkitPlayer().setHealth(uhcPlayer1.getBukkitPlayer().getMaxHealth());
            });
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §3" + uhcPlayer.getBukkitPlayer().getName() + " §7vient de soigner toute la partie !");
            SoundUtils.playSoundToAll(Sound.VILLAGER_IDLE);
        }
        else if (args[0].equalsIgnoreCase("groupes") || args[0].equalsIgnoreCase("groups")){
            if (!uhc.getUhcConfig().isGroupSystem()){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cLe système de groupe est désactivé !");
                return true;
            }
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Gestion des groupes:");
                uhcPlayer.sendMessage("");
                uhcPlayer.sendMessage("§8§l» §6§l/h groups set <nombre>§7: Définnissez la taille des groupes.");
                uhcPlayer.sendMessage("");
                uhcPlayer.sendMessage("§8§l» §6§l/h groups warn§7: Rappelez à l'ordre les joueurs sur les groupes.");
                uhcPlayer.sendMessage("");
            }else if (args[1].equalsIgnoreCase("warn")){
                fr.anto42.emma.utils.players.GameUtils.warnGroups();
            }else if (args[1].equalsIgnoreCase("set")){
                if (args.length == 2){
                    uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cErreur de syntaxe ! (/h groups set <montant>");
                    return true;
                }
                fr.anto42.emma.utils.players.GameUtils.setGroupsLimit(Integer.parseInt(args[2]));
                fr.anto42.emma.utils.players.GameUtils.warnGroups();
            }
        }
        else if (args[0].equalsIgnoreCase("name")){
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous devez indiquer le nom de votre partie ! §3(/h name <name>)");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            args[0] = args[0].replace("name", "");
            for(String l : args){
                sb.append(l).append(" ");
            }
            if (sb.toString().length()  >= 32){
                uhcPlayer.sendClassicMessage("§cVous ne pouvez pas définir un nom de partie donc la longueur est supérieure à 32 caractères !");
                return true;
            }
            uhc.getUhcConfig().setUHCName(sb.toString().replace("&", "§"));
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §7Votre partie est désormais sous le nom suivant: §a" + sb.toString());
        }
        else if (args[0].equalsIgnoreCase("vote")){
            if (VoteSystem.getInstance().isVote()){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cUn vote est déjà en cours !");
                return true;
            }
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVeuillez indiquer sur quoi le vote portera §3(/h vote <sujet>)");
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            args[0] = args[0].replace(args[0], "");
            for (String arg : args) {
                stringBuilder.append(arg).append(" ");
            }
            VoteSystem.getInstance().startVote(stringBuilder.toString());
        }
        else if (args[0].equalsIgnoreCase("say") || args[0].equalsIgnoreCase("bc")){
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVeuillez indiquer un message à transmettre ! §3(/h say <message>)");
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            args[0] = args[0].replace(args[0], "");
            for (String arg : args) {
                stringBuilder.append(arg).append(" ");
            }
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(UHC.getInstance().getConfig().getString("sayPrefix").replace("&", "§") + " §e" + uhcPlayer.getBukkitPlayer().getDisplayName() + ": §c" + stringBuilder.toString());
            Bukkit.broadcastMessage("");
            SoundUtils.playSoundToAll(Sound.ORB_PICKUP);
        }
        else if (args[0].equalsIgnoreCase("kick")) {
            if (args.length == 1){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cMerci de préciser un joueur connecté.");
                return true;
            }
            target.kickPlayer("§cVous avez été kick de la partie.");
        }

        else if (args[0].equalsIgnoreCase("whitelist") || args[0].equalsIgnoreCase("wl")){
            if (args.length == 1){
                uhcPlayer.sendClassicMessage("§7Gestion de la whitelist:");
                uhcPlayer.sendMessage("");
                uhcPlayer.sendMessage("§8§l» §6/h wl add <Player>§7: Ajouter un joueur à la whitelist.");
                uhcPlayer.sendMessage("");
                uhcPlayer.sendMessage("§8§l» §6/h wl remove <Player>§7: Retirer un joueur de la whitelist.");
                uhcPlayer.sendMessage("");
                uhcPlayer.sendMessage("§8§l» §6/h wl list§7: Consulter la whitelist.");
                return true;
            }
            if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                if (args.length == 2){
                    uhcPlayer.sendClassicMessage("§cMerci d'indiquer un joueur ! §3/whitelist <add:remove> <Player>");
                    return true;
                }
                if (args[1].equalsIgnoreCase("add")){
                    if (UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().contains(args[2])){
                        uhcPlayer.sendClassicMessage("§cCe joueur est déjà présent dans la whitelist.");
                        SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                        return true;
                    }
                    UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().add(args[2]);
                    uhcPlayer.sendClassicMessage("§7Vous avez bien ajouter §a" + args[2] + " §7a la whitelist.");
                    return true;
                }
                if (args [1].equalsIgnoreCase("remove")){
                    if (!UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().contains(args[2])){
                        uhcPlayer.sendClassicMessage("§cCe joueur n'est pas dans la whitelist.");
                        SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                        return true;
                    }
                    if (args[2].equals(uhcPlayer.getName())) {
                        uhcPlayer.sendClassicMessage("§cVous ne pouvez pas vous retirer vous même de la whitelist !");
                        SoundUtils.playSoundToPlayer(player, Sound.VILLAGER_NO);
                    }
                    UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().remove(args[2]);
                    uhcPlayer.sendClassicMessage("§7Vous avez bien retirer §c" + args[2] + "§7 de la whitelist.");
                    return true;
                }

            }
            if (args[1].equalsIgnoreCase("list")){
                if (UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().size() == 0){
                    uhcPlayer.sendClassicMessage("§cAucun joueur n'est présent dans la whitelist.");
                    return true;
                }
                uhcPlayer.sendClassicMessage("§7Voici la liste des joueurs présents dans la whitelist:");
                UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().forEach(s1 -> {
                    uhcPlayer.sendMessage("§8§l» §e" + s1);
                });

            }
            uhcPlayer.sendClassicMessage("§7Gestion de la whitelist:");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h wl add <Player>§7: Ajouter un joueur à la whitelist.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h wl remove <Player>§7: Retirer un joueur de la whitelist.");
            uhcPlayer.sendMessage("");
            uhcPlayer.sendMessage("§8§l» §6/h wl list§7: Consulter la whitelist.");
        } else uhcPlayer.sendClassicMessage("§cCommande non trouvée ! §3(/h help)");
        return false;
    }
}
