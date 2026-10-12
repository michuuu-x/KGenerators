package me.kryniowesegryderiusz.kgenerators.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.dependencies.hooks.ItemsAdderHook;
import me.kryniowesegryderiusz.kgenerators.dependencies.hooks.MythicMobsHook;
import me.kryniowesegryderiusz.kgenerators.dependencies.hooks.NexoHook;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;

public abstract class ItemUtils {
	
    public static ItemStack parseItemStack(String material, String place, boolean isBlockCheck) { 	
    	if (material.contains(":")) {
    		String[] splitted = material.split(":");
        	if (splitted[0].equals("generator")
        			&& Main.getGenerators() != null
        			&& Main.getGenerators().exists(splitted[1]))
        		return Main.getGenerators().get(splitted[1]).getGeneratorItem().clone();
        	else if (splitted[0].equals("customhead")
        			&& Main.getMultiVersion().getSkullUtils().itemFromBase64(splitted[1]) != null)
        		return Main.getMultiVersion().getSkullUtils().itemFromBase64(splitted[1]);
        	else if (splitted[0].equals("itemsadder")
        			&& ItemsAdderHook.getItemStack(splitted[1]) != null)
        		return ItemsAdderHook.getItemStack(splitted[1]);
        	else if (splitted[0].equals("nexo")
        			&& NexoHook.exists(splitted[1]))
        		return NexoHook.getItemStack(splitted[1]);
        	else if (splitted[0].equals("mythicmobs")
        			&& MythicMobsHook.getItemStack(splitted[1]) != null)
        		return MythicMobsHook.getItemStack(splitted[1]);
        	
    	}
		return new ItemStack(getMaterial(material, place, isBlockCheck));
    }

	public static Material getMaterial(String material, String place, boolean isBlockCheck) {
		Material m = Material.matchMaterial(material);
		if (m == null) {
			Logger.error(place + ": " + material + " is not a proper material or generator id! Using STONE!");
			return Material.STONE;
		}
		if (isBlockCheck && !m.isBlock()) {
			Logger.error(place + ": " + material + " is not a block! Using STONE!");
			return Material.STONE;
		}
		return m;
	}
	
	/**
	 * Compares ItemStacks ignoring "internal" NBT and itemstack amount
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static boolean compareSafe(ItemStack is1, ItemStack is2) {
		
		ItemStack is1c = is1.clone(), is2c = is2.clone();
		is1c.setAmount(1);
		is2c.setAmount(1);
		
        String is1String = splitInternal(is1c.toString()), is2String = splitInternal(is2c.toString());

        return is1String.equals(is2String);
	}
	
	private static String splitInternal(String s) {
        if (s.contains(", internal=")) {
            String[] split = s.split(", internal=");
            if (split[1].split(", ", 2).length > 1)
            	s = split[0] + ", " + split[1].split(", ", 2)[1];
            else
            	s = split[0] + "}}";
        }
		return s;
	}
	
	public static ItemStack replaceLore(ItemStack item, String... replacables) {

		if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) return item;
		
		ArrayList<String> reps = new ArrayList<>(Arrays.asList(replacables));
		ArrayList<String> newLore = new ArrayList<String>();
		
		for (String s : ChatUtils.getLore(item)) {
			String message = s;
			for (int i = 0; i < reps.size(); i = i + 2) {
				message = message.replace(reps.get(i), reps.get(i + 1));
			}
			newLore.add(message);
		}
		
		ItemMeta meta = item.getItemMeta();
		ChatUtils.setLore(meta, newLore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	/**
	 * Creates ItemStack of material. Block-only materials (crops, stems) are mapped to their item form
	 */
	public static ItemStack getItemStackFromMaterial(Material m) {

		if (m == null)
			return new ItemStack(Material.AIR);
		else if (m == Material.CARROTS)
			return new ItemStack(Material.CARROT);
		else if (m == Material.POTATOES)
			return new ItemStack(Material.POTATO);
		else if (m == Material.BEETROOTS)
			return new ItemStack(Material.BEETROOT);
		else if (m == Material.PUMPKIN_STEM)
			return new ItemStack(Material.PUMPKIN_SEEDS);
		else if (m == Material.MELON_STEM)
			return new ItemStack(Material.MELON_SEEDS);
		else
			return new ItemStack(m);

	}
}
