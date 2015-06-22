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

public class LockChat implements CommandExecutor, Listener {
	
	String prefix = Main.prefix;
	public static boolean globalmute = false;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("lockchat")) {
			
			if (globalmute == false) {
				
				if (player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Chat has been " + ChatColor.RED + "locked!");
					globalmute = true;					
				} else if(!player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
				
			} else if (globalmute == true) {
				
				if (player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Chat is already " + ChatColor.RED + "locked!");			
				} else if(!player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
			}
		}
		
		if (command.equalsIgnoreCase("unlockchat")) {
			
			if (globalmute == false) {
				
				if (player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Chat is already " + ChatColor.GREEN + "unlocked!");
					globalmute = true;		
					
				} else if(!player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
				
			} else if (globalmute == true) {
				
				if (player.hasPermission(Permissions.command_lockchat)) {
					player.sendMessage(prefix + ChatColor.GOLD + "Chat has been " + ChatColor.GREEN + "unlocked!");
					globalmute = false;
					
				} else if(!player.hasPermission(Permissions.command_lockchat)) {
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
			player.sendMessage(prefix + ChatColor.GOLD + "Chat is currently " + ChatColor.RED + "locked!");
		}
	}
}
