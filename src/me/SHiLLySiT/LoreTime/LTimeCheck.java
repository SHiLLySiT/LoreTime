package me.SHiLLySiT.LoreTime;

import org.bukkit.Server;

public class LTimeCheck implements Runnable {
	public static LoreTime plugin;
	public static Server server = LoreTime.server;
	public static String worldName = LoreTime.configWorld;
	private boolean handled = false;
	
	public LTimeCheck(LoreTime instance) {
		plugin = instance;
	}

	//for reference:
	//Sunrise is around 23000, noon 6000, sunset 13000, and midnight 18000.
	
	@Override
	public void run() {
		
		//time range is check instead of exact to ensure that it is caught
		if (server.getWorld(worldName).getTime() > 17950 && server.getWorld(worldName).getTime() < 18050) {
			
			//has the time already been checked?
			if (!handled) {
				
				//flip flag so this doesnt happen again
				handled = true;
				
				if (LoreTime.debug) { LoreTime.log.info(LoreTime.logPrefix + "Date Change!"); }
				
				//where the magic happens
				if (LoreTime.configCurrentDay < (LoreTime.configDaysInAMonth - 1)) {
					LoreTime.configCurrentDay++;
				} else {
					LoreTime.configCurrentDay = 0;
					if (LoreTime.configCurrentMonth < (LoreTime.configMonths.size() - 1)) {
						LoreTime.configCurrentMonth++;
					}else{
						LoreTime.configYear++;
						LoreTime.configCurrentMonth = 0;
					}
				}
				if (LoreTime.configSaveOnDateChange) { LoreTime.saveConfig(); }
			}
			
		} else {
			//once outside of time range, reset handled flag
			handled = false;
		}
	}
}
