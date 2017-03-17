package nl.jortenmilo.command;

import nl.jortenmilo.plugin.Plugin;

/**
 * This event is executed when a Command is added to the CommandManager.
 * @see CommandManager
 * @see Command
 */
public class CommandAddedEvent extends CommandEvent {
	
	private Plugin plugin;
	
	/**
	 * Returns the plugin that registered this command.
	 * @return The plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "CommandAddedEvent";
	}

}
