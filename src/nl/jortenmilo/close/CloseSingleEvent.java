package nl.jortenmilo.close;

import nl.jortenmilo.plugin.Plugin;

public class CloseSingleEvent extends CloseEvent {
	
	private Closable closable;
	private Plugin plugin;
	
	public Closable getClosable() {
		return closable;
	}
	
	protected void setClosable(Closable closable) {
		this.closable = closable;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "CloseSingleEvent";
	}
	
}
