package me.kryniowesegryderiusz.kgenerators.multiversion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.generators.holograms.interfaces.IHologramProvider;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;
import me.kryniowesegryderiusz.kgenerators.multiversion.interfaces.BlocksUtils;
import me.kryniowesegryderiusz.kgenerators.multiversion.interfaces.RecipesLoader;
import me.kryniowesegryderiusz.kgenerators.multiversion.interfaces.SkullUtils;
import me.kryniowesegryderiusz.kgenerators.multiversion.interfaces.WorldGuardUtils;

/**
 * Loads the implementations of the multiversion interfaces. Only Minecraft 26.2+ is supported,
 * so there is a single set of implementations (module v26_3). They are loaded by name, as the
 * module depends on core and not the other way around.
 */
public class MultiVersionManager {

	private static final int MIN_SUPPORTED_MAJOR = 26;
	private static final int MIN_SUPPORTED_MINOR = 2;
	private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)");

	/** False, if server runs Minecraft older than 26.2. Checked in Main#onEnable. */
	@Getter
	private boolean supported = true;

	@Getter
	private RecipesLoader recipesLoader;
	@Getter
	private BlocksUtils blocksUtils;
	@Getter
	private WorldGuardUtils worldGuardUtils;
	@Getter
	private SkullUtils skullUtils;
	@Getter
	private IHologramProvider hologramProvider;

	public MultiVersionManager() {
		try {
			String serverVersion = Main.getInstance().getServer().getVersion();
			serverVersion = serverVersion.split("MC: ")[1];
			serverVersion = serverVersion.split("\\)")[0];
			Logger.debugMultiVersionManager("MultiVersionManager: Found version " + serverVersion);

			if (!isSupportedVersion(serverVersion)) {
				supported = false;
				Logger.error("MultiVersionManager: Minecraft " + serverVersion + " is not supported! KGenerators requires Minecraft " + MIN_SUPPORTED_MAJOR + "." + MIN_SUPPORTED_MINOR + " or higher.");
				return;
			}

			String packageName = Main.class.getPackage().getName() + ".multiversion";

			try {
				this.recipesLoader = (RecipesLoader) Class.forName(packageName + ".RecipesLoader_26_3").newInstance();
				this.blocksUtils = (BlocksUtils) Class.forName(packageName + ".BlocksUtils_26_3").newInstance();

				if (Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard") != null) {
					this.worldGuardUtils = (WorldGuardUtils) Class.forName(packageName + ".WorldGuardUtils_26_3").newInstance();
					this.worldGuardUtils.worldGuardFlagsAdd();
				}

				this.skullUtils = (SkullUtils) Class.forName(packageName + ".SkullUtils_26_3").newInstance();
				this.hologramProvider = (IHologramProvider) Class.forName(packageName + ".HologramProvider_26_3").newInstance();

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e3) {
				Logger.error(e3);
			}
		} catch (Exception e) {
			Logger.error(e);
		}
	}

	/**
	 * @param serverVersion version as reported by the server, ex. "26.2" or "26.2.1"
	 * @return true if it is at least {@value #MIN_SUPPORTED_MAJOR}.{@value #MIN_SUPPORTED_MINOR}.
	 *         Legacy "1.x" versions are older than that. If the version can not be parsed, the
	 *         server is assumed to be supported.
	 */
	private static boolean isSupportedVersion(String serverVersion) {
		Matcher matcher = VERSION_PATTERN.matcher(serverVersion);
		if (!matcher.find())
			return true;
		int major = Integer.parseInt(matcher.group(1));
		int minor = Integer.parseInt(matcher.group(2));
		return major > MIN_SUPPORTED_MAJOR || (major == MIN_SUPPORTED_MAJOR && minor >= MIN_SUPPORTED_MINOR);
	}
}
