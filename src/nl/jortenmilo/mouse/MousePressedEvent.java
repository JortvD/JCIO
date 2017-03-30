package nl.jortenmilo.mouse;

/**
 * This event is executed when a mouse has pressed on something.
 * @see MouseEvent
 */
public class MousePressedEvent extends MouseEvent {
	
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
		return "MousePressedEvent";
	}
	
}
