package ratatoskr.Utilities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

// this is currently unused and only left here for future reference. 
// not included in the actual .jar to avoid adding unnecessary files.
public class AutoChopConfig implements CommandExecutor {
	Plugin plugin;
	
	public AutoChopConfig(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.isOp() || sender instanceof ConsoleCommandSender) {
			if(args.length == 2) {
				// for whatever reason, switch(args[0]) and args[1] == "string" refused to work so I have to use this ugly workaround
				if(args[0].equals("enabled")) {
					if(args[1].equals("true")) {
						plugin.getConfig().set("treechopping.enabled", true);
					}
					else if(args[1].equals("false")) {
						plugin.getConfig().set("treechopping.enabled", false);
					}
					else {
						return false;
					}
				}
				else if(args[0].equals("instantleafdecay")) {
					if(args[1].equals("true")) {
						plugin.getConfig().set("treechopping.instantleafdecay", true);
					}
					else if(args[1].equals("false")) {
						plugin.getConfig().set("treechopping.instantleafdecay", false);
					}
					else {
						return false;
					}
				}
				else if(args[0].equals("playertoggle")) {
					if(args[1].equals("true")) {
						plugin.getConfig().set("treechopping.playertoggle", true);
					}
					else if(args[1].equals("false")) {
						plugin.getConfig().set("treechopping.playertoggle", false);
					}
					else {
						return false;
					}
				}
				else if(args[0].equals("durability")) {
					if(args[1].equals("true")) {
						plugin.getConfig().set("treechopping.durability", true);
					}
					else if(args[1].equals("false")) {
						plugin.getConfig().set("treechopping.durability", false);
					}
					else {
						return false;
					}
				}
				else {
					// first argument matches none of the above
					return false;
				}
			}
			else if(args.length == 1 && args[0].equals("help")){
				sender.sendMessage(new String[] {"Command usage:","/autochopconfig [setting] [true/false]","settings:",
						"- enabled: enables or disables the plugin altogether",
						"- instantleafdecay: toggle leaves being destroyed on felling a tree, leaves will still drop saplings.",
						"- playertoggle: toggles the ability of players to use /autochop to toggle their own tree feller. Default plugin settings will be used for all players if this is disabled.",
						"- durability: toggles the tree felling using axe durability for every block chopped.",
						"Please note: you cannot add durability usage for breaking leaves, this is intended design."
						});
			}
			else {
				// every other case
				return false;
			}
		}
		plugin.saveConfig(); // always saves after successful command
		return true;
	}
}
