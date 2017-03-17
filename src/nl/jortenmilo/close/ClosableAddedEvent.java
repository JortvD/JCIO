package nl.jortenmilo.close;

/**
 * This event is executed when a new Closable is added to the CloseManager.
 * @see CloseManager
 * @see Closable
 */
public class ClosableAddedEvent extends ClosableEvent {
	
	@Override
	public String getName() {
		return "ClosableRemovedEvent";
	}
	
}
