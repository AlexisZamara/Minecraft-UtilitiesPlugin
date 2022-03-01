package ratatoskr.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// detect when a block is broken
public class BlockBreak implements Listener {
	Plugin plugin;
	Main main;
	
	public String[] logs = {"oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "dark_oak_log", "crimson_stem", "warped_stem"};
	public String[] axes = {"wooden_axe", "stone_axe", "iron_axe", "golden_axe", "diamond_axe", "netherite_axe"};
	public String[] leaves = {"oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves", "acacia_leaves", "dark_oak_leaves"};
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		// check if the plugin is enabled
		if(plugin.getConfig().getBoolean("treechopping.enabled")) {
			// check if the block is a log and was not placed by a player and the player is using an axe
			if(Arrays.asList(logs).contains(event.getBlock().getType().toString().toLowerCase()) && event.getBlock().getMetadata("ppb").isEmpty() && Arrays.asList(axes).contains(event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase())) {
				// check if the player is using autochopper, in the alternative if this option is disabled ignore this.
				if(main.getTreeChopperSettings().getBoolean(event.getPlayer().getName()) || !plugin.getConfig().getBoolean("treechopping.playertoggle")) {
					int n = 0; // durability tracker
					Boolean canDestroy = true;
					// arraylist of objects, because arrays are not dynamic in java but lists are
					ArrayList<Block> bList = new ArrayList<Block>();
					bList.add(event.getBlock());
					
					// check item durability
					ItemMeta axeMeta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
					Damageable meta = (Damageable) axeMeta;
					if(plugin.getConfig().getBoolean("treechopping.durability",false) && (n == (event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() - meta.getDamage()))) {
						canDestroy = false;
					}

					// check every log block above
					// this may cause lag on larger trees
					while (!bList.isEmpty() && canDestroy) {				
						// check surrounding blocks for other logs
						// Y axis
						for (int y = 0; y < 2; y++) {
							// X axis
							for (int x = -1; x < 2; x++) {
								// Z axis
								for (int z = -1; z < 2; z++) {
									// check blocks surrounding the initial one
									// if they're logs that haven't been destroyed yet, add to block list
									if(Arrays.asList(logs).contains(bList.get(0).getRelative(x,y,z).getType().toString().toLowerCase()) && !bList.contains(bList.get(0).getRelative(x,y,z)) && bList.get(0).getRelative(x,y,z).getMetadata("ppb").isEmpty()) {
										// add the next block to the list that hasn't been destroyed yet
										bList.add(bList.get(0).getRelative(x,y,z));
									}
								}
							}
						}
						
						// check that the log was not placed by a player
						if(bList.get(0).getMetadata("ppb").isEmpty()) {
							// break current log
							bList.get(0).breakNaturally();
							n++; // increment tool damage
						}
						
						// exit early if tool breaks
						if(plugin.getConfig().getBoolean("treechopping.durability",false) && (n == (event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() - meta.getDamage()))) {
							canDestroy = false;
							n++;
						}
						
						// remove leaves if necessary
						// also a possible source of lag
						if(plugin.getConfig().getBoolean("treechopping.instantleafdecay",false) && canDestroy) {
							for (int xl = -3; xl < 4; xl++) {
								for (int zl = -3; zl < 4; zl++) {
									for (int yl = 0; yl < 2; yl++) {
										if(Arrays.asList(leaves).contains(bList.get(0).getRelative(xl,yl,zl).getType().toString().toLowerCase())) {
											if(bList.get(0).getRelative(xl,yl,zl).getMetadata("ppb").isEmpty()) {
												bList.get(0).getRelative(xl,yl,zl).breakNaturally();
											}
										}
									}
								}
							}
						}

						// update list before looping
						bList.remove(0);
					}
					
					// if durability usage is on, remove durability from axe
					if(plugin.getConfig().getBoolean("treechopping.durability",false)) {
						int currentDamage = meta.getDamage();
						meta.setDamage(currentDamage + n);
						event.getPlayer().getInventory().getItemInMainHand().setItemMeta((ItemMeta) meta);
						axeMeta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
						meta = (Damageable) axeMeta;
						if ((event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() - meta.getDamage()) < 1) {
							meta.setDamage(event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability());
							event.getPlayer().getInventory().getItemInMainHand().setItemMeta((ItemMeta) meta);
						}
					}
				}
			}
		}
	}
	
	public BlockBreak(Plugin plugin, Main main) {
		this.plugin = plugin;
		this.main = main;
	}
}
