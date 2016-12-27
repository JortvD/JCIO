package nl.jortenmilo.command;

/**
 * This class is created by the CommandManager. It contains all data that all Command events must have when they are created.
 * @see CommandManager
 * @see Command
 */
public class CommandEvent {
	
	private Command command;
	private String[] arguments;
	
	protected CommandEvent() {}
	
	/**
	 * Returns the command that was used when this event was created.
	 * @return The command
	 */
	public Command getCommand() {
		return command;
	}
	
	protected void setCommand(Command command) {
		this.command = command;
	}
	
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
	
	/**
	 * Use this interface as listener for all of your Command events. When you add an instance of this interface to the CommandManager
	 * it will execute one of the methods when that event has happened.
	 */
	public interface CommandEventListener {
		
		/**
		 * This method is executed before a command is executed. It has a CommandPreExecuteEvent object that contains all the data 
		 * about the event.
		 * @param e The event
		 * @see CommandPreExecuteEvent
		 */
		public void onCommandPreExecute(CommandPreExecuteEvent e);
		
		/**
		 * This method is executed after a command is executed. It has a CommandPostExecuteEvent object that contains all the data 
		 * about the event.
		 * @param e The event
		 * @see CommandPostExecuteEvent
		 */
		public void onCommandPostExecute(CommandPostExecuteEvent e);
		
		public void onCommandRemoved(CommandRemovedEvent e);
		
		public void onCommandAdded(CommandAddedEvent e);
	}

}
