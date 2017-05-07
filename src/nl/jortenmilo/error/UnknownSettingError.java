package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a setting is called that doesn't exist.
 * @see Error
 */
public class UnknownSettingError extends Error{
	
	private String value;
	
	public UnknownSettingError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][UnknownSettingError][" + value + "]");
		
		this.printError("UnknownSettingError", "Unknown setting '" + value + "'!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
