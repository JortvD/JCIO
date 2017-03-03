package nl.jortenmilo.close;

import nl.jortenmilo.event.Event;
import nl.jortenmilo.plugin.Plugin;

/**
 * This is the general Close event. All ClsoeEvents instantiate this class since it contains the general information about a CloseEvent.
 * @see CloseManager
 */
public abstract class ClosableEvent extends Event {
	
	private Closable closable;
	private Plugin plugin;
	
	/**
	 * Returns the Closable that was called.
	 * @return The closable
	 */
	public Closable getClosable() {
		return closable;
	}
	
	protected void setClosable(Closable closable) {
		this.closable = closable;
	}
	
	/**
	 * Returns the plugin where the Closable is from. This may return null if the Closable is anonymous.
	 * @return
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
