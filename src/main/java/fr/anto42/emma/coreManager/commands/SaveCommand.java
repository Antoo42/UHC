package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.worldManager.WorldManager;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.game.impl.UHCData;
import fr.anto42.emma.utils.players.PlayersUtils;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save");
        super.getAliases().add("finish");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(((Player) sender));
        if(!uhcPlayer.isEditing()){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
            return true;
        }

        UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig().setStartInv(uhcPlayer.getBukkitPlayer().getInventory().getContents());
        UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig().setHead(uhcPlayer.getBukkitPlayer().getInventory().getHelmet());
        UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig().setBody(uhcPlayer.getBukkitPlayer().getInventory().getChestplate());
        UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig().setLeggins(uhcPlayer.getBukkitPlayer().getInventory().getLeggings());
        UHC.getInstance().getUhcGame().getUhcConfig().getStarterStuffConfig().setBoots(uhcPlayer.getBukkitPlayer().getInventory().getBoots());
        uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §aL'inventaire de départ a été modifié avec succés !");
        uhcPlayer.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        uhcPlayer.getBukkitPlayer().teleport(UHC.getInstance().getWorldManager().getSpawnLocation());
        uhcPlayer.getBukkitPlayer().getInventory().clear();
        uhcPlayer.getBukkitPlayer().getInventory().setHelmet(null);
        uhcPlayer.getBukkitPlayer().getInventory().setChestplate(null);
        uhcPlayer.getBukkitPlayer().getInventory().setLeggings(null);
        uhcPlayer.getBukkitPlayer().getInventory().setBoots(null);
        uhcPlayer.setEditing(false);
        PlayersUtils.giveWaitingStuff(uhcPlayer.getBukkitPlayer());
        return false;
    }
}
