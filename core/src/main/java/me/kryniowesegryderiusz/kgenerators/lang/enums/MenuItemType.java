package me.kryniowesegryderiusz.kgenerators.lang.enums;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.gui.objects.MenuItem;
import me.kryniowesegryderiusz.kgenerators.lang.interfaces.IMenuItemType;

public enum MenuItemType implements IMenuItemType
{
	GENERATOR_MENU_FILLER(MenuInventoryType.GENERATOR, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44")),
	GENERATOR_MENU_TIME_LEFT(MenuInventoryType.GENERATOR,"time-left", new MenuItem("CLOCK", "<green>Time left for regeneration", false, "21" , "<green><time>")),
	GENERATOR_MENU_OWNER(MenuInventoryType.GENERATOR,"owner", new MenuItem("BOOK", "<green>Generator owner", false, "22", "<yellow><owner>")),
	GENERATOR_MENU_PICK_UP(MenuInventoryType.GENERATOR,"pick-up", new MenuItem("BARRIER", "<green>Pick up", false, "23", "<yellow>Click here to pick up generator")),
	GENERATOR_MENU_QUIT(MenuInventoryType.GENERATOR,"quit", new  MenuItem("ARROW", "<red>Quit", false, "40", "<gold>Quit menu")),
	GENERATOR_MENU_RESET(MenuInventoryType.GENERATOR,"reset", new  MenuItem("MUSIC_DISC_11", "<red>Reset generator", false, "13", "<gold>This generator seems to be corrupted!", "<gold>Click here to repair it")),
	GENERATOR_MENU_UPGRADE(MenuInventoryType.GENERATOR,"upgrade", new  MenuItem("EXPERIENCE_BOTTLE", "<red>Upgrade generator", false, "31", "<gold>Click here to upgrade this generator!", "", "<gold>Next stage: <yellow><next_generator>", "<gold>Costs:" ,"<costs>")),
	GENERATOR_MENU_UPGRADE_MAXED(MenuInventoryType.GENERATOR,"upgrade-maxed", new  MenuItem("EXPERIENCE_BOTTLE", "<red>Maximum generator level", false, "31", "<gold>This generator doesnt have", "<gold>any further upgrades!","<green>You've maxed it!")),
	
	MAIN_MENU_FILLER(MenuInventoryType.MAIN, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44")),
	MAIN_MENU_GENERATOR(MenuInventoryType.MAIN,"generator", new MenuItem("<generator>", "<green><generator_name>", false, "9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35" , "<gold>Left click:", "<yellow>Check what it generates")),
	MAIN_MENU_LIMITS(MenuInventoryType.MAIN,"limits", new MenuItem("PLAYER_HEAD", "<green>Limits", false, "8", "<yellow>Click here to check limits")),
	MAIN_MENU_QUIT(MenuInventoryType.MAIN,"quit", new  MenuItem("BARRIER", "<red>Quit", false, "40", "<gold>Quit menu")),
	MAIN_MENU_PAGE_PREVIOUS(MenuInventoryType.MAIN,"page.previous", new  MenuItem("ARROW", "<red>Previous page", false, "38", "<gold>Click to back", "<gold>to previous page")),
	MAIN_MENU_PAGE_NEXT(MenuInventoryType.MAIN,"page.next", new  MenuItem("ARROW", "<red>Next page", false, "42", "<gold>Click to go", "<gold>to next page")),
	
	GENERATED_OBJECTS_MENU_FILLER(MenuInventoryType.GENERATED_OBJECTS, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44")),
	GENERATED_OBJECTS_MENU_GENERATED_OBJECT(MenuInventoryType.GENERATED_OBJECTS, "generated-object", new MenuItem("<generated_object>", "<green><generated_object_name>", false, "9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35", "<generated_object_lore>", "", "<generated_object_type>", "<gold>Chance: <yellow><chance>%","<generated_object_custom_drops>")),
	GENERATED_OBJECTS_MENU_BACK(MenuInventoryType.GENERATED_OBJECTS,"back", new  MenuItem("ARROW", "<red>Back", false, "40", "<gold>Get back to", "<red>generators list")),
	
	RECIPE_MENU_FILLER(MenuInventoryType.RECIPE, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44")),
	RECIPE_MENU_INGREDIENS(MenuInventoryType.RECIPE,"ingredients", new MenuItem("<block>", "<green><block_name>", false, "10,11,12,19,20,21,28,29,30")),
	RECIPE_MENU_RESULT(MenuInventoryType.RECIPE,"result", new MenuItem("<generator>", "<green><generator_name>", false, "25")),
	RECIPE_MENU_MARKER(MenuInventoryType.RECIPE,"marker", new MenuItem("CRAFTING_TABLE", "<green>Crafting", false, "14,23,32")),
	RECIPE_MENU_BACK(MenuInventoryType.RECIPE,"back", new  MenuItem("ARROW", "<red>Back", false, "40", "<gold>Get back to", "<red>generators list")),
	
	UPGRADE_MENU_FILLER(MenuInventoryType.UPGRADE, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44")),
	UPGRADE_MENU_INGREDIENT(MenuInventoryType.UPGRADE,"ingredient", new MenuItem("<generator>", "<green><generator_name>", false, "20")),
	UPGRADE_MENU_RESULT(MenuInventoryType.UPGRADE,"result", new MenuItem("<generator>", "<green><generator_name>", false, "24")),
	UPGRADE_MENU_MARKER(MenuInventoryType.UPGRADE,"marker", new MenuItem("EXPERIENCE_BOTTLE", "<green>Upgrade", false, "13,22,31", "<gold>To upgrade get", "<gold>generator and type", "<yellow>/kgenerators upgrade", "<gold>or use generator menu", "", "<gold>Costs:", "<costs>")),
	UPGRADE_MENU_BACK(MenuInventoryType.UPGRADE,"back", new  MenuItem("ARROW", "<red>Back", false, "40", "<gold>Get back to", "<red>generators list")),
	
	LIMITS_MENU_FILLER(MenuInventoryType.LIMITS, "filler", new MenuItem("GRAY_STAINED_GLASS_PANE", "<reset>", false, "0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44")),
	LIMITS_MENU_LIMIT(MenuInventoryType.LIMITS,"limit", new MenuItem("<limit_display_item>", "<green><limit_name>", false, "9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26" , "<gold>You've placed <yellow><amount><gold>/<yellow><limit><gold> of", "<generators_list>", "<is_only_owner_use>", "<is_only_owner_pick_up>")),
	LIMITS_MENU_BACK(MenuInventoryType.LIMITS,"back", new  MenuItem("ARROW", "<red>Back", false, "40", "<gold>Get back to", "<red>generators list")),

	;
	@Getter
	MenuInventoryType menuInventory;
	String subKey;
	@Getter
	MenuItem menuItem;
	
	MenuItemType(MenuInventoryType menuInventory, String subKey, MenuItem menuItem) {
		this.menuInventory = menuInventory;
		this.subKey = subKey;
		this.menuItem = menuItem;
	}
	
	public String getKey()
	{
		return this.menuInventory.getKey() + "." + this.subKey;
	}
}