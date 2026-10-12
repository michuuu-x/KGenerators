package me.kryniowesegryderiusz.kgenerators.logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.addons.Addons;
import me.kryniowesegryderiusz.kgenerators.addons.objects.Addon;
import me.kryniowesegryderiusz.kgenerators.lang.Lang;
import me.kryniowesegryderiusz.kgenerators.lang.enums.Message;
import me.kryniowesegryderiusz.kgenerators.logger.enums.LogType;
import me.kryniowesegryderiusz.kgenerators.utils.FilesUtils;

public class Logger {

	static String logFile = "logs/latest.log";
	static String configFile = "config.yml";
	static String generatorsFile = "generators.yml";
	static String recipesFile = "recipes.yml";
	static String upgradesFile = "upgrades.yml";
	static String limitsFile = "limits.yml";

	private static void log(Object object, LogType logType) {
		String message = logType.getHeader() + "";
		if (object instanceof String) {
			message += (String) object;
			if (logType != LogType.DEBUG)
				if (logType == LogType.ERROR || logType == LogType.WARNING)
					Main.getInstance().getLogger().warning(message);
				else
					Main.getInstance().getLogger().info(message);
		} else if (object instanceof Exception) {
			message += exceptionStacktraceToString((Exception) object);
			((Exception) object).printStackTrace();
		} else {
			message += object.toString();
			if (logType != LogType.DEBUG)
				if (logType == LogType.ERROR || logType == LogType.WARNING)
					Main.getInstance().getLogger().warning(message);
				else
					Main.getInstance().getLogger().info(message);
		}
		logToFile(message, logFile);
	}

	public static void error(Object object) {
		log(object, LogType.ERROR);
	}

	public static void info(Object object) {
		log(object, LogType.INFO);
	}
	
	public static void warn(Object object) {
		if (Main.getSettings().isWarnings())
			log(object, LogType.WARNING);
	}

	public static void debugPlayer(Object object) {
		if (Main.getSettings().isPlayersDebug())
			log(object, LogType.DEBUG);
	}
	
	public static void debugPluginLoad(Object object) {
		if (Main.getSettings().isPluginLoadDebug())
			log(object, LogType.DEBUG);
	}
	
	public static void debugPlacedGeneratorsManager(Object object) {
		if (Main.getSettings().isPlacedGeneratorsManagerDebug())
			log(object, LogType.DEBUG);
	}
	
	public static void debugSchedulesManager(Object object) {
		if (Main.getSettings().isSchedulesManagerDebug())
			log(object, LogType.DEBUG);
	}
	
	public static void debugMultiVersionManager(Object object) {
		if (Main.getSettings().isMultiVersionManagerDebug())
			log(object, LogType.DEBUG);
	}

	public static void debug(Object object) {
		log(object, LogType.DEBUG);
	}

	public static void textToConsole(String message) {
		Main.getInstance().getLogger().info(message);
	}

	private static String exceptionStacktraceToString(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		ps.close();
		return baos.toString();
	}

	public static void logToFile(String message, String file) {
		try {
			File dataFolder = Main.getInstance().getDataFolder();
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}

			File saveTo = new File(Main.getInstance().getDataFolder(), file);
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}

			FileWriter fw = new FileWriter(saveTo, true);

			PrintWriter pw = new PrintWriter(fw);

			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss] ");

			pw.println(format.format(now) + message);

			pw.flush();

			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void setup() {
		FilesUtils.mkdir("logs");
		File saveTo = new File(Main.getInstance().getDataFolder(), logFile);
		if (saveTo.exists()) {
			try {
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
				Files.move(saveTo.toPath(),
						new File(Main.getInstance().getDataFolder(), "logs/" + format.format(now).toString() + ".log")
								.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Logger.error("Cannot create new logs file");
				Logger.error(e);
			}
		}
	}

	/**
	 * Saves debug information (server and plugin info, plugin log and configs) to the debug
	 * folder of the plugin. Nothing is sent anywhere, so the file can be checked before it is
	 * shared with someone.
	 */
	public static void debugDump(CommandSender sender) {
		Main.getInstance().getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				String fileString = "";

				Iterator<Map.Entry<Object, Object>> iterator = System.getProperties().entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Object, Object> e = iterator.next();
					if (e.getKey().toString().contains("java.version"))
						fileString += "Java version: " + e.getValue().toString() + "\n";
					else if (e.getKey().toString().contains("user.country"))
						fileString += "User country: " + e.getValue().toString() + "\n";
					else if (e.getKey().toString().contains("os.name"))
						fileString += "System: " + e.getValue().toString() + "\n";
				}

				fileString += "\n";

				fileString += "Server version: " + Main.getInstance().getServer().getVersion() + "\n";
				fileString += "Plugin version: " + Main.getInstance().getDescription().getVersion() + "\n";
				fileString += "Enabled dependencies: " + Main.getDependencies().getDependencies().toString() + "\n";
				fileString += "Registered addons: " + Addons.getAddons().toString() + "\n";

				fileString += "Enabled plugins: ";
				for (Plugin plugin : Main.getInstance().getServer().getPluginManager().getPlugins())
					fileString += plugin.getName() + " " + plugin.getDescription().getVersion() + ", ";

				fileString += "\n\n";

				fileString += "Loaded generators: " + Main.getPlacedGenerators().getLoadedGeneratorsAmount() + "/"
						+ Main.getDatabases().getDb().getGeneratorsAmount() + "\n";
				fileString += "Scheduled generators: " + Main.getSchedules().getAmount() + "\n";

				fileString += "\n\n";

				fileString += getLinesFromFile(logFile) + "\n";
				fileString += getLinesFromFile(configFile) + "\n";
				fileString += getLinesFromFile(generatorsFile) + "\n";
				fileString += getLinesFromFile(upgradesFile) + "\n";
				fileString += getLinesFromFile(limitsFile) + "\n";
				fileString += getLinesFromFile(recipesFile) + "\n";

				for (Addon a : Addons.getAddons()) {
					if (a.getConfigs() != null) {
						for (String f : a.getConfigs()) {
							fileString += getLinesFromFile(f) + "\n";
						}
					}
				}

				try {
					FilesUtils.mkdir("debug");
					String name = "debug/debug-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".txt";
					Files.write(new File(Main.getInstance().getDataFolder(), name).toPath(),
							fileString.getBytes(StandardCharsets.UTF_8));
					Lang.getMessageStorage().send(sender, Message.COMMANDS_DEBUG_DONE, "<file>",
							Main.getInstance().getDataFolder().getPath().replace(File.separatorChar, '/') + "/" + name);
				} catch (IOException e) {
					Lang.getMessageStorage().send(sender, Message.COMMANDS_DEBUG_ERROR);
					Logger.error(e);
				}
			}
		});
	}

	/**
	 * @return file content under a header, or the reason, why it could not be read. One missing
	 *         file should not stop the whole debug output
	 */
	private static String getLinesFromFile(String file) {
		String fileStrings = "########################################\n########## " + file
				+ " ##########\n########################################\n\n";
		try {
			List<String> lines = Files.readAllLines(Paths.get(Main.getInstance().getDataFolder().getPath(), file),
					StandardCharsets.UTF_8);
			for (String l : lines) {
				if (!isDatabaseCredential(l))
					fileStrings += l + "\n";
			}
		} catch (IOException e) {
			fileStrings += "Cannot read this file: " + e + "\n";
		}
		return fileStrings;
	}

	/** Database connection details are not needed to debug anything */
	private static boolean isDatabaseCredential(String line) {
		String key = line.trim().toLowerCase(Locale.ROOT);
		return key.contains("password") || key.startsWith("username:") || key.startsWith("host:");
	}

}
