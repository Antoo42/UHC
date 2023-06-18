package fr.anto42.emma.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.util.Map;

public class CommandUtils {
    public CommandUtils() {
    }

    public static void registerCommand(String fallback, Command command) {
        ((CraftServer) Bukkit.getServer()).getCommandMap().register(fallback, command);
    }

    public static void unregisterCommand(String fallback, Command command) {
        try {
            SimpleCommandMap commandMap = ((CraftServer)Bukkit.getServer()).getCommandMap();
            Map<String, Command> commands = (Map)SimpleReflection.getFieldValue(commandMap, "knownCommands");
            Command removed = (Command)commands.remove(fallback + ":" + command.getName());
            if (removed != null && removed == command) {
                removed.unregister(commandMap);
            }

            removed = (Command)commands.remove(command.getName());
            if (removed != null && removed == command) {
                removed.unregister(commandMap);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}

