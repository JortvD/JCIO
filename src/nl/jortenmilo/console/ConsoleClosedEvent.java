package nl.jortenmilo.console;

public class ConsoleClosedEvent extends ConsoleEvent {
	
	public interface ConsoleClosedEventListener extends ConsoleEventListener {
		public void onConsoleClosed(ConsoleClosedEvent e);
	}
	
}
