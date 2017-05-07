package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.plugin.Plugin;

/**
 * Command is the class that you use for creating your own commands. When you add an instance of this class to the CommandManager 
 * this class' CommandExecutor will execute when the command is used. You can also add aliasses, an alias is an extra command that
 * will execute the same CommandExecutor as the class' command does.
 * @see CommandManager
 * @see CommandExecutor
 */
public class Command {
	
	private String command;
	private String desc = "";
	private List<CommandExecutor> executors = new ArrayList<CommandExecutor>();
	private HashMap<Plugin, List<CommandExecutor>> pexecutors = new HashMap<Plugin, List<CommandExecutor>>();
	private List<String> aliasses = new ArrayList<String>();
	private Plugin plugin;
	private boolean added = false;
	
	/**
	 * Creates an empty instance of this class, you will need to set the Command String and the CommandExecutor,
	 * before adding it to the CommandManager.
	 * @see CommandManager#addCommand(Command c)
	 */
	public Command() {}
	
	/**
	 * Creates an instance of this class, you have to set the the Command string.
	 * @param command The command that will call this class' CommandExecutors.
	 */
	public Command(String command) {
		if(command == null) {
			new NonNullableParameterError("Command", "command").print();
			return;
		}
		
		this.command = command;
	}
	
	/**
	 * Returns the command that is used in this instance.
	 * @return The command String
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Sets the command that causes the CommandExecutors to execute when it was used.
	 * @param command The command
	 */
	public void setCommand(String command) {
		if(command == null) {
			new NonNullableParameterError("String", "command").print();
			return;
		}
		if(command.equals("")) {
			new NonNullableParameterError("String", "command").print();
			return;
		}
		
		this.command = command;
	}
	
	/**
	 * Returns the CommandExecutor that is called when the command of this class is used.
	 * @return The CommandExecutor
	 * @see CommandExecutor
	 */
	public List<CommandExecutor> getCommandExecutors() {
		return executors;
	}
	
	/**
	 * Adds a CommandExecutor that will be called when this class' command is used.
	 * Please don't use this CommandExecutor, for it is anonymous.
	 * @param ce The CommandExecutor
	 * @see CommandExecutor
	 */
	public void addCommandExecutor(CommandExecutor ce) {
		if(ce == null) {
			new NonNullableParameterError("CommandExecutor", "ce").print();
			return;
		}
		
		executors.add(ce);
	}
	
	/**
	 * Adds a CommandExecutor that will be called when this class' command is used.
	 * This method needs a Plugin for debugging purposes.
	 * @param ce The CommandExecutor
	 * @param plugin The Plugin
	 * @see Plugin
	 * @see CommandExecutor
	 */
	public void addCommandExecutor(CommandExecutor ce, Plugin plugin) {
		if(ce == null) {
			new NonNullableParameterError("CommandExecutor", "ce").print();
			return;
		}
		else if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		executors.add(ce);
		
		if(pexecutors.get(plugin) == null) {
			pexecutors.put(plugin, new ArrayList<CommandExecutor>());
		}
		
		List<CommandExecutor> l = pexecutors.get(plugin);
		l.add(ce);
		
		pexecutors.put(plugin, l);
	}
	
	/**
	 * Clears all the CommandExecuters there are for this command.
	 * @see CommandExecutor
	 */
	public void clearCommandExecutors() {
		executors.clear();
		
		for(Plugin plugin : pexecutors.keySet()) {
			pexecutors.put(plugin, null);
		}
	}
	
	/**
	 * Removes a CommandExecutor.
	 * @param ce The CommandExecutor
	 * @see CommandExecutor
	 */
	public void removeCommandExecutor(CommandExecutor ce) {
		if(ce == null) {
			new NonNullableParameterError("CommandExecutor", "ce").print();
			return;
		}
		
		executors.remove(ce);
	}
	
	/**
	 * Removes all the CommandExecutors that were added by a certain Plugin.
	 * @param plugin The plugin
	 * @see Plugin
	 * @see CommandExecutor
	 */
	public void removeCommandExecutors(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		for(CommandExecutor ce : pexecutors.get(plugin)) {
			executors.remove(ce);
		}
		
		pexecutors.put(plugin, null);
	}
	
	/**
	 * Returns a list of all the CommandExecutors that were added by the specified Plugin.
	 * @param plugin The Plugin
	 * @return The list of CommandExecutors
	 * @see CommandExecutor
	 * @see Plugin
	 */
	public List<CommandExecutor> getCommandExecutors(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		return pexecutors.get(plugin);
	}
	
	/**
	 * Returns the Plugin that added the specified CommandExecutor.
	 * @param ce The CommandExecutor
	 * @return the Plugin
	 * @see Plugin
	 * @see CommandExecutor
	 */
	public Plugin getPlugin(CommandExecutor ce) {
		if(ce == null) {
			new NonNullableParameterError("CommandExecutor", "ce").print();
			return null;
		}
		
		for(Plugin plugin : pexecutors.keySet()) {
			for(CommandExecutor c : pexecutors.get(plugin)) {
				if(c == ce) {
					return plugin;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns if this command already contains that CommandExecutor.
	 * @param ce The CommandExecutor
	 * @return If this command contains that CommandExecutor
	 * @see CommandExecutor
	 */
	public boolean containsCommandExecutor(CommandExecutor ce) {
		if(ce == null) {
			new NonNullableParameterError("CommandExecutor", "ce").print();
			return false;
		}
		
		return executors.contains(ce);
	}
	
	/**
	 * Returns the description of this command, the description is shown when you use the command "<code>help</code>".
	 * @return The description
	 */
	public String getDescription() {
		return desc;
	}
	
	/**
	 * Sets the description of this command, the description is shown when you use the command "<code>help</code>".
	 * @param desc The description of this command
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Adds an alias to the list of aliasses. An alias is almost the same as a command, but when it is used it will execute the 
	 * general CommandExecutor instead of having a separate CommandExecutor. 
	 * @param alias The alias you want to add to the list
	 */
	public void addAlias(String alias) {
		if(alias == null) {
			new NonNullableParameterError("String", "alias").print();
			return;
		}
		if(alias.equals("")) {
			new NonNullableParameterError("String", "alias").print();
			return;
		}
		
		aliasses.add(alias);
	}
	
	/**
	 * Removes that alias from this command.
	 * @param alias The alias you want to add
	 */
	public void removeAlias(String alias) {
		if(alias == null) {
			new NonNullableParameterError("String", "alias").print();
			return;
		}
		if(alias.equals("")) {
			new NonNullableParameterError("String", "alias").print();
			return;
		}
		
		aliasses.remove(alias);
	}
	
	/**
	 * Returns if this command already contains this alias.
	 * @param alias The alias
	 * @return If this command contains that alias
	 */
	public boolean containsAlias(String alias) {
		if(alias == null) {
			new NonNullableParameterError("String", "alias").print();
			return false;
		}
		if(alias.equals("")) {
			new NonNullableParameterError("String", "alias").print();
			return false;
		}
		
		return aliasses.contains(alias);
	}
	
	/**
	 * Clears all of the aliasses.
	 */
	public void clearAliasses() {
		aliasses.clear();
	}
	
	/**
	 * Returns all the aliasses that are used in this instance.
	 * @return The list of aliasses
	 */
	public List<String> getAliasses() {
		return aliasses;
	}
	
	/**
	 * Returns the plugin that registered this command.
	 * @return The plugin
	 * @see Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns if this command is added to the CommandManager.
	 * @return If this command is added to the CommandManager
	 */
	public boolean isAdded() {
		return added;
	}

	protected void setAdded(boolean added) {
		this.added = added;
	}
	
	/**
	 * Returns if this command is anonymous. This means that it was added to the CommandManager without a plugin assigned to it. When this plugin 
	 * hasn't been added to the CommandManager yet it will also return false.
	 * @return If this command is anonymous
	 */
	public boolean isAnonymous() {
		return (plugin == null && added);
	}
	
}
