package nl.jortenmilo.command;

import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.error.InvalidParameterError;

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
	private CommandExecutor ce;
	private List<String> aliasses = new ArrayList<String>();
	
	/**
	 * Creates an empty instance of this class, you will need to set the Command String and the CommandExecutor,
	 * before adding it to the CommandManager.
	 * @see CommandManager#addCommand(Command c)
	 */
	public Command() {}
	
	/**
	 * Creates an instance of this class, you have to set the the Command String and the CommandExecutor.
	 * @param command The command that will call this class' CommandExecutor.
	 * @param ce The CommandExecutor that is called when the command is used.
	 * @see CommandExecutor
	 */
	public Command(String command, CommandExecutor ce) {
		this.command = command;
		this.ce = ce;
	}
	
	/**
	 * Returns the command that is used in this instance.
	 * @return The command String
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Sets the command that will call the CommandExecutor when it is used. <p>
	 * Throws: <p>
	 * InvalidParameterError - When the command is null or "".
	 * @param command The command that will call this class' CommandExecutor.
	 * @see InvalidParameterError
	 */
	public void setCommand(String command) {
		if(command == null) {
			new InvalidParameterError(command).print();
		}
		
		else if(command.equals("")) {
			new InvalidParameterError(command).print();
		}
		
		this.command = command;
	}
	
	/**
	 * Returns the CommandExecutor that is called when the command of this class is used.
	 * @return The CommandExecutor
	 * @see CommandExecutor
	 */
	public CommandExecutor getCommandExecutor() {
		return ce;
	}
	
	/**
	 * Sets the CommandExecutor that will be called when the command of this class is used.
	 * @param ce The CommandExecutor
	 * @see CommandExecutor
	 */
	public void setCommandExecutor(CommandExecutor ce) {
		this.ce = ce;
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
	 * general CommandExecutor instead of having a separate CommandExecutor. <p>
	 * Throws: <p>
	 * InvalidParameterError - When the command is null or "".
	 * @param alias The alias you want to add to the list
	 */
	public void addAlias(String alias) {
		if(alias == null) {
			new InvalidParameterError(command).print();
		}
		
		else if(alias.equals("")) {
			new InvalidParameterError(command).print();
		}
		
		aliasses.add(alias);
	}
	
	/**
	 * Returns all the aliasses that are used in this instance.
	 * @return The list of aliasses
	 */
	public List<String> getAliasses() {
		return aliasses;
	}
	
}
