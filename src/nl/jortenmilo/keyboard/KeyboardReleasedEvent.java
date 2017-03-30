package nl.jortenmilo.keyboard;

/**
 * This event is executed when a key was released.
 * @see KeyboardManager
 */
public class KeyboardReleasedEvent extends KeyboardEvent {

	@Override
	public String getName() {
		return "KeyboardReleasedEvent";
	}

}
