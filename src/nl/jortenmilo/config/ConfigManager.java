package nl.jortenmilo.config;

import java.io.File;

import nl.jortenmilo.error.MissingObjectError;

public class ConfigManager {
	
	private ConfigLoader loader = new ConfigLoader();
	
	public ConfigFile createConfig() {
		return new ConfigFile();
	}
	
	public ConfigFile loadConfig(File file) {
		ConfigFile config = new ConfigFile();
		config.setFile(file);
		loader.load(config);
		return config;
	}
	
	public void saveConfig(ConfigFile file) {
		if(file.getFile() == null) {
			new MissingObjectError("ConfigFile", "file").print();
		}
		loader.save(file);
	}
	
}
