package ratatoskr.Utilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener {
	Plugin plugin;
	Main main;
	
	public PlayerJoin(Plugin plugin, Main main) {
		this.plugin = plugin;
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// if the player who joins does not have his preferences set for autochop, default them to true
		if(!main.getTreeChopperSettings().isBoolean(event.getPlayer().getName())) {
			main.getTreeChopperSettings().set(event.getPlayer().getName(), true);
			main.saveTreeChopperSettings();
		}
	}
}
