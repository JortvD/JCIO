package nl.jortenmilo.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.command.defaults.DefaultCommands;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.error.ErrorManager;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.mouse.MouseManager;
import nl.jortenmilo.plugin.PluginLoader;
import nl.jortenmilo.plugin.PluginManager;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.UtilsManager;

public class Launcher {
	
	private File[] files = {new File("plugins"), new File("settings.jcio"), new File("logs")};
	private boolean running = true;
	
	private CommandManager command;
	private PluginManager plugin;
	private KeyboardManager keyboard;
	private MouseManager mouse;
	private ConsoleManager console;
	private SettingsManager settings;
	private ConfigManager config;
	private ErrorManager error;
	private UtilsManager utils;
	
	public Launcher() {
		preInit();
		
		//Initialize the program
		init();
		
		//Start the program
		Console.println("<- JCIO [Jortenmilo (c) 2017]->");
		start();
		
	}
	
	private void start() {
		while(running) {
			String[] args = CommandDecoder.getParameters(Console.readln());
			command.executeCommand(args);
		}
	}
	
	private void preInit() {
		Console.init();
		keyboard = new KeyboardManager(Console.getKeyboardInput());
		settings = new SettingsManager();
		Console.setSettingsManager(settings);
		checkForInstall();
		settings.load();
	}
	
	private void init() {
		CloseManager.setLauncher(this);
		
		plugin = new PluginManager();
		PluginLoader pl = new PluginLoader();
		plugin.setPluginLoader(pl);
		command = new CommandManager();
		mouse = new MouseManager(Console.getMouseInput());
		console = new ConsoleManager();
		config = new ConfigManager();
		error = new ErrorManager();
		utils = new UtilsManager();
		
		plugin.setMouseManager(mouse);
		plugin.setConsoleManager(console);
		plugin.setKeyboardManager(keyboard);
		plugin.setCommandManager(command);
		plugin.setConfigManager(config);
		plugin.setSettingsManager(settings);
		plugin.setErrorManager(error);
		plugin.setUtilsManager(utils);
		
		initCommands();
		
		plugin.loadAll();
		
		plugin.enableAll();
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
			System.out.println("There are " + missing + " file(s) missing. Installing them now.");
			Installer i = new Installer(files, keyboard, settings);
			
			try {
				i.install();
			} catch(Error | Exception e) {
				new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
			}
		}
	}

	private void initCommands() {
		DefaultCommands dc = new DefaultCommands();
		dc.create(command, keyboard);
	}
	
	public void close() {
		try {
			settings.save();
			
			plugin.disableAll();
			
			Console.close();
			System.exit(0);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}

	public static void main(String[] args) throws Exception {
		new Launcher();
	}

}