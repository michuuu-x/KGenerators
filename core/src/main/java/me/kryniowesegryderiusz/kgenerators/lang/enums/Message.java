package me.kryniowesegryderiusz.kgenerators.lang.enums;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.lang.interfaces.IMessage;

public enum Message implements IMessage {
	PREFIX("prefix", "<dark_gray>[<gold>KGenerators<dark_gray>] "),
	
	GENERATORS_ANY_OWNER_NONE("generators.any.owner-none", "<red>None"),
	GENERATORS_ANY_DISABLED_WORLD_SPECIFIC("generators.any.disabled-specific", "<red><generator> is disabled in this world!"),
	GENERATORS_ANY_REPAIRED("generators.any.repaired", "<green>Generator succesfully repaired!"),
	GENERATORS_ANY_NO_LONGER_THERE("generators.any.no-longer-there", "<red>Generator is no longer there!"),
	GENERATORS_ANY_OBJECT_AMOUNT("generators.any.object-amount", "<amount> x <object>"),
	
	GENERATORS_DIGGING_CANT_HERE("generators.digging.cant-here", "<red>You can't dig generated blocks here!"),
	GENERATORS_DIGGING_ONLY_GEN("generators.digging.only-gen", "<red>You can only dig generated blocks here!"),
	GENERATORS_DIGGING_NO_PERMISSION("generators.digging.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to mine <generator><red>!"),
	
	GENERATORS_CRAFTING_CANT_USE("generators.crafting.cant-use", "<red>You cant craft anything from generator!"),
	GENERATORS_CRAFTING_NO_PERMISSION("generators.crafting.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to craft <generator><red>!"),
	
	GENERATORS_TIME_LEFT_OUTPUT("generators.timeleft.output", "<green>This generator will be regenerated in <yellow><time>"),
	
	GENERATORS_ACTION_CANT_HERE("generators.action.cant-here", "<red>You cant interact with generator here!"),
	
	GENERATORS_PICK_UP_SUCCESFULL("generators.pick-up.successful", "<yellow><generator> <green>picked up"),
	GENERATORS_PICK_UP_CANT_HERE("generators.pick-up.cant-here", "<red>You cant pick up generator here!"),
	GENERATORS_PICK_UP_NO_PERMISSION("generators.pick-up.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to pick up that type generator"),
	
	GENERATORS_PLACE_CANT_HERE("generators.place.cant-here", "<red>You cant place generator here!"),
	GENERATORS_PLACE_CANT_PLACE_BLOCK("generators.place.cant-place-block", "<red>You cant place any block in generator's place"),
	GENERATORS_PLACE_CANT_PLACE_DOUBLE_BELOW_BLOCK("generators.place.cant-place-double-below-block", "<red>You cant place double generator below any block!"),
	GENERATORS_PLACE_CANT_PLACE_DOUBLE_BELOW_GENERATOR("generators.place.cant-place-double-below-generator", "<red>You cant place double generator below other generator!"),
	GENERATORS_PLACE_NO_PERMISSION("generators.place.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to place that type generator"),
	
	GENERATORS_LIMITS_CANT_MORE("generators.limits.cant-place-more-type", "<red>You cant place more than <gold><number> <generator> <red>generators, because of <gold><limit><red>!"),
	GENERATORS_LIMITS_CANT_PICK_UP("generators.limits.cant-pick-up", "<red>You cant pick up generator owned by <gold><owner><red>!"),
	GENERATORS_LIMITS_CANT_USE("generators.limits.cant-use", "<red>You cant use generator owned by <gold><owner><red>!"),
	
	GENERATOR_PROTECTION_CANT_CHANGE_GENERATOR_ITEM("generators.protection.cant-change-generator-item", "<red>You cant change generator item!"),
	
	GENERATOR_MENU_CANT_OPEN_HERE("generators.menus.cant-open-here", "<red>You cant open this menu here!"),
	
	GENERATOR_CHUNK_NOT_LOADED_YET("generators.chunks.not-loaded-yet", "<red>Database hasnt loaded this chunk yet! Wait a while!"),
	
	VAULT_ECONOMY_NOT_AVAILABLE("economy.no-economy-avaible", "<red>There is no economy system avaible!"),
	VAULT_ECONOMY_NOT_ENOUGH_MONEY("economy.not-enough-money", "<red>You dont have enough money! You need <gold><cost><red>!"),
	
	UPGRADES_UPGRADED("upgrades.upgraded", "<green>You've upgraded <yellow><number> <green>generators for <yellow><costs><green>!"),
	UPGRADES_COST_NOT_FULFILLED("upgrades.gui-upgraded", "<red>You dont have <dark_red><cost><red>!"),
	UPGRADES_COSTS_SEPARATOR("upgrades.separator", ", "),
	UPGRADES_NO_NEXT_LEVEL("upgrades.no-next-level", "<red>This is maximum level of that generator!"),
	UPGRADES_NO_PERMISSION("upgrades.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to upgrade generator!"),
	UPGRADES_NOT_A_GENERATOR("upgrades.not-a-generator", "<red>You dont have generator in hand!"),
	UPGRADES_COST_EXP("upgrades.costs.exp","xp"),
	UPGRADES_COST_EXP_LEVELS("upgrades.costs.exp-levels","xp levels"),
	UPGRADES_COST_PLAYERPOINTS("upgrades.costs.player-points-hook","player points"),
	
	HOOKS_EXPLODE_PICKAXE_CANNOT_USE_ON_DOUBLE("hooks.explode-pickaxe.cannot-use-on-double", "<red>You cannot use explosion pickaxe on type double generator!"),
	
	DATABASE_ERROR("database.error", "<red>Database error occured, please contact with administration!"),
	
	COMMANDS_ANY_WRONG("commands.any.wrong", "<red>Wrong command! Type /kgenerators for help"),
	COMMANDS_ANY_NO_PERMISSION("commands.any.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to use KGenerators commands"),
	COMMANDS_ANY_PLAYER_NOT_ONLINE("commands.any.player-not-online", "<red>Player is not online"),
	COMMANDS_ANY_PLAYER_DOESNT_EXIST("commands.any.player-doesnt-exist", "<red>Player doesnt exist"),
	COMMANDS_ANY_GENERATOR_DOESNT_EXIST("commands.any.generator-doesnt-exist", "<red>That generator doesn't exist"),
	COMMANDS_ANY_MENU_DOESNT_EXIST("commands.any.menu-doesnt-exist", "<red>That menu doesn't exist"),
	COMMANDS_ANY_ONLY_CONSOLE("commands.any.console-only", "<red>This command can be executed only from console!"),
	COMMANDS_ANY_NOT_A_INTEGER("commands.any.not-a-integer", "<red>'<variable>' should be integer!"),
	COMMANDS_ANY_NOT_A_FLOATING_POINT_NUMBER("commands.any.not-a-floating-point-number", "<red>'<variable>' should be floating point number!"),
	COMMANDS_ANY_WORLD_DOESNT_EXIST("commands.any.world-doesnt-exist", "<red>World '<world>' doesn't exist"),
	
	COMMANDS_LIST_HEADER("commands.list.header", "<green>Generators:"),
	COMMANDS_LIST_LIST("commands.list.list", "<dark_gray>- <generator> <dark_gray>(<gray>ID: <generatorID><dark_gray>)"),
	COMMANDS_LIST_NO_PERMISSION("commands.list.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to list generators"),
	COMMANDS_LIST_HELP("commands.list.help", "List all generators"),
	
	COMMANDS_MENU_OPENED_OTHERS("commands.menu.opened-others", "<green>Opened <yellow><generator> <menu> menu <green>for <yellow><player>"),
	COMMANDS_MENU_ERROR("commands.menu.error", "<red>An error occured, while using menus! Contact with administrator!"),
	COMMANDS_MENU_NO_PERMISSION("commands.menu.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to open menu!"),
	COMMANDS_MENU_NO_PERMISSION_OTHERS("commands.menu.no-permission-others", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to open menu for other players!"),
	COMMANDS_MENU_HELP("commands.menu.help", "Opens menu"),
	
	COMMANDS_GIVE_GENERATOR_GIVEN("commands.give.generator-given", "<yellow><amount>x <green><generator> <green>was given to <yellow><player>"),
	COMMANDS_GIVE_GENERATOR_RECIEVED("commands.give.generator-recieved", "<green>You recieved <yellow><amount>x <green><generator><green>!"),
	COMMANDS_GIVE_USAGE("commands.give.usage", "<red>Usage: <yellow>/kgenerators give <player> <generator> [amount]"),
	COMMANDS_GIVE_NO_PERMISSION("commands.give.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to give generators"),
	COMMANDS_GIVE_HELP("commands.give.help", "Give generator to player"),
	
	COMMANDS_GET_ALL_RECIEVED("commands.getall.recieved", "<green>Generators recieved"),
	COMMANDS_GET_ALL_NO_PERMISSION("commands.getall.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to get all generators"),
	COMMANDS_GET_ALL_HELP("commands.getall.help", "Get all generators"),
	
	COMMANDS_LIMITS_NO_PERMISSION("commands.limits.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to open limits menu!"),
	COMMANDS_LIMITS_HELP("commands.limits.help", "Check limits"),
	
	COMMANDS_ACTIONS_NO_PERMISSION("commands.actions.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to check actions!"),
	COMMANDS_ACTIONS_HELP("commands.actions.help", "Check possible actions"),
	COMMANDS_ACTIONS_HEADER("commands.actions.header", "<green>Possible actions for generators:"),
	COMMANDS_ACTIONS_LIST("commands.actions.list", "<gold><action> <dark_gray>- <yellow><sneak><mode><item>"),
	COMMANDS_ACTIONS_SNEAK("commands.actions.sneak", "sneak and "),
	COMMANDS_ACTIONS_BREAK("commands.actions.break", "break block"),
	COMMANDS_ACTIONS_LEFT_CLICK("commands.actions.left-click", "left click"),
	COMMANDS_ACTIONS_RIGHT_CLICK("commands.actions.right-click", "right click"),
	COMMANDS_ACTIONS_ITEM("commands.actions.item", " with <itemname>"),
	COMMANDS_ACTIONS_PICK_UP("commands.actions.pick-up", "Pick up"),
	COMMANDS_ACTIONS_OPEN_GUI("commands.actions.gui", "Open GUI"),
	COMMANDS_ACTIONS_TIME_LEFT("commands.actions.time-left", "Time left"),
	COMMANDS_ACTIONS_UPGRADE("commands.actions.upgrade", "Upgrade"),
	
	COMMANDS_TIME_LEFT_NO_GENERATOR("commands.timeleft.no-generator", "<red>You are not looking at generator waiting for regeneration!"),
	COMMANDS_TIME_LEFT_NO_PERMISSION("commands.timeleft.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to check generation remaining time"),
	COMMANDS_TIME_LEFT_HELP("commands.timeleft.help", "Check time remaining to regeneration"),
	COMMANDS_TIME_LEFT_FORMAT_SEC("commands.timeleft.format.seconds", "s"),
	COMMANDS_TIME_LEFT_FORMAT_MIN("commands.timeleft.format.minutes", "m"),
	COMMANDS_TIME_LEFT_FORMAT_HOUR("commands.timeleft.format.hours", "h"),
	COMMANDS_TIME_LEFT_FORMAT_DAY("commands.timeleft.format.days", "d"),
	COMMANDS_TIME_LEFT_FORMAT_NONE("commands.timeleft.format.none", "None"),
	
	COMMANDS_SPAWN_DONE_COORDINATES("commands.spawn.coordinates.done", "<green>Generator spawned! New generator: <yellow><location_info>"),
	COMMANDS_SPAWN_DONE_WORLDEDIT("commands.spawn.worldedit.done", "<green>Spawned <yellow><amount> <green>generators!"),
	COMMANDS_SPAWN_USAGE_COORDINATES("commands.spawn.coordinates.usage", "<red>Usage: <yellow>/kgenerators spawn <world> <x> <y> <z> <generator> [owner]"),
	COMMANDS_SPAWN_USAGE_WORLDEDIT("commands.spawn.worldedit.usage", "<red>Usage: <yellow>/kgenerators spawn worldedit <generator> [owner]"),
	COMMANDS_SPAWN_NO_PERMISSION("commands.spawn.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to spawn generator!"),
	COMMANDS_SPAWN_HELP("commands.spawn.help", "Spawns generator at location"),
	
	COMMANDS_REMOVE_DONE("commands.remove.done", "<green>Removed <amount> generators!"),
	COMMANDS_REMOVE_USAGE_WORLDEDIT("commands.remove.usage.worldedit", "<red>Usage: <yellow>/kgenerators remove worldedit"),
	COMMANDS_REMOVE_USAGE_OWNER("commands.remove.usage.owner", "<red>Usage: <yellow>/kgenerators remove owner <owner>"),
	COMMANDS_REMOVE_NO_PERMISSION("commands.remove.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to remove generators!"),
	COMMANDS_REMOVE_HELP("commands.remove.help", "Removes generators"),
	COMMANDS_REMOVE_WORLDEDIT_NOT_FOUND("commands.remove.worldedit-not-found", "<red>Selection is not complete!"),
	COMMANDS_REMOVE_WORLDEDIT_WRONG_SELECTION("commands.remove.worldedit-wrong-selection", "<red>Selection is not complete!"),
	
	COMMANDS_UPGRADE_HELP("commands.upgrade.help", "Upgrade generator"),
	
	COMMANDS_RELOAD_DONE("commands.reload.done", "<green>Plugin configuration reloaded! Check console or log.txt in plugin''s directory for possible errors!"),
	COMMANDS_RELOAD_NO_PERMISSION("commands.reload.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to reload plugin"),
	COMMANDS_RELOAD_HELP("commands.reload.help", "Plugin configuration reload"),
	
	COMMANDS_DEBUG_DONE("commands.debug.done", "<green>Debug file saved: <yellow><file>"),
	COMMANDS_DEBUG_ERROR("commands.debug.error", "<red>An error occured, while making debug output!"),
	COMMANDS_DEBUG_NO_PERMISSION("commands.debug.no-permission", "<red>You dont have permission <dark_gray>(<gray><permission><dark_gray>)<red> to paste debug"),
	
	COMMANDS_HELP_LABEL("commands.help.label", "<gold><bold>KGenerators <reset><green>Commands help:"),
	COMMANDS_HELP_FORMAT("commands.help.format", "<yellow>/kgenerators <subcommand> <gray><help>"),
	;
	
	@Getter
	private String key;
	@Getter
	private String message;
	
	Message(String key, String message) {
		this.key = key;
		this.message = message;
	}

	@Override
	public Enum<?> getEnum() {
		return this;
	}
}
