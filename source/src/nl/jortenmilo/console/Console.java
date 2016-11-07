package nl.jortenmilo.console;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.settings.Settings;
import nl.jortenmilo.utils.SystemUtils;
import nl.jortenmilo.utils.SystemUtils.Platform;

public class Console {
	
	public void init() {
		
	}
	
	public static void println(ConsoleUser user, String s) {
		if(!Settings.contains("time")) {
			String time = SystemUtils.getTime();
			if(user == ConsoleUser.System) {
				System.out.println("[SYS " + time + "]: " + s);
			}
			else if(user == ConsoleUser.User) {
				System.out.println("[YOU " + time + "]: " + s);
			}
			else if(user == ConsoleUser.Error) {
				System.out.println("[ERR " + time + "]: " + s);
			}
			else if(user == ConsoleUser.Empty) {
				System.out.println("[" + time + "]: " + s);
			}
			return;
		}
		if(Settings.get("time").equals("true")) {
			String time = SystemUtils.getTime();
			if(user == ConsoleUser.System) {
				System.out.println("[SYS " + time + "]: " + s);
			}
			else if(user == ConsoleUser.User) {
				System.out.println("[YOU " + time + "]: " + s);
			}
			else if(user == ConsoleUser.Error) {
				System.out.println("[ERR " + time + "]: " + s);
			}
			else if(user == ConsoleUser.Empty) {
				System.out.println("[" + time + "]: " + s);
			}
		}
		else if(Settings.get("time").equals("false")) {
			if(user == ConsoleUser.System) {
				System.out.println("[SYS]: " + s);
			}
			else if(user == ConsoleUser.User) {
				System.out.println("[YOU]: " + s);
			}
			else if(user == ConsoleUser.Error) {
				System.out.println("[ERR]: " + s);
			}
			else if(user == ConsoleUser.Empty) {
				System.out.println("[]: " + s);
			}
		}
	}
	
	public static void println(String s) {
		if(!Settings.contains("time")) {
			System.out.println("[SYS " + SystemUtils.getTime() + "]: " + s);
			return;
		}
		if(Settings.get("time").equals("true")) {
			System.out.println("[SYS " + SystemUtils.getTime() + "]: " + s);
		}
		else if(Settings.get("time").equals("false")) {
			System.out.println("[SYS]: " + s);
		}
	}
	
	public static void write(String s) {
		System.out.print(s);
	}
	
	public static void writeln(String s) {
		System.out.println(s);
	}
	
	public static String readln() {
		Scanner scr = new Scanner(System.in);
		if(Settings.get("time").equals("true")) {
			System.out.print("[YOU " + SystemUtils.getTime() + "]: ");
			return scr.nextLine();
		}
		else if(Settings.get("time").equals("false")) {
			System.out.print("[YOU]: ");
			return scr.nextLine();
		}
		return "";
	}
	
	public static String read() {
		Scanner scr = new Scanner(System.in);
		return scr.nextLine();
	}
	
	public enum ConsoleUser {
		System,
		User,
		Empty,
		Error
	}

	public static void clear() {
		try {
			if(SystemUtils.getPlatform() == Platform.Windows) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
