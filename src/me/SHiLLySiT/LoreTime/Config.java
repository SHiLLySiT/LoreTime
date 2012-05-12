package me.SHiLLySiT.LoreTime;

import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;

public class Config {
	private LoreTime plugin;
	
	public Config(LoreTime instance)
	{
	    plugin = instance;
	}
	
	public void loadDefaults()
	{
		plugin.getConfig().options().copyDefaults(true);
		
		plugin.getConfig().addDefault("options.debug", false);
		plugin.getConfig().addDefault("options.color", "AQUA");
		plugin.getConfig().addDefault("options.interval", 30);
		Log.debug("interval: " + plugin.getConfig().getInt("options.interval"));
		plugin.getConfig().addDefault("options.newdaytime", 18000);
		Log.debug("newdaytime: " + plugin.getConfig().getInt("options.newdaytime"));
		plugin.getConfig().addDefault("options.displayFormat", "&W, &M &D, &Y");
		plugin.getConfig().addDefault("options.timeFormat", 24);
		Log.debug("timeformat: " + plugin.getConfig().getInt("options.timeFormat"));
		plugin.getConfig().addDefault("options.useDaySuffix", true);
		String[] list = {"th", "st", "nd", "rd"};
		plugin.getConfig().addDefault("options.daySuffixes", Arrays.asList(list));
		
		plugin.getConfig().addDefault("notifications.onJoin", true);
		Log.debug("notifyOnJoin: " + plugin.getConfig().getBoolean("notifications.onJoin"));
		plugin.getConfig().addDefault("notifications.onNewDay", true);
		Log.debug("notifyOnNewDay: " + plugin.getConfig().getBoolean("notifications.onNewDay"));

		plugin.getConfig().addDefault("year.currentYear", 0);
		
		plugin.getConfig().addDefault("month.currentMonth", 0);
		String[] list1 = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		plugin.getConfig().addDefault("month.monthNames", Arrays.asList(list1));
	    
		plugin.getConfig().addDefault("day.currentDay", 0);
		String[] list2 = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		plugin.getConfig().addDefault("day.dayNames", Arrays.asList(list2));
		plugin.getConfig().addDefault("day.daysPerMonth", 35);
		
		plugin.saveConfig();
	}
	
	public void save()
	{
		 plugin.saveConfig();
	}
	
	// debug mode
	public boolean getDebug() { return plugin.getConfig().getBoolean("options.debug"); }
	public void setDebug(boolean value) { plugin.getConfig().set("options.debug", value); }
	// chat color
	public ChatColor getColor() { return ChatColor.valueOf(plugin.getConfig().getString("options.color").toUpperCase()); }
	public void setColor(String value) { plugin.getConfig().set("options.color", value); }
	// interval between time checks
	public int getInterval() { return plugin.getConfig().getInt("options.interval"); }
	// time for date change
	public int getNewDayTime() { return plugin.getConfig().getInt("options.newdaytime"); }
	// display string
	public String getDisplayFormat() { return plugin.getConfig().getString("options.displayFormat"); }
	public void setDisplayFormat(String value) { plugin.getConfig().set("options.displayFormat", value); }
	// time format
	public int getTimeFormat() { return plugin.getConfig().getInt("options.timeFormat"); }
	public void setTimeFormat(int value) { plugin.getConfig().set("options.timeFormat", value); }
	// use day suffix?
	public boolean getUseDaySuffix() { return plugin.getConfig().getBoolean("options.useDaySuffix"); }
	public void setUseDaySuffix(boolean value) { plugin.getConfig().set("options.useDaySuffix", value); }
	// day suffixes
	public List<String> getDaySuffixes() { return plugin.getConfig().getStringList("options.daySuffixes"); }
	public void setDaySuffixes(String value) { plugin.getConfig().set("options.daySuffixes", value.split(",")); } // TODO: test this
	
	// notify on join
	public boolean getNotifyOnJoin() { return plugin.getConfig().getBoolean("notifications.onJoin"); }
	public void setNotifyOnJoin(boolean value) { plugin.getConfig().set("notifications.onJoin", value); }
	// notify on day new day
	public boolean getNotifyOnNewDay() { return plugin.getConfig().getBoolean("notifications.onNewDay"); }
	public void setNotifyOnNewDay(boolean value) { plugin.getConfig().set("notifications.onNewDay", value); }
	
	// current year
	public int getCurrentYear() { return plugin.getConfig().getInt("year.currentYear"); }
	public void setCurrentYear(int value) { plugin.getConfig().set("year.currentYear", value); }
	// current month (zero based)
	public int getCurrentMonth() { return plugin.getConfig().getInt("month.currentMonth"); }
	public void setCurrentMonth(int value) { plugin.getConfig().set("month.currentMonth", value); }
	// month names
	public List<String> getMonthNames() { return plugin.getConfig().getStringList("month.monthNames"); }
	public void setCurrentMonth(String value) { plugin.getConfig().set("month.monthNames", value.split(",")); } // TODO: test this
	// current day (zero based)
	public int getCurrentDay() { return plugin.getConfig().getInt("day.currentDay"); }
	public void setCurrentDay(int value) { plugin.getConfig().set("day.currentDay", value); }
	// month names
	public List<String> getDayNames() { return plugin.getConfig().getStringList("day.dayNames"); }
	public void getDayNames(String value) { plugin.getConfig().set("day.dayNames", value.split(",")); } // TODO: test this
	// days per month (zero based)
	public int getDaysPerMonth() { return plugin.getConfig().getInt("day.daysPerMonth"); }
	public void setDaysPerMonth(int value) { plugin.getConfig().set("day.daysPerMonth", value); }
	
}
