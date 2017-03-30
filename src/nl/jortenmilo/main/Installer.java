package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;

import nl.jortenmilo.settings.SettingsManager;

/**
 * This is the Installer. It installs files if they weren't installed already.
 * @author Jort
 *
 */
public class Installer {
	
	private File[] files;
	private SettingsManager s;
	
	public Installer(File[] files, SettingsManager s) {
		this.files = files;
		this.s = s;
	}
	
	/**
	 * Checks if all the files were installed. If they weren't it will install them.
	 */
	public void check() {
		boolean install = false;
		int missing = 0;
		
		for(File file : files) {
			if(!file.exists()) {
				install = true;
				missing++;
			}
		}
		
		if(install) {
			System.out.println("There are " + missing + " file(s) missing. Installing them now.");
			
			try {
				install();
			} 
			catch(Error | Exception e) {
				new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
			}
		}
	}
	
	private void install() throws IOException {
		for(File file : files) {
			if(!file.exists()) {
				switch(file.getPath()) {
					case "plugins":
					case "logs":
						file.mkdirs();
						break;
					
					case "settings.jcio":
						file.createNewFile();
						s.reset();
						
						s.save();
						break;
				}
			}
		}
	}
	
}
