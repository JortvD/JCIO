package nl.jortenmilo.console;

/**
 * This event is executed when the Console was shown.
 * @see Console
 */
public class ConsoleShownEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleShownEvent";
	}
	
}
