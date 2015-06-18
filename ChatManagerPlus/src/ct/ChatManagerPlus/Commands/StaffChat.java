package ct.ChatManagerPlus.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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

public class StaffChat implements Listener,CommandExecutor {
	
	String prefix = Main.prefix;
	String scprefix = Main.scprefix;
	static ArrayList<String> staffchat = new ArrayList<String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
			return true;
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		String uuid = player.getUniqueId().toString();
		
		if (command.equalsIgnoreCase("StaffChat")) {
			
			if (!player.hasPermission(Permissions.command_staffchat)) {
				player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
				player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				return true;
			}
			
			if (!staffchat.contains(uuid)) {
				staffchat.add(uuid);
				player.sendMessage(prefix + ChatColor.GOLD + "Staff Chat " + ChatColor.GREEN + "enabled!");
			} 
			
			else if (staffchat.contains(uuid)) {
				staffchat.remove(uuid);
				player.sendMessage(prefix + ChatColor.GOLD + "Staff Chat " + ChatColor.RED + "disabled!");
			}
		
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		if (staffchat.contains(uuid)) {
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				
				if (staffchat.contains(p.getUniqueId().toString())) {
					event.setCancelled(true);
					p.sendMessage(scprefix + ChatColor.GREEN + event.getMessage());
				} 
				
				else {
					event.setCancelled(true);
				}
			}
		}
	}
}
