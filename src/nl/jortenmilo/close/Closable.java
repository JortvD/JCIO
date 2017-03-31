package nl.jortenmilo.close;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.plugin.Plugin;

/**
 * You extend Closable when you want to close stuff when the program is closed. You will need to add the class to the CloseManager for the Closable 
 * to be executed.
 * @see CommandManager
 */
public abstract class Closable {
	
	private ClosablePriority priority = ClosablePriority.MEDIUM;
	private Plugin plugin;
	
	/**
	 * The method that is called when the program is closed. When it is called a ClosableCalledEvent is executed with it.
	 */
	public abstract void close();
	
	/**
	 * Returns the priority of this Closable. Closables with higher priorities will be called after the Closables with a lower priority.
	 * @return The priority
	 * @see ClosablePriority
	 */
	public ClosablePriority getPriority() {
		return priority;
	}
	
	/**
	 * Sets the priority of this Closable. Closables with higher priorities will be called after the Closables with a lower priority.
	 * @param priority The priority
	 * @see ClosablePriority
	 */
	public void setPriority(ClosablePriority priority) {
		if(priority == null) {
			new NonNullableParameterError("ClosablePriority", "priority").print();
			return;
		}
		
		this.priority = priority;
	}
	
	/**
	 * Returns the plugin that registered this Closable.
	 * @return The plugin
	 * @see Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
