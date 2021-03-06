package me.SHiLLySiT.LoreTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
	private LoreTime plugin;
	
	public Commands(LoreTime instance)
	{
	    plugin = instance;
	}
   
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
		//note: alias of /loretime is /lt
		if (cmd.getName().equalsIgnoreCase("loretime"))  {
			//if no arguments, return date
			if (args.length == 0) {
				if (sender instanceof Player) { 
					Player p = (Player) sender;
					if (p.hasPermission("loretime.user.loretime")) {
						p.sendMessage(plugin.config.getColor() + displayString());
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					Log.info(displayString());
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("debug")) { // turns debug on/off
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.debug")) {
						plugin.config.setDebug(!plugin.config.getDebug());
						p.sendMessage(plugin.config.getColor() + "Debug Mode: " + Boolean.toString(plugin.config.getDebug()));
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					plugin.config.setDebug(!plugin.config.getDebug());
					Log.info("Debug Mode: " + Boolean.toString(plugin.config.getDebug()));
				}
				plugin.config.save();
			} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) { // reloads the plugin
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.debug")) {
						plugin.reloadConfig();
						p.sendMessage(plugin.config.getColor() + "Reloaded data from config.yml!");
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					plugin.reloadConfig();
					Log.info("Reloaded data from config.yml!");
				}
				plugin.config.save();
			} else if (args.length == 1 && args[0].equalsIgnoreCase("togglehourformat")) { // toggle hour format
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.togglehourformat")) {
						plugin.config.setTimeFormat((plugin.config.getTimeFormat() == 12) ? 24 : 12);
						p.sendMessage(plugin.config.getColor() + "Hour format set to " + plugin.config.getTimeFormat() + "-hour.");
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					plugin.config.setTimeFormat((plugin.config.getTimeFormat() == 12) ? 24 : 12);
					Log.info("Hour format set to " + plugin.config.getTimeFormat() + "-hour.");
				}
				plugin.config.save();
			} else if(args.length == 2 && args[0].equalsIgnoreCase("setcolor")) {// sets color of loretime messages
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.setcolor")) {
						if (args[1] != null) {
							plugin.config.setColor(args[1]);
							p.sendMessage(plugin.config.getColor() + "Display color set to " + plugin.config.getColor().name());
							Log.info("Display color set to " + plugin.config.getColor().name() + " by " + p.getDisplayName());
						}
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					plugin.config.setColor(args[1]);
					Log.info("Display color set to " + plugin.config.getColor().name() + " by console.");
				}
			} else if(args.length == 1 && args[0].equalsIgnoreCase("time")) {// returns current server time
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.user.time")) {
						p.sendMessage(plugin.config.getColor() + getTime());
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					Log.info(getTime());
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("nextday")) { // goto next day
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.nextday")) {
						if (plugin.config.getCurrentDay() <= (plugin.config.getDaysPerMonth() - 2)) {
							plugin.config.setCurrentDay(plugin.config.getCurrentDay() + 1);
						} else {
							plugin.config.setCurrentDay(0);
							if (plugin.config.getCurrentMonth() <= (plugin.config.getMonthNames().size() - 2)) {
								plugin.config.setCurrentMonth(plugin.config.getCurrentMonth() + 1);
							} else {
								plugin.config.setCurrentYear(plugin.config.getCurrentYear() + 1);
								plugin.config.setCurrentMonth(0);
							}
						}
						p.sendMessage(plugin.config.getColor() + "Date moved forward.");
						Log.info("Date moved forward by " + p.getDisplayName());
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else {
					if (plugin.config.getCurrentDay() <= (plugin.config.getDaysPerMonth() - 2)) {
						plugin.config.setCurrentDay(plugin.config.getCurrentDay() + 1);
					} else {
						plugin.config.setCurrentDay(0);
						if (plugin.config.getCurrentMonth() <= (plugin.config.getMonthNames().size() - 2)) {
							plugin.config.setCurrentMonth(plugin.config.getCurrentMonth() + 1);
						} else {
							plugin.config.setCurrentYear(plugin.config.getCurrentYear() + 1);
							plugin.config.setCurrentMonth(0);
						}
					}
					Log.info("Date moved forward by console.");
				}
				plugin.config.save();
			} else if (args.length == 4 && args[0].equalsIgnoreCase("setDate")) { //sets the date to the specified date
				if (sender instanceof Player) { // if issues from player
					Player p = (Player) sender;
					if (p.hasPermission("loretime.admin.set")) {
						if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
							if (Integer.parseInt(args[1]) <= plugin.config.getMonthNames().size() && Integer.parseInt(args[2]) <= plugin.config.getDaysPerMonth()) {
								plugin.config.setCurrentMonth(Integer.parseInt(args[1]) - 1);
								plugin.config.setCurrentDay(Integer.parseInt(args[2]) - 1);
								plugin.config.setCurrentYear(Integer.parseInt(args[3]));
								p.sendMessage(plugin.config.getColor() + "Date set to: " + displayString());
								Log.info("Date changed to: " + displayString() + " by " + p.getDisplayName());
							} else {
								p.sendMessage(plugin.config.getColor() + "Day/Month must be less than the max!");
							}
						} else {
							p.sendMessage(plugin.config.getColor() + "Day/Month must be greater than zero!");
						}
					} else {
						p.sendMessage(plugin.config.getColor() + "You don't have permission for that!");
					}
				} else { // if issues from console
					if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
						if (Integer.parseInt(args[1]) <= plugin.config.getMonthNames().size() && Integer.parseInt(args[2]) <= plugin.config.getDaysPerMonth()) {
							plugin.config.setCurrentMonth(Integer.parseInt(args[1]) - 1);
							plugin.config.setCurrentDay(Integer.parseInt(args[2]) - 1);
							plugin.config.setCurrentYear(Integer.parseInt(args[3]));
							Log.info("Date changed to: " + displayString() + " by console.");
						} else {
							Log.info("Day/Month must be less than the max!");
						}
					} else {
						Log.info("Day/Month must be greater than zero!");
					}
				}
				plugin.config.save();
			} else {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					p.sendMessage(plugin.config.getColor() + "That's not a valid command!");
				} else {
					Log.info("That's not a valid command!");
				}
			}
			return true;
		} 
		return false; 
	}
    
 	private String getTime() {
 		String returnString = "";
 		Long time = plugin.getServer().getWorld("world").getTime();
 		
 		Log.debug("Raw Time: " + time.toString());
 		
 		// add 1000 since MC time ranges from 0 - 23000, cut off last zero, time offset
 		time = ((time + 1000) / 10) + 500;
 		time = (time >= 2400) ? time - 2400 : time; // set time to the difference
 		
 		Log.debug("Unformatted Time: " + time.toString());
 		
 		String sTime = time.toString();
 		String hour, minute;
 		
 		if (sTime.length() == 4) {        // 4 digits
 			hour = sTime.substring(0, 2);     // get hour
 			minute = sTime.substring(2, 4);   // get minute
 		} else if (sTime.length() == 3) { // 3 digits
 			hour = sTime.substring(0, 1); 
 			minute = sTime.substring(1, 3); 
 		} else {                          // 2 or less digits
 			hour = "0"; 
 			minute = sTime; 
 		}
 		
 		Log.debug("hour:" + hour + " minute:" + minute);
 		
 		// scale minutes 
 		int buffer = (int) scale(Integer.parseInt(minute));
 		String minBuffer = Integer.toString(buffer);
 		minBuffer = (minBuffer.length() == 1) ? "0" + minBuffer : minBuffer;
 		
 		// determine am or pm
 		int hourBuffer = Integer.parseInt(hour);
 		if (plugin.config.getTimeFormat() == 24) { // 24 hour format
 			returnString = hourBuffer + ":" + minBuffer;
 		} else { // 12 hour format (defaults if wrong value)
 			if (hourBuffer >= 12) { 
 				if (hourBuffer > 12) { hourBuffer -= 12; } // otherwise 12pm becomes 1pm
 				returnString = hourBuffer + ":" + minBuffer + " pm";
 			} else {
 				returnString = (hourBuffer == 0) ? "12:" + minBuffer + " am" : hourBuffer + ":" + minBuffer + " am";
 			}
 		}

 		return returnString;
 	}
 	
 	private double scale(int value) {
 		return Math.floor(60 * (value / 100.0));
 	}
 	
     public String displayString() {
     	// credit to Vaquxine of Lord of the Craft for the following day suffix generator 
     	String day;
     	Integer current = plugin.config.getCurrentDay() + 1;
     	if (plugin.config.getUseDaySuffix()) {
 	    	//add st, th, etc to day
 			day = current + plugin.config.getDaySuffixes().get(0); //takes are of days ending with 4 - 9 in one line
 			if(current.toString().endsWith("0") && current.toString().length() == 2) {day = current + plugin.config.getDaySuffixes().get(0); }
 			if(current.toString().endsWith("1")) { day = current + plugin.config.getDaySuffixes().get(1); }
 			if(current.toString().endsWith("2")) { day = current + plugin.config.getDaySuffixes().get(2); }
 			if(current.toString().endsWith("3")) { day = current + plugin.config.getDaySuffixes().get(3); }
 			if(current.toString().startsWith("1") && current.toString().length() == 2) { day = current + plugin.config.getDaySuffixes().get(0); }
     	} else {
     		day = current.toString();
     	}
     	
 		//dayofweek, month, daynum, year
 		//&W, &M &D, &Y
 		//return LConfig.configDays.get(getWeekDay(LConfig.configCurrentDay)) + ", " + LConfig.configMonths.get(LConfig.configCurrentMonth) + " " + day + ", " + LConfig.configYear;
 		String weekday = plugin.config.getDayNames().get(getWeekDay(plugin.config.getCurrentDay()));
 		String month = plugin.config.getMonthNames().get(plugin.config.getCurrentMonth());
 		String year = Integer.toString(plugin.config.getCurrentYear());
 		
 		//replace user string with date
     	String string = plugin.config.getDisplayFormat();
 		string = string.replaceFirst("&W", weekday);
 		string = string.replaceFirst("&M", month);
 		string = string.replaceFirst("&D", day);
 		string = string.replaceFirst("&Y", year);
 		string = string.replaceFirst("&T", getTime());
 		
 		return string;
    }

    //magic function
    public int getWeekDay(int day) {
 		int weekday = 0;
 		for (int i = 0; i < day; i++) {
 			weekday = (weekday < (plugin.config.getDayNames().size() - 1)) ? weekday + 1 : 0;
 		}
 		return weekday;
 	}
}
