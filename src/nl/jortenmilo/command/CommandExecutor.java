package nl.jortenmilo.command;

/**
 * This interface is executed when a command is used. You have to add the CommandExecutor to the Command for it to work.
 * @see CommandExecutor
 */
public interface CommandExecutor {
	
	/**
	 * This method is executed when the command is used. 
	 * @param command The command that is used
	 * @param cmd The Command object of the command that is used
	 * @param params The params that were used together with the command
	 */
	public void execute(String command, Command cmd, String[] params);

}
