package me.kryniowesegryderiusz.kgenerators.multiversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.generators.holograms.interfaces.IHologramProvider;
import me.kryniowesegryderiusz.kgenerators.generators.locations.objects.GeneratorLocation;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;
import me.kryniowesegryderiusz.kgenerators.settings.objects.HologramSettings;
import me.kryniowesegryderiusz.kgenerators.utils.ChatUtils;

/**
 * Holograms made of text display entities, so no hologram plugin is needed.
 * Every method is called on the main thread.
 */
public class HologramProvider_26_3 extends IHologramProvider implements Listener {

	/** Marks entities created by this provider, so leftovers can be found and removed */
	private static final String TAG = "kgenerators_hologram";

	private final Map<String, Entry> holograms = new HashMap<String, Entry>();
	private Style style;

	@Override
	public void start() {
		this.style = new Style(Main.getSettings().getHologram());

		/* Persistent holograms are saved with chunks, so remove those from the previous run */
		for (World world : Bukkit.getWorlds()) {
			for (TextDisplay display : world.getEntitiesByClass(TextDisplay.class)) {
				if (display.getScoreboardTags().contains(TAG))
					display.remove();
			}
		}

		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}

	/**
	 * Every hologram that is loaded from disk is a leftover, as holograms are created again on chunk load
	 */
	@EventHandler
	public void onEntitiesLoad(EntitiesLoadEvent e) {
		for (Entity entity : e.getEntities()) {
			if (entity instanceof TextDisplay && entity.getScoreboardTags().contains(TAG))
				entity.remove();
		}
	}

	@Override
	public void reload() {
		this.style = new Style(Main.getSettings().getHologram());
		for (Entry entry : holograms.values()) {
			if (!entry.display.isValid())
				continue;
			style.apply(entry.display);
			entry.display.teleport(style.locationOf(entry.gLocation));
		}
	}

	@Override
	public void removeAll() {
		for (Entry entry : holograms.values())
			entry.display.remove();
		holograms.clear();
	}

	@Override
	protected void providerCreateHologram(GeneratorLocation gLocation, ArrayList<String> lines) {
		Entry existing = holograms.get(gLocation.getHologramUUID());
		if (existing != null && existing.display.isValid())
			return;

		Location location = style.locationOf(gLocation);
		World world = location.getWorld();

		/* Spawning would load the chunk */
		if (world == null || !world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4))
			return;

		String text = String.join("\n", lines);
		TextDisplay display = world.spawn(location, TextDisplay.class, d -> {
			style.apply(d);
			d.text(ChatUtils.parse(text));
		});
		holograms.put(gLocation.getHologramUUID(), new Entry(gLocation, display, text));
	}

	@Override
	protected void providerUpdateHologram(GeneratorLocation gLocation, ArrayList<String> lines) {
		Entry entry = holograms.get(gLocation.getHologramUUID());
		if (entry == null || !entry.display.isValid()) {
			providerCreateHologram(gLocation, lines);
			return;
		}

		String text = String.join("\n", lines);
		if (!text.equals(entry.text)) {
			entry.display.text(ChatUtils.parse(text));
			entry.text = text;
		}
	}

	@Override
	protected void providerRemoveHologram(GeneratorLocation gLocation) {
		Entry entry = holograms.remove(gLocation.getHologramUUID());
		if (entry != null)
			entry.display.remove();
	}

	private static class Entry {
		private final GeneratorLocation gLocation;
		private final TextDisplay display;
		private String text;

		private Entry(GeneratorLocation gLocation, TextDisplay display, String text) {
			this.gLocation = gLocation;
			this.display = display;
			this.text = text;
		}
	}

	/**
	 * Hologram settings translated to display entity values
	 */
	private static class Style {
		private final double offsetX, offsetY, offsetZ;
		private final Display.Billboard billboard;
		private final TextDisplay.TextAlignment alignment;
		private final boolean shadowed, persistent;
		private final float viewRange;
		private final Color background;
		private final Transformation transformation;

		private Style(HologramSettings settings) {
			this.offsetX = settings.getOffsetX();
			this.offsetY = settings.getOffsetY();
			this.offsetZ = settings.getOffsetZ();
			this.billboard = parse(Display.Billboard.class, settings.getBillboard(), Display.Billboard.CENTER, "billboard");
			this.alignment = parse(TextDisplay.TextAlignment.class, settings.getAlignment(), TextDisplay.TextAlignment.CENTER, "alignment");
			this.shadowed = settings.isShadowed();
			this.persistent = settings.isPersistent();
			this.viewRange = (float) settings.getViewRange();
			this.background = Color.fromARGB(settings.getBackgroundAlpha(), settings.getBackgroundRed(),
					settings.getBackgroundGreen(), settings.getBackgroundBlue());
			float scale = (float) settings.getScale();
			this.transformation = new Transformation(new Vector3f(), new AxisAngle4f(), new Vector3f(scale, scale, scale),
					new AxisAngle4f());
		}

		private void apply(TextDisplay display) {
			display.setBillboard(billboard);
			display.setAlignment(alignment);
			display.setShadowed(shadowed);
			display.setViewRange(viewRange);
			display.setDefaultBackground(false);
			display.setBackgroundColor(background);
			display.setTransformation(transformation);
			display.setPersistent(persistent);
			display.addScoreboardTag(TAG);
		}

		private Location locationOf(GeneratorLocation gLocation) {
			return gLocation.getHologramBaseLocation().add(offsetX, offsetY, offsetZ);
		}

		private static <T extends Enum<T>> T parse(Class<T> type, String value, T fallback, String option) {
			try {
				return Enum.valueOf(type, value.trim().toUpperCase(Locale.ROOT));
			} catch (IllegalArgumentException e) {
				Logger.error("Config file: hologram." + option + " " + value + " is not valid! Possible values: "
						+ Arrays.toString(type.getEnumConstants()) + ". Using " + fallback);
				return fallback;
			}
		}
	}
}
