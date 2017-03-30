package nl.jortenmilo.keyboard;

/**
 * This event is executed when a key was typed.
 * @see KeyboardManager
 */
public class KeyboardTypedEvent extends KeyboardEvent {

	@Override
	public String getName() {
		return "KeyboardTypedEvent";
	}

}
