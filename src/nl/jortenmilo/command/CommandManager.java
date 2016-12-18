package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.jortenmilo.command.CommandEvent.CommandEventListener;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.error.CommandUsedError;
import nl.jortenmilo.error.InvalidParameterError;

public class CommandManager {
	
	private List<Command> commands = new ArrayList<Command>();
	private List<CommandEventListener> listeners = new ArrayList<CommandEventListener>();
	
	public void addCommand(Command c) {
		if(c.getCommand() == null) {
			//Throw an error when the command is null.
			new InvalidParameterError(c.getCommand()).print();
		}
		
		boolean exists = false;
		
		//Go through all the commands.
		for(Command command : commands) {
			//Check if this command is equal to the command.
			if(command.getCommand().equals(c.getCommand())) {
				exists = true;
			}
			
			//Check if these aliasses is equal to the command.
			for(String s : command.getAliasses()) {
				if(s.equalsIgnoreCase(c.getCommand())) {
					exists = true;
				}
			}
			
			//Check if this command is equal to the aliasses.
			for(String s : c.getAliasses()) {
				if(s.equalsIgnoreCase(command.getCommand())) {
					c.getAliasses().remove(s);
				}
			}
			
			//Check if these aliasses are equal to the aliasses.
			for(String s1 : c.getAliasses()) {
				for(String s2 : command.getAliasses()) {
					if(s1.equalsIgnoreCase(s2)) {
						c.getAliasses().remove(s1);
					}
				}
			}
		}
		
		if(exists) {
			//Throw an error when the command/aliasses already exist.
			new CommandUsedError(c.getCommand()).print();
		} else {
			//Add the command to the list.
			commands.add(c);
		}
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public void addListener(CommandEventListener listener) {
		listeners.add(listener);
	}
	
	public void executeCommand(Command cmd, String[] args) {
		//Create the arguments from the last arguments.
		String[] a = new String[args.length+1];
		
		a[0] = cmd.getCommand();
		
		for(int i = 0; i < 0; i++){
			a[i+1] = args[i];
		}
		
		//Execute the command in the new method.
		executeCommand(a);
	}
	
	public void executeCommand(String[] args) {
		String command = args[0];
		
		//Go through all the commands.
		for(Command c : commands) {
			//Check if this command is equal to the command.
			if(c.getCommand().equalsIgnoreCase(command)) {
				//Create the arguments from original arguments.
				String[] params = new String[args.length-1];
				
				for(int i = 0; i < args.length-1; i++) {
					params[i] = args[i+1];
				}
				
				//Create the PreExecute event.
				CommandPreExecuteEvent event = new CommandPreExecuteEvent();
				event.setArguments(args);
				event.setCommand(c);
				
				//Run all the listeners.
				for(CommandEventListener listener : listeners) {
					try {
						listener.onCommandPreExecute(event);
					} catch(Error | Exception e) {
						if(e instanceof NoSuchMethodException) return;
						new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
					}
				}
				
				//Run the command executor.
				try {
					c.getCommandExecutor().execute(command, c, params);
				} catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
				}
				
				CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
				event2.setArguments(args);
				event2.setCommand(c);
				
				for(CommandEventListener listener : listeners) {
					try {
						listener.onCommandPostExecute(event2);
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
					}
				}
				
				return;
			}
			for(String s : c.getAliasses()) {
				if(s.equalsIgnoreCase(command)) {
					String[] params = new String[args.length-1];
					
					for(int i = 0; i < args.length-1; i++) {
						params[i] = args[i+1];
					}
					
					CommandPreExecuteEvent event = new CommandPreExecuteEvent();
					event.setArguments(args);
					event.setCommand(c);
					
					for(CommandEventListener listener : listeners) {
						try {
							listener.onCommandPreExecute(event);
						} catch(Error | Exception e) {
							new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
						}
					}
					
					try {
						c.getCommandExecutor().execute(command, c, params);
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
					}
					
					CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
					event2.setArguments(args);
					event2.setCommand(c);
					
					for(CommandEventListener listener : listeners) {
						try {
							listener.onCommandPostExecute(event2);
						} catch(Error | Exception e) {
							new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
						}
					}
					
					return;
				}
			}
		}
		
		Console.println(ConsoleUser.Error, "Unknown command. Try 'help' for a list of all the commands. " + Arrays.toString(args));
	}
	
}
