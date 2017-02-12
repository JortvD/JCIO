package nl.jortenmilo.main;

import java.io.File;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.command.defaults.DefaultCommands;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.error.ErrorManager;
import nl.jortenmilo.event.EventManager;
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
	private EventManager event;
	
	protected Launcher() {
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
		
		event = new EventManager(); // Loads first
		
		keyboard = new KeyboardManager(Console.getKeyboardInput(), event);
		settings = new SettingsManager(event);
		Console.setSettingsManager(settings);
		Console.setEventManager(event);
		checkForInstall();
		settings.load();
	}
	
	private void init() {
		CloseManager.setLauncher(this);
		
		plugin = new PluginManager();
		
		PluginLoader pl = new PluginLoader();
		plugin.setPluginLoader(pl);
		
		command = new CommandManager(event);
		mouse = new MouseManager(Console.getMouseInput(), event);
		console = new ConsoleManager();
		config = new ConfigManager(event);
		error = new ErrorManager(event);
		utils = new UtilsManager(event);
		
		plugin.setMouseManager(mouse);
		plugin.setConsoleManager(console);
		plugin.setKeyboardManager(keyboard);
		plugin.setCommandManager(command);
		plugin.setConfigManager(config);
		plugin.setSettingsManager(settings);
		plugin.setErrorManager(error);
		plugin.setUtilsManager(utils);
		plugin.setEventManager(event);
		
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
		
		/*TestListener listener = new TestListener();
		event.registerListener(listener, null);
		for(EventHandler handler : event.getHandlers(TestEvent.class)) {
			handler.execute(new TestEvent());
		}*/
	}

}