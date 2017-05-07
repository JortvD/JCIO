package nl.jortenmilo.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;

public class Storagable {
	
	private HashMap<String, String> data = new HashMap<String, String>();
	private HashMap<String, Plugin> pdata = new HashMap<String, Plugin>();
	private int ID;
	private EventManager events;
	
	public Storagable(EventManager events) {
		this.events = events;
	}
	
	public void put(Plugin plugin, String key, String value) {
		data.put(key, value);
		pdata.put(key, plugin);
		
		StoragableStoredEvent event = new StoragableStoredEvent();
		event.setStoragable(this);
		event.setKey(key);
		event.setValue(value);
		event.setPlugin(plugin);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public String get(String key) {
		return data.get(data);
	}
	
	public Plugin getPlugin(String key) {
		return pdata.get(key);
	}
	
	public List<String> getKeys(Plugin plugin) {
		List<String> keys = new ArrayList<String>();
		
		for(String key : pdata.keySet()) {
			if(pdata.get(key) == plugin) {
				keys.add(key);
			}
		}
		
		return keys;
	}
	
	public void delete(String key) {
		StoragableDeletedEvent event = new StoragableDeletedEvent();
		event.setStoragable(this);
		event.setKey(key);
		event.setValue(data.get(key));
		event.setPlugin(pdata.get(key));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		data.put(key, null);
		pdata.put(key, null);
	}
	
	public void reset(Plugin plugin) {
		for(String key : data.keySet()) {
			data.put(key, null);
			pdata.put(key, null);
		}
		
		StoragableResetEvent event = new StoragableResetEvent();
		event.setStoragable(this);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

	public int getID() {
		return ID;
	}

	protected void setID(int ID) {
		this.ID = ID;
	}
}
