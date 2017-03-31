package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a plugin file doesn't contain all needed information.
 * @see Error
 */
public class UnspecifiedError extends Error {
	
	private String value1;
	private String value2;
	
	public UnspecifiedError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][UnspecifiedError][" + value1 + ", " + value2 + "]");
		
		this.printError("UnspecifiedError", "The plugin '" + value1 + "' has no '" + value2+ "' specified!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
