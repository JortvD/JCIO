package nl.jortenmilo.close;

import nl.jortenmilo.event.Event;
import nl.jortenmilo.plugin.Plugin;

/**
 * This is the general Closable event. All ClosableEvents instantiate this class since it contains the general information about a ClosableEvent.
 * @see Closable
 */
public abstract class ClosableEvent extends Event {
	
	private Closable closable;
	private Plugin plugin;
	
	/**
	 * Returns the Closable that this event is executed for.
	 * @return The Closable
	 * @see Closable
	 */
	public Closable getClosable() {
		return closable;
	}
	
	protected void setClosable(Closable closable) {
		this.closable = closable;
	}
	
	/**
	 * Returns the Plugin where the Closable is from. This may return null if the Closable was added anonymously to the CloseManager.
	 * @return The Plugin
	 * @see Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
