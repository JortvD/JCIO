package nl.jortenmilo.storage;

import java.util.HashMap;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;

public class StorageManager {

	private HashMap<Integer, Storagable> storagables = new HashMap<Integer, Storagable>();
	private EventManager events;
	
	public StorageManager(EventManager events) {
		this.events = events;
	}
	
	public Storagable create(Object object) {
		int id = object.hashCode();
		
		Storagable storagable = new Storagable(events);
		storagable.setID(id);
		
		storagables.put(id, storagable);
		
		StoragableCreatedEvent event = new StoragableCreatedEvent();
		event.setStoragable(storagable);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return storagable;
	}
	
	public void remove(Object object) {
		int id = object.hashCode();
		
		StoragableRemovedEvent event = new StoragableRemovedEvent();
		event.setStoragable(storagables.get(id));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		storagables.put(id, null);
	}
	
	public Storagable get(Object object) {
		return storagables.get(object.hashCode());
	}
	
	public Storagable get(int id) {
		return storagables.get(id);
	}
	
}
