package me.kryniowesegryderiusz.kgenerators.settings.objects;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;

import me.kryniowesegryderiusz.kgenerators.logger.Logger;
import me.kryniowesegryderiusz.kgenerators.utils.immutable.Config;

public class Sound {

	org.bukkit.Sound sound;
	float volume = 0.2f;
	float pitch = 0.0f;

	/**
	 * Constructor loading sound from config
	 *
	 * @param cfg
	 * @param path to sound
	 */
	public Sound(Config cfg, String path) {
		if (cfg.contains(path + ".sound")) {
			String name = cfg.getString(path + ".sound");
			if (!name.toLowerCase().equals("none")) {
				org.bukkit.Sound found = find(name);
				if (found == null) {
					Logger.error("Config file: Sound " + name + " doesnt exist! It'll not work!");
					return;
				}
				this.sound = found;

				if (cfg.contains(path + ".volume"))
					this.volume = (float) cfg.getDouble(path + ".volume");
				if (cfg.contains(path + ".pitch"))
					this.pitch = (float) cfg.getDouble(path + ".pitch");
			}
		}
	}

	public Sound(org.bukkit.Sound sound) {
		this.sound = sound;
	}

	public void play(Player p) {
		if (this.sound != null)
			p.playSound(p.getLocation(), this.sound, this.volume, this.pitch);
	}

	/**
	 * Finds sound by its enum-like name (ENTITY_PLAYER_LEVELUP) or key (entity.player.levelup)
	 */
	private static org.bukkit.Sound find(String name) {
		String wanted = normalize(name);
		for (org.bukkit.Sound s : Registry.SOUNDS) {
			NamespacedKey key = Registry.SOUNDS.getKey(s);
			if (key != null && normalize(key.getKey()).equals(wanted))
				return s;
		}
		return null;
	}

	private static String normalize(String name) {
		return name.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
	}

}
