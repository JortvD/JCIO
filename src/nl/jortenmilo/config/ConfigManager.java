package nl.jortenmilo.config;

import java.io.File;
import nl.jortenmilo.error.MissingObjectError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;

public class ConfigManager {
	
	private ConfigLoader loader = new ConfigLoader();
	private EventManager events;
	
	public ConfigManager(EventManager events) {
		this.events = events;
	}
	
	public ConfigFile createConfig() {
		ConfigFile config = new ConfigFile();
		
		ConfigCreatedEvent event = new ConfigCreatedEvent();
		event.setConfig(config);
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
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
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
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
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		loader.save(file);
	}
	
}
