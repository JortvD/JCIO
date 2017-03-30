package nl.jortenmilo.console;

/**
 * This event is executed when the Console was closed.
 * @see Console
 */
public class ConsoleClosedEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleClosedEvent";
	}
	
}
