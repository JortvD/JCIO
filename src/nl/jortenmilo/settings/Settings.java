package nl.jortenmilo.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.UnknownSettingError;

public class Settings {
	
	private static HashMap<String, String> settings = new HashMap<String, String>();
	private static List<String> keys = new ArrayList<String>();
	private static List<String> saved = new ArrayList<String>();
	
	public static String get(String key) {
		if(!settings.containsKey(key)) {
			new UnknownSettingError(key).print();
		}
		return settings.get(key);
	}
	
	public static HashMap<String, String> getSettings() {
		return settings;
	}
	
	public static void set(String key, String value) {
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		settings.put(key, value);
	}
	
	public static void add(String key) {
		keys.add(key);
	}
	
	public static void remove(String key) {
		keys.remove(key);
	}
	
	public static void save(String key) {
		saved.add(key);
	}
	
	public static List<String> getSaved() {
		return saved;
	}
	
	public static void reset() {
		saved.clear();
		add("time");
		set("time", "true");
		save("time");
		add("log");
		set("log", "true");
		save("log");
	}

	public static boolean contains(String key) {
		return keys.contains(key);
	}
	
}
