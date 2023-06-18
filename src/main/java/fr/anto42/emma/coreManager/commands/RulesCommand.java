package fr.anto42.emma.coreManager.commands;

import fr.anto42.emma.coreManager.uis.RulesGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand extends Command {
    public RulesCommand() {
        super("rules");
        super.getAliases().add("regles");
        super.getAliases().add("rule");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        new RulesGUI().getkInventory().open(((Player) commandSender));
        return false;
    }
}
