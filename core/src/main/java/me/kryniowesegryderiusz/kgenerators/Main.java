package me.kryniowesegryderiusz.kgenerators;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lone.itemsadder.api.ItemsAdder;
import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.api.events.PluginDisabledEvent;
import me.kryniowesegryderiusz.kgenerators.api.events.PluginEnabledEvent;
import me.kryniowesegryderiusz.kgenerators.api.events.PluginReloadEvent;
import me.kryniowesegryderiusz.kgenerators.data.DatabaseManager;
import me.kryniowesegryderiusz.kgenerators.dependencies.DependenciesManager;
import me.kryniowesegryderiusz.kgenerators.dependencies.hooks.ItemsAdderHook;
import me.kryniowesegryderiusz.kgenerators.dependencies.hooks.NBTAPIHook;
import me.kryniowesegryderiusz.kgenerators.generators.generator.GeneratorsManager;
import me.kryniowesegryderiusz.kgenerators.generators.holograms.HologramsManager;
import me.kryniowesegryderiusz.kgenerators.generators.locations.PlacedGeneratorsManager;
import me.kryniowesegryderiusz.kgenerators.generators.players.PlayersManager;
import me.kryniowesegryderiusz.kgenerators.generators.players.limits.LimitsManager;
import me.kryniowesegryderiusz.kgenerators.generators.recipes.RecipesManager;
import me.kryniowesegryderiusz.kgenerators.generators.schedules.SchedulesManager;
import me.kryniowesegryderiusz.kgenerators.generators.upgrades.UpgradesManager;
import me.kryniowesegryderiusz.kgenerators.gui.MenusManager;
import me.kryniowesegryderiusz.kgenerators.lang.Lang;
import me.kryniowesegryderiusz.kgenerators.listeners.BlockBreakListener;
import me.kryniowesegryderiusz.kgenerators.listeners.BlockPistonListener;
import me.kryniowesegryderiusz.kgenerators.listeners.BlockPlaceListener;
import me.kryniowesegryderiusz.kgenerators.listeners.CraftingListeners;
import me.kryniowesegryderiusz.kgenerators.listeners.FurnaceSmeltListener;
import me.kryniowesegryderiusz.kgenerators.listeners.InventoryClickListener;
import me.kryniowesegryderiusz.kgenerators.listeners.GeneratorProtectionListeners;
import me.kryniowesegryderiusz.kgenerators.listeners.PlayerInteractListener;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;
import me.kryniowesegryderiusz.kgenerators.multiversion.MultiVersionManager;
import me.kryniowesegryderiusz.kgenerators.settings.Settings;
import me.kryniowesegryderiusz.kgenerators.utils.FilesConverter;

public class Main extends JavaPlugin {

	@Getter private static Main instance;
	
	@Getter private static Settings settings;
	
	@Getter private static GeneratorsManager generators;
	@Getter private static HologramsManager holograms;
	@Getter private static PlacedGeneratorsManager placedGenerators;
	@Getter private static PlayersManager players = new PlayersManager();
	@Getter private static LimitsManager limits;
	@Getter private static RecipesManager recipes;
	@Getter private static SchedulesManager schedules;
	@Getter private static UpgradesManager upgrades;
	
	@Getter private static DatabaseManager databases;
	@Getter private static DependenciesManager dependencies = new DependenciesManager();
	@Getter private static MultiVersionManager multiVersion;
	@Getter private static MenusManager menus;
	
    @Override
    public void onEnable() {

    	if (!multiVersion.isSupported()) {
    		this.getServer().getPluginManager().disablePlugin(this);
    		return;
    	}

    	dependencies.onEnableDependenciesCheck();
    	if (Bukkit.getPluginManager().getPlugin("ItemsAdder") != null && !ItemsAdder.areItemsLoaded()) {
    		Logger.warn("ItemsAdder is enabled, KGenerators loading postponed until IA is loaded");
    		this.getServer().getPluginManager().registerEvents(new ItemsAdderHook().new ItemsAdderListeners(), this);
    	} else {
    		Logger.warn("KGenerators will be loaded in post init check, check for more informations futher in this log");
    		this.getServer().getScheduler().runTask(instance, () -> {
    			Main.getInstance().enable();
    		});
    	}
    }

	@Override
    public void onLoad() {
    	instance = this;

    	NBTAPIHook.disableTelemetry();

    	Logger.setup();
    	
		settings = new Settings();
		settings.reload();
		FilesConverter.updateConfig(settings);
    	
    	multiVersion = new MultiVersionManager();
    }
    
    @Override
    public void onDisable() {
    	Logger.info("Disabling KGenerators");
    	
    	instance.getServer().getPluginManager().callEvent(new PluginDisabledEvent());
    	
    	Logger.info("Safely closing menus.");
    	if (menus != null)
    		menus.closeAll();
    	
    	if (placedGenerators != null) {
    		Logger.info("Saving " + placedGenerators.getLoadedGeneratorsAmount() + " running generators.");
    		placedGenerators.onDisable();
    	}
    	
    	if (holograms != null)
    		holograms.onDisable();
    	
    	Logger.info("Safely shutting down database.");
    	if (databases != null && databases.getDb() != null)
    		databases.getDb().closeConnection();
    	
    	if (dependencies != null)
    		dependencies.onDisable();
    	
    	this.getServer().getScheduler().cancelTasks(this);
    }
    
    public void reload() {
    	Logger.info("Reload: KGenerators reload started");
    	settings.reload();
    	generators.reload();
    	//players.clear();
    	upgrades.reload();
    	limits = new LimitsManager();
    	Lang.loadFromFiles();
    	this.getServer().getPluginManager().callEvent(new PluginReloadEvent());
    	if (holograms != null)
    		holograms.reload();
    	Logger.info("Reload: KGenerators reloaded successfully");
    }
    
    public void enable() {
    	try {
    		
			/* Commands setup */
			this.getServer().getPluginCommand("kgenerators").setExecutor(new Commands());
			this.getServer().getPluginCommand("kgenerators").setTabCompleter(new CommandTabCompleter());
    		
			/* Dependencies check */
			dependencies.standardDependenciesCheck();
			
			/* Configs loader */
			generators = new GeneratorsManager();
			
			Lang.loadFromFiles();
			
			recipes = new RecipesManager();
			upgrades = new UpgradesManager();
			limits = new LimitsManager();
			
			/* Database setup */
			databases = new DatabaseManager(Main.getSettings().getDbType());
			databases.getDb().updateTable();
			
			/* Other systems starter */
			holograms = new HologramsManager();
			schedules = new SchedulesManager();
			menus = new MenusManager();
			
			/* Load from already loaded chunks */
			placedGenerators = new PlacedGeneratorsManager();
			
			/* Listeners setup */
			Logger.debugPluginLoad("MainManager: Loading listeners");
			this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
			this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
			this.getServer().getPluginManager().registerEvents(new CraftingListeners(), this);
			this.getServer().getPluginManager().registerEvents(new BlockPistonListener(), this);
			this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
			this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
			this.getServer().getPluginManager().registerEvents(new FurnaceSmeltListener(), this);
			this.getServer().getPluginManager().registerEvents(new GeneratorProtectionListeners(), this);
			//Chunk listeners were moved to PlacedGeneratorsManager
			
			Logger.info("MainManager: KGenerators loaded successfully");
			
			this.getServer().getPluginManager().callEvent(new PluginEnabledEvent());
	        
		} catch (Exception e) {
			Logger.error(e);
		}    	
    }
}