package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.main.CloseManager;

/**
 * With this command you can exit the console.
 * Usage: "<code>exit</code>"
 */
public class ExitCommand implements CommandExecutor {
	
	private KeyboardManager keyboard;
	
	protected ExitCommand(KeyboardManager keyboard) {
		this.keyboard = keyboard;
	}
	
	/**
	 * The execute method for this command.
	 */
	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("Exiting the program. Press any key to continue!");
			
			keyboard.waitUntilTyped();
			
			CloseManager.close();
		} else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
