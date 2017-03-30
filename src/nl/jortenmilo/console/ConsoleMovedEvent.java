package nl.jortenmilo.console;

/**
 * This event is executed when the Console was moved.
 * @see Console
 */
public class ConsoleMovedEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleMovedEvent";
	}
	
}
