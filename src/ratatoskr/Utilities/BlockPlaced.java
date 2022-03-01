package ratatoskr.Utilities;

import java.util.Arrays;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// adds metadata to placed blocks
public class BlockPlaced implements Listener {
	Plugin plugin;
	
	public BlockPlaced(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public String[] logs = {"oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "dark_oak_log", "crimson_stem", "warped_stem"};
	public String[] leaves = {"oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves", "acacia_leaves", "dark_oak_leaves"};
	
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent event) {
		if(Arrays.asList(logs).contains(event.getBlock().getType().toString().toLowerCase()) || Arrays.asList(leaves).contains(event.getBlock().getType().toString().toLowerCase())) {
			event.getBlock().setMetadata("ppb", new FixedMetadataValue(this.plugin, event.getPlayer().getName()));
		}
		
		if(plugin.getConfig().getBoolean("cryingportals.enabled")) {
			int minWidth = plugin.getConfig().getInt("cryingportals.minwidth");
			int minHeight = plugin.getConfig().getInt("cryingportals.minheight");
			int maxWidth = plugin.getConfig().getInt("cryingportals.maxwidth");
			int maxHeight = plugin.getConfig().getInt("cryingportals.maxheight");
			if(event.getBlock().getType() == Material.FIRE && event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.CRYING_OBSIDIAN) {
				Block bPoz = event.getBlock().getRelative(BlockFace.DOWN);
				Block bNeg = event.getBlock().getRelative(BlockFace.DOWN);
				boolean axis; // true = east, false = south
				Location bl;
				Location br;
//				Location tl;
//				Location tr;
				int width = 1;
				int height = 1;
				
				// determine horizontal axis
				if(minWidth > 1) {
					if(bPoz.getRelative(BlockFace.EAST).getType() == Material.CRYING_OBSIDIAN || bNeg.getRelative(BlockFace.WEST).getType() == Material.CRYING_OBSIDIAN) {
						axis = true;
					}
					else if(bPoz.getRelative(BlockFace.SOUTH).getType() == Material.CRYING_OBSIDIAN || bNeg.getRelative(BlockFace.NORTH).getType() == Material.CRYING_OBSIDIAN) {
						axis = false;
					}
					else return;
				}
				// 1 block wide portal configurations
				else {
					if(bPoz.getRelative(1, 1, 0).getType() == Material.CRYING_OBSIDIAN || bNeg.getRelative(-1, 1, 0).getType() == Material.CRYING_OBSIDIAN) {
						axis = true;
					}
					else if(bPoz.getRelative(0, 1, 1).getType() == Material.CRYING_OBSIDIAN || bNeg.getRelative(0, 1, -1).getType() == Material.CRYING_OBSIDIAN) {
						axis = false;
					}
					else return;
				}
				
				// find the bottom row of the portal
				while(width < maxWidth) {
					// search x axis
					if(axis) {
						// check positive x direction
						if(bPoz.getRelative(BlockFace.EAST).getType() == Material.CRYING_OBSIDIAN) {
							bPoz = bPoz.getRelative(BlockFace.EAST);
							width++;
						}
						if(bNeg.getRelative(BlockFace.WEST).getType() == Material.CRYING_OBSIDIAN) {
							bNeg = bNeg.getRelative(BlockFace.WEST);
							width++;
						}
						if(bPoz.getRelative(BlockFace.EAST).getType() != Material.CRYING_OBSIDIAN && bNeg.getRelative(BlockFace.WEST).getType() != Material.CRYING_OBSIDIAN) {
							break;
						}
					}
					else {
						if(bPoz.getRelative(BlockFace.SOUTH).getType() == Material.CRYING_OBSIDIAN) {
							bPoz = bPoz.getRelative(BlockFace.SOUTH);
							width++;
						}
						if(bNeg.getRelative(BlockFace.NORTH).getType() == Material.CRYING_OBSIDIAN) {
							bNeg = bNeg.getRelative(BlockFace.NORTH);
							width++;
						}
						if(bPoz.getRelative(BlockFace.SOUTH).getType() != Material.CRYING_OBSIDIAN && bNeg.getRelative(BlockFace.NORTH).getType() != Material.CRYING_OBSIDIAN) {
							break;
						}
					}
				}
				
				// find bottom corners
				if(axis) {
					// portal with corners filled in
					if(bNeg.getRelative(BlockFace.UP).getType() == Material.CRYING_OBSIDIAN) {
						bl = bNeg.getLocation();
						bNeg = bNeg.getRelative(BlockFace.WEST);
						width--;
					}
					else if(bNeg.getRelative(-1, 1, 0).getType() == Material.CRYING_OBSIDIAN) {
						bl = bNeg.getRelative(BlockFace.WEST).getLocation();
					}
					else return;
					if(bPoz.getRelative(BlockFace.UP).getType() == Material.CRYING_OBSIDIAN) {
						br = bPoz.getLocation();
						bPoz = bPoz.getRelative(BlockFace.EAST);
						width--;
					}
					else if(bPoz.getRelative(1, 1, 0).getType() == Material.CRYING_OBSIDIAN) {
						br = bPoz.getRelative(BlockFace.EAST).getLocation();
					}
					else return;
				}
				else {
					if(bNeg.getRelative(BlockFace.UP).getType() == Material.CRYING_OBSIDIAN) {
						bl = bNeg.getLocation();
						bNeg = bNeg.getRelative(BlockFace.NORTH);
						width--;
					}
					else if(bNeg.getRelative(0, 1, -1).getType() == Material.CRYING_OBSIDIAN) {
						bl = bNeg.getRelative(BlockFace.NORTH).getLocation();
					}
					else {
						return;
					}
					if(bPoz.getRelative(BlockFace.UP).getType() == Material.CRYING_OBSIDIAN) {
						br = bPoz.getLocation();
						bNeg = bNeg.getRelative(BlockFace.SOUTH);
						width--;
					}
					else if(bPoz.getRelative(0, 1, 1).getType() == Material.CRYING_OBSIDIAN) {
						br = bPoz.getRelative(BlockFace.SOUTH).getLocation();
					}
					else return;
				}
				
				// find portal height
				Block b = event.getBlock();
				while(height < maxHeight) {
					// search until a block is reached
					if(b.getRelative(BlockFace.UP).getType() == Material.AIR) {
						b = b.getRelative(BlockFace.UP);
						height++;
					}
					else if(b.getRelative(BlockFace.UP).getType() == Material.CRYING_OBSIDIAN) {
						break;
					}
					else return;
				}
				
				if(height < minHeight) {
					return;
				}
				
				// find top row and top corners
				for(int i = 1; i <= width; i++) {
					if(axis) {
						if(!(bl.getBlock().getRelative(i, (height + 1), 0).getType() == Material.CRYING_OBSIDIAN)) {
							return; 
						}
						if(i == (width - 1)) {
							if(bl.getBlock().getRelative(0, height, 0).getType() == Material.CRYING_OBSIDIAN && br.getBlock().getRelative(0, height, 0).getType() == Material.CRYING_OBSIDIAN) {
//								tl = bl.getBlock().getRelative(0, (height + 1), 0).getLocation();
//								tr = br.getBlock().getRelative(0, (height + 1), 0).getLocation();
							}
							else return;
						}
					}
					else {
						if(!(bl.getBlock().getRelative(0, (height + 1), i).getType() == Material.CRYING_OBSIDIAN)) {
							return;
						}
						if(i == (width - 1)) {
							if(bl.getBlock().getRelative(0, height, 0).getType() == Material.CRYING_OBSIDIAN && br.getBlock().getRelative(0, height, 0).getType() == Material.CRYING_OBSIDIAN) {
//								tl = bl.getBlock().getRelative(0, (height + 1), 0).getLocation();
//								tr = br.getBlock().getRelative(0, (height + 1), 0).getLocation();
							}
							else return;
						}
					}
				}
				
				// check the sides
				Block bLeft = bl.getBlock().getRelative(BlockFace.UP);
				Block bRight = br.getBlock().getRelative(BlockFace.UP);
				for(int i = 0; i < height; i++) {
					if(!(bLeft.getRelative(0, i, 0).getType() == Material.CRYING_OBSIDIAN)) {
						return;
					}
					if(!(bRight.getRelative(0, i, 0).getType() == Material.CRYING_OBSIDIAN)) {
						return;
					}
				}
				
				// check that all blocks inside the portal are AIR 
				for (int i = 1; i <= height; i++) {
					for (int j = 1; j <= width; j++) {
						if(axis) {
							if(!(bl.getBlock().getRelative(j,i,0).getType() == Material.AIR || bl.getBlock().getRelative(j,i,0).getType() == event.getBlock().getType())) {
								return;
							}
						}
						else {
							if(!(bl.getBlock().getRelative(0,i,j).getType() == Material.AIR || bl.getBlock().getRelative(0,i,j).getType() == event.getBlock().getType())) {
								return;
							}
						}
					}
				}
				
				// place portal blocks
				// TODO: change portal blocks behaviors
				// see: PlayerPortalEvent and PortalCreateEvent
				for (int i = 1; i <= height; i++) {
					for (int j = 1; j <= width; j++) {
						if(axis) {
							bl.getBlock().getRelative(j,i,0).setType(Material.NETHER_PORTAL);
						}
						else {
							bl.getBlock().getRelative(0,i,j).setType(Material.NETHER_PORTAL);
							BlockData data = bl.getBlock().getRelative(0,i,j).getBlockData();
							Orientable face = (Orientable) data;
							face.setAxis(Axis.Z);
							bl.getBlock().getRelative(0, i, j).setBlockData(face);
							bl.getBlock().getRelative(0, i, j).setMetadata("conp", new FixedMetadataValue(this.plugin, event.getPlayer().getName()));
						}
					}
				}
			}
		}
	}
}
