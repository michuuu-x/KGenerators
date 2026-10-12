package me.kryniowesegryderiusz.kgenerators.lang.enums;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.lang.interfaces.IMenuItemAdditionalLines;
import me.kryniowesegryderiusz.kgenerators.lang.objects.StringContent;

public enum MenuItemAdditionalLines implements IMenuItemAdditionalLines
{
	RECIPE("recipe.menu-how-to", new StringContent("", "<gold>Right click:", "<yellow>check the recipe")),
	UPGRADE("upgrades.menu-how-to", new StringContent("", "<gold>Right click:", "<yellow>check how to", "<yellow>get from upgrade")),
	UPGRADE_COST("upgrades.upgrade-cost", new StringContent("<dark_gray>- <gray><cost>")),
	GENERATED_OBJECT_TYPE_ITEM("generated-object-type.item", new StringContent("<gold>Generates: <yellow>item")),
	GENERATED_OBJECT_TYPE_BLOCK("generated-object-type.block", new StringContent("<gold>Generates: <yellow>block")),
	GENERATED_OBJECT_TYPE_ENTITY("generated-object-type.entity", new StringContent("<gold>Generates: <yellow>entity")),
	GENERATED_OBJECT_TYPE_CUSTOM_DROPS("generated-object-type.custom-drops", new StringContent("<gold>Generates: <yellow>custom drops")),
	GENERATED_OBJECT_CUSTOM_DROPS("generated-object-custom-drops.custom-drops", new StringContent("", "<gold>Custom drops:", "<dark_gray>- <gold>Drops from: <yellow><custom_drops_source>", "<custom_drops_money>", "<custom_drops_exp>", "<custom_drops_commands>")),
	GENERATED_OBJECT_CUSTOM_DROPS_MONEY("generated-object-custom-drops.money", new StringContent("<dark_gray>- <gold>Money: <yellow><money>")),
	GENERATED_OBJECT_CUSTOM_DROPS_EXP("generated-object-custom-drops.exp", new StringContent("<dark_gray>- <gold>Exp: <yellow><exp> xp")),
	GENERATED_OBJECT_CUSTOM_DROPS_COMMAND("generated-object-custom-drops.command", new StringContent("<dark_gray>- <gold>Command: <yellow><command>")),
	LIMITS_GENERATOR_LIST("limits.generator-list", new StringContent("<dark_gray>- <yellow><generator_name>")),
	LIMITS_ALL_GENERATORS("limits.all-generators", new StringContent("<yellow>all generators")),
	LIMITS_ONLY_OWNER_PICK_UP("limits.only-owner-pick-up", new StringContent("<red>Only owners could pick up them.")),
	LIMITS_ONLY_OWNER_USE("limits.only-owner-use", new StringContent("<red>Only owners could use them.")),
	;

	@Getter
	String key;
	@Getter
	StringContent stringContent;
	MenuItemAdditionalLines(String key, StringContent stringContent) {
		this.key = key;
		this.stringContent = stringContent;
	}
}