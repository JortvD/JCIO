package nl.jortenmilo.plugin;

import nl.jortenmilo.plugin.PluginManager.LoadedPlugin;

public class PluginEvent {
	
	private LoadedPlugin plugin;
	
	public LoadedPlugin getPlugin() {
		return plugin;
	}
	
	protected void setPlugin(LoadedPlugin plugin) {
		this.plugin = plugin;
	}
	
}
