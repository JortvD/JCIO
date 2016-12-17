package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.settings.SettingsManager;

public class Installer {
	
	private File[] files;
	private KeyboardManager m;
	private SettingsManager s;
	
	public Installer(File[] files, KeyboardManager m, SettingsManager s) {
		this.files = files;
		this.m = m;
		this.s = s;
	}
	
	public void install() throws IOException {
		for(File file : files) {
			if(!file.exists()) {
				if(file.getPath().equals("plugins")) {
					Console.println("Installing: " + file.getPath());
					file.mkdirs();
				}
				
				if(file.getPath().equals("logs")) {
					Console.println("Installing: " + file.getPath());
					file.mkdirs();
				}
				
				if(file.getPath().equals("settings.jcio")) {
					Console.println("Installing: " + file.getPath());
					file.createNewFile();
					s.reset();
					
					s.load();
				}
			}
		}
		
		Console.println("Restart the program to finish the installment.");
		Console.println("Press any key to continue...");
		m.waitUntilTyped();
		System.exit(0);
	}
	
}
