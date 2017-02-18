package nl.jortenmilo.config;

import java.io.File;
import nl.jortenmilo.error.MissingObjectError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;

/**
 * This is the manager for controlling all of the configs. You can create, save and/or load configs. 
 * Use this method instead of creating all classes for the events to be called.
 */
public class ConfigManager {
	
	private ConfigLoader loader = new ConfigLoader();
	private EventManager events;
	
	public ConfigManager(EventManager events) {
		this.events = events;
	}
	
	/**
	 * Returns a newly created config. 
	 * It will execute the ConfigCreatedEvents when the config is successfully created.
	 * @return The new ConfigFile
	 */
	public ConfigFile createConfig() {
		ConfigFile config = new ConfigFile();
		
		ConfigCreatedEvent event = new ConfigCreatedEvent();
		event.setConfig(config);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return config;
	}
	
	/**
	 * Returns the config that is loaded from the specified file. 
	 * It will execute the ConfigLoadedEvents when the config is successfully loaded.
	 * @param file The file you want the config to load from
	 * @return The loaded ConfigFile
	 */
	public ConfigFile loadConfig(File file) {
		ConfigFile config = new ConfigFile();
		config.setFile(file);
		loader.load(config);
		
		ConfigLoadedEvent event = new ConfigLoadedEvent();
		event.setConfig(config);
		event.setFile(file);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return config;
	}
	
	/**
	 * This will save the specified config to it's file.
	 * It will execute all of the ConfigSavedEvents when the config is successfully saved.
	 * @param file The config you want to save
	 */
	public void saveConfig(ConfigFile file) {
		if(file.getFile() == null) {
			new MissingObjectError("ConfigFile", "file").print();
		}
		
		ConfigSavedEvent event = new ConfigSavedEvent();
		event.setConfig(file);
		event.setFile(file.getFile());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		loader.save(file);
	}
	
}
