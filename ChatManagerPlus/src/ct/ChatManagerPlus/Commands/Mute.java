package ct.ChatManagerPlus.Commands;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ct.ChatManagerPlus.Main;
import ct.ChatManagerPlus.Permissions;

public class Mute implements CommandExecutor, Listener {
	
	static File mpyml = Main.mutedplayersYml;
	static FileConfiguration mpConfig = YamlConfiguration.loadConfiguration(mpyml);
	
	public static List<String> mutedplayers = mpConfig.getStringList("Muted Players");
	String prefix = Main.prefix;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("mute")) {
			
			if (args.length == 0) {
				player.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Do " + ChatColor.AQUA + "/cmp help " + ChatColor.RED + "for more information!");
			}
			
			if (args.length == 1) {
				
				if (!player.hasPermission(Permissions.command_muteplayer)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
				
				if (player.hasPermission(Permissions.command_muteplayer)) {
					
						@SuppressWarnings("deprecation")
						Player target = Bukkit.getServer().getPlayer(args[0]);
					
						if (target == null) {
							player.sendMessage(prefix + ChatColor.AQUA + args[0] + ChatColor.GOLD + " is " + ChatColor.RED + "offline!");
						}
				
						if (target != null) {
						
							String uuidtarget = target.getUniqueId().toString();

							if (mutedplayers.contains(uuidtarget)) {
								player.sendMessage(prefix + ChatColor.AQUA + target.getName() + ChatColor.GOLD + " is already " + ChatColor.RED + "muted!");
							}
					
							else if (!mutedplayers.contains(uuidtarget)) {
								mutedplayers.add(uuidtarget);
								player.sendMessage(prefix + ChatColor.GOLD + "You have " + ChatColor.RED + "muted " + ChatColor.AQUA + target.getName() + ChatColor.GOLD + ".");
								target.sendMessage(prefix + ChatColor.GOLD + "You have been " + ChatColor.RED + "muted " + ChatColor.GOLD + " by " + ChatColor.AQUA + player.getName());
								Bukkit.getServer().broadcastMessage(prefix + ChatColor.AQUA + player.getName() + ChatColor.GOLD + " has " + ChatColor.RED + "muted " + ChatColor.AQUA + target.getName() + ChatColor.GOLD + ".");
							}
					
						}	
					} 
				}
			}
		
		if (command.equalsIgnoreCase("unmute")) {
			
			if (args.length == 0) {
				player.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Do " + ChatColor.AQUA + "/cmp help " + ChatColor.RED + "for more information!");
			}
			
			if (args.length == 1) {
				
				if (!player.hasPermission(Permissions.command_muteplayer)) {
					player.sendMessage(prefix + ChatColor.RED + "Insufficient permission.");
					player.sendMessage(prefix + ChatColor.RED + "To find out more, do " + ChatColor.AQUA + "/cmp permissions");
				}
			
				if (player.hasPermission(Permissions.command_muteplayer)) {
					
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getServer().getPlayer(args[0]);
				
					if (target == null) {
						player.sendMessage(prefix + ChatColor.AQUA + args[0] + ChatColor.GOLD + " is " + ChatColor.RED + "offline!");
					}
				
					if (target != null) {
						
						String uuidtarget = target.getUniqueId().toString();

						if (!mutedplayers.contains(uuidtarget)) {
							player.sendMessage(prefix + ChatColor.AQUA + target.getName() + ChatColor.GOLD + " is already " + ChatColor.GREEN + "unmuted!");
						}
					
						else if (mutedplayers.contains(uuidtarget)) {
							mutedplayers.remove(uuidtarget);
							player.sendMessage(prefix + ChatColor.GOLD + "You have " + ChatColor.GREEN + "unmuted " + ChatColor.AQUA + target.getName() + ChatColor.GOLD + ".");
							target.sendMessage(prefix + ChatColor.GOLD + "You have been " + ChatColor.GREEN + "unmuted " + ChatColor.GOLD + " by " + ChatColor.AQUA + player.getName());
							Bukkit.getServer().broadcastMessage(prefix + ChatColor.AQUA + player.getName() + ChatColor.GOLD + " has "+ ChatColor.GREEN + "unmuted " + ChatColor.AQUA + target.getName() + ChatColor.GOLD + ".");
						}
					
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		
		Player player = (Player) event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		if (mutedplayers.contains(uuid)) {
			player.sendMessage(prefix + ChatColor.GOLD + "You are currently " + ChatColor.RED + "muted!");
			event.setCancelled(true);
		}
	}

}
