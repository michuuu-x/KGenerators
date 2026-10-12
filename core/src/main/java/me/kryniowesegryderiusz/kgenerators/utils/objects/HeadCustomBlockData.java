package me.kryniowesegryderiusz.kgenerators.utils.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.kryniowesegryderiusz.kgenerators.Main;

public class HeadCustomBlockData extends CustomBlockData  {

	private String skullBase64;

	public HeadCustomBlockData(Material material, String skullBase64) {
		super(material);
		this.skullBase64 = skullBase64;
	}
	
	@Override
	public void setBlock(Location location) {
		Main.getMultiVersion().getSkullUtils().blockWithBase64(location.getBlock(), skullBase64);
	}
	
	@Override
	public void setBlock(Location location, boolean phyciss) {
		setBlock(location);
	}
	
	@Override
	public ItemStack getItem() {
		return Main.getMultiVersion().getSkullUtils().itemFromBase64(skullBase64);
	}
	
	@Override
	public String toString() {
		return "Player Head: " + this.skullBase64;
	}
}	
