package nl.jortenmilo.event;

/**
 * This is the general event class. All events instantiate this class. To create an event you need to instantiate this.
 */
public abstract class Event {
	
	/**
	 * Returns the name of this Event.
	 * @return The name
	 */
	public abstract String getName();
	
}
