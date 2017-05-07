package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a file is missing from a plugin that was loaded.
 * @see Error
 */
public class MissingFileError extends Error {
	
	private String value1;
	private String value2;
	
	public MissingFileError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][MissingFileError][" + value1 + ", " + value2 + "]");
		
		this.printError("MissingFileError", "The plugin '" + value1 + "' is missing the file '" + value2 + "'!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
