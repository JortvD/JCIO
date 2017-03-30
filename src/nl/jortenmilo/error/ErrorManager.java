package nl.jortenmilo.error;

import nl.jortenmilo.event.EventManager;

/**
 * This is the ErrorManager. It currently does not contain any methods.
 * @see Error
 */
public class ErrorManager {
	
	public ErrorManager(EventManager manager) {
		Error.setEventManager(manager);
	}
	
}
