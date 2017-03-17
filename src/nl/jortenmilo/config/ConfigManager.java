package nl.jortenmilo.config;

import java.io.File;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.MissingObjectError;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.utils.defaults.SystemUtils;

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
		
		Console.debug("CONFIG_CREATED [" + new SystemUtils().getTime() + "][" + config.hashCode() + "]");
		
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
		if(file == null) {
			new NonNullableParameterError("File", "file").print();
			return null;
		}
		
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
	 * @param config The config you want to save
	 */
	public void saveConfig(ConfigFile config) {
		if(config == null) {
			new NonNullableParameterError("ConfigFile", "config").print();
			return;
		}
		if(config.getFile() == null) {
			new MissingObjectError("File", "config.getFile()").print();
		}
		
		loader.save(config);
		
		ConfigSavedEvent event = new ConfigSavedEvent();
		event.setConfig(config);
		event.setFile(config.getFile());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
}
