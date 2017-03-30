package nl.jortenmilo.console;

/**
 * This event is executed when the Console was hidden. This does not mean it was destroyed.
 * @see Console
 */
public class ConsoleHiddenEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleHiddenEvent";
	}
	
}
