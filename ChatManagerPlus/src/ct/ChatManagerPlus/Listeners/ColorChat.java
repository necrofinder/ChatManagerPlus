package ct.ChatManagerPlus.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ct.ChatManagerPlus.Main;
import ct.ChatManagerPlus.Permissions;

public class ColorChat implements Listener {
	
	String prefix = Main.prefix;
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		if (event.getMessage().contains("&")) {
			
			if (player.hasPermission(Permissions.passive_colorchat)) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				event.setMessage(message);
			}
			
			else if (!player.hasPermission(Permissions.passive_colorchat)) {
				event.setCancelled(true);
				player.sendMessage(prefix + ChatColor.RED + "Insufficient permissions " + ChatColor.GOLD + "to chat in color!");
				
			}
		}
	}

}
