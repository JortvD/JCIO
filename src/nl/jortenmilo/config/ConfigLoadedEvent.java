package nl.jortenmilo.config;

import java.io.File;

public class ConfigLoadedEvent extends ConfigEvent {
	
	private File file;
	
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
