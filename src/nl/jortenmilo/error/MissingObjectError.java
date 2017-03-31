package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a object is missing from a class.
 * @see Error
 */
public class MissingObjectError extends Error {
	
	private String value1;
	private String value2;
	
	public MissingObjectError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][MissingObjectError][" + value1 + ", " + value2 + "]");
		
		this.printError("MissingObjectError", "The class '" + value1 + "' is missing the object '" + value2 + "'!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
