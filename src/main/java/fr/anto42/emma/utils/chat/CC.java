package fr.anto42.emma.utils.chat;

import org.bukkit.ChatColor;

public class CC {
    public static final String CC_BAR = CC.translate("&f&m----------------------------");

    public static String translate(String var1) {
        return ChatColor.translateAlternateColorCodes('&', var1);
    }

    public static String prefix(String var1) {
        return "§7▎ §f" + ChatColor.translateAlternateColorCodes('&', var1);
    }


}
