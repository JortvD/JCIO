package nl.jortenmilo.console;

public class ConsoleHiddenEvent extends ConsoleEvent {
	
	public interface ConsoleHiddenEventListener extends ConsoleEventListener {
		public void onConsoleHidden(ConsoleHiddenEvent e);
	}
	
}
