package nl.jortenmilo.main;

import java.awt.event.KeyEvent;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandExecutor;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.input.KeyboardInput;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.settings.Settings;

public class SettingsIO extends Plugin implements CommandExecutor {

	@Override
	public void enable() {
		Command cmd = new Command();
		cmd.setCommand("settings");
		cmd.setDescription("All the settings commands");
		cmd.setCommandExecutor(this);
		this.getCommandManager().addCommand(cmd);
	}

	@Override
	public void disable() {
		
	}
	
	@Override
	public void execute(String s, Command cmd, String[] args) {
		if(args.length == 0) {
			Console.println(" ------------ SettingsIO ------------ ");
			Console.println("This are all the possible commands:");
			Console.println("settings add <key> 		- Creates a new setting.");
			Console.println("settings remove <key> 	- Removes a setting.");
			Console.println("settings contains <key> 	- Checks if that setting exists.");
			Console.println("settings set <key> <value> 	- Sets a setting's value.");
			Console.println("settings get <key>			- Gets a setting's value.");
			Console.println("settings save <key> 	- Adds a setting to the list of settings that will be saved.");
			Console.println("settings reset 		- Resets all of the settings.");
			Console.println("settings help 		- Displays this menu.");
			Console.println(" ------------ SettingsIO ------------ ");
		} else {
			if(args[0].equals("add")) {
				if(args.length == 2) {
					String key = args[1];
					Settings.add(key);
					Console.println("Created a new setting called: " + key);
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings add <key>");
				}
			}
			else if(args[0].equals("remove")) {
				if(args.length == 2) {
					String key = args[1];
					
					if(Settings.contains(key)) {
						Settings.remove(key);
						Console.println("Removed the setting called: " + key);
					} else {
						Console.println(ConsoleUser.Error, "The setting called '" + key + "' doesn't exist!");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings remove <key>");
				}
			}
			else if(args[0].equals("contains")) {
				if(args.length == 2) {
					String key = args[1];
					
					if(Settings.contains(key)) {
						Console.println("The setting called '" + key + "' exists.");
					} else {
						Console.println("The setting called '" + key + "' doesn't exist.");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings contains <key>");
				}
			}
			else if(args[0].equals("set")) {
				if(args.length == 3) {
					String key = args[1];
					String value = args[2];
					
					if(Settings.contains(key)) {
						Settings.set(key, value);
						Console.println("Set the setting called '" + key + "' to: " + value);
					} else {
						Console.println(ConsoleUser.Error, "The setting called '" + key + "' doesn't exist!");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings set <key> <value>");
				}
			}
			else if(args[0].equals("get")) {
				if(args.length == 2) {
					String key = args[1];
					
					if(Settings.contains(key)) {
						String value = Settings.get(key);
						Console.println("The value of the setting called '" + key + "' is: " + value);
					} else {
						Console.println(ConsoleUser.Error, "The setting called '" + key + "' doesn't exist!");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings get <key>");
				}
			}
			else if(args[0].equals("save")) {
				if(args.length == 2) {
					String key = args[1];
					
					if(Settings.contains(key)) {
						Settings.save(key);
						Console.println("The setting called '" + key + "' will now be saved.");
					} else {
						Console.println(ConsoleUser.Error, "The setting called '" + key + "' doesn't exist!");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings save <key>");
				}
			}
			else if(args[0].equals("reset")) {
				if(args.length == 1) {
					Console.println("Are you sure you want to reset all of the settings?");
					Console.println("Press Y (for YES) or N (for NO).");
					
					int i = KeyboardInput.waitUntilTyped(new int[]{KeyEvent.VK_Y, KeyEvent.VK_N, 110, 121});
					
					if(i == KeyEvent.VK_N || i == 110) {
						Console.println("Aborting the reset.");
					} else {
						Settings.reset();
						Console.println("All the settings are reset.");
					}
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings reset");
				}
			}
			else if(args[0].equals("help")) {
				if(args.length == 1) {
					Console.println(" ------------ SettingsIO ------------ ");
					Console.println("This are all the possible commands:");
					Console.println("settings add <key> 		- Creates a new setting.");
					Console.println("settings remove <key> 	- Removes a setting.");
					Console.println("settings contains <key> 	- Checks if that setting exists.");
					Console.println("settings set <key> <value> 	- Sets a setting's value.");
					Console.println("settings get <key>			- Gets a setting's value.");
					Console.println("settings save <key> 	- Adds a setting to the list of settings that will be saved.");
					Console.println("settings reset 		- Resets all of the settings.");
					Console.println("settings help 		- Displays this menu.");
					Console.println(" ------------ SettingsIO ------------ ");
				} else {
					Console.println(ConsoleUser.Error, "Wrong command usage: settings help");
				}
			} 
			else {
				Console.println(ConsoleUser.Error, "Unknown command, see 'settings help' for more information!");
			}
		}
	}

}
