package nl.jortenmilo.error;

import nl.jortenmilo.event.EventManager;

/**
 * This is the abstract method for an error. All errors instantiate this class.
 */
public abstract class Error {
	
	private static EventManager events;
	
	/**
	 * With this method you print everything about the error.
	 */
	public abstract void print();

	protected static EventManager getEventManager() {
		return events;
	}

	protected static void setEventManager(EventManager events) {
		Error.events = events;
	}
	
}
