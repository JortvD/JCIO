package nl.jortenmilo.close;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.error.NonNullableParameterError;

/**
 * You extend Closable when you want to close stuff when the program is closed. You will need to add the class to the CloseManager
 * @see CommandManager
 */
public abstract class Closable {
	
	private ClosablePriority priority = ClosablePriority.MEDIUM;
	
	/**
	 * The method that is called when the program is closed.
	 */
	public abstract void close();
	
	/**
	 * Returns the priority of this Closable. Closables with higher priorities will be called before the lower priorities.
	 * @return The priority
	 * @see ClosablePriority
	 */
	public ClosablePriority getPriority() {
		return priority;
	}
	
	/**
	 * Sets the priority of this Closable. Closables with higher priorities will be called before the lower priorities.
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
	
}
