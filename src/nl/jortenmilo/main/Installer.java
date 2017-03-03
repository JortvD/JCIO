package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;

import nl.jortenmilo.settings.SettingsManager;

public class Installer {
	
	private File[] files;
	private SettingsManager s;
	
	public Installer(File[] files, SettingsManager s) {
		this.files = files;
		this.s = s;
	}
	
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
	
	public void install() throws IOException {
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
