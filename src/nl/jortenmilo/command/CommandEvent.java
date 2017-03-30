package nl.jortenmilo.command;

import nl.jortenmilo.event.Event;

/**
 * This is the general CommandEvent. All CommandEvents instantiate this class since it contains the general information about a CommandEvent.
 * @see CommandManager
 * @see Command
 */
public abstract class CommandEvent extends Event {
	
	private Command command;
	
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

}
