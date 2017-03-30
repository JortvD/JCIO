package nl.jortenmilo.console;

/**
 * This event is executed when the Console was resized.
 * @see Console
 */
public class ConsoleResizedEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleResizedEvent";
	}
	
}
