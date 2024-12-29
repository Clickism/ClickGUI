package me.clickism.clickgui.menu;

import me.clickism.clickgui.annotations.Colorized;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents a button in a menu.
 */
public class Button {
    /**
     * The icon of this button.
     */
    protected Icon icon;
    /**
     * Whether this button is immovable.
     */
    protected boolean immovable = true;

    /**
     * The action to perform when this button is clicked.
     */
    protected BiConsumer<InventoryClickEvent, MenuView> onClick = (event, view) -> {};

    /**
     * Creates a new button with the specified icon.
     *
     * @param icon the icon
     */
    protected Button(Icon icon) {
        this.icon = icon;
    }

    /**
     * Handles the click event for this button.
     *
     * @param event the click event
     * @param view  the view
     */
    protected void handleClick(InventoryClickEvent event, MenuView view) {
        event.setCancelled(immovable);
        onClick.accept(event, view);
    }

    /**
     * Sets the name of this button's icon. See {@link Icon#setName(String)}.
     *
     * @param name the name
     * @return this button
     */
    public Button setName(@Colorized String name) {
        icon.setName(name);
        return this;
    }

    /**
     * Sets the material of this button's icon. See {@link Icon#setMaterial(Material)}.
     *
     * @param material the material
     * @return this button
     */
    public Button setMaterial(Material material) {
        icon.setMaterial(material);
        return this;
    }

    /**
     * Hides the attributes of this button's icon. See {@link Icon#hideAttributes()}.
     *
     * @return this button
     */
    public Button hideAttributes() {
        icon.hideAttributes();
        return this;
    }

    /**
     * Hides the potion effects of this button's icon. See {@link Icon#hidePotionEffects()}.
     *
     * @return this button
     */
    public Button hidePotionEffects() {
        icon.hidePotionEffects();
        return this;
    }

    /**
     * Hides all attributes of this button's icon. See {@link Icon#hideAllAttributes()}.
     *
     * @return this button
     */
    public Button hideAllAttributes() {
        icon.hideAllAttributes();
        return this;
    }

    /**
     * Adds an enchantment glint to this button's icon. See {@link Icon#addEnchantmentGlint()}.
     *
     * @return this button
     */
    public Button addEnchantmentGlint() {
        icon.addEnchantmentGlint();
        return this;
    }

    /**
     * Sets the lore of this button's icon. See {@link Icon#setLore(List)}.
     *
     * @param lore the lore
     * @return this button
     */
    public Button setLore(@Colorized String... lore) {
        icon.setLore(lore);
        return this;
    }

    /**
     * Sets the lore of this button's icon. See {@link Icon#setLore(List)}.
     *
     * @param lore the lore
     * @return this button
     */
    public Button setLore(@Colorized List<String> lore) {
        icon.setLore(lore);
        return this;
    }

    /**
     * Runs the consumer on this button if the condition is true.
     *
     * @param condition the condition
     * @param consumer  the consumer
     * @return this button
     */
    public Button runIf(boolean condition, Consumer<Button> consumer) {
        if (condition) {
            consumer.accept(this);
        }
        return this;
    }

    /**
     * Sets the action to perform when this button is clicked.
     *
     * @param action the action
     * @return this button
     */
    public Button setOnClick(ClickAction action) {
        this.onClick = (event, view) -> action.onClick(view.getMenu().getPlayer(), view, event.getRawSlot());
        return this;
    }

    /**
     * Sets the action to perform when this button is clicked.
     *
     * @param eventConsumer the action
     * @return this button
     */
    public Button setOnClick(Consumer<InventoryClickEvent> eventConsumer) {
        this.onClick = (event, view) -> eventConsumer.accept(event);
        return this;
    }

    /**
     * Marks this button as movable, allowing it to be moved and removed from the inventory.
     *
     * @return this button
     */
    public Button setMovable() {
        this.immovable = false;
        return this;
    }

    /**
     * Applies the consumer to the meta of this button's icon. See {@link Icon#applyToMeta(Consumer)}.
     *
     * @param consumer the consumer
     * @return this button
     */
    public Button applyToMeta(Consumer<ItemMeta> consumer) {
        icon.applyToMeta(consumer);
        return this;
    }

    /**
     * Creates a button with an empty icon.
     *
     * @return the button
     */
    public static Button empty() {
        return withIcon(Material.AIR);
    }

    /**
     * Creates a button with the specified icon.
     *
     * @param icon the icon
     * @return the button
     */
    public static Button withIcon(Icon icon) {
        return new Button(icon);
    }

    /**
     * Creates a button with the specified item stack as the icon.
     *
     * @param item the item stack
     * @return the button
     */
    public static Button withIcon(ItemStack item) {
        return new Button(Icon.of(item));
    }

    /**
     * Creates a button with the specified material as the icon.
     *
     * @param material the material
     * @return the button
     */
    public static Button withIcon(Material material) {
        return new Button(Icon.of(material));
    }
}
