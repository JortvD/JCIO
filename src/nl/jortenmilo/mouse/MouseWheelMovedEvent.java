package nl.jortenmilo.mouse;

/**
 * This event is executed when the mousewheel was moved.
 * @see MouseEvent
 */
public class MouseWheelMovedEvent extends MouseEvent {
	
	private int scrollAmount;
	private int scrollType;
	private int wheelRotation;
	private double presiceWheelRotation;
	
	/**
	 * Returns the amount that was scrolled.
	 * @return The amount
	 */
	public int getScrollAmount() {
		return scrollAmount;
	}

	protected void setScrollAmount(int scrollAmount) {
		this.scrollAmount = scrollAmount;
	}
	
	/**
	 * Returns the wheel rotation.
	 * @return The wheel rotation
	 */
	public int getWheelRotation() {
		return wheelRotation;
	}

	protected void setWheelRotation(int wheelRotation) {
		this.wheelRotation = wheelRotation;
	}

	/**
	 * Returns the precise wheel rotation.
	 * @return The precise wheel rotation
	 */
	public double getPresiceWheelRotation() {
		return presiceWheelRotation;
	}

	protected void setPresiceWheelRotation(double presiceWheelRotation) {
		this.presiceWheelRotation = presiceWheelRotation;
	}

	/**
	 * Returns the type of scroll that was used.
	 * @return The scroll type
	 */
	public int getScrollType() {
		return scrollType;
	}

	protected void setScrollType(int scrollType) {
		this.scrollType = scrollType;
	}

	@Override
	public String getName() {
		return "MouseWheelMovedEvent";
	}
	
}
