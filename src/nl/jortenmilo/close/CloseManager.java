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
 * This is the manager for all of the Closable related stuff. You can add Closables, remove them and/or call them.
 */
public class CloseManager {
	
	private List<Closable> closables = new ArrayList<Closable>();
	private HashMap<Plugin, List<Closable>> pclosables = new HashMap<Plugin, List<Closable>>();
	
	private EventManager events;
	
	public CloseManager(EventManager events) {
		this.events = events;
	}
	
	/**
	 * Used to register a new Closable. It also needs the Plugin for debugging purposes.
	 * This method executes all of the ClosableAddedEvents when it successfully registered.
	 * @param closable The Closable to register
	 * @param plugin The Plugin this closable is from
	 * @see Closable
	 * @see Plugin
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
		closable.setPlugin(plugin);
		
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
	
	/**
	 * Used to register a new Closable. It is better to use the other method to add a Closable, since this method is anonymous.
	 * This method executes all of the ClosableAddedEvents when it successfully registered.
	 * @param closable The Closable to register
	 * @see Closable
	 */
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
	
	/**
	 * Used to remove a Closable.
	 * This method executes all of the ClosableRemovedEvents when it successfully removed.
	 * @param closable The Closable to remove
	 * @see Closable
	 */
	public void removeClosable(Closable closable) {
		if(closable == null) {
			new NonNullableParameterError("Closable", "closable").print();
			return;
		}
		
		closables.remove(closable);
		closable.setPlugin(null);
		
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
	
	/**
	 * This method removes all the Closables from a certain Plugin.
	 * It will execute a ClosableRemovedEvent for every Closable that was removed.
	 * @param plugin The Plugin which Closables need to be removed
	 * @see Plugin
	 */
	public void removeClosables(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		for(Closable closable : pclosables.get(plugin)) {
			closables.remove(closable);
			closable.setPlugin(null);
			
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
	
	/**
	 * Returns a list of all the Closables there are.
	 * @return The list of Closables
	 * @see Closable
	 */
	public List<Closable> getClosables() {
		return new ArrayList<Closable>(closables);
	}
	
	/**
	 * Returns the list of Closables that were registered by a certain Plugin.
	 * @param plugin The plugin
	 * @return The list of Closables
	 * @see Plugin
	 */
	public List<Closable> getClosables(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		return new ArrayList<Closable>(pclosables.get(plugin));
	}
	
	/**
	 * Returns a list of Closables that all have a certain priority.
	 * @param priority The priority
	 * @return The list of Closables
	 * @see ClosablePriority
	 */
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
	
	/**
	 * Returns the Plugin that registered the specified Closable.
	 * @param closable The Closable
	 * @return The Plugin
	 * @see Closable
	 */
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
	
	/**
	 * Calls all the Closables with the specified priority.
	 * It will execute a ClosableCalledEvent for every Closable that was called.
	 * @param priority The priority
	 * @see ClosablePriority
	 */
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
	
	/**
	 * Calls the specified Closable.
	 * It will execute a ClosableCalledEvent when the Closable was called.
	 * @param closable The Closable
	 * @see Closable
	 */
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
		
		ClosableCalledEvent event = new ClosableCalledEvent();
		event.setClosable(closable);
		event.setPlugin(getPlugin(closable));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	/**
	 * Calls all of the Closables in order. Closables with higher priorities will be called after the Closables with a lower priority.
	 * It will execute a ClosableCalledEvent for every Closable that was called.
	 */
	public void close() {
		close(ClosablePriority.LOW);
		close(ClosablePriority.MEDIUM);
		close(ClosablePriority.HIGH);
		close(ClosablePriority.LAUNCHER);
	}
	
}
