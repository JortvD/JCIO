package nl.jortenmilo.main;

import java.io.File;

import nl.jortenmilo.close.Closable;
import nl.jortenmilo.close.CloseManager;
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

public class Launcher extends Closable {
	
	private File[] files = {
			new File("plugins"), 
			new File("settings.jcio"), 
			new File("logs")};
	
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
	private CloseManager close;
	
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
		close = new CloseManager(event);
		
		Console.setSettingsManager(settings);
		Console.setEventManager(event);
		Console.setCloseManager(close);
		
		Installer installer = new Installer(files, keyboard, settings);
		installer.check();
		
		settings.load();
	}
	
	private void init() {
		plugin = new PluginManager();
		
		PluginLoader pl = new PluginLoader();
		plugin.setPluginLoader(pl);
		
		command = new CommandManager(event);
		mouse = new MouseManager(Console.getMouseInput(), event);
		console = new ConsoleManager();
		config = new ConfigManager(event);
		error = new ErrorManager(event);
		utils = new UtilsManager(event);
		
		close.addClosable(this);
		
		plugin.setMouseManager(mouse);
		plugin.setConsoleManager(console);
		plugin.setKeyboardManager(keyboard);
		plugin.setCommandManager(command);
		plugin.setConfigManager(config);
		plugin.setSettingsManager(settings);
		plugin.setErrorManager(error);
		plugin.setUtilsManager(utils);
		plugin.setEventManager(event);
		plugin.setCloseManager(close);
		
		initCommands();
		
		plugin.loadAll();
		plugin.enableAll();
	}

	private void initCommands() {
		DefaultCommands dc = new DefaultCommands();
		dc.create(command, keyboard, close);
	}
	
	@Override
	public void close() {
		try {
			settings.save();
			
			plugin.disableAll();
			
			Console.close();
			System.exit(0);
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}

	public static void main(String[] args) throws Exception {
		Launcher launcher = new Launcher();
		
		launcher.preInit();
		
		// Initialize the program
		launcher.init();
		
		// Start the program
		Console.println("<- JCIO [Jortenmilo (c) 2017]->");
		launcher.start();
	}

}