package nl.jortenmilo.mouse;

/**
 * This event is executed when the mouse has moved.
 * @see MouseEvent
 */
public class MouseMovedEvent extends MouseEvent {

	@Override
	public String getName() {
		return "MouseMovedEvent";
	}

}
