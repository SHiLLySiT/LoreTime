package me.SHiLLySiT.LoreTime;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderProvider extends PlaceholderExpansion {
	private final LoreTime plugin;
	
	public PlaceholderProvider(LoreTime plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return "SHiLLySiT";
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
