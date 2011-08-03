package me.SHiLLySiT.LoreTime;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class LTimeCheck implements Runnable {
	public static LoreTime plugin;
	public static Server server = LoreTime.server;
	public static String worldName = LConfig.configWorld;
	private int rangeCheck = LConfig.configInterval * 20; //dynamic range depends on interval
	private boolean handled = false;
	
	public LTimeCheck(LoreTime instance) {
		plugin = instance;
	}

	//for reference:
	//Sunrise is around 23000, noon 6000, sunset 13000, and midnight 18000.
	
	@Override
	public void run() {
		if (LConfig.debug) { LLogger.log.info("Checking time..."); }
		
		//time range is check instead of exact to ensure that it is caught
		if (server.getWorld(worldName).getTime() > (18000 - rangeCheck) && server.getWorld(worldName).getTime() < (18000 + rangeCheck)) {
			
			//has the time already been checked?
			if (!handled) {
				
				//flip flag so this doesn't happen again
				handled = true;
				
				if (LConfig.debug) { LLogger.log.info("Date Change!"); }
				
				//where the magic happens
				if (LConfig.configCurrentDay < (LConfig.configDaysInAMonth - 1)) {
					LConfig.configCurrentDay++;
				} else {
					LConfig.configCurrentDay = 0;
					if (LConfig.configCurrentMonth < (LConfig.configMonths.size() - 1)) {
						LConfig.configCurrentMonth++;
					}else{
						LConfig.configYear++;
						LConfig.configCurrentMonth = 0;
					}
				}
				if (LConfig.configSaveOnDateChange) { LConfig.saveConfig(); }
				
				//notify all plays on date change
				for (Player player : plugin.getServer().getOnlinePlayers()){
					if(player != null){
						player.sendMessage(LConfig.color + LCommands.displayString());
					}
				}
			} else {
				if (LConfig.debug) { LLogger.log.info("Already changed date!"); }
			}
			
		} else {
			//once outside of time range, reset handled flag
			handled = false;
		}
	}
}
