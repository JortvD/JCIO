package nl.jortenmilo.command.defaults;

import nl.jortenmilo.close.CloseManager;
import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.keyboard.KeyboardManager;

/**
 * With this command you can exit the console.
 * Usage: "<code>exit</code>"
 */
public class ExitCommand implements CommandExecutor {
	
	private KeyboardManager keyboard;
	private CloseManager close;
	
	protected ExitCommand(KeyboardManager keyboard, CloseManager close) {
		this.keyboard = keyboard;
		this.close = close;
	}
	
	/**
	 * The execute method for this command.
	 */
	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("Exiting the program. Press any key to continue!");
			
			keyboard.waitUntilTyped();
			
			close.close();
		} 
		else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
