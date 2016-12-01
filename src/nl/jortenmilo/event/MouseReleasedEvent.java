package nl.jortenmilo.event;

public class MouseReleasedEvent extends MouseEvent {
	
	private int button;

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}
	
}
