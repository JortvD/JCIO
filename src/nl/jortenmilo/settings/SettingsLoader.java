package nl.jortenmilo.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import nl.jortenmilo.console.Console;

public class SettingsLoader {

	public void load(File f, SettingsManager settings) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		int amount = 0;
		
		while((line=br.readLine())!=null) {
			if(line.startsWith("#") || line.equals("")) {
				continue;
			} 
			
			byte[] bytes = line.getBytes();
			String key = "";
			String value = "";
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i]==58) {
					key = line.substring(0, i);
					value = line.substring(i+2);
				} 
			}
			
			settings.create(key);
			settings.set(key, value);
			amount++;
		}
		
		br.close();
		Console.println("There were " + amount + " settings loaded.");
	}
	
	public void save(File f, SettingsManager settings) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		for(String key : settings.getSettings().keySet()) {
			String value = settings.get(key);
			bw.write(key + ": " + value);
			bw.newLine();
		}
		
		bw.close();
	}
	
}
