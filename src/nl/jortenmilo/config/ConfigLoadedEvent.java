package nl.jortenmilo.config;

import java.io.File;

/**
 * This event is executed when a config is loaded.
 */
public class ConfigLoadedEvent extends ConfigEvent {
	
	private File file;
	
	/**
	 * Returns the file the config is loaded from.
	 * @return
	 */
	public File getFile() {
		return file;
	}

	protected void setFile(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return "ConfigLoadedEvent";
	}
	
}
