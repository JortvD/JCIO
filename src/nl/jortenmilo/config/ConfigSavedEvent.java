package nl.jortenmilo.config;

import java.io.File;

/**
 * This event is executed when a config is saved.
 */
public class ConfigSavedEvent extends ConfigEvent {
	
	private File file;
	
	/**
	 * Returns the file the config is saved to.
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	protected void setFile(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return "ConfigSavedEvent";
	}
	
}
