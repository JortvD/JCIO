package nl.jortenmilo.close;

/**
 * This event is executed when a Closable is called.
 * @see Closable
 */
public class ClosableCalledEvent extends ClosableEvent {

	@Override
	public String getName() {
		return "ClosableCalledEvent";
	}
	
}
