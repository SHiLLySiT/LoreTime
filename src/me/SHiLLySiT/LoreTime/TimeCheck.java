package me.SHiLLySiT.LoreTime;

import org.bukkit.entity.Player;

public class TimeCheck implements Runnable {
	private LoreTime plugin;
	private boolean handled = false;
	
	public TimeCheck(LoreTime instance) {
		plugin = instance;
	}

	//for reference:
	//Sunrise is around 23000, noon 6000, sunset 13000, and midnight 18000.
	
	@Override
	public void run() {
		Log.debug("Checking time...");
		
		//time range to check instead of exact to ensure that it is caught
		if (plugin.getServer().getWorld("world").getTime() > (plugin.config.getNewDayTime() - plugin.config.getInterval() * 20) && plugin.getServer().getWorld("world").getTime() < (plugin.config.getNewDayTime() + plugin.config.getInterval() * 20)) {
			
			//has the time already been checked?
			if (!handled) {
				
				//flip flag so this doesn't happen again
				handled = true;
				
				Log.debug("Date Change!");
				
				//where the magic happens
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
				plugin.config.save();
				
				Log.debug(plugin.commands.displayString());
				
				//notify all plays on date change
				for (Player player : plugin.getServer().getOnlinePlayers()){
					if (player != null){
						player.sendMessage(plugin.config.getColor() + plugin.commands.displayString());
					}
				}
			} else {
				Log.debug("Already changed date!");
			}
			
		} else {
			//once outside of time range, reset handled flag
			handled = false;
		}
	}
}
