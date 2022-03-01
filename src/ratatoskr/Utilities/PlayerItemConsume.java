package ratatoskr.Utilities;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemConsume implements Listener {
	Plugin plugin;
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if(plugin.getConfig().getBoolean("godapples.glorious") && event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
			event.setCancelled(true);
			// foodlevel is capped at 20
			event.getPlayer().setFoodLevel(Math.min(event.getPlayer().getFoodLevel() + 4, 20));
			// max saturation is always equal to food level
			event.getPlayer().setSaturation(Math.min(event.getPlayer().getSaturation() + 9.6f, event.getPlayer().getFoodLevel()));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,2400,3));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,600,4));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,6000,0));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,6000,0));
			if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
				event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
			}
			else {
				event.getPlayer().getInventory().getItemInOffHand().setAmount(event.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
			}
		}
	}
	
	public PlayerItemConsume(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
