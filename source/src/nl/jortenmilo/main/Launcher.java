package nl.jortenmilo.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.InvalidParameterError;
import nl.jortenmilo.plugin.PluginLoader;
import nl.jortenmilo.plugin.PluginManager;
import nl.jortenmilo.settings.Settings;
import nl.jortenmilo.settings.SettingsLoader;
import nl.jortenmilo.utils.SystemUtils;

public class Launcher {
	
	private File[] files = {new File("plugins"), new File("settings.jcio")};
	private boolean running = true;
	
	private CommandManager cm;
	private PluginManager pm;
	
	public Launcher() {
		//Initialize the program
		Console.println("<- JCIO Loading  ->");
		init();
		Console.clear();
		
		//Start the program
		Console.println("<- JCIO [Jortenmilo (c) 2016]  ->");
		start();
	}
	
	private void start() {
		Scanner in = new Scanner(System.in);
		while(running) {
			String[] args = null;
			if(Settings.get("time").equals("true")) {
				System.out.print("[YOU " + SystemUtils.getTime() + "]: ");
				args = CommandDecoder.getParameters(in.nextLine());
			}
			else if(Settings.get("time").equals("false")) {
				System.out.print("[YOU]: ");
				args = CommandDecoder.getParameters(in.nextLine());
			}
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
			e.printStackTrace();
		}

		pm = new PluginManager();
		cm = new CommandManager();
		pm.setCommandManager(cm);
		
		Console.println("Loading all the default commands.");
		initCommands();
		
		Console.println("Loading all the plugins.");
		PluginLoader pl = new PluginLoader();
		pl.load(pm);
		
		Console.println("Enabling all the plugins.");
		pm.enableAll();
	}

	private void initCommands() {
		DefaultCommands dc = new DefaultCommands();
		
		Command c1 = new Command();
		c1.setCommand("exit");
		c1.setDescription("This exits the program.");
		c1.setCommandExecutor(dc);
		cm.addCommand(c1);
		
		Command c2 = new Command();
		c2.setCommand("help");
		c2.setDescription("This displays all the commands.");
		c2.setCommandExecutor(dc);
		cm.addCommand(c2);
	}
	
	class DefaultCommands implements CommandExecutor {

		@Override
		public void execute(String command, Command cmd, String[] params) {
			if(command.equalsIgnoreCase("exit")) {
				Console.println("Exiting the program. Bye!");
				CloseManager.close();
			}
			else if(command.equalsIgnoreCase("help")) {
				Console.println("This are all the possible commands:");
				int l = 0;
				for(Command c : cm.getCommands()) {
					if(c.getCommand().length() > l) {
						l = c.getCommand().length();
					}
				}
				for(Command c : cm.getCommands()) {
					String text = c.getCommand();
					int a = l-text.length();
					for(int i = 0; i < a; i++) {
						text += " ";
					}
					text += " - " + c.getDescription();
					Console.println(text);
				}
				Console.println("So, what do you want to do?");
			}
		}
		
	}
	
	public void close() {
		try {
			SettingsLoader.save(files[1]);
			pm.disableAll();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			Console.println("There are " + missing + " files missing. Installing them now!");
			Installer i = new Installer(files);
			try {
				i.install();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Console.println("All files are installed!");
		}
	}

	public static void main(String[] args) {
		new Launcher();
	}

}