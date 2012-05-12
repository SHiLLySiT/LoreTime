package me.SHiLLySiT.LoreTime;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Log {
	private static LoreTime plugin;
	public static Logger log =  Logger.getLogger("Minecraft");
	public static String prefix;
	
    public static void initialize(LoreTime instance, Logger newLog) {
        plugin = instance;
        Log.log = newLog;
        prefix = "[LoreTime] ";
    }
    
    public static void debug(String message) {
        if (plugin.config.getDebug()) { log.info(prefix + message); }
    }
    
    public static void info(String message) {
        log.info(prefix + message);
    }

    public static void error(String message) {
        log.severe(prefix + message);
    }

    public static void warning(String message) {
        log.warning(prefix + message);
    }

    public static void config(String message) {
        log.config(prefix + message);
    }

    public static void log(Level level, String message) {
        log.log(level, prefix + message);
    }
    
    
}
