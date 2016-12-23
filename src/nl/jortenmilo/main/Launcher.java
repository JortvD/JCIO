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
import nl.jortenmilo.config.ConfigFile;
import nl.jortenmilo.config.ConfigManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleManager;
import nl.jortenmilo.keyboard.KeyboardManager;
import nl.jortenmilo.mouse.MouseManager;
import nl.jortenmilo.plugin.PluginLoader;
import nl.jortenmilo.plugin.PluginManager;
import nl.jortenmilo.settings.SettingsManager;

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
	
	public Launcher() {
		preInit();
		
		//Initialize the program
		init();
		
		//Start the program
		Console.println("<- JCIO [Jortenmilo (c) 2016]->");
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
		
		plugin.setMouseManager(mouse);
		plugin.setConsoleManager(console);
		plugin.setKeyboardManager(keyboard);
		plugin.setCommandManager(command);
		plugin.setConfigManager(config);
		
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
				new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
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
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}

	public static void main(String[] args) throws Exception {
		final boolean DEBUG = false;
		final String VERSION = "0.1.0_1";
		
		if(DEBUG) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						BufferedReader br = new BufferedReader(new FileReader(new File("version.txt")));
						int v = Integer.parseInt(br.readLine());
						BufferedWriter bw = new BufferedWriter(new FileWriter(new File("version.txt")));
						bw.write((v+1)+"");
						br.close();
						bw.close();
						
						File src = new File("src/");
						File dst = new File("../Auto_Builds/Build " + VERSION + " - " + v);
						if(!src.exists()){
							System.out.println("Directory does not exist.");
						} else {
							try{
								System.out.println("Starting the DEBUG backup: " + VERSION + " - " + v);
								copyFolder(src, dst);
							} catch(IOException e){
								e.printStackTrace();
							}
						}

						System.out.println("Done with the backup!");
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
					}
				}
				
				public void copyFolder(File src, File dest) throws IOException {
					if(src.isDirectory()){
						if(!dest.exists()){
							dest.mkdir();
							System.out.println("Copied [DIR]: " + src.getCanonicalPath() + " > " + dest.getCanonicalPath());
						}
						String files[] = src.list();

						for (String file : files) {
							File srcFile = new File(src, file);
							File destFile = new File(dest, file);
							copyFolder(srcFile, destFile);
						}
					} else{
						InputStream in = new FileInputStream(src);
						OutputStream out = new FileOutputStream(dest);

						byte[] buffer = new byte[1024];

						int length;
						while ((length = in.read(buffer)) > 0){
							out.write(buffer, 0, length);
						}

						in.close();
						out.close();
						System.out.println("Copied [FIL]: " + src.getCanonicalPath() + " > " + dest.getCanonicalPath());
					}
				}
			});
			t.start();
			
		}
		
		new Launcher();
	}

}