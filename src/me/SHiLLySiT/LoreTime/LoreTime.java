package me.SHiLLySiT.LoreTime;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;

public class LoreTime extends JavaPlugin {
	
	public static String version;
	public static Logger log =  Logger.getLogger("Minecraft");
	public static String logPrefix = "[LoreTime] ";
	public static Server server;
	private LTimeCheck timeCheck = null;
	public static PermissionHandler permissionHandler;
	public static boolean hasPermissions = false;
	LPermissionsCore pc = new LPermissionsCore();
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
	public static Boolean debug;
	
	public void onEnable() {
		version = this.getDescription().getVersion();
		log.info(logPrefix + version + " has been loaded!");
		server = this.getServer();
		
		config = getConfiguration();
		loadConfig();
		config.save();
		
		pc.setupPermissions();
	    this.timeCheck = new LTimeCheck(this);
	    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.timeCheck, 0, 1);
	}
	
	public void onDisable() {
		saveConfig();
		log.info(logPrefix + "has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

		if(cmd.getName().equalsIgnoreCase("loretime"))  {
			if(args.length == 0){
				if (sender instanceof Player) { 
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.user.loretime")) {
						p.sendMessage(ChatColor.AQUA + displayString());
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					log.info(displayString());
				}
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("time")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.user.time")) {
						p.sendMessage(ChatColor.AQUA + "Time: " + server.getWorld(configWorld).getTime());
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					log.info(logPrefix  + "Time: " + server.getWorld(configWorld).getTime());
				}
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("save")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.save")) {
						saveConfig();
						p.sendMessage(ChatColor.AQUA + logPrefix + "Saved!");
						log.info(logPrefix + "Config Saved!");
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					saveConfig();
					log.info(logPrefix + "Config Saved!");
				}
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.reload")) {
						config.load();
						loadConfig();
						p.sendMessage(ChatColor.AQUA + logPrefix + "Reloaded!");
						log.info(logPrefix + "Config Reloaded!");
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					config.load();
					loadConfig();
					log.info(logPrefix + "Config Reloaded!");
				}
			}
			
			if(args.length == 4 && args[0].equalsIgnoreCase("set")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.set")) {
						if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
							if (Integer.parseInt(args[1]) <= configMonths.size() && Integer.parseInt(args[2]) <= configDaysInAMonth) {
								configCurrentMonth = (Integer.parseInt(args[1]) - 1);
								configCurrentDay = (Integer.parseInt(args[2]) - 1);
								configYear = Integer.parseInt(args[3]);
								p.sendMessage(ChatColor.AQUA + logPrefix + displayString());
								log.info(logPrefix + "Date Changed to: " + displayString());
							} else {
								p.sendMessage(ChatColor.AQUA + logPrefix + "Day/Month must be less than the max!");
							}
						} else {
							p.sendMessage(ChatColor.AQUA + logPrefix + "Day/Month must be greater than zero!");
						}
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
						if (Integer.parseInt(args[1]) <= configMonths.size() && Integer.parseInt(args[2]) <= configDaysInAMonth) {
							configCurrentMonth = (Integer.parseInt(args[1]) - 1);
							configCurrentDay = (Integer.parseInt(args[2]) - 1);
							configYear = Integer.parseInt(args[3]);
							log.info(logPrefix + "Date Changed to: " + displayString());
						} else {
							log.info(logPrefix + "Day/Month must be less than the max!");
						}
					} else {
						log.info(logPrefix + "Day/Month must be greater than zero!");
					}
				}
			}
			
			return true;
		} 
		return false; 
	}
	
	private String displayString(){
		return configDays.get(getWeekDay(configCurrentDay)) + ", " + configMonths.get(configCurrentMonth) + " " + (configCurrentDay + 1) + ", " + configYear;
	}
	
	public void loadConfig() {
		debug = config.getBoolean("options.debug", false);
	    if (debug) { log.info(LoreTime.logPrefix + "Debug Mode: " + debug); }
		configWorld = config.getString("options.worldname", "world");
		if (debug) { log.info(LoreTime.logPrefix + "World Name: " + configWorld); }
	    configSaveOnDateChange = config.getBoolean("options.saveOnDateChange", true);
	    if (debug) { log.info(LoreTime.logPrefix + "Save on Date Change: " + configSaveOnDateChange); }
	    
		configYear = config.getInt("year.current", 0);
		if (debug) { log.info(LoreTime.logPrefix + "Year: " + configYear); }
		
	    List<String> defaultMonths = Arrays.asList("January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");  
	    configMonths = config.getStringList("months.names", null);
	    if (configMonths.isEmpty()){
	    	configMonths = defaultMonths;
	    	config.setProperty("months.names", defaultMonths);
	    }
	    if (debug) { log.info(LoreTime.logPrefix + "Months: " + configMonths); }
	    configCurrentMonth = config.getInt("months.current", 0);
	    if (debug) { log.info(LoreTime.logPrefix + "Current Month: " + configCurrentMonth); }
	    
	    List<String> defaultDays = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
	    configDays = config.getStringList("days.names", null);
	    if (configDays.isEmpty()){
	    	configDays = defaultDays;
	    	config.setProperty("days.names", defaultDays);
	    }
	    if (debug) { log.info(LoreTime.logPrefix + "Days: " + configDays); }
	    configCurrentDay = config.getInt("days.current", 0);
	    if (debug) { log.info(LoreTime.logPrefix + "Current Day: " + configCurrentDay); }
	    configDaysInAMonth = config.getInt("months.daysInAMonth", 35);
	    if (debug) { log.info(LoreTime.logPrefix + "Days in a Month: " + configDaysInAMonth); }
	    
	    if (debug) { log.info(logPrefix + "Loaded config values!"); } 
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
	    
	    if (debug) { log.info(logPrefix + "Saved config values!"); }
	}
	
	private static int getWeekDay(int day) {
		int weekday = 0;
		for (int i = 0; i < day; i++) {
			if (weekday < (configDays.size() - 1)) {
				weekday++;
			} else {
				weekday = 0;
			}
		}
		return weekday;
	}
	
}
