package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a settings is created that already exists.
 * @see Error
 */
public class ExistingSettingError extends Error {
	
	private String value;
	
	public ExistingSettingError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ExistingSettingError][" + value + "]");
		
		this.printError("ExistingSettingError", "The setting '" + value + "' already exists!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
