package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a command is created while it already exists.
 * @see Error
 */
public class CommandUsedError extends Error {
	
	private String value;
	
	public CommandUsedError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][CommandUsedError][" + value + "]");
		
		this.printError("CommandUsedError", "The command '" + value + "' is already used!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
