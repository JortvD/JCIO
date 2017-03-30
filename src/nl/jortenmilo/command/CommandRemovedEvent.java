package nl.jortenmilo.command;

/**
 * This event is executed when a command is removed from the CommandManager.
 * @see CommandManager
 */
public class CommandRemovedEvent extends CommandEvent {

	@Override
	public String getName() {
		return "CommandRemovedEvent";
	}

}
