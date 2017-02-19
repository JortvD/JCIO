package nl.jortenmilo.utils;

import java.text.SimpleDateFormat;

public class SystemUtils extends Utils {
	
	@Override
	public void create() {
		
	}

	@Override
	public Utils clone() {
		SystemUtils clone = new SystemUtils();
		clone.create();
		
		return clone;
	}
	
	public Platform getPlatform() {
		Platform p = Platform.Unknown;
		String operSys = System.getProperty("os.name").toLowerCase();
		
		if (operSys.contains("win")) {
			p = Platform.Windows;
		}
		else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
			p = Platform.Linux;
		}
		else if (operSys.contains("mac")) {
			p = Platform.Mac;
		}
		else if (operSys.contains("sunos")) {
			p = Platform.Solaris;
		}
		
		return p;
	}
	
	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		return sdf.format(System.currentTimeMillis());
	}
	
	public enum Platform {
		Windows,
		Linux,
		Mac,
		Solaris,
		Unknown
	}

	public String getData() {
		return "";
	}

	@Override
	public String getName() {
		return "SystemUtils";
	}
	
}
