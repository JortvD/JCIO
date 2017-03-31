package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a method is called with an invalid parameter.
 * @see Error
 */
public class InvalidParameterError extends Error {
	
	private String value;
	
	public InvalidParameterError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][InvalidParameterError][" + value + "]");
		
		this.printError("InvalidParameterError", value + "' is an invalid value for this method!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}

}
