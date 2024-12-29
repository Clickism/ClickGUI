package me.clickism.clickgui.menu;

import me.clickism.clickgui.annotations.Colorized;
import me.clickism.clickgui.util.Utils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Represents the icon of a button.
 */
@FunctionalInterface
public interface Icon {
    /**
     * Gets the item stack of this icon.
     *
     * @return the item stack
     */
    ItemStack get();

    /**
     * Sets the name of this icon.
     *
     * @param name the name
     * @return this icon
     */
    default Icon setName(@Colorized String name) {
        return applyToMeta(meta -> meta.setDisplayName(Utils.colorize(name)));
    }

    /**
     * Sets the material of this icon.
     *
     * @param material the material
     * @return this icon
     */
    default Icon setMaterial(Material material) {
        get().setType(material);
        return this;
    }

    /**
     * Hides the attributes of this icon.
     *
     * @return this icon
     */
    default Icon hideAttributes() {
        return applyToMeta(meta -> {
            addDummyAttributeModifier(meta);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
    }

    /**
     * Hides potion effects of this icon.
     *
     * @return this icon
     */
    default Icon hidePotionEffects() {
        return applyToMeta(meta -> {
            addDummyAttributeModifier(meta);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        });
    }

    /**
     * Hides all attributes of this icon.
     *
     * @return this icon
     */
    default Icon hideAllAttributes() {
        return applyToMeta(meta -> {
            addDummyAttributeModifier(meta);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ARMOR_TRIM,
                    ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
        });
    }

    private void addDummyAttributeModifier(ItemMeta meta) {
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,
                new AttributeModifier("clickgui.hide_attributes", 0, AttributeModifier.Operation.ADD_NUMBER));
    }

    /**
     * Adds the enchantment glint to this icon.
     *
     * @return this icon
     */
    default Icon addEnchantmentGlint() {
        return applyToMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        });
    }

    /**
     * Sets the lore of this icon.
     *
     * @param lore the lore
     * @return this icon
     */
    default Icon setLore(@Colorized String... lore) {
        return setLore(List.of(lore));
    }

    /**
     * Sets the lore of this icon.
     *
     * @param lore the lore
     * @return this icon
     */
    default Icon setLore(@Colorized List<String> lore) {
        return applyToMeta(meta -> {
            meta.setLore(lore.stream()
                    .map(Utils::colorize)
                    .toList());
        });
    }

    /**
     * Applies the consumer to the meta of this icon.
     *
     * @param consumer the consumer
     * @return this icon
     */
    default Icon applyToMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = get().getItemMeta();
        if (meta == null) return this;
        consumer.accept(meta);
        get().setItemMeta(meta);
        return this;
    }

    /**
     * Runs the consumer if the condition is true.
     *
     * @param condition the condition
     * @param consumer  the consumer
     * @return this icon
     */
    default Icon runIf(boolean condition, Consumer<Icon> consumer) {
        if (condition) {
            consumer.accept(this);
        }
        return this;
    }

    /**
     * Creates an empty icon.
     *
     * @return the icon
     */
    static Icon empty() {
        return of(Material.AIR);
    }

    /**
     * Creates an icon from the given item stack.
     *
     * @param item the item stack
     * @return the icon
     */
    static Icon of(ItemStack item) {
        final ItemStack copy = item.clone();
        return () -> copy;
    }

    /**
     * Creates an icon from the given material.
     *
     * @param material the material
     * @return the icon
     */
    static Icon of(Material material) {
        final ItemStack item = new ItemStack(material);
        return () -> item;
    }

    /**
     * Creates a dynamic icon from the given icon supplier.
     *
     * @param iconSupplier the icon supplier
     * @return the icon
     */
    static Icon of(Supplier<Icon> iconSupplier) {
        return () -> iconSupplier.get().get();
    }
}
