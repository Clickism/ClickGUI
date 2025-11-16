package de.clickism.clickgui.menu;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a background for a menu.
 */
@FunctionalInterface
public interface MenuBackground {
    /**
     * Gets the background button at the specified slot.
     *
     * @param slot the slot
     * @return the button
     */
    @Nullable
    Button getButton(int slot);
}
