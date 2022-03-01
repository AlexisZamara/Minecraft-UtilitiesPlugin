package ratatoskr.Utilities;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

// code alignment: spaghetti neutral
public class Main extends JavaPlugin {
	
	public static Plugin plugin;
	public File portalsConfigFile;
	public File treeChopperConfigFile;
	public FileConfiguration portalsConfig;
	public FileConfiguration treeChopperConfig;
	
	// default bukkit stuff
	@Override
	public void onEnable() {
		if(!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}
		this.saveDefaultConfig();
		createCustomConfig();
		
		this.getCommand("autochop").setExecutor(new AutoChop(this, this));
		
		if(this.getConfig().getBoolean("godapples.enabled")) {
			Recipes recipes = new Recipes(this);
			recipes.godAppleRestored();
		}
		
		getServer().getPluginManager().registerEvents(new BlockPlaced(this), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(this, this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(this, this), this);
		getServer().getPluginManager().registerEvents(new EntityExplode(this), this);
		getServer().getPluginManager().registerEvents(new EntityChangeBlock(this), this);
		getServer().getPluginManager().registerEvents(new ProjectileLaunch(this), this);
		getServer().getPluginManager().registerEvents(new PlayerItemConsume(this),this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
		// TODO: crying obsidian portal
		// ongoing: testing the parameters sent by portal events.
		// goal: figure out how to modify them to create custom portals without having to write the whole event again
		getServer().getPluginManager().registerEvents(new PlayerPortal(this, this), this);
		getServer().getPluginManager().registerEvents(new PortalCreate(this, this), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public FileConfiguration getPortalsCoordinates() {
		return this.portalsConfig;
	}
	
	public void savePortalsCoordinates() {
		if(portalsConfig == null || portalsConfigFile == null) {
			return;
		}
		try {
			getPortalsCoordinates().save(portalsConfigFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getTreeChopperSettings() {
		return this.treeChopperConfig;
	}
	
	public void saveTreeChopperSettings() {
		if(treeChopperConfig == null || treeChopperConfigFile == null) {
			return;
		}
		try {
			getTreeChopperSettings().save(treeChopperConfigFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createCustomConfig() {
		portalsConfigFile = new File(this.getDataFolder(), "portals.yml");
		if(!portalsConfigFile.exists()) {
			this.saveResource("portals.yml", false);
		}
		
		portalsConfig = new YamlConfiguration();
		try {
			portalsConfig.load(portalsConfigFile);
		}
		catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		treeChopperConfigFile = new File(this.getDataFolder(), "treechopper.yml");
		if(!treeChopperConfigFile.exists()) {
			this.saveResource("treechopper.yml", false);
		}
		
		treeChopperConfig = new YamlConfiguration();
		try {
			treeChopperConfig.load(treeChopperConfigFile);
		}
		catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}