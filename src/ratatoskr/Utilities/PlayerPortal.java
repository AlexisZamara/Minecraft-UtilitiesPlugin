package ratatoskr.Utilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.Plugin;

public class PlayerPortal implements Listener {
	Plugin plugin;
	Main main;
	
	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		System.out.println(event.getCreationRadius());
		System.out.println(event.getSearchRadius());
		System.out.println(event.getCanCreatePortal());
		System.out.println(event.getFrom().toString());
		System.out.println(event.getTo().toString());
		if(!event.getFrom().getBlock().getMetadata("conp").isEmpty() && !plugin.getConfig().getBoolean("cryingportals.enabled")) {
			event.setCancelled(true);
			return;
		}
		// TODO: when a portal is entered, check that it leads to its correct pair if the plugin is enabled
		// TODO: save portal pair in file for future reference?
		// TODO: when creating, override PortalCreateEvent to replace Obsidian with Crying Obsidian and add the metadata to portal blocks
		if(event.getFrom().getBlock().getMetadata("conp").isEmpty() && !event.getTo().getBlock().getMetadata("conp").isEmpty()) {
			// if a normal portal connects to crying portal
			event.setCanCreatePortal(true);
			event.setCreationRadius(64);
		}
		if(!event.getFrom().getBlock().getMetadata("conp").isEmpty() && event.getTo().getBlock().getMetadata("conp").isEmpty()) {
			// if a crying portal connects to a normal portal
			event.setCanCreatePortal(true);
			event.setCreationRadius(64);
		}
	}
	
	public PlayerPortal(Plugin plugin, Main main) {
		this.plugin = plugin;
		this.main = main;
	}
}
