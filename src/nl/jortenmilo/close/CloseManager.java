package nl.jortenmilo.close;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This is the manager for all of the closable related stuff. You can add Closables, remove them and call them.
 */
public class CloseManager {
	
	private List<Closable> closables = new ArrayList<Closable>();
	private HashMap<Plugin, List<Closable>> pclosables = new HashMap<Plugin, List<Closable>>();
	
	private EventManager events;
	
	public CloseManager(EventManager events) {
		this.events = events;
	}
	
	/**
	 * Used to register a new closable. It also needs the plugin for debugging purposes.
	 * This method executes all of the ClosableAddedEvents when it successfully registered.
	 * @param closable The closable to register
	 * @param plugin The plugin this closable is from
	 */
	public void addClosable(Closable closable, Plugin plugin) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return;
		}
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		Console.debug("CLOSABLE_ADDED [" + new SystemUtils().getTime() + "][" + closable.getClass().getName() + "][" + closable.getPriority() + "][" + plugin.getLoadedPlugin().getName() + "]");
		
		closables.add(closable);
		
		if(pclosables.get(plugin) == null) {
			pclosables.put(plugin, new ArrayList<Closable>());
		}
		
		List<Closable> l = pclosables.get(plugin);
		l.add(closable);
		
		pclosables.put(plugin, l);
		
		ClosableAddedEvent event = new ClosableAddedEvent();
		event.setClosable(closable);
		event.setPlugin(plugin);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void addClosable(Closable closable) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return;
		}
		
		Console.debug("CLOSABLE_ADDED [" + new SystemUtils().getTime() + "][" + closable.getClass().getName() + "][" + closable.getPriority() + "][null]");
		
		ClosableAddedEvent event = new ClosableAddedEvent();
		event.setClosable(closable);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		closables.add(closable);
	}
	
	public void removeClosable(Closable closable) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return;
		}
		
		closables.remove(closable);
		
		Console.debug("CLOSABLE_REMOVED [" + new SystemUtils().getTime() + "][" + closable.getClass().getName() + "]");
		
		Plugin plugin = getPlugin(closable);
		
		if(plugin != null) {
			List<Closable> l = pclosables.get(plugin);
			l.remove(closable);
		
			pclosables.put(plugin, l);
		}
		
		ClosableRemovedEvent event = new ClosableRemovedEvent();
		event.setClosable(closable);
		event.setPlugin(plugin);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void removeClosables(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		for(Closable closable : pclosables.get(plugin)) {
			closables.remove(closable);
			
			Console.debug("CLOSABLE_REMOVED [" + new SystemUtils().getTime() + "][" + closable.getClass().getName() + "]");
			
			ClosableRemovedEvent event = new ClosableRemovedEvent();
			event.setClosable(closable);
			event.setPlugin(plugin);
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
		}
		
		pclosables.remove(plugin);
	}
	
	public List<Closable> getClosables() {
		List<Closable> clone = new ArrayList<Closable>();
		clone.addAll(closables);
		
		return clone;
	}
	
	public List<Closable> getClosables(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		return pclosables.get(plugin);
	}
	
	public List<Closable> getClosables(ClosablePriority priority) {
		if(priority == null) {
			new NonNullableParameterError("ClosablePriority", "priority").print();
			return null;
		}
		
		List<Closable> cs = new ArrayList<Closable>();
		
		for(Closable closable : closables) {
			if(closable.getPriority() == priority) {
				cs.add(closable);
			}
		}
		
		return cs;
	}
	
	public Plugin getPlugin(Closable closable) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return null;
		}
		
		for(Plugin plugin : pclosables.keySet()) {
			for(Closable c : pclosables.get(plugin)) {
				if(c == closable) {
					return plugin;
				}
			}
		}
		
		return null;
	}
	
	public void close(ClosablePriority priority) {
		if(priority == null) {
			new NonNullableParameterError("ClosablePriority", "priority").print();
			return;
		}
		
		for(Closable closable : closables) {
			if(closable.getPriority() == priority) {
				close(closable);
			}
		}
	}
	
	public void close(Closable closable) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return;
		}
		
		Console.debug("CLOSE [" + new SystemUtils().getTime() + "][" + closable.getClass().getName() + "]");
		
		try {
			closable.close();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	public void close() {
		close(ClosablePriority.LOW);
		close(ClosablePriority.MEDIUM);
		close(ClosablePriority.HIGH);
		close(ClosablePriority.LAUNCHER);
	}
	
}
