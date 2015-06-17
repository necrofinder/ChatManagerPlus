package ct.ChatManagerPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ct.ChatManagerPlus.Main;
import ct.ChatManagerPlus.Permissions;

public class GlobalChat implements CommandExecutor, Listener {
	
	String prefix = Main.prefix;
	public static boolean globalmute = false;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("globalmute")) {
			
			if (globalmute == false) {
				
				if (player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Global chat has been " + ChatColor.RED + "muted!");
					globalmute = true;					
				} else if(!player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
				
			} else if (globalmute == true) {
				
				if (player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Global chat is already " + ChatColor.RED + "muted!");			
				} else if(!player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
			}
		}
		
		if (command.equalsIgnoreCase("globalunmute")) {
			
			if (globalmute == false) {
				
				if (player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Global chat is already " + ChatColor.GREEN + "unmuted!");
					globalmute = true;		
					
				} else if(!player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
				
			} else if (globalmute == true) {
				
				if (player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Global chat has been " + ChatColor.GREEN + "unmuted!");
					globalmute = false;
					
				} else if(!player.hasPermission(Permissions.command_globalchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
			}
		}
	
	return false;
	
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		if (globalmute == true) {
			event.setCancelled(true);
			player.sendMessage(prefix + ChatColor.GOLD + "Global chat is currently " + ChatColor.RED + "muted!");
		}
	}
}
