package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;

import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.settings.SettingsManager;

public class Installer {
	
	private File[] files;
	private SettingsManager s;
	
	public Installer(File[] files, KeyboardManager m, SettingsManager s) {
		this.files = files;
		this.s = s;
	}
	
	public void install() throws IOException {
		for(File file : files) {
			if(!file.exists()) {
				switch(file.getPath()) {
					case "plugins":
					case "logs":
						System.out.println("Installing: " + file.getPath());
						file.mkdirs();
						break;
					
					case "settings.jcio":
						System.out.println("Installing: " + file.getPath());
						file.createNewFile();
						s.reset();
						
						s.save();
						break;
				}
			}
		}
		
		System.out.println("Restart the program to finish the installment.");
		System.exit(0);
	}
	
}
