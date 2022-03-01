package ratatoskr.Utilities;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class ProjectileLaunch implements Listener {
	Plugin plugin;
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if(plugin.getConfig().getBoolean("ghastprotection.enabled") && event.getEntity().getShooter() instanceof Ghast && event.getEntity() instanceof Fireball) {
			event.getEntity().setMetadata("gfb", new FixedMetadataValue(this.plugin, event.getEntity().getShooter()));
		}
	}
	
	public ProjectileLaunch(Plugin plugin) {
		this.plugin = plugin;
	}
}
