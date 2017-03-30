package nl.jortenmilo.keyboard;

/**
 * This event is executed when a key was pressed.
 * @see KeyboardManager
 */
public class KeyboardPressedEvent extends KeyboardEvent {

	@Override
	public String getName() {
		return "KeyboardPressedEvent";
	}
	
}
