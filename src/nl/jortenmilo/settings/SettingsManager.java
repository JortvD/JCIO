package nl.jortenmilo.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.UnknownSettingError;

public class SettingsManager {
	
	private HashMap<String, String> settings = new HashMap<String, String>();
	private List<String> keys = new ArrayList<String>();
	private List<String> saved = new ArrayList<String>();
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
	
	public void add(String key) {
		keys.add(key);
	}
	
	public void remove(String key) {
		keys.remove(key);
		if(saved.contains(key)) saved.remove(key);
	}
	
	public void save(String key) {
		saved.add(key);
	}
	
	public List<String> getSaved() {
		return saved;
	}
	
	public void reset() {
		saved.clear();
		
		add("time");
		set("time", "true");
		save("time");
		
		add("log");
		set("log", "true");
		save("log");
		
		add("foreground");
		set("foreground", "light_gray");
		save("foreground");
		
		add("background");
		set("background", "black");
		save("background");
		
		add("default_width");
		set("default_width", "1600");
		save("default_width");
		
		add("default_height");
		set("default_height", "800");
		save("default_height");
		
		add("default_title");
		set("default_title", "JCIO");
		save("default_title");
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
