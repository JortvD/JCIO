package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a method is called with a parameter that is null while it wasn't allowed to.
 * @see Error
 */
public class NonNullableParameterError extends Error {
	
	private String value1;
	private String value2;
	
	public NonNullableParameterError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][NonNullableParameterError][" + value1 + ", " + value2 + "]");
		
		this.printError("NonNullableParameterError", "The parameter " + value1 + "#" + value2 + " is null!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
