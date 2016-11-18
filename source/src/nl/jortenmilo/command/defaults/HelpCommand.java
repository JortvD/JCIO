package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.utils.StringUtils;

public class HelpCommand implements CommandExecutor {
	
	private CommandManager cm;
	
	public HelpCommand(CommandManager cm) {
		this.cm = cm;
	}

	@Override
	public void execute(String command, Command cmd, String[] params) {
		if(params.length == 0) {
			Console.println("This are all the possible commands:");
			int l = 0;
			
			for(Command c : cm.getCommands()) {
				if(c.getCommand().length() > l) {
					l = c.getCommand().length();
				}
			}
			
			for(Command c : cm.getCommands()) {
				String text = c.getCommand();
				int a = l - text.length();
				
				text = StringUtils.addChars(text, ' ', a);
				
				text += " - " + c.getDescription();
				Console.println(text);
			}
			
			Console.println("So, what do you want to do?");
		} else {
			Console.println(ConsoleUser.Error, "Wrong command usage: exit");
		}
	}

}
