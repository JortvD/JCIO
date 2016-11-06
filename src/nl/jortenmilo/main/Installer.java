package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.settings.Settings;
import nl.jortenmilo.settings.SettingsLoader;
import nl.jortenmilo.utils.SystemUtils;

public class Installer {
	
	private File[] files;
	
	public Installer(File[] files) {
		this.files = files;
	}
	
	public void install() throws IOException {
		for(File file : files) {
			if(!file.exists()) {
				if(file.getPath().equals("plugins")) {
					Console.println("Installing: " + file.getPath());
					file.mkdirs();
				}
				if(file.getPath().equals("settings.jcio")) {
					Console.println("Installing: " + file.getPath());
					file.createNewFile();
					Settings.reset();
					try {
						SettingsLoader.save(files[1]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Console.println("Restart the program to finish the installment!");
		System.exit(0);
	}
	
}
