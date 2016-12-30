package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.keyboard.KeyboardManager;

public class ClearCommand implements CommandExecutor {

	private KeyboardManager keyboard;
	
	public ClearCommand(KeyboardManager keyboard) {
		this.keyboard = keyboard;
	}

	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("Clearing the screen. Press any key to continue!");
			
			keyboard.waitUntilTyped();
			
			Console.clear();
		} else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
