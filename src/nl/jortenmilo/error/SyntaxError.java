package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when there are unknown symbols in a String.
 * @see Error
 */
public class SyntaxError extends Error {
	
	private String value1;
	private String value2;
	
	public SyntaxError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][SyntaxError][" + value1 + ", " + value2 + "]");
		
		this.printError("SyntaxError", "Unknown symbols: '" + value1+ "' in '" + value2 + "'!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
