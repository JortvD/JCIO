package nl.jortenmilo.plugin;

import nl.jortenmilo.command.CommandManager;

public abstract class Plugin {
	
	private PluginManager pm;
	private CommandManager cm;
	
	public abstract void enable();
	
	public abstract void disable();

	public PluginManager getPluginManager() {
		return pm;
	}

	protected void setPluginManager(PluginManager pm) {
		this.pm = pm;
	}

	public CommandManager getCommandManager() {
		return cm;
	}

	protected void setCommandManager(CommandManager cm) {
		this.cm = cm;
	}
	
}
