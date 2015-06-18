package ct.ChatManagerPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ct.ChatManagerPlus.Main;
import ct.ChatManagerPlus.Permissions;

public class ClearChat implements CommandExecutor {

	String prefix = Main.prefix;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
			return true;
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("clearchat")) {
			
			if (!player.hasPermission(Permissions.command_clearchat)) {
				player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
				player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
			}
			
			if (player.hasPermission(Permissions.command_clearchat)) {
			
				if (args.length == 0) {
					
					for (int i = 0; i<100; i++) {
						player.sendMessage(" ");
					}
					player.sendMessage(prefix + ChatColor.GOLD + "Your chat has been cleared by " + ChatColor.AQUA + player.getName());
				}
				
				if (args.length == 1) {
					
					if (args[0].equalsIgnoreCase("global")) {
						
						for (int i = 0; i<100; i++) {
							Bukkit.getServer().broadcastMessage(" ");
						}
						
						for (Player all : Bukkit.getServer().getOnlinePlayers()) {
							all.sendMessage(prefix + ChatColor.GOLD + "Chat has been cleared by " + ChatColor.AQUA + player.getName());
						}
					}
					
					if (!args[0].equalsIgnoreCase("global")) {
						
						@SuppressWarnings("deprecation")
						Player target = Bukkit.getServer().getPlayer(args[0]);
						
						if (target == null) {
							player.sendMessage(prefix + ChatColor.AQUA + args[0] + ChatColor.GOLD + " is " + ChatColor.RED + "offline!");
						}
						
						if (target != null) {
							
							for (int i = 0;i<100;i++) {
								target.sendMessage(" ");
							}
							target.sendMessage(prefix + ChatColor.GOLD + "Your chat has been cleared by " + ChatColor.AQUA + player.getName());
						}
					}
				}
			}
		}
		
		return false;
	}
	
	

}
