package me.SHiLLySiT.LoreTime;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

public class LConfig {
	//config
	public static Configuration config;
	public static String configWorld;
	public static List<String> configMonths = null;
	public static List<String> configDays = null;
	public static Integer configYear;
	public static Integer configCurrentMonth; 
	public static Integer configCurrentDay;
	public static Integer configCurrentWeekDay;
	public static Integer configDaysInAMonth;
	public static Boolean configSaveOnDateChange;
	public static Boolean configNotifyOnJoin;
	public static Integer configInterval;
	public static String configColorString;
	public static Boolean debug;
	public static ChatColor color;
	
	public static void init()
	{
		loadConfig();
		config.save();
	}
	
	//converts string from config to code
	private static ChatColor convertColor(String col)
	{
		ChatColor color = ChatColor.AQUA;
		//yourString.replace("{BLUE}", ChatColor.BLUE.toString())
		if (col.equals("BLACK")) { color = ChatColor.BLACK; }
		else if (col.equals("DARK_BLUE")) { color = ChatColor.DARK_BLUE; }
		else if (col.equals("DARK_GREEN")) { color = ChatColor.DARK_GREEN; }
		else if (col.equals("DARK_AQUA")) { color = ChatColor.DARK_AQUA; }
		else if (col.equals("DARK_RED")) { color = ChatColor.DARK_RED; }
		else if (col.equals("DARK_PURPLE")) { color = ChatColor.DARK_PURPLE; }
		else if (col.equals("GOLD")) { color = ChatColor.GOLD; }
		else if (col.equals("DARK_GRAY")) { color = ChatColor.DARK_GRAY; }
		else if (col.equals("BLUE")) { color = ChatColor.BLUE; }
		else if (col.equals("GREEN")) { color = ChatColor.GREEN; }
		else if (col.equals("AQUA")) { color = ChatColor.AQUA; }
		else if (col.equals("RED")) { color = ChatColor.RED; }
		else if (col.equals("LIGHT_PURPLE")) { color = ChatColor.LIGHT_PURPLE; }
		else if (col.equals("YELLOW")) { color = ChatColor.YELLOW; }
		else if (col.equals("WHITE")) { color = ChatColor.WHITE; }
		
		return color;
	}
	
	public static void loadConfig() {
		debug = config.getBoolean("options.debug", false);
	    if (debug) { LLogger.info("Debug Mode: " + debug); }
	    configColorString = config.getString("options.color", "AQUA");
	    if (debug) { LLogger.info("Chat Color: " + configColorString); }
	    color = convertColor(configColorString);
	    configInterval = config.getInt("options.interval", 30);
	    if (debug) { LLogger.info("Interval: " + configInterval); }
		configWorld = config.getString("options.worldname", "world");
		if (debug) { LLogger.info("World Name: " + configWorld); }
	    configSaveOnDateChange = config.getBoolean("options.saveOnDateChange", true);
	    if (debug) { LLogger.info("Save on Date Change: " + configSaveOnDateChange); }
	    configNotifyOnJoin = config.getBoolean("options.NotifyOnJoin", true);
	    if (debug) { LLogger.info("Notify on Join: " + configNotifyOnJoin); }
	    
		configYear = config.getInt("year.current", 0);
		if (debug) { LLogger.info("Year: " + configYear); }
		
	    List<String> defaultMonths = Arrays.asList("January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");  
	    configMonths = config.getStringList("months.names", null);
	    if (configMonths.isEmpty()){
	    	configMonths = defaultMonths;
	    	config.setProperty("months.names", defaultMonths);
	    }
	    if (debug) { LLogger.info("Months: " + configMonths); }
	    configCurrentMonth = config.getInt("months.current", 0);
	    if (debug) { LLogger.info("Current Month: " + configCurrentMonth); }
	    
	    List<String> defaultDays = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
	    configDays = config.getStringList("days.names", null);
	    if (configDays.isEmpty()){
	    	configDays = defaultDays;
	    	config.setProperty("days.names", defaultDays);
	    }
	    if (debug) { LLogger.info("Days: " + configDays); }
	    configCurrentDay = config.getInt("days.current", 0);
	    if (debug) { LLogger.info("Current Day: " + configCurrentDay); }
	    configDaysInAMonth = config.getInt("months.daysInAMonth", 35);
	    if (debug) { LLogger.info("Days in a Month: " + configDaysInAMonth); }
	    
	    if (debug) { LLogger.info("Loaded config values!"); } 
	}
	
	public static void saveConfig() {
		config.save();
		config.setProperty("options.debug", debug);
		config.setProperty("options.worldname", configWorld);
	    config.setProperty("options.saveOnDateChange", configSaveOnDateChange);
	    config.setProperty("year.current", configYear);
		config.setProperty("months.names", configMonths);
	    config.setProperty("months.current", configCurrentMonth);
	    config.setProperty("days.names", configDays);
	    config.setProperty("days.current", configCurrentDay);
	    config.setProperty("months.daysInAMonth", configDaysInAMonth);
	    
	    if (debug) { LLogger.info("Saved config values!"); }
	}
}
