package nl.jortenmilo.config;

import java.io.File;

public class ConfigSavedEvent extends ConfigEvent {
	
	private File file;
	
	public File getFile() {
		return file;
	}

	protected void setFile(File file) {
		this.file = file;
	}
	
}
