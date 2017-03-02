package nl.jortenmilo.close;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;

public class CloseManager {
	
	private List<Closable> closables = new ArrayList<Closable>();
	private HashMap<Plugin, List<Closable>> pclosables = new HashMap<Plugin, List<Closable>>();
	
	private EventManager events;
	
	public CloseManager(EventManager events) {
		this.events = events;
	}
	
	public void addClosable(Closable closable, Plugin plugin) {
		if(closable == null) {
			new NullableParameterError("Closable", "closable").print();
			return;
		}
		
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		closables.add(closable);
		
		if(pclosables.get(plugin) == null) {
			pclosables.put(plugin, new ArrayList<Closable>());
		}
		
		List<Closable> l = pclosables.get(plugin);
		l.add(closable);
		
		pclosables.put(plugin, l);
	}
	
	public void addClosable(Closable closable) {
		if(closable == null) {
			new NullableParameterError("Closable", "closable").print();
			return;
		}
		
		closables.add(closable);
	}
	
	public void close(ClosablePriority priority) {
		if(priority == null) {
			new NullableParameterError("ClosablePriority", "priority").print();
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
			new NullableParameterError("Closable", "closable").print();
			return;
		}
		
		try {
			closable.close();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	public void close() {
		CloseAllEvent event = new CloseAllEvent();
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		close(ClosablePriority.LAUNCHER);
		close(ClosablePriority.HIGH);
		close(ClosablePriority.MEDIUM);
		close(ClosablePriority.LOW);
	}
	
}
