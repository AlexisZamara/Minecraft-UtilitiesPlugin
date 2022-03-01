package ratatoskr.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class InventoryClick implements Listener {
	Plugin plugin;
	
	public InventoryAction[] takeActions = {InventoryAction.PICKUP_ALL, InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.SWAP_WITH_CURSOR};
	public InventoryAction[] placeActions = {InventoryAction.PLACE_ALL, InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.SWAP_WITH_CURSOR};
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(plugin.getConfig().getBoolean("morexp.enabled") && plugin.getConfig().getBoolean("morexp.brewing")) {
			if(Arrays.asList(takeActions).contains(event.getAction()) && event.getClickedInventory().getType() == InventoryType.BREWING && event.getCurrentItem().getType() == Material.POTION) {
				if(event.getCurrentItem().getItemMeta().getLore() == null) {
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add("ratutil.exp.give");
					meta.setLore(lore);
					event.getCurrentItem().setItemMeta(meta);
					ExperienceOrb expOrb = event.getClickedInventory().getLocation().getWorld().spawn(event.getClickedInventory().getLocation(), ExperienceOrb.class);
					List<Integer> values = plugin.getConfig().getIntegerList("morexp.brewingxp");
					expOrb.setExperience(Random.RandomInt(values.get(0), values.get(1)));
				}
				else if(!event.getCurrentItem().getItemMeta().getLore().contains("ratutil.exp.give")) {
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					List<String> lore = event.getCurrentItem().getItemMeta().getLore();
					lore.add("ratutil.exp.give");
					meta.setLore(lore);
					event.getCurrentItem().setItemMeta(meta);
				}
			}
			else if(Arrays.asList(placeActions).contains(event.getAction()) && event.getCurrentItem().getType() == Material.POTION && (event.getClickedInventory().getType() == InventoryType.BREWING || event.getClickedInventory().getType() == InventoryType.PLAYER)) {
				if(event.getCurrentItem().getItemMeta().getLore() == null) {
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add("ratutil.exp.give");
					meta.setLore(lore);
					event.getCurrentItem().setItemMeta(meta);
				}
			}
		}
	}
	
	public InventoryClick(Plugin plugin) {
		this.plugin = plugin;
	}
}
