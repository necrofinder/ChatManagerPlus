package ct.ChatManagerPlus;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ct.ChatManagerPlus.Commands.Broadcast;
import ct.ChatManagerPlus.Commands.ChatCooldown;
import ct.ChatManagerPlus.Commands.ClearChat;
import ct.ChatManagerPlus.Commands.LockChat;
import ct.ChatManagerPlus.Commands.Mute;
import ct.ChatManagerPlus.Commands.StaffChat;
import ct.ChatManagerPlus.Listeners.ColorChat;

public class Main extends JavaPlugin {
	
	PluginDescriptionFile pdf = this.getDescription();
	public static String prefix;
	public static String scprefix;
	public static String bcprefix;
	
	public static File mutedplayersYml;
	FileConfiguration mpConfig;
	
	private static Plugin plugin;
	
	public static Plugin getPlugin() {
		return plugin;
	}

	public void onEnable() {
		
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("ChatManagerPlus.prefix"));
		scprefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("ChatManagerPlus.staffchatprefix"));
		bcprefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("ChatManagerPlus.broadcastprefix"));
		mutedplayersYml = new File(getDataFolder() + "/MutedPlayers.yml");
		plugin = this;
		
		getServer().getLogger().info("[ChatManagerPlus] Plugin enabled!");
		saveConfig();
		
		mpConfig = YamlConfiguration.loadConfiguration(mutedplayersYml);
		mpConfig.set("Muted Players", Mute.mutedplayers);
		saveCustomYml(mpConfig, mutedplayersYml);
		
		ChatCooldown chatcd = new ChatCooldown();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new LockChat(), this);
		pm.registerEvents(new Mute(),this);
		pm.registerEvents(new StaffChat(), this);
		pm.registerEvents(new ColorChat(), this);
		pm.registerEvents(chatcd, this);
		
		new ClearChat();
		getCommand("clearchat").setExecutor(new ClearChat());
		
		new LockChat();
		getCommand("lockchat").setExecutor(new LockChat());
		getCommand("unlockchat").setExecutor(new LockChat());
		
		new Mute();
		getCommand("mute").setExecutor(new Mute());
		getCommand("unmute").setExecutor(new Mute());
		
		new StaffChat();
		getCommand("staffchat").setExecutor(new StaffChat());
		
		getCommand("chatcooldown").setExecutor(chatcd);
		
		new Broadcast();
		getCommand("cbroadcast").setExecutor(new Broadcast());
	}

	public void onDisable() {
		getServer().getLogger().info("[ChatManagerPlus] Plugin disabled! ");
		
		mpConfig.set("Muted Players", Mute.mutedplayers);
		saveCustomYml(mpConfig, mutedplayersYml);
		
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("[ChatManagerPlus] Only players can do those commands!");
			return true;
		}
		
		Player player = (Player) sender;
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("chatmanagerplus")) {
			
			if (args.length == 0) {
				player.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Do " + ChatColor.AQUA + "/cmp help " + ChatColor.RED + "for more information!");
			}
			
			if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.GREEN + "ChatManagerPlus Help Menu");
					player.sendMessage(ChatColor.AQUA + "[argument] " + ChatColor.GRAY + " = " + ChatColor.GREEN + "REQUIRED");
					player.sendMessage(ChatColor.AQUA + "<argument> " + ChatColor.GRAY + " = " + ChatColor.RED + "NOT REQUIRED");
					player.sendMessage(ChatColor.RED + "To lock chat, do " + ChatColor.AQUA + "/lockchat");
					player.sendMessage(ChatColor.RED + "To unlock chat, do " + ChatColor.AQUA + "/unlockchat");
					player.sendMessage(ChatColor.RED + "To mute a player, do " + ChatColor.AQUA + "/mute [Player|global]");
					player.sendMessage(ChatColor.RED + "To unmute a player, do " + ChatColor.AQUA + "/unmute [Player|global]");
					player.sendMessage(ChatColor.RED + "To clear chat, do " + ChatColor.AQUA + "/clearchat [Player|global]");
					player.sendMessage(ChatColor.RED + "To broadcast, do " + ChatColor.AQUA + "/broadcast|bc [message]");
					player.sendMessage(ChatColor.RED + "To initiate chat cooldown, do " + ChatColor.AQUA + "/chatcooldown [seconds]");
					player.sendMessage(ChatColor.RED + "To view permissions list, do " + ChatColor.AQUA + "/cmp permissions " + ChatColor.RED + "(IN PROGRESS)");
				}
			}
		}
		return false;	
	}
	
	public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
