package nl.jortenmilo.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SystemUtils {
	
	public static Platform getPlatform() {
		Platform p = Platform.Unknown;
		String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            p = Platform.Windows;
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            p = Platform.Linux;
        } else if (operSys.contains("mac")) {
            p = Platform.Mac;
        } else if (operSys.contains("sunos")) {
            p = Platform.Solaris;
        }
        return p;
	}
	
	public static String getTime() {
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
	
}
