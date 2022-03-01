package ratatoskr.Utilities;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;

public class PortalCreate implements Listener{
	Plugin plugin;
	Main main;
	
	@EventHandler
	public void onPortalCreate(PortalCreateEvent event) {
		for (BlockState b : event.getBlocks()) {
			System.out.println(b.getLocation().toString());
			System.out.println(b.getType().toString());
			System.out.println(b.getBlockData().toString());
		}
		event.setCancelled(true);
	}
	
	public PortalCreate(Plugin plugin, Main main) {
		this.plugin = plugin;
		this.main = main;
	}
}
