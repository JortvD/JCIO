package nl.jortenmilo.plugin;

import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.event.EventManager;

public class PluginManager {
	
	private List<LoadedPlugin> plugins = new ArrayList<LoadedPlugin>();
	private CommandManager cm;
	private EventManager em;
	
	protected void addPlugin(LoadedPlugin plugin) {
		Console.println("Loaded: " + plugin.getName());
		plugins.add(plugin);
	}
	
	public List<LoadedPlugin> getPlugins() {
		return plugins;
	}
	
	public void enableAll() {
		for(LoadedPlugin plugin : plugins) {
			Console.println("Enabling: " + plugin.getName());
			plugin.getPlugin().setCommandManager(cm);
			plugin.getPlugin().setPluginManager(this);
			plugin.getPlugin().setEventManager(em);
			plugin.getPlugin().setLoadedPlugin(plugin);
			plugin.getPlugin().enable();
			Console.println("Enabled: " + plugin.getName());
		}
	}
	
	public void enable(LoadedPlugin plugin) {
		plugin.getPlugin().setCommandManager(cm);
		plugin.getPlugin().setPluginManager(this);
		plugin.getPlugin().setEventManager(em);
		plugin.getPlugin().setLoadedPlugin(plugin);
		plugin.getPlugin().enable();
	}
	
	public void disable(LoadedPlugin plugin) {
		plugin.getPlugin().disable();
	}
	
	public void disableAll() {
		for(LoadedPlugin plugin : plugins) {
			plugin.getPlugin().disable();
		}
	}
	
	public class LoadedPlugin {
		private Plugin plugin;
		private String name;
		private String path;
		
		public Plugin getPlugin() {
			return plugin;
		}
		
		public void setPlugin(Plugin plugin) {
			this.plugin = plugin;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getPath() {
			return path;
		}
		
		public void setPath(String path) {
			this.path = path;
		}
	}

	public void setCommandManager(CommandManager cm) {
		this.cm = cm;
	}
	
	public void setEventManager(EventManager em) {
		this.em = em;
	}
}
