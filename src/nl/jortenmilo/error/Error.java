package nl.jortenmilo.error;

import nl.jortenmilo.event.EventManager;

public abstract class Error {
	
	private static EventManager events;
	
	public abstract void print();

	protected static EventManager getEventManager() {
		return events;
	}

	protected static void setEventManager(EventManager events) {
		Error.events = events;
	}
	
}
