package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;

public class ClassNotFoundError extends Error {
	
	/* This error is thrown when:
	 * A class has not been found that is called in a plugin.
	 */
	
	private String value1;
	private String value2;
	
	public ClassNotFoundError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.println(ConsoleUser.Error, "ClassNotFound: The class '" + value1 + "' is not found!");
		Console.println(ConsoleUser.Error, "Caused by: " + value2);
		Console.println(ConsoleUser.Error, "If you don't know what to do, please contact us at: goo.gl/1ROGMh.");
		
		ErrorThrownEvent event = new ErrorThrownEvent();
		event.setError(this);
		
		for(EventHandler handler : getEventManager().getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

}
