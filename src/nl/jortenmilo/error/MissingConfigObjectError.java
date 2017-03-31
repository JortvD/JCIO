package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when the object is called that doesn't exist.
 * @see Error
 */
public class MissingConfigObjectError extends Error {
	
	private String value1;
	private String value2;
	
	public MissingConfigObjectError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][MissingConfigObjectError][" + value1 + ", " + value2 + "]");
		
		this.printError("MissingConfigObjectError", "The object '" + value1 + "' from the path '" + value2 + "' non-existant!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
