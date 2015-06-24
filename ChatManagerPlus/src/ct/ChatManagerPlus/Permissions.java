package ct.ChatManagerPlus;

import org.bukkit.permissions.Permission;

public class Permissions {
	
	public static Permission command_lockchat = new Permission("ChatManagerPlus.lockchat");
	public static Permission command_muteplayer = new Permission("ChatManagerPlus.mute");
	public static Permission command_clearchat = new Permission("ChatManagerPlus.clearchat");
	public static Permission command_staffchat = new Permission("ChatManagerPlus.staffchat");
	public static Permission command_broadcast = new Permission("ChatManagerPlus.broadcast");
	public static Permission command_cooldown = new Permission("ChatManagerPlus.cooldownchat");

	public static Permission bypass_lockchat = new Permission("ChatManagerPlus.lockchatbypass");
	public static Permission bypass_mute = new Permission("ChatManagerPlus.mutebypass");
	public static Permission bypass_cooldown = new Permission("ChatManagerPlus.cooldownchatbypass");

	public static Permission passive_colorchat = new Permission("ChatManagerPlus.coloredchat");
}
