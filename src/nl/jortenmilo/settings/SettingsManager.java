package nl.jortenmilo.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.UnknownSettingError;

public class SettingsManager {
	
	private HashMap<String, String> settings = new HashMap<String, String>();
	private List<String> keys = new ArrayList<String>();
	private SettingsLoader loader = new SettingsLoader();
	
	public String get(String key) {
		if(!settings.containsKey(key)) {
			new UnknownSettingError(key).print();
		}
		
		return settings.get(key);
	}
	
	public HashMap<String, String> getSettings() {
		return settings;
	}
	
	public void set(String key, String value) {
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		
		settings.put(key, value);
	}
	
	public void create(String key) {
		if(keys.contains(key)) {
			
		}
		keys.add(key);
	}
	
	public void remove(String key) {
		keys.remove(key);
	}
	
	public void reset() {
		keys.clear();
		settings.clear();
		
		create("time");
		set("time", "true");
		
		create("log");
		set("log", "true");
		
		create("foreground");
		set("foreground", "light_gray");
		
		create("background");
		set("background", "black");
		
		create("default_width");
		set("default_width", "1600");
		
		create("default_height");
		set("default_height", "800");
		
		create("default_title");
		set("default_title", "JCIO");
	}
	
	public void save() {
		try {
			loader.save(new File("settings.jcio"), this);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
	public void load() {
		try {
			loader.load(new File("settings.jcio"), this);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}

	public boolean contains(String key) {
		return keys.contains(key);
	}
	
}
