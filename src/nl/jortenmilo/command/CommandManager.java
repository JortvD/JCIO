package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.error.CommandUsedError;
import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;

/**
 * This is the manager for all of the command related stuff. You can add commands, add listeners and simulate commands.
 * @see Command
 */
public class CommandManager {
	
	private List<Command> commands = new ArrayList<Command>();
	private HashMap<Plugin, List<Command>> pcommands = new HashMap<Plugin, List<Command>>();
	private EventManager events;
	
	public CommandManager(EventManager events) {
		this.events = events;
	}
	
	/**
	 * Used to register a new command. It also needs the plugin for debugging purposes.
	 * This method executes all of the CommandAddedEvents when it successfully registered.
	 * @param command The command to register
	 * @param plugin The plugin this command is from
	 */
	public void addCommand(Command command, Plugin plugin) {
		if(command == null) {
			new NullableParameterError("Command", "command").print();
			return;
		}
		if(command.getCommand() == null) {
			new NullableParameterError("String", "command.getCommand()").print();
			return;
		}
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		boolean exists = false;
		
		//Go through all the commands.
		for(Command c : commands) {
			//Check if this command is equal to the command.
			if(c.getCommand().equals(command.getCommand())) {
				exists = true;
			}
			
			//Check if these aliasses is equal to the command.
			if(c.getAliasses().contains(command.getCommand())) {
				exists = true;
			}
			
			//Check if this command is equal to the aliasses.
			if(command.getAliasses().contains(c.getCommand())) {
				command.getAliasses().remove(c.getCommand());
			}
			
			//Check if these aliasses are equal to the aliasses.
			for(String s1 : c.getAliasses()) {
				for(String s2 : command.getAliasses()) {
					if(s1.equalsIgnoreCase(s2)) {
						command.getAliasses().remove(s1);
					}
				}
			}
		}
		
		if(exists) {
			//Throw an error when the command/aliasses already exist.
			new CommandUsedError(command.getCommand()).print();
		} 
		else {
			//Add the command to the list.
			commands.add(command);
			
			if(pcommands.get(plugin) == null) {
				pcommands.put(plugin, new ArrayList<Command>());
			}
			
			List<Command> l = pcommands.get(plugin);
			l.add(command);
			
			pcommands.put(plugin, l);
			
			CommandAddedEvent event = new CommandAddedEvent();
			event.setCommand(command);
			event.setPlugin(plugin);
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
		}
	}
	
	/**
	 * Used to anonymously register a new command. Please avoid this method at all cost! 
	 * This method executes all of the CommandAddedEvents when it successfully registered.
	 * @param command The command to register
	 */
	public void addCommand(Command command) {
		if(command == null) {
			new NullableParameterError("Command", "command").print();
			return;
		}
		
		boolean exists = false;
		
		//Go through all the commands.
		for(Command c : commands) {
			//Check if this command is equal to the command.
			if(c.getCommand().equals(command.getCommand())) {
				exists = true;
			}
			
			//Check if these aliasses is equal to the command.
			if(c.getAliasses().contains(command.getCommand())) {
				exists = true;
			}
			
			//Check if this command is equal to the aliasses.
			if(command.getAliasses().contains(c.getCommand())) {
				command.getAliasses().remove(c.getCommand());
			}
			
			//Check if these aliasses are equal to the aliasses.
			for(String s1 : c.getAliasses()) {
				for(String s2 : command.getAliasses()) {
					if(s1.equalsIgnoreCase(s2)) {
						command.getAliasses().remove(s1);
					}
				}
			}
		}
		
		if(exists) {
			//Throw an error when the command/aliasses already exist.
			new CommandUsedError(command.getCommand()).print();
		} 
		else {
			//Add the command to the list.
			commands.add(command);
			
			CommandAddedEvent event = new CommandAddedEvent();
			event.setCommand(command);
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
		}
	}
	
	/**
	 * Unregisters the command and disables the executor. 
	 * This method also executes all of the CommandRemovedEvents.
	 * @param command The command to unregister
	 */
	public void removeCommand(Command command) {
		if(command == null) {
			new NullableParameterError("Command", "command").print();
			return;
		}
		
		commands.remove(command);
		
		Plugin plugin = getPlugin(command);
		List<Command> l = pcommands.get(plugin);
		l.remove(command);
		
		pcommands.put(plugin, l);
		
		CommandRemovedEvent event = new CommandRemovedEvent();
		event.setCommand(command);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	/**
	 * Unregisters all the commands that are registered by that plugin. 
	 * It will also execute all of the CommandRemovedEvents for every command.
	 * @param plugin The plugin to unregister
	 */
	public void removeCommands(Plugin plugin) {
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		for(Command command : pcommands.get(plugin)) {
			commands.remove(command);
			
			CommandRemovedEvent event = new CommandRemovedEvent();
			event.setCommand(command);
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
		}
		
		pcommands.remove(plugin);
	}
	
	/**
	 * Returns all of the commands that are registered.
	 * @return A list of the commands
	 */
	public List<Command> getCommands() {
		return commands;
	}
	
	/**
	 * Returns all of the commands that are registered by a specific plugin.
	 * @param plugin The plugin
	 * @return A list of the commands
	 */
	public List<Command> getCommands(Plugin plugin) {
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		return pcommands.get(plugin);
	}
	
	/**
	 * Returns the plugin that registered that command.
	 * @param command The command
	 * @return A plugin
	 */
	public Plugin getPlugin(Command command) {
		if(command == null) {
			new NullableParameterError("Command", "command").print();
			return null;
		}
		
		for(Plugin plugin : pcommands.keySet()) {
			for(Command c : pcommands.get(plugin)) {
				if(c == command) {
					return plugin;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the command that belongs to the specified name.
	 * @param command The name or alias of the command
	 * @return The command (can be null)
	 */
	public Command getCommand(String command) {
		if(command == null) {
			new NullableParameterError("String", "command").print();
			return null;
		}
		
		for(Command c : commands) {
			if(c.getCommand().equalsIgnoreCase(command)) {
				return c;
			}
			
			if(c.getAliasses().contains(command)) {
				return c;
			}
		}
		
		return null;
	}
	
	/**
	 * Executes the command with the specified arguments.
	 * It will also execute all of the CommandPreExecuteEvents and the CommandPostExecuteEvents before and after the command is executed.
	 * @param command The command to execute
	 * @param args The specified arguments to use
	 */
	public void executeCommand(Command command, String[] args) {
		if(command == null) {
			new NullableParameterError("Command", "command").print();
			return;
		}
		if(args == null) {
			new NullableParameterError("String[]", "args").print();
			return;
		}
		
		//Create the arguments from the last arguments.
		String[] a = new String[args.length+1];
		
		a[0] = command.getCommand();
		
		for(int i = 0; i < 0; i++){
			a[i+1] = args[i];
		}
		
		//Execute the command in the new method.
		executeCommand(a);
	}
	
	/**
	 * Executes the command from that arguments. The first argument is the command that has to executed.
	 * It will also execute all of the CommandPreExecuteEvents and the CommandPostExecuteEvents before and after the command is executed.
	 * @param args The arguments and command that have to be executed
	 */
	public void executeCommand(String[] args) {
		if(args == null) {
			new NullableParameterError("String[]", "args").print();
			return;
		}
		
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
				event.setArguments(params);
				event.setCommand(c);
				
				//Run all the listeners.
				for(EventHandler handler : events.getHandlers(event.getClass())) {
					handler.execute(event);
				}
				
				//Run the command executor.
				try {
					c.getCommandExecutor().execute(command, c, params);
				} 
				catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
				}
				
				CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
				event2.setArguments(args);
				event2.setCommand(c);
				
				for(EventHandler handler : events.getHandlers(event2.getClass())) {
					handler.execute(event2);
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
					event.setArguments(params);
					event.setCommand(c);
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
					
					try {
						c.getCommandExecutor().execute(command, c, params);
					} 
					catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
					}
					
					CommandPostExecuteEvent event2 = new CommandPostExecuteEvent();
					event2.setArguments(args);
					event2.setCommand(c);
					
					for(EventHandler handler : events.getHandlers(event2.getClass())) {
						handler.execute(event2);
					}
					
					return;
				}
			}
		}
		
		Console.println(ConsoleUser.Error, "Unknown command. Try 'help' for a list of all the commands.");
	}
	
}
