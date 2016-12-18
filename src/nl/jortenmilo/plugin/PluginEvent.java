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
	
	public class PluginEventListener {
		public void onPluginEnabled(PluginEnabledEvent e) {}
		public void onPluginDisabled(PluginDisabledEvent e) {}
		public void onPluginLoaded(PluginLoadedEvent e) {}
		public void onPluginUnloaded(PluginUnloadedEvent e) {}
	}
	
}
