package me.clickism.clickgui.menu;

import org.bukkit.ChatColor;

/**
 * Utility class.
 */
class Utils {
    /**
     * No constructor for utility class.
     */
    private Utils() {
    }

    /**
     * Colorizes a string with the alternate color code '&amp;'.
     *
     * @param text the text to colorize
     * @return colorized string
     **/
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
