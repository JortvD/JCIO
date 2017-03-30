package nl.jortenmilo.utils;

import nl.jortenmilo.plugin.Plugin;

public abstract class Utils {
	
	private Plugin plugin;
	
	public abstract void create();
	public abstract Utils clone();
	
	public abstract String getData();
	public abstract String getName();
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
