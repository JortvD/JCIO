package nl.jortenmilo.command.defaults;

import nl.jortenmilo.close.CloseManager;
import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.keyboard.KeyboardManager;

/**
 * This class creates all of the default commands. There's nothing interesting you will find here.
 */
public class DefaultCommands {
	
	/**
	 * The method used to create all of the commands.
	 * @param command The CommandManager
	 * @param keyboard The KeyboardManager
	 * @param close The CloseManager
	 */
	public void create(CommandManager command, KeyboardManager keyboard, CloseManager close) {
		Command c1 = new Command();
		c1.setCommand("exit");
		c1.setDescription("This exits the program.");
		c1.addCommandExecutor(new ExitCommand(keyboard, close));
		
		command.addCommand(c1);
		
		Command c2 = new Command();
		c2.setCommand("help");
		c2.setDescription("This displays all the commands.");
		c2.addCommandExecutor(new HelpCommand(command));
		
		command.addCommand(c2);
		
		Command c3 = new Command();
		c3.setCommand("clear");
		c3.setDescription("Clears the display.");
		c3.addCommandExecutor(new ClearCommand(keyboard));
		
		command.addCommand(c3);
	}
	
}
