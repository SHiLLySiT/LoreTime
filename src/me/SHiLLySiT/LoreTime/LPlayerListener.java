package me.SHiLLySiT.LoreTime;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class LPlayerListener extends PlayerListener {

    @SuppressWarnings("unused")
	private final LoreTime plugin;

    public LPlayerListener(LoreTime instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Player p = event.getPlayer();
    	if (LConfig.configNotifyOnJoin) { p.sendMessage(LConfig.color + LCommands.displayString()); }
    }

}
