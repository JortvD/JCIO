package nl.jortenmilo.error;

/**
 * This event is executed when a error was thrown.
 */
public class ErrorThrownEvent extends ErrorEvent {

	@Override
	public String getName() {
		return "ErrorThrownEvent";
	}

}
