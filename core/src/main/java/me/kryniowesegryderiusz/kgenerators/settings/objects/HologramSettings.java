package me.kryniowesegryderiusz.kgenerators.settings.objects;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;
import me.kryniowesegryderiusz.kgenerators.utils.immutable.Config;

/**
 * Options of built in holograms (text display entities), loaded from "hologram" section of config.yml.
 * Billboard and alignment are kept as text, as they are parsed by the multiversion implementation.
 */
@Getter
public class HologramSettings {

	private static final String PATH = "hologram.";

	private boolean enabled = true;
	private double offsetX = 0.0;
	private double offsetY = 0.2;
	private double offsetZ = 0.0;
	private String billboard = "CENTER";
	private String alignment = "CENTER";
	private boolean shadowed = false;
	private boolean persistent = false;
	private double viewRange = 1.0;
	private int backgroundRed = 0;
	private int backgroundGreen = 0;
	private int backgroundBlue = 0;
	private int backgroundAlpha = 64;
	private double scale = 1.0;

	/**
	 * Default settings
	 */
	public HologramSettings() {
	}

	public HologramSettings(Config config) {
		this.enabled = config.getBoolean(PATH + "enabled", this.enabled);
		this.offsetX = config.getDouble(PATH + "position-offset.x", this.offsetX);
		this.offsetY = config.getDouble(PATH + "position-offset.y", this.offsetY);
		this.offsetZ = config.getDouble(PATH + "position-offset.z", this.offsetZ);
		this.billboard = config.getString(PATH + "billboard", this.billboard);
		this.alignment = config.getString(PATH + "alignment", this.alignment);
		this.shadowed = config.getBoolean(PATH + "shadowed", this.shadowed);
		this.persistent = config.getBoolean(PATH + "persistent", this.persistent);
		this.viewRange = config.getDouble(PATH + "view-range", this.viewRange);
		this.backgroundRed = colorComponent(config, "red", this.backgroundRed);
		this.backgroundGreen = colorComponent(config, "green", this.backgroundGreen);
		this.backgroundBlue = colorComponent(config, "blue", this.backgroundBlue);
		this.backgroundAlpha = colorComponent(config, "alpha", this.backgroundAlpha);
		this.scale = config.getDouble(PATH + "scale", this.scale);

		if (this.scale <= 0) {
			Logger.error("Config file: hologram.scale must be greater than 0! Using 1.0");
			this.scale = 1.0;
		}
	}

	private static int colorComponent(Config config, String name, int def) {
		int value = config.getInt(PATH + "background-color." + name, def);
		if (value < 0 || value > 255) {
			Logger.error("Config file: hologram.background-color." + name + " must be between 0 and 255! Using " + def);
			return def;
		}
		return value;
	}
}
