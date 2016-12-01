package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.main.CloseManager;

public class ExitCommand implements CommandExecutor {
	
	private KeyboardManager keyboard;
	
	public ExitCommand(KeyboardManager keyboard) {
		this.keyboard = keyboard;
	}

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
