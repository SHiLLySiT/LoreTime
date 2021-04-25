package me.SHiLLySiT.LoreTime;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.lorenzo0111.hook.PlaceholderProvider;

public class LoreTime extends JavaPlugin {
	public String version;
	public TimeCheck timeCheck = null;
	public Commands commands = null;
	public Config config = null;
	public PlayerListener listener = null;
	
	public void onEnable() {
		Log.initialize(this, Logger.getLogger("Minecraft"));
		Logger customLogger = Logger.getLogger("Lorenzo0111 Hook");
		
		version = this.getDescription().getVersion();
		listener = new PlayerListener(this);
		commands = new Commands(this);
		
		config = new Config(this);
		config.loadDefaults();
		
	    //For reference:
	    //300 * 20L = 300 seconds (1L = 1 tick, 20 ticks per second)
		timeCheck = new TimeCheck(this);
	    this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, this.timeCheck, 20L * 5, 20L * config.getInterval());
	    
	    customLogger.info("Loading custom hook..");
	    new PlaceholderProvider(this).register();
	    
	    Log.info("version " + version + " is enabled!");
	}
	
	public void onDisable() {
		config.save();
		Log.info("has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		return commands.executeCommand(sender, cmd, commandLabel, args);
	}
	
}
