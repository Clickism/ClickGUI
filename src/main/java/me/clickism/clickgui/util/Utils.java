package me.clickism.clickgui.util;

import org.bukkit.ChatColor;

public class Utils {

    /**
     * Colorizes a string with the alternate color code '&'.
     *
     * @return colorized string
     **/
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
