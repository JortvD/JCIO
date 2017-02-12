package nl.jortenmilo.command;

/**
 * This event is executed when a command is added to the CommandManager.
 * @see CommandManager
 */
public class CommandAddedEvent extends CommandEvent {

	@Override
	public String getName() {
		return "CommandAddedEvent";
	}

}
