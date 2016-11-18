package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.error.CommandUsedError;
import nl.jortenmilo.error.InvalidParameterError;

public class CommandManager {
	
	private List<Command> commands = new ArrayList<Command>();
	
	public void addCommand(Command c) {
		if(c.getCommand()==null) {
			new InvalidParameterError(c.getCommand()).print();
		}
		
		boolean exists = false;
		
		for(Command command : commands) {
			//Check: command == command
			if(command.getCommand().equals(c.getCommand())) {
				exists = true;
			}
			
			//Check: command == aliasses
			for(String s : command.getAliasses()) {
				if(s.equalsIgnoreCase(c.getCommand())) {
					exists = true;
				}
			}
			
			//Check: aliasses == command
			for(String s : c.getAliasses()) {
				if(s.equalsIgnoreCase(command.getCommand())) {
					c.getAliasses().remove(s);
				}
			}
			
			//Check: aliasses == aliasses
			for(String s1 : c.getAliasses()) {
				for(String s2 : command.getAliasses()) {
					if(s1.equalsIgnoreCase(s2)) {
						c.getAliasses().remove(s1);
					}
				}
			}
		}
		
		if(exists) {
			new CommandUsedError(c.getCommand()).print();
		} else {
			commands.add(c);
		}
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public void executeCommand(String[] args) {
		String command = args[0];
		
		for(Command c : commands) {
			if(c.getCommand().equalsIgnoreCase(command)) {
				String[] params = new String[args.length-1];
				
				for(int i = 0; i < args.length-1; i++) {
					params[i] = args[i+1];
				}
				
				c.getCommandExecutor().execute(command, c, params);
				
				return;
			}
			for(String s : c.getAliasses()) {
				if(s.equalsIgnoreCase(command)) {
					String[] params = new String[args.length-1];
					
					for(int i = 0; i < args.length-1; i++) {
						params[i] = args[i+1];
					}
					
					c.getCommandExecutor().execute(command, c, params);
					
					return;
				}
			}
		}
		Console.println(ConsoleUser.Error, "Unknown command. Try 'help' for a list of all the commands. " + Arrays.toString(args));
	}
	
}
