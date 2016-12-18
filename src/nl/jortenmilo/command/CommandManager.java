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
	
	public void addListener(CommandEventListener listener) {
		listeners.add(listener);
	}
	
	public void executeCommand(Command cmd, String[] args) {
		String[] a = new String[args.length+1];
		
		a[0] = cmd.getCommand();
		
		for(int i = 0; i < 0; i++){
			a[i+1] = args[i];
		}
		executeCommand(a);
	}
	
	public void executeCommand(String[] args) {
		String command = args[0];
		
		for(Command c : commands) {
			if(c.getCommand().equalsIgnoreCase(command)) {
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
						if(e instanceof NoSuchMethodException) return;
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
