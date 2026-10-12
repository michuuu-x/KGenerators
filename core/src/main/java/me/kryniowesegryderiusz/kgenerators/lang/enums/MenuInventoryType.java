package me.kryniowesegryderiusz.kgenerators.lang.enums;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.gui.objects.MenuInventory;
import me.kryniowesegryderiusz.kgenerators.lang.interfaces.IMenuInventoryType;

public enum MenuInventoryType implements IMenuInventoryType
{
	GENERATOR("generator", new MenuInventory("<blue><generator_name>", 45)),
	
	MAIN("main", new MenuInventory("<blue>Generators", 45)),
	GENERATED_OBJECTS("generated-objects", new MenuInventory("<blue>Generates:", 45)),
	RECIPE("recipe", new MenuInventory("<blue>Recipe", 45)),
	UPGRADE("upgrade", new MenuInventory("<blue>Upgrade", 45)),
	LIMITS("limits", new MenuInventory("<blue>Limits", 45)),
	;

	@Getter
	String key;
	@Getter
	MenuInventory menuInventory;
	MenuInventoryType(String key, MenuInventory menuInventory) {
		this.key = key;
		this.menuInventory = menuInventory;
	}
}