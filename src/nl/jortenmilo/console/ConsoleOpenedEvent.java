package nl.jortenmilo.console;

public class ConsoleOpenedEvent extends ConsoleEvent {
	
	public interface ConsoleOpenedEventListener extends ConsoleEventListener {
		public void onConsoleOpened(ConsoleOpenedEvent e);
	}
	
}
