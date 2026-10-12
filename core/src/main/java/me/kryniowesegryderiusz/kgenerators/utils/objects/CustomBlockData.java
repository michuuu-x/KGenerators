package me.kryniowesegryderiusz.kgenerators.utils.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.utils.ItemUtils;

public class CustomBlockData {

	@Getter Material material;

	public CustomBlockData(Material material){
		this.material = material;
	}

	public void setBlock(Location location) {
		Main.getMultiVersion().getBlocksUtils().setBlock(location, material);
	}

	public void setBlock(Location location, boolean physics) {
		Main.getMultiVersion().getBlocksUtils().setBlock(location, material, physics);
	}

	public ItemStack getItem() {
		return ItemUtils.getItemStackFromMaterial(material);
	}

	public String toString() {
		return "Material: " + this.material.name();
	}

	/**
	 * Checks material
	 * @param item
	 * @return true if materials are same
	 */
	public boolean isSimilar(ItemStack is) {
		return is.getType() == material;
	}

	public static CustomBlockData load(String configString, String place) {
		if (configString.contains(":")) {
			String[] splitted = configString.split(":");
			if (splitted[0].contains("customhead")) {
				return new HeadCustomBlockData(Material.PLAYER_HEAD, splitted[1]);
			}
		}

		return new CustomBlockData(ItemUtils.getMaterial(configString, place+" CustomBlockData:", true));
		
	}
}
