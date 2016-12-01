package nl.jortenmilo.plugin;

import java.io.File;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.mouse.MouseManager;
import nl.jortenmilo.plugin.PluginManager.LoadedPlugin;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.math.UtilsManager;

public abstract class Plugin {
	
	private PluginManager plugin;
	private CommandManager command;
	private ConsoleManager console;
	private MouseManager mouse;
	private KeyboardManager keyboard;
	private ConfigManager config;
	private SettingsManager settings;
	private UtilsManager utils;
	
	private LoadedPlugin lp;
	private File sf;
	
	public void enable() {}
	public void disable() {}
	public void load() {}
	public void unload() {}

	public PluginManager getPluginManager() {
		return plugin;
	}

	protected void setPluginManager(PluginManager pm) {
		this.plugin = pm;
	}

	public CommandManager getCommandManager() {
		return command;
	}

	protected void setCommandManager(CommandManager cm) {
		this.command = cm;
	}
	
	protected void setLoadedPlugin(LoadedPlugin lp) {
		this.lp = lp;
		sf = new File("plugins/" + lp.getName());
	}
	
	public LoadedPlugin getLoadedPlugin() {
		return lp;
	}
	
	public File getSaveFolder() {
		return sf;
	}
	
	public void createSaveFolder() {
		if(!sf.exists()) {
			sf.mkdirs();
		}
	}
	
	public ConsoleManager getConsoleManager() {
		return console;
	}
	
	protected void setConsoleManager(ConsoleManager console) {
		this.console = console;
	}
	
	public MouseManager getMouseManager() {
		return mouse;
	}
	
	protected void setMouseManager(MouseManager mouse) {
		this.mouse = mouse;
	}
	
	public KeyboardManager getKeyboardManager() {
		return keyboard;
	}
	
	protected void setKeyboardManager(KeyboardManager keyboard) {
		this.keyboard = keyboard;
	}
	
	public ConfigManager getConfigManager() {
		return config;
	}
	
	protected void setConfigManager(ConfigManager config) {
		this.config = config;
	}
	
	public SettingsManager getSettingsManager() {
		return settings;
	}
	
	protected void setSettingsManager(SettingsManager settings) {
		this.settings = settings;
	}
	
	public UtilsManager getUtilsManager() {
		return utils;
	}
	
	protected void setUtilsManager(UtilsManager utils) {
		this.utils = utils;
	}
}
