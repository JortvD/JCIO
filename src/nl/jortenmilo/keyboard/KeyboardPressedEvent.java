package nl.jortenmilo.keyboard;

public class KeyboardPressedEvent extends KeyboardEvent {
	
	public interface KeyboardPressedEventListener extends KeyboardEventListener {
		public void onKeyboardPressed(KeyboardPressedEvent e);
	}
	
}
