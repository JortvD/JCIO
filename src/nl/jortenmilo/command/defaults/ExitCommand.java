package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.input.KeyboardInput;
import nl.jortenmilo.main.CloseManager;

public class ExitCommand implements CommandExecutor {

	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("Exiting the program. Press any key to continue!");
			KeyboardInput.waitUntilTyped();
			CloseManager.close();
		} else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
