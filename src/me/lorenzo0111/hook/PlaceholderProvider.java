package me.lorenzo0111.hook;

import me.SHiLLySiT.LoreTime.LoreTime;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderProvider extends PlaceholderExpansion {
	private final LoreTime plugin;
	
	public PlaceholderProvider(LoreTime plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return "Lorenzo0111";
	}

	@Override
	public String getIdentifier() {
		return "loretime";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
  
        if(identifier.equals("time")){
            return plugin.commands.getTime();
        }

        return null;
    }

}
