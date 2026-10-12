package me.kryniowesegryderiusz.kgenerators.dependencies.hooks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.clip.placeholderapi.PlaceholderAPI;
import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.dependencies.enums.Dependency;
import me.kryniowesegryderiusz.kgenerators.utils.ChatUtils;

public class PlaceholderAPIHook {

	public static String translatePlaceholders(OfflinePlayer op, String text) {

		if (op != null && Main.getDependencies().isEnabled(Dependency.PLACEHOLDERAPI)) {
			return PlaceholderAPI.setPlaceholders(op, text);
		} else
			return text;
	}

	public static ItemStack translatePlaceholders(OfflinePlayer op, ItemStack item) {

		if (op != null && Main.getDependencies().isEnabled(Dependency.PLACEHOLDERAPI) && item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName())
				ChatUtils.setDisplayName(meta, PlaceholderAPI.setPlaceholders(op, ChatUtils.getDisplayName(item)));
			if (meta.hasLore()) {
				List<String> lore = new ArrayList<String>(PlaceholderAPI.setPlaceholders(op, ChatUtils.getLore(item)));
				ChatUtils.setLore(meta, lore);
			}
			item.setItemMeta(meta);
		}
		return item;
	}
}
