package me.SHiLLySiT.LoreTime;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class PlayerListener implements Listener {
	private LoreTime plugin;
	
    public PlayerListener(LoreTime instance)
    {
    	plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, instance);
    }
    
    @EventHandler 
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	Player p = event.getPlayer();
    	if (plugin.config.getNotifyOnJoin()) { p.sendMessage(plugin.config.getColor() + plugin.commands.displayString()); }
    }
    
    @EventHandler 
    public void OnPLayerClick(PlayerInteractEvent event)
    {
    	if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            // note: blocks will never have a type of Material.SIGN
            if (block.getType().equals(Material.LEGACY_SIGN_POST) || block.getType().equals(Material.LEGACY_WALL_SIGN)) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase("[calendar]")) {
                	player.sendMessage(plugin.config.getColor() + plugin.commands.displayString());
                }
            }
    	}
    }

}
