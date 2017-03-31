package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when the main class in the plugin.jcio wasn't found.
 * @see Error
 */
public class ClassNotFoundError extends Error {
	
	private String value1;
	private String value2;
	
	public ClassNotFoundError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ClassNotFoundError][" + value1 + ", " + value2 + "]");
		
		this.printError("ClassNotFoundError", "The class '" + value1 + "' is not found!");
		Console.println(ConsoleUser.Error, "Caused by: " + value2);
		this.printHelp();
		this.event();
	}

}
