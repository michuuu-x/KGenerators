package me.kryniowesegryderiusz.kgenerators.generators.holograms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.addons.events.HologramReplaceLinesEvent;
import me.kryniowesegryderiusz.kgenerators.generators.generator.objects.Generator;
import me.kryniowesegryderiusz.kgenerators.generators.holograms.interfaces.IHologramProvider;
import me.kryniowesegryderiusz.kgenerators.generators.locations.objects.GeneratorLocation;
import me.kryniowesegryderiusz.kgenerators.lang.Lang;
import me.kryniowesegryderiusz.kgenerators.lang.enums.HologramText;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;

public class HologramsManager {

	@Getter
	private IHologramProvider hologramProvider;

	private List<GeneratorLocation> holograms = Collections.synchronizedList(new ArrayList<GeneratorLocation>());

	public HologramsManager() {

		Logger.debugPluginLoad("HologramsManager: Setting up manager");

		if (Main.getSettings().getHologram().isEnabled())
			hologramProvider = Main.getMultiVersion().getHologramProvider();

		if (hologramProvider == null) {
			for (Map.Entry<String, Generator> e : Main.getGenerators().getEntrySet()) {
				if ((e.getValue()).isHologram())
					Logger.warn("Holograms: Generator " + e.getKey()
							+ " has enabled holograms, but holograms are disabled or unavailable! Holograms wont work!");
			}
			return;
		}

		hologramProvider.start();
		Logger.debugPluginLoad("Holograms: Enabling " + hologramProvider.getClass().getSimpleName());

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {

				try {
					synchronized (holograms) {
						Iterator<GeneratorLocation> iter = holograms.iterator();
						while (iter.hasNext()) {
							try {
								GeneratorLocation gLocation = iter.next();

								if (Main.getPlacedGenerators().isLoaded(gLocation) && Main.getSchedules().timeLeft(gLocation) > 0) {
									hologramProvider.updateHologram(gLocation,
											Main.getHolograms().getHologramRemainingTimeLines(gLocation));
								} else {
									try {
										iter.remove();
									} finally {}
								}
							} catch (ConcurrentModificationException eme) {
								Logger.debugSchedulesManager(eme);
							}

						}
					}

		    	} catch (Exception e) {
		    		Logger.error("Holograms: An error occured at holograms task");
		    		Logger.error(e);
		    	}

			}
		}, 0L, Main.getSettings().getHologramUpdateFrequency() * 1L);
	}

	public void reload() {
		if (hologramProvider != null)
			hologramProvider.reload();
	}

	public void onDisable() {
		if (hologramProvider != null)
			hologramProvider.removeAll();
		holograms.clear();
	}

	public void createRemainingTimeHologram(GeneratorLocation gLocation) {
		if (Bukkit.isPrimaryThread()) {
			this.createHologram(gLocation, this.getHologramRemainingTimeLines(gLocation));
		} else {
			Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> {
				this.createHologram(gLocation, this.getHologramRemainingTimeLines(gLocation));
			});
		}
	}

	private void createHologram(GeneratorLocation gLocation, ArrayList<String> lines) {
		if (hologramProvider == null)
			return;
		if (gLocation == null)
			return;
		hologramProvider.createHologram(gLocation, lines);
		if (!holograms.contains(gLocation))
			holograms.add(gLocation);
	}

	public void removeHologram(GeneratorLocation gLocation) {
		if (hologramProvider == null)
			return;
		hologramProvider.removeHologram(gLocation);
		if (holograms.contains(gLocation))
			holograms.remove(gLocation);
	}

	public ArrayList<String> getHologramRemainingTimeLines(GeneratorLocation gLocation) {

		HologramReplaceLinesEvent e = new HologramReplaceLinesEvent(gLocation, HologramText.REMAINING_TIME);

		Main.getInstance().getServer().getPluginManager().callEvent(e);

		ArrayList<String> lines = new ArrayList<>();
		String time = Main.getSchedules().timeLeftFormatted(gLocation);
		for (String s : Lang.getHologramTextStorage().get(HologramText.REMAINING_TIME).getLines()) {
			if (s.contains("<time>"))
				s = s.replaceAll("<time>", time);
			if (s.contains("<generator_name>"))
				s = s.replace("<generator_name>", gLocation.getGenerator().getGeneratorItemName());

			for (Entry<String, String> en : e.getReplacablesMap().entrySet()) {
				s = s.replaceAll(en.getKey(), en.getValue());
			}

			lines.add(s);
		}
		return lines;
	}
}
