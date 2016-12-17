package nl.jortenmilo.plugin;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.mouse.MouseManager;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.UtilsManager;

public class PluginManager {
	
	private List<LoadedPlugin> plugins = new ArrayList<LoadedPlugin>();
	private List<PluginEventListener> listeners = new ArrayList<PluginEventListener>();
	private PluginLoader loader;
	private CommandManager command;
	private ConsoleManager console;
	private MouseManager mouse;
	private KeyboardManager keyboard;
	private ConfigManager config;
	private SettingsManager settings;
	private UtilsManager utils;
	
	protected void addPlugin(LoadedPlugin plugin) {
		plugins.add(plugin);
	}
	
	public void removePlugin(LoadedPlugin plugin) {
		plugins.remove(plugin);
	}
	
	public List<LoadedPlugin> getPlugins() {
		return plugins;
	}
	
	public void enableAll() {
		for(LoadedPlugin plugin : plugins) {
			enable(plugin);
		}
	}
	
	public void enable(LoadedPlugin plugin) {
		Console.println("Enabling: " + plugin.getName());
		plugin.getPlugin().setCommandManager(command);
		plugin.getPlugin().setPluginManager(this);
		plugin.getPlugin().setConsoleManager(console);
		plugin.getPlugin().setKeyboardManager(keyboard);
		plugin.getPlugin().setConfigManager(config);
		plugin.getPlugin().setMouseManager(mouse);
		plugin.getPlugin().setSettingsManager(settings);
		plugin.getPlugin().setUtilsManager(utils);
		plugin.getPlugin().setLoadedPlugin(plugin);
		plugin.getPlugin().enable();
		Console.println("Enabled: " + plugin.getName());
		
		PluginEnabledEvent event = new PluginEnabledEvent();
		event.setPlugin(plugin);
		for(PluginEventListener listener : listeners) {
			try {
				listener.onPluginEnabled(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public void disable(LoadedPlugin plugin) {
		Console.println("Disabling: " + plugin.getName());
		plugin.getPlugin().disable();
		Console.println("Disabled: " + plugin.getName());
		
		PluginDisabledEvent event = new PluginDisabledEvent();
		event.setPlugin(plugin);
		for(PluginEventListener listener : listeners) {
			try {
				listener.onPluginDisabled(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public void disableAll() {
		for(LoadedPlugin plugin : plugins) {
			disable(plugin);
		}
	}
	
	public void load(LoadedPlugin plugin) {
		loader.load(new File("plugins/" + plugin.getPath()), this);
		
		PluginLoadedEvent event = new PluginLoadedEvent();
		event.setPlugin(plugin);
		for(PluginEventListener listener : listeners) {
			try {
				listener.onPluginLoaded(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public void loadAll() {
		loader.loadAll(this);
	}
	
	public void unload(LoadedPlugin plugin) {
		loader.unload(plugin);
		
		PluginUnloadedEvent event = new PluginUnloadedEvent();
		event.setPlugin(plugin);
		for(PluginEventListener listener : listeners) {
			try {
				listener.onPluginUnloaded(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public void unloadAll() {
		loader.unloadAll(this);
	}
	
	public void reload(LoadedPlugin plugin) {
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
	
	public void addListener(PluginEventListener listener) {
		listeners.add(listener);
	}

	public void setPluginLoader(PluginLoader loader) {
		this.loader = loader;
	}

	public class LoadedPlugin {
		private Plugin plugin;
		private String name;
		private String path;
		private URLClassLoader loader;
		
		public Plugin getPlugin() {
			return plugin;
		}
		
		protected void setPlugin(Plugin plugin) {
			this.plugin = plugin;
		}
		
		public String getName() {
			return name;
		}
		
		protected void setName(String name) {
			this.name = name;
		}
		
		public String getPath() {
			return path;
		}
		
		protected void setPath(String path) {
			this.path = path;
		}

		public URLClassLoader getClassLoader() {
			return loader;
		}

		protected void setClassLoader(URLClassLoader loader) {
			this.loader = loader;
		}
	}

}
