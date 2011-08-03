package me.SHiLLySiT.LoreTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LCommands {
    @SuppressWarnings("unused")
	private LoreTime plugin;
	LPermissionsCore pc = new LPermissionsCore();
	
	public LCommands(LoreTime instance) {
	    this.plugin = instance;
	}
    
    public static String displayString() {
		Integer current = LConfig.configCurrentDay + 1;
		String day = current + "th"; //takes are of days ending with 4 - 9 in one line
		if(current.toString().endsWith("0") && current.toString().length() == 2) {day = current + "th";}
		if(current.toString().endsWith("1")) {day = current + "st";}
		if(current.toString().endsWith("2")) {day = current + "nd";}
		if(current.toString().endsWith("3")) {day = current + "rd";}
		if(current.toString().startsWith("1") && current.toString().length() == 2){day = current + "th";}
		
		//TODO support user formatting
		return LConfig.configDays.get(getWeekDay(LConfig.configCurrentDay)) + ", " + LConfig.configMonths.get(LConfig.configCurrentMonth) + " " + day + ", " + LConfig.configYear;
   }

   //magic function
    private static int getWeekDay(int day) {
		int weekday = 0;
		for (int i = 0; i < day; i++) {
			if (weekday < (LConfig.configDays.size() - 1)) {
				weekday++;
			} else {
				weekday = 0;
			}
		}
		return weekday;
	}
   
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		//note: alias of /loretime is /lt
		if(cmd.getName().equalsIgnoreCase("loretime"))  {
			//if no arguments, return date
			if(args.length == 0){
				if (sender instanceof Player) { 
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.user.loretime")) {
						p.sendMessage(LConfig.color + displayString());
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					LLogger.info(displayString());
				}
			}
			
			//return current server time
			if(args.length == 1 && args[0].equalsIgnoreCase("time")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.user.time")) {
						p.sendMessage(LConfig.color + "Time: " + LoreTime.server.getWorld(LConfig.configWorld).getTime());
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					LLogger.info("Time: " + LoreTime.server.getWorld(LConfig.configWorld).getTime());
				}
			}
			
			//save config in game
			if(args.length == 1 && args[0].equalsIgnoreCase("save")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.save")) {
						LConfig.saveConfig();
						p.sendMessage(LConfig.color + "LoreTime config saved!");
						LLogger.info("Config Saved!");
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					LConfig.saveConfig();
					LLogger.info("Config Saved!");
				}
			}
			
			//reload config, useful for when you were playing with date names
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.reload")) {
						LConfig.config.load();
						LConfig.loadConfig();
						p.sendMessage(LConfig.color + "LoreTime reloaded!");
						LLogger.info("Config Reloaded!");
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					LConfig.config.load();
					LConfig.loadConfig();
					LLogger.info("Config Reloaded!");
				}
			}
			
			//sets the date to the specified date
			if(args.length == 4 && args[0].equalsIgnoreCase("set")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if(pc.doesHaveNode(p, "loretime.admin.set")) {
						if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
							if (Integer.parseInt(args[1]) <= LConfig.configMonths.size() && Integer.parseInt(args[2]) <= LConfig.configDaysInAMonth) {
								LConfig.configCurrentMonth = (Integer.parseInt(args[1]) - 1);
								LConfig.configCurrentDay = (Integer.parseInt(args[2]) - 1);
								LConfig.configYear = Integer.parseInt(args[3]);
								p.sendMessage(LConfig.color + "Date set to: " + displayString());
								LLogger.info("Date Changed to: " + displayString());
							} else {
								p.sendMessage(LConfig.color + "Day/Month must be less than the max!");
							}
						} else {
							p.sendMessage(LConfig.color + "Day/Month must be greater than zero!");
						}
					} else {
						pc.sendInsufficientPermsMsg(p);
					}
				} else {
					if (Integer.parseInt(args[1]) != 0 && Integer.parseInt(args[2]) != 0) {
						if (Integer.parseInt(args[1]) <= LConfig.configMonths.size() && Integer.parseInt(args[2]) <= LConfig.configDaysInAMonth) {
							LConfig.configCurrentMonth = (Integer.parseInt(args[1]) - 1);
							LConfig.configCurrentDay = (Integer.parseInt(args[2]) - 1);
							LConfig.configYear = Integer.parseInt(args[3]);
							LLogger.info("Date set to: " + displayString());
						} else {
							LLogger.info("Day/Month must be less than the max!");
						}
					} else {
						LLogger.info("Day/Month must be greater than zero!");
					}
				}
			}
			
			return true;
		} 
		return false; 
	}
}
