package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when there was an error while parsing a object into another object.
 * @see Error
 */
public class ParsingError extends Error {
	
	private String value1;
	private String value2;
	private String value3;
	
	public ParsingError(String value1, String value2, String value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ParsingError][" + value1 + ", " + value2 + ", " + value3 + "]");
		
		this.printError("ParsingError", "The " + value1 + " '" + value2 + "' can't be parsed to a(n) '" + value3 + "'!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
