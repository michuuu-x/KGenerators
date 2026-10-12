package me.kryniowesegryderiusz.kgenerators.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * Every text in the plugin (messages, menus, generator items, holograms) is a MiniMessage string.
 * Placeholders like &lt;generator&gt; are replaced in those strings, and they are turned into
 * components just before they are shown.
 */
public final class ChatUtils {

	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	private ChatUtils() {
	}

	/**
	 * @param text MiniMessage string
	 * @return component to send to a player, an entity name or a hologram
	 */
	public static Component parse(String text) {
		return MINI_MESSAGE.deserialize(text);
	}

	/**
	 * Same as {@link #parse(String)}, but not italic unless the text says so. Item names and lores
	 * are italic by default.
	 */
	public static Component parseItemText(String text) {
		Component component = parse(text);
		if (component.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
			component = component.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
		return component;
	}

	public static List<Component> parseItemText(Collection<String> lines) {
		List<Component> components = new ArrayList<Component>(lines.size());
		for (String line : lines)
			components.add(parseItemText(line));
		return components;
	}

	/**
	 * @return MiniMessage string
	 */
	public static String serialize(Component component) {
		return MINI_MESSAGE.serialize(component);
	}

	/**
	 * @return MiniMessage string of item name or empty string if item has no custom name
	 */
	public static String getDisplayName(ItemStack item) {
		if (!item.hasItemMeta())
			return "";
		Component name = item.getItemMeta().displayName();
		return name == null ? "" : serializeItemText(name);
	}

	/**
	 * @return MiniMessage strings of item lore lines or empty list if item has no lore
	 */
	public static List<String> getLore(ItemStack item) {
		List<String> lore = new ArrayList<String>();
		if (!item.hasItemMeta())
			return lore;
		List<Component> components = item.getItemMeta().lore();
		if (components != null) {
			for (Component component : components)
				lore.add(serializeItemText(component));
		}
		return lore;
	}

	public static void setDisplayName(ItemMeta meta, String name) {
		meta.displayName(parseItemText(name));
	}

	public static void setLore(ItemMeta meta, Collection<String> lore) {
		meta.lore(parseItemText(lore));
	}

	/** Explicit non italic is what {@link #parseItemText(String)} adds, so it is not needed in the string */
	private static String serializeItemText(Component component) {
		if (component.decoration(TextDecoration.ITALIC) == TextDecoration.State.FALSE)
			component = component.decoration(TextDecoration.ITALIC, TextDecoration.State.NOT_SET);
		return serialize(component);
	}
}
