package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.UHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhitelistCommand extends Command {
    public WhitelistCommand() {
        super("whitelist");
        super.getAliases().add("wl");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player){
        UHC.getUHCPlayer(((Player) sender)).sendClassicMessage("§cVeuillez passer par la commande §3/host whitelist §c!");
        return true;
    }
        if (args.length == 0){
            System.out.println("UHC: /whitelist <add:remove> <Player>");
            return true;
        }
        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1){
                System.out.println("UHC: Merci d'indiquer un joueur ! /whitelist <add:remove> <Player>");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")){
                if (UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().contains(args[1])){
                    System.out.println("UHC: Ce joueur est déjà présent dans la whitelist.");
                    return true;
                }
                UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().add(args[1]);
                System.out.println("UHC: Vous avez bien ajouter " + args[1] + " a la whitelist.");
                return true;
            }
            if (args [0].equalsIgnoreCase("remove")){
                if (!UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().contains(args[1])){
                    System.out.println("UHC: Ce joueur n'est pas dans la whitelist.");
                    return true;
                }
                UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().remove(args[1]);
                System.out.println("UHC: Vous avez bien retirer " + args[1] + " de la whitelist.");
                return true;
            }

        }
        if (args[0].equalsIgnoreCase("list")){
            if (UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().size() == 0){
                System.out.println("UHC: Aucun joueur n'est présent dans la whitelist.");
            }
            else {
                System.out.println("UHC: Voici la liste des joueurs présents dans la whitelist:");
                UHC.getInstance().getUhcGame().getUhcData().getPreWhitelist().forEach(s1 -> {
                    System.out.println("- " + s1);
                });
            }
        }
        return false;
    }
}
