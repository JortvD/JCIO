package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when there was an error while loading a config.
 * @see Error
 */
public class ConfigLoadingError extends Error {
	
	private String value1; //File
	private String value2; //Line
	private String value3; //Col
	private String value4; //Error
	
	public ConfigLoadingError(String value1, String value2, String value3, String value4) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ConfigLoadingError][" + value1 + ", " + value2 + ", " + value3 + ", " + value4 + "]");
		
		this.printError("ConfigLoadingError", value4 + "!");
		Console.println(ConsoleUser.Error, " in the file: " + value1);
		Console.println(ConsoleUser.Error, " at the line: " + value2);
		Console.println(ConsoleUser.Error, " at the col: " + value3);
		this.printStackTrace();
		this.printHelp();
		this.event();
	}

}
