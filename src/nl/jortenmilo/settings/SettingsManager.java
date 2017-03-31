package nl.jortenmilo.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.ExistingSettingError;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.error.UnknownSettingError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.utils.defaults.SystemUtils;

public class SettingsManager {
	
	private HashMap<String, String> settings = new HashMap<String, String>();
	private List<String> keys = new ArrayList<String>();
	private SettingsLoader loader = new SettingsLoader();
	private EventManager events;
	
	public SettingsManager(EventManager events) {
		this.events = events;
	}
	
	public String get(String key) {
		if(key == null) {
			new NonNullableParameterError("String", "key").print();
			return null;
		}
		
		if(!settings.containsKey(key)) {
			new UnknownSettingError(key).print();
		}
		
		return settings.get(key);
	}
	
	public void set(String key, String value) {
		if(key == null) {
			new NonNullableParameterError("String", "key").print();
			return;
		}
		if(value == null) {
			new NonNullableParameterError("String", "value").print();
			return;
		}
		
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		
		Console.debug("SETTING_SET [" + new SystemUtils().getTime() + "][" + key + "][" + value + "]");
		
		settings.put(key, value);
		
		SettingsChangedEvent event = new SettingsChangedEvent();
		event.setKey(key);
		event.setValue(value);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		Console.update();
	}
	
	public void create(String key) {
		if(key == null) {
			new NonNullableParameterError("String", "key").print();
			return;
		}
		
		if(keys.contains(key)) {
			new ExistingSettingError(key).print();
		}
		
		Console.debug("SETTING_CREATE [" + new SystemUtils().getTime() + "][" + key + "]");
		
		keys.add(key);
		settings.put(key, "");
		
		SettingsCreatedEvent event = new SettingsCreatedEvent();
		event.setKey(key);
		event.setValue(settings.get(key));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public boolean contains(String key) {
		if(key == null) {
			new NonNullableParameterError("String", "key").print();
			return false;
		}
		
		return keys.contains(key);
	}
	
	public void remove(String key) {
		if(key == null) {
			new NonNullableParameterError("String", "key").print();
			return;
		}
		
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		
		Console.debug("SETTING_REMOVED [" + new SystemUtils().getTime() + "][" + key + "]");
		
		keys.remove(key);
		
		SettingsRemovedEvent event = new SettingsRemovedEvent();
		event.setKey(key);
		event.setValue(settings.get(key));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public HashMap<String, String> getSettings() {
		return settings;
	}
	
	public void reset() {
		Console.debug("SETTING_RESET [" + new SystemUtils().getTime() + "]");
		
		keys.clear();
		settings.clear();
		
		create("time");
		set("time", "true");
		
		create("log");
		set("log", "true");
		
		create("foreground");
		set("foreground", "192_192_192");
		
		create("background");
		set("background", "0_0_0");
		
		create("default_width");
		set("default_width", "1600");
		
		create("default_height");
		set("default_height", "800");
		
		create("default_title");
		set("default_title", "JCIO");
		
		SettingsResetEvent event = new SettingsResetEvent();
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void save() {
		Console.debug("SETTING_SAVED [" + new SystemUtils().getTime() + "]");
		
		try {
			loader.save(new File("settings.jcio"), this);
			
			SettingsSavedEvent event = new SettingsSavedEvent();
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
			
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	public void load() {
		Console.debug("SETTING_LOADED [" + new SystemUtils().getTime() + "]");
		
		try {
			loader.load(new File("settings.jcio"), this);
			
			SettingsLoadedEvent event = new SettingsLoadedEvent();
			
			for(EventHandler handler : events.getHandlers(event.getClass())) {
				handler.execute(event);
			}
			
		} 
		catch(Error | Exception e) {
			e.printStackTrace();
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
}
