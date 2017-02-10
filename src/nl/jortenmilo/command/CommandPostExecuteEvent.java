package nl.jortenmilo.command;

/**
 * This event is executed after a command is executed.
 */
public class CommandPostExecuteEvent extends CommandEvent {
	
	private String[] arguments;
	
	/**
	 * Returns the arguments that were used when this event was created.
	 * @return The arguments
	 */
	public String[] getArguments() {
		return arguments;
	}

	protected void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
	
}
