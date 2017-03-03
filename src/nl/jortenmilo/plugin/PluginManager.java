package nl.jortenmilo.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.close.CloseManager;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.error.ErrorManager;
import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.mouse.MouseManager;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.UtilsManager;
import nl.jortenmilo.utils.defaults.SystemUtils;

public class PluginManager {
	
	private List<LoadedPlugin> plugins = new ArrayList<LoadedPlugin>();
	private PluginLoader loader;
	private CommandManager command;
	private ConsoleManager console;
	private MouseManager mouse;
	private KeyboardManager keyboard;
	private ConfigManager config;
	private SettingsManager settings;
	private UtilsManager utils;
	private ErrorManager error;
	private EventManager event;
	private CloseManager close;
	
	protected void addPlugin(LoadedPlugin plugin) {
		plugins.add(plugin);
	}
	
	public void removePlugin(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		plugins.remove(plugin);
	}
	
	public List<LoadedPlugin> getPlugins() {
		return plugins;
	}
	
	// TODO: Create the dependency system
	public void enableAll() {
		for(LoadedPlugin plugin : plugins) {
			enable(plugin);
		}
	}
	
	public void enable(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		Console.debug("PLUGIN_ENABLED [" + new SystemUtils().getTime() + "][" + plugin.getName() + "]");
		
		plugin.getPlugin().setCommandManager(command);
		plugin.getPlugin().setPluginManager(this);
		plugin.getPlugin().setConsoleManager(console);
		plugin.getPlugin().setKeyboardManager(keyboard);
		plugin.getPlugin().setConfigManager(config);
		plugin.getPlugin().setMouseManager(mouse);
		plugin.getPlugin().setSettingsManager(settings);
		plugin.getPlugin().setUtilsManager(utils);
		plugin.getPlugin().setErrorManager(error);
		plugin.getPlugin().setEventManager(event);
		plugin.getPlugin().setCloseManager(close);
		
		plugin.getPlugin().setLoadedPlugin(plugin);
		
		try {
			plugin.getPlugin().enable();
		} 
		catch(Error | Exception e2) {
			new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
		}
		
		PluginEnabledEvent event = new PluginEnabledEvent();
		event.setPlugin(plugin);
		
		for(EventHandler handler : this.event.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void disable(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		Console.debug("PLUGIN_DISABLED [" + new SystemUtils().getTime() + "][" + plugin.getName() + "]");
		
		try {
			plugin.getPlugin().disable();
		} 
		catch(Error | Exception e2) {
			new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
		}
		
		plugin.getPlugin().setCommandManager(null);
		plugin.getPlugin().setPluginManager(null);
		plugin.getPlugin().setConsoleManager(null);
		plugin.getPlugin().setKeyboardManager(null);
		plugin.getPlugin().setConfigManager(null);
		plugin.getPlugin().setMouseManager(null);
		plugin.getPlugin().setSettingsManager(null);
		plugin.getPlugin().setUtilsManager(null);
		plugin.getPlugin().setErrorManager(null);
		plugin.getPlugin().setEventManager(null);
		
		command.removeCommands(plugin.getPlugin());
		event.unregisterPlugin(plugin.getPlugin());
		
		PluginDisabledEvent event = new PluginDisabledEvent();
		event.setPlugin(plugin);
		
		for(EventHandler handler : this.event.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void disableAll() {
		for(LoadedPlugin plugin : plugins) {
			disable(plugin);
		}
	}
	
	public void load(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		loader.load(new File("plugins/" + plugin.getPath()), this);
		
		PluginLoadedEvent event = new PluginLoadedEvent();
		event.setPlugin(plugin);
		
		for(EventHandler handler : this.event.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void loadAll() {
		loader.loadAll(this);
	}
	
	public void unload(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		loader.unload(plugin);
		
		PluginUnloadedEvent event = new PluginUnloadedEvent();
		event.setPlugin(plugin);
		
		for(EventHandler handler : this.event.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void unloadAll() {
		loader.unloadAll(this);
	}
	
	public void reload(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		Console.debug("PLUGIN_RELOADED [" + new SystemUtils().getTime() + "][" + plugin.getName() + "]");
		
		loader.unload(plugin);
		loader.load(new File("plugins/" + plugin.getPath()), this);
	}
	
	public void reloadAll() {
		loader.unloadAll(this);
		loader.loadAll(this);
	}
	
	public void setCommandManager(CommandManager command) {
		this.command = command;
	}

	public void setConsoleManager(ConsoleManager console) {
		this.console = console;
	}

	public void setMouseManager(MouseManager mouse) {
		this.mouse = mouse;
	}

	public void setKeyboardManager(KeyboardManager keyboard) {
		this.keyboard = keyboard;
	}

	public void setConfigManager(ConfigManager config) {
		this.config = config;
	}

	public void setSettingsManager(SettingsManager settings) {
		this.settings = settings;
	}

	public void setUtilsManager(UtilsManager utils) {
		this.utils = utils;
	}

	public void setPluginLoader(PluginLoader loader) {
		this.loader = loader;
	}

	public ErrorManager getErrorManager() {
		return error;
	}

	public void setErrorManager(ErrorManager error) {
		this.error = error;
	}

	public EventManager getEventManager() {
		return event;
	}

	public void setEventManager(EventManager event) {
		this.event = event;
	}

	public CloseManager getCloseManager() {
		return close;
	}

	public void setCloseManager(CloseManager close) {
		this.close = close;
	}
}
