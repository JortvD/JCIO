package nl.jortenmilo.mouse;

/**
 * This event is executed when the mouse was released.
 * @see MouseEvent
 */
public class MouseReleasedEvent extends MouseEvent {
	
	private int button;

	/**
	 * Returns the button that was used.
	 * @return The button
	 */
	public int getButton() {
		return button;
	}

	protected void setButton(int button) {
		this.button = button;
	}

	@Override
	public String getName() {
		return "MouseReleasedEvent";
	}
	
}
