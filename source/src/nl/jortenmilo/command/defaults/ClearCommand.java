package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.input.KeyboardInput;
import nl.jortenmilo.utils.StringUtils;

public class ClearCommand implements CommandExecutor {

	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("Clearing the screen. Press any key to continue!");
			KeyboardInput.waitUntilTyped();
			Console.clear();
		} else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
