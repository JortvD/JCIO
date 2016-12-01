package nl.jortenmilo.event;

public interface KeyboardEventListener extends EventListener {
	
	public void onPressed(KeyboardPressedEvent e);
	
	public void onReleased(KeyboardReleasedEvent e);
	
	public void onTyped(KeyboardTypedEvent e);
	
}
