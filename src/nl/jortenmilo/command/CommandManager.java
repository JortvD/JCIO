package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.command.CommandEvent.CommandEventListener;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.error.CommandUsedError;
import nl.jortenmilo.error.InvalidParameterError;
import nl.jortenmilo.plugin.Plugin;

public class CommandManager {
	
	private List<Command> commands = new ArrayList<Command>();
	private HashMap<Plugin, List<Command>> pcommands = new HashMap<Plugin, List<Command>>();
	private List<CommandEventListener> listeners = new ArrayList<CommandEventListener>();
	private HashMap<Plugin, List<CommandEventListener>> plisteners = new HashMap<Plugin, List<CommandEventListener>>();
	
	public void addCommand(Command c, Plugin plugin) {
		if(c.getCommand() == null) {
			//Throw an error when the command is null.
			new InvalidParameterError(c.getCommand()).print();
		}
		
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
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
			
			if(pcommands.get(plugin)==null) pcommands.put(plugin, new ArrayList<Command>());
			
			List<Command> l = pcommands.get(plugin);
			l.add(c);
			
			pcommands.put(plugin, l);
			
			CommandAddedEvent event = new CommandAddedEvent();
			event.setCommand(c);
			
			for(CommandEventListener listener : listeners) {
				try {
					listener.onCommandAdded(event);
				} catch(Error | Exception e2) {
					new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
				}
			}
		}
	}
	
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
			
			CommandAddedEvent event = new CommandAddedEvent();
			event.setCommand(c);
			
			for(CommandEventListener listener : listeners) {
				try {
					listener.onCommandAdded(event);
				} catch(Error | Exception e2) {
					new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
				}
			}
		}
	}
	
	public void removeCommand(Command c) {
		commands.remove(c);
		
		Plugin plugin = getPlugin(c);
		List<Command> l = pcommands.get(plugin);
		l.remove(c);
		
		pcommands.put(plugin, l);
		
		CommandRemovedEvent event = new CommandRemovedEvent();
		event.setCommand(c);
		
		for(CommandEventListener listener : listeners) {
			try {
				listener.onCommandRemoved(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
			}
		}
	}
	
	public void removeCommands(Plugin plugin) {
		for(Command command : pcommands.get(plugin)) {
			commands.remove(command);
			
			CommandRemovedEvent event = new CommandRemovedEvent();
			event.setCommand(command);
			
			for(CommandEventListener listener : listeners) {
				try {
					listener.onCommandRemoved(event);
				} catch(Error | Exception e2) {
					new nl.jortenmilo.error.UnknownError(e2.toString(), e2.getMessage()).print();
				}
			}
		}
		pcommands.remove(plugin);
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public void addListener(CommandEventListener listener, Plugin plugin) {
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
			
			return;
		}
		
		listeners.add(listener);
		
		if(plisteners.get(plugin)==null) 
			plisteners.put(plugin, new ArrayList<CommandEventListener>());
		
		List<CommandEventListener> l = plisteners.get(plugin);
		l.add(listener);
		
		plisteners.put(plugin, l);
	}
	
	public List<CommandEventListener> getListeners() {
		return listeners;
	}
	
	public void removeListener(CommandEventListener listener) {
		if(listeners.contains(listener)) {
			listeners.remove(listener);
			return;
		}
		
		Plugin plugin = getPlugin(listener);
		
		if(!plisteners.containsValue(listener) || !plisteners.containsKey(plugin)) {
			return;
		}
		
		List<CommandEventListener> l = plisteners.get(plugin);
		l.remove(listener);
		
		plisteners.put(plugin, l);
	}
	
	public void removeListeners(Plugin plugin) {
		if(!plisteners.containsKey(plugin)) {
			return;
		}
		
		for(CommandEventListener listener : plisteners.get(plugin)) {
			listeners.remove(listener);
		}
		
		plisteners.remove(plugin);
	}
	
	private Plugin getPlugin(Command command) {
		for(Plugin plugin : pcommands.keySet()) {
			for(Command c : pcommands.get(plugin)) {
				if(c == command) return plugin;
			}
		}
		return null;
	}
	
	private Plugin getPlugin(CommandEventListener listener) {
		for(Plugin plugin : plisteners.keySet()) {
			for(CommandEventListener c : plisteners.get(plugin)) {
				if(c == listener) return plugin;
			}
		}
		return null;
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
						new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
					}
				}
				
				//Run the command executor.
				try {
					c.getCommandExecutor().execute(command, c, params);
				} catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
				}
				
				CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
				event2.setArguments(args);
				event2.setCommand(c);
				
				for(CommandEventListener listener : listeners) {
					try {
						listener.onCommandPostExecute(event2);
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
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
							new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
						}
					}
					
					try {
						c.getCommandExecutor().execute(command, c, params);
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
					}
					
					CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
					event2.setArguments(args);
					event2.setCommand(c);
					
					for(CommandEventListener listener : listeners) {
						try {
							listener.onCommandPostExecute(event2);
						} catch(Error | Exception e) {
							new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
						}
					}
					
					return;
				}
			}
		}
		
		Console.println(ConsoleUser.Error, "Unknown command. Try 'help' for a list of all the commands. " + Arrays.toString(args));
	}
	
}
