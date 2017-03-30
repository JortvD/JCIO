package nl.jortenmilo.mouse;

/**
 * This event is executed when the mouse has dragged something.
 * @see MouseEvent
 */
public class MouseDraggedEvent extends MouseEvent {

	@Override
	public String getName() {
		return "MouseDraggedEvent";
	}

}
