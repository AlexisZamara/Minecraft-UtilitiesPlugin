package ratatoskr.Utilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class PlayerInteract implements Listener {
	Plugin plugin;
	
	public Material[] cropsBlock = {Material.WHEAT,Material.BEETROOTS,Material.POTATOES,Material.CARROTS,Material.NETHER_WART,Material.COCOA};
	public Material[] seeds = {Material.WHEAT_SEEDS,Material.BEETROOT_SEEDS,Material.POTATO,Material.CARROT,Material.NETHER_WART,Material.COCOA_BEANS};
	public Material[] hoes = {Material.WOODEN_HOE,Material.STONE_HOE,Material.GOLDEN_HOE,Material.IRON_HOE,Material.DIAMOND_HOE,Material.NETHERITE_HOE};
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(plugin.getConfig().getBoolean("hoeharvest.enabled") && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				if(Arrays.asList(cropsBlock).contains(event.getClickedBlock().getType()) && Arrays.asList(hoes).contains(event.getItem().getType())) {
					Damageable meta = (Damageable) event.getItem().getItemMeta(); 
					if(!plugin.getConfig().getBoolean("hoeharvest.usesdurability") || (plugin.getConfig().getBoolean("hoeharvest.usesdurability") && (event.getItem().getType().getMaxDurability() - meta.getDamage()) > 0)) {
						Ageable age = (Ageable) event.getClickedBlock().getBlockData();
						if(age.getAge() == age.getMaximumAge()) {
							for(ItemStack item : event.getClickedBlock().getDrops()) {
								if(item.getAmount() > 1 && Arrays.asList(seeds).contains(item.getType())) {
									item.setAmount(item.getAmount() - 1);
								}
								event.getPlayer().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);
							}
							age.setAge(0);
							event.getClickedBlock().setBlockData(age);
							if(plugin.getConfig().getBoolean("hoeharvest.usesdurability")) {
								meta.setDamage(meta.getDamage() + 1);
								event.getItem().setItemMeta((ItemMeta) meta);
							}
							if(plugin.getConfig().getBoolean("morexp.enabled") && plugin.getConfig().getBoolean("morexp.crops")) {
								ExperienceOrb expOrb = event.getPlayer().getWorld().spawn(event.getClickedBlock().getLocation(), ExperienceOrb.class);
								List<Integer> values = plugin.getConfig().getIntegerList("morexp.cropsxp");
								expOrb.setExperience(Random.RandomInt(values.get(0), values.get(1)));
							}
						}
					}
				}
			}
		}
	}
	
	public PlayerInteract(Plugin plugin) {
		this.plugin = plugin;
	}
}
