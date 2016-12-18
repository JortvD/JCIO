package nl.jortenmilo.console;

public class ConsoleResizedEvent extends ConsoleEvent {
	
	public interface ConsoleResizedEventListener extends ConsoleEventListener {
		public void onConsoleResized(ConsoleResizedEvent e);
	}
	
}
