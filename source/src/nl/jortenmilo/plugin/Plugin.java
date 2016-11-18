package nl.jortenmilo.plugin;

import java.io.File;

import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.PluginManager.LoadedPlugin;

public abstract class Plugin {
	
	private PluginManager pm;
	private CommandManager cm;
	private EventManager em;
	private LoadedPlugin lp;
	private File sf;
	
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
	
	public EventManager getEventManager() {
		return em;
	}
	
	protected void setEventManager(EventManager em) {
		this.em = em;
	}
	
	protected void setLoadedPlugin(LoadedPlugin lp) {
		this.lp = lp;
		sf = new File("plugins/" + lp.getName());
	}
	
	public LoadedPlugin getLoadedPlugin() {
		return lp;
	}
	
	public File getSaveFolder() {
		return sf;
	}
	
	public void createSaveFolder() {
		if(!sf.exists()) {
			sf.mkdirs();
		}
	}
}
