package nl.jortenmilo.mouse;

public class MousePressedEvent extends MouseEvent {
	
	private int button;

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	@Override
	public String getName() {
		return "MousePressedEvent";
	}
	
}
