package ct.ChatManagerPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ct.ChatManagerPlus.Main;
import ct.ChatManagerPlus.Permissions;

public class Broadcast implements CommandExecutor {
	
	String prefix = Main.prefix;
	String bcprefix = Main.bcprefix;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
			return true;
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("cbroadcast")) {
			
			if (!player.hasPermission(Permissions.command_broadcast)) {
				player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
				player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				return true;
			}
			
			if (args.length == 0) {
				player.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Do " + ChatColor.AQUA + "/cmp help " + ChatColor.RED + "for more information!");
			}
			
			if (args.length == 1) {
				Bukkit.broadcastMessage(args[0]);
			}
			
			if (args.length > 1) {
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					str.append(" ").append(args[i]);
				}
				String message = str.toString();
				Bukkit.broadcastMessage(bcprefix + message);
 			}
		}
		return false;
	}
}
