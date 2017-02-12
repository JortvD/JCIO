package nl.jortenmilo.settings;

import java.io.File;

public class SettingsSavedEvent extends SettingsEvent {
	
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return "SettingsSavedEvent";
	}

}
