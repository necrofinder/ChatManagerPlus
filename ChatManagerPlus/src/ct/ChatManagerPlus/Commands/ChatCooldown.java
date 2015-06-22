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

public class ChatCooldown implements Listener, CommandExecutor{
	
	String prefix = Main.prefix;
	static ArrayList<String> cooldown = new ArrayList<String>();
	boolean chatcd = false;
	int cds;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
			return true;
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("chatcooldown")) {
			
			if (!player.hasPermission(Permissions.command_cooldown)) {
				player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
				player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				return true;
			}
			
			if (args.length == 0) {
				player.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Do " + ChatColor.AQUA + "/cmp help " + ChatColor.RED + "for more information!");
			}
			
			if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("disable")) {
					
					if (chatcd == true) {
						chatcd = false;
						player.sendMessage(prefix + ChatColor.GOLD + "Chat cooldown has been " + ChatColor.RED + "disabled!");
						return true;
					}
				}
				
				if (!args[0].equalsIgnoreCase("disable")) {
					int cd;
					try {
						cd = Integer.parseInt(args[0]);
					} catch(NumberFormatException e) {
						player.sendMessage(prefix + ChatColor.RED + "Please specify the number of seconds!");
						return true;
					}
					cds = cd;
					if (chatcd == false) {
						chatcd = true;
						player.sendMessage(prefix + ChatColor.GOLD + "Chat set on cooldown at " + ChatColor.RED + cds + ChatColor.GOLD + " seconds between messages!");
					}
				
					else if (chatcd == true) {
						player.sendMessage(prefix + ChatColor.GOLD + "Chat cooldown is already" + ChatColor.GREEN + " enabled at " + ChatColor.AQUA + cd + ChatColor.GOLD + " seconds!");
					}
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChatCooldown(AsyncPlayerChatEvent event) {
		Player player = (Player) event.getPlayer();
		String pname = player.getName();
		final String name = player.getName();

		if (chatcd == true) {
			
			if (!cooldown.contains(pname)) {
				cooldown.add(pname);
				
				Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(), new Runnable() {
					public void run() {
						cooldown.remove(name);
					}
				}, cds * 20);
			}

			else if (cooldown.contains(pname)) {
				player.sendMessage(prefix + ChatColor.RED + "You are currently on cooldown!");
				event.setCancelled(true);
			}	
		}
	}
}
