package ratatoskr.Utilities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AutoChop implements CommandExecutor {
	Plugin plugin;
	Main main;
	
	public AutoChop(Plugin plugin, Main main) {
		this.plugin = plugin;
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 1) {
				if (args[0].equals("true")) {
					main.getTreeChopperSettings().set(sender.getName(), true);
				}
				else if(args[0].equals("false")) {
					main.getTreeChopperSettings().set(sender.getName(), false);
				}
				else if(args[0].equals("help")) {
					sender.sendMessage("Changes your tree feller settings, false will disable it. Command use is: /autochop [true/false]");
				}
				else {
					return false;
				}
				main.saveTreeChopperSettings();
			}
			else {
				return false;
			}
		}
		else if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command. If you wish to disable this plugin see /autochopconfig");
		}
		return true;
	}
}
