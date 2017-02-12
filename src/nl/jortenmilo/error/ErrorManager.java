package nl.jortenmilo.error;

import nl.jortenmilo.event.EventManager;

public class ErrorManager {
	
	public ErrorManager(EventManager manager) {
		Error.setEventManager(manager);
	}
	
}
