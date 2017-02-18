package nl.jortenmilo.plugin;

import nl.jortenmilo.event.Event;

public abstract class PluginEvent extends Event {
	
	private LoadedPlugin plugin;
	
	public LoadedPlugin getPlugin() {
		return plugin;
	}
	
	protected void setPlugin(LoadedPlugin plugin) {
		this.plugin = plugin;
	}
	
}
