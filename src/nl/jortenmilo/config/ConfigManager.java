package nl.jortenmilo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.command.CommandEvent.CommandEventListener;
import nl.jortenmilo.config.ConfigEvent.ConfigEventListener;
import nl.jortenmilo.error.InvalidParameterError;
import nl.jortenmilo.error.MissingObjectError;
import nl.jortenmilo.plugin.Plugin;

public class ConfigManager {
	
	private ConfigLoader loader = new ConfigLoader();
	private List<ConfigEventListener> listeners = new ArrayList<ConfigEventListener>();
	private HashMap<Plugin, List<ConfigEventListener>> plisteners = new HashMap<Plugin, List<ConfigEventListener>>();
	
	public ConfigFile createConfig() {
		ConfigFile config = new ConfigFile();
		
		ConfigCreatedEvent event = new ConfigCreatedEvent();
		event.setConfig(config);
		
		for(ConfigEventListener listener : listeners) {
			try {
				listener.onConfigCreated(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		return config;
	}
	
	public ConfigFile loadConfig(File file) {
		ConfigFile config = new ConfigFile();
		config.setFile(file);
		loader.load(config);
		
		ConfigLoadedEvent event = new ConfigLoadedEvent();
		event.setConfig(config);
		event.setFile(file);
		
		for(ConfigEventListener listener : listeners) {
			try {
				listener.onConfigLoaded(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		return config;
	}
	
	public void saveConfig(ConfigFile file) {
		if(file.getFile() == null) {
			new MissingObjectError("ConfigFile", "file").print();
		}
		
		ConfigSavedEvent event = new ConfigSavedEvent();
		event.setConfig(file);
		event.setFile(file.getFile());
		
		for(ConfigEventListener listener : listeners) {
			try {
				listener.onConfigSaved(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		loader.save(file);
	}
	
	public void addListener(ConfigEventListener listener, Plugin plugin) {
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
			return;
		}
		
		listeners.add(listener);
		
		List<ConfigEventListener> l = plisteners.get(plugin);
		l.add(listener);
		plisteners.put(plugin, l);
	}
	
	public List<ConfigEventListener> getListeners() {
		return listeners;
	}
	
	public void removeListener(ConfigEventListener listener) {
		listeners.remove(listener);
	}
	
	public void removeListeners(Plugin plugin) {
		for(ConfigEventListener listener : plisteners.get(plugin)) {
			listeners.remove(listener);
		}
		plisteners.remove(plugin);
	}
	
}
