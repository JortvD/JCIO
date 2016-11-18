package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.command.defaults.DefaultCommands;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.PluginLoader;
import nl.jortenmilo.plugin.PluginManager;
import nl.jortenmilo.settings.SettingsLoader;

public class Launcher {
	
	private File[] files = {new File("plugins"), new File("settings.jcio"), new File("logs")};
	private boolean running = true;
	private CommandManager cm;
	private PluginManager pm;
	private EventManager em;
	
	public Launcher() {
		Console.init();
		
		//Initialize the program
		Console.println("<- JCIO Loading->");
		init();
		Console.clear();
		
		
		//Start the program
		Console.println("<- JCIO [Jortenmilo (c) 2016]->");
		start();
		
	}
	
	private void start() {
		while(running) {
			String[] args = CommandDecoder.getParameters(Console.readln());
			cm.executeCommand(args);
		}
	}
	
	private void init() {
		CloseManager.setLauncher(this);
		
		Console.println("Checking if all the files are installed.");
		checkForInstall();
		
		Console.println("Loading all the settings.");
		try {
			SettingsLoader.load(files[1]);
		} catch (IOException e) {
			Console.println(ConsoleUser.Error, "Unknown Error: " + e.getMessage());
		}

		pm = new PluginManager();
		cm = new CommandManager();
		pm.setCommandManager(cm);
		em = new EventManager();
		em.setKeyboardInput(Console.getKeyboardInput());
		em.setMouseInput(Console.getMouseInput());
		pm.setEventManager(em);
		
		Console.println("Loading all the default commands.");
		initCommands();
		
		Console.println("Loading all the plugins.");
		PluginLoader pl = new PluginLoader();
		pl.load(pm);
		
		Console.println("Enabling all the plugins.");
		pm.enableAll();
	}
	
	private void checkForInstall() {
		boolean install = false;
		int missing = 0;
		
		for(File file : files) {
			if(!file.exists()) {
				install = true;
				missing++;
			}
		}
		
		if(install) {
			Console.println("There are " + missing + " file(s) missing. Installing them now.");
			Installer i = new Installer(files);
			
			try {
				i.install();
			} catch (IOException e) {
				Console.println(ConsoleUser.Error, "Unknown Error: " + e.getMessage());
			}
		} else {
			Console.println("All files are installed.");
		}
	}

	private void initCommands() {
		DefaultCommands dc = new DefaultCommands();
		dc.create(cm);
	}
	
	public void close() {
		try {
			SettingsLoader.save(files[1]);
			pm.disableAll();
			Console.close();
			System.exit(0);
		} catch (IOException e) {
			Console.println(ConsoleUser.Error, "Unknown Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		//new Launcher();
	}

}