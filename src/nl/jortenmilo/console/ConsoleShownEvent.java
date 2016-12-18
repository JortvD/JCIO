package nl.jortenmilo.console;

public class ConsoleShownEvent extends ConsoleEvent {
	
	public interface ConsoleShownEventListener extends ConsoleEventListener {
		public void onConsoleShown(ConsoleShownEvent e);
	}
	
}
