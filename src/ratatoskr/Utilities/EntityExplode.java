package ratatoskr.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

public class EntityExplode implements Listener{
	Plugin plugin;
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		// check if the creeper protection is enabled
		if(plugin.getConfig().getBoolean("creeperprotection.enabled") && event.getEntity() instanceof Creeper) {
			// if the exceptions are enabled
			if(plugin.getConfig().getBoolean("creeperprotection.exceptions")) {
				// loop through the whole thing
				// using a copy because you can't edit a list while iterating through it
				for (Block b : new ArrayList<Block>(event.blockList())) {
					if(!Arrays.asList(plugin.getConfig().getList("creeperprotection.exceptionslist").toArray()).contains(b.getType().toString().toLowerCase())) {
						// remove blocks on that are not on the whitelist
						event.blockList().remove(b);
					}
				}
			}
			else {
				// remove all blocks from the list
				event.blockList().clear();
			}
		}
		
		if(plugin.getConfig().getBoolean("ghastprotection.enabled") && event.getEntity().hasMetadata("gfb")) {
			if(plugin.getConfig().getBoolean("ghastprotection.exceptions") && !event.blockList().isEmpty()) {
				for (Block b : new ArrayList<Block>(event.blockList())) {
					if(!Arrays.asList(plugin.getConfig().getList("ghastprotection.exceptionslist").toArray()).contains(b.getType().toString().toLowerCase())) {
						event.blockList().remove(b);
					}
				}
			}
			else {
				event.blockList().clear();
			}
		}
	}
	
	public EntityExplode(Plugin plugin) {
		this.plugin = plugin;
	}
}
