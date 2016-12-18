package nl.jortenmilo.console;

public class ConsoleMovedEvent extends ConsoleEvent {
	
	public interface ConsoleMovedEventListener extends ConsoleEventListener {
		public void onConsoleMoved(ConsoleMovedEvent e);
	}
	
}
