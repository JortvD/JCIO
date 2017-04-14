package nl.jortenmilo.storage;

import nl.jortenmilo.plugin.Plugin;

public class StoragableDeletedEvent extends StoragableEvent {

	private String key;
	private String value;
	private Plugin plugin;
	
	public String getKey() {
		return key;
	}

	protected void setKey(String key) {
		this.key = key;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public String getValue() {
		return value;
	}

	protected void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getName() {
		return "StoragableDeletedEvent";
	}
	
}
