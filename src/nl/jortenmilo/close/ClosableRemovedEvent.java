package nl.jortenmilo.close;

/**
 * This event is executed when a new Closable is removed from the CloseManager.
 * @see CloseManager
 * @see Closable
 */
public class ClosableRemovedEvent extends ClosableEvent {
	
	@Override
	public String getName() {
		return "ClosableRemovedEvent";
	}
	
}
