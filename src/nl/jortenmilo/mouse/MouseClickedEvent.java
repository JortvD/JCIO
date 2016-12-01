package nl.jortenmilo.mouse;

public class MouseClickedEvent extends MouseEvent {
	
	private int button;

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}
	
}
