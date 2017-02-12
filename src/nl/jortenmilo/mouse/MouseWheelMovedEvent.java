package nl.jortenmilo.mouse;

public class MouseWheelMovedEvent extends MouseEvent {
	
	private int scrollAmount;
	private int scrollType;
	private int wheelRotation;
	private double presiceWheelRotation;

	public int getScrollAmount() {
		return scrollAmount;
	}

	public void setScrollAmount(int scrollAmount) {
		this.scrollAmount = scrollAmount;
	}

	public int getWheelRotation() {
		return wheelRotation;
	}

	public void setWheelRotation(int wheelRotation) {
		this.wheelRotation = wheelRotation;
	}

	public double getPresiceWheelRotation() {
		return presiceWheelRotation;
	}

	public void setPresiceWheelRotation(double presiceWheelRotation) {
		this.presiceWheelRotation = presiceWheelRotation;
	}

	public int getScrollType() {
		return scrollType;
	}

	public void setScrollType(int scrollType) {
		this.scrollType = scrollType;
	}

	@Override
	public String getName() {
		return "MouseWheelMovedEvent";
	}
	
}
