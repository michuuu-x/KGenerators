package me.kryniowesegryderiusz.kgenerators.lang.storages;

import me.kryniowesegryderiusz.kgenerators.utils.ChatUtils;
import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.kryniowesegryderiusz.kgenerators.utils.immutable.Config;

public class CustomNamesStorage {

	private HashMap<String, String> customNames = new HashMap<String, String>();
	private Config config;

	public CustomNamesStorage(Config config) {
		this.config = config;
		this.reload();
	}

	public void reload() {
		for (String obj : config.getConfigurationSection("").getKeys(false)) {
			customNames.put(obj, config.getString(obj));
		}
	}

	public String getItemTypeName(ItemStack item) {
		String name = "";

		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName())
			name = ChatUtils.getDisplayName(item);
		else if (customNames.get(item.getType().name()) != null)
			name = customNames.get(item.getType().name());
		else {
			String type = item.getType().toString();
			type = type.toLowerCase().replaceAll("_", " ");
			type = type.substring(0, 1).toUpperCase() + type.substring(1);
			name = type;
		}
		return name;
	}

	public String getEnchantName(Enchantment enchant) {
		String name = "";

		String customName = customNames.get(enchant.getKey().getKey().toUpperCase());
		if (customName != null)
			name = customName;
		else {
			String type = enchant.getKey().getKey();
			type = type.toLowerCase().replaceAll("_", " ");
			type = type.substring(0, 1).toUpperCase() + type.substring(1);
			name = type;
		}

		return name;
	}

}
