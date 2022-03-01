package ratatoskr.Utilities;

import java.util.Arrays;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.Plugin;

public class EntityChangeBlock implements Listener {
	Plugin plugin;
	
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if(plugin.getConfig().getBoolean("endermanprotection.enabled") && event.getEntity() instanceof Enderman) {
			if(plugin.getConfig().getBoolean("endermandprotection.exceptions")) {
				if(!Arrays.asList(plugin.getConfig().getList("endermanprotection.exceptionslist").toArray()).contains(event.getBlock().getType().toString().toLowerCase())) {
					event.setCancelled(true);
				}
			}
			else {
				event.setCancelled(true);
			}
		}
	}
	
	public EntityChangeBlock(Plugin plugin) {
		this.plugin = plugin;
	}
}
