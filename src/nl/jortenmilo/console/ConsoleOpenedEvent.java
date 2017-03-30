package nl.jortenmilo.console;

/**
 * This event is executed when the Console was opened.
 * @see Console
 */
public class ConsoleOpenedEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleOpenedEvent";
	}
	
}
