package nl.jortenmilo.console;

/**
 * This event is executed when the console was cleared.
 * @see Console
 */
public class ConsoleClearedEvent extends ConsoleEvent {

	@Override
	public String getName() {
		return "ConsoleClearedEvent";
	}

}
