package nl.jortenmilo.close;

/**
 * This event is executed when a single Closable is called.
 */
public class ClosableCalledEvent extends ClosableEvent {

	@Override
	public String getName() {
		return "ClosableCalledEvent";
	}
	
}
