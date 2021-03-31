package com.blockhead7360.mc.wedbars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WedBars extends JavaPlugin {

	// gamerTicks = 10 per second
	
	
	public static boolean running = false;
	public static Arena arena = null;
	
	
	// speeds (interval between spawns in 10*seconds)
	
	public static final int MAX_EMERALDS_IN_GEN = 3;
	public static final int MAX_DIAMONDS_IN_GEN = 3;
	
	public static final int EMERALD1_SPEED = 50;
	public static final int DIAMOND1_SPEED = 20;
	
	public static final int IRON1_SPEED = 20;
	
	
	public static final int GOLD1_SPEED = 50;
	
	public static final int RESPAWN_TIME = 50;
	
	
	private static WedBars instance;
		
	public void onEnable() {
		// test commit
		getLogger().info("Github works!");
		
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		
		instance = this;
		
	}
	
	public void onDisable() {

	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("wedbars")) {
			
			sender.sendMessage(" ");
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Wed Bars " + getDescription().getVersion() + ChatColor.GRAY + " by Dilan and Jack.");
			sender.sendMessage(ChatColor.GRAY + getDescription().getDescription());
			sender.sendMessage(ChatColor.GRAY + getDescription().getWebsite());
			sender.sendMessage(" ");
			
		}
		
		if (cmd.getName().equalsIgnoreCase("start")) {
			
			// TEMPORARY
			
			World world = getServer().getWorld("world");
			
			Generator[] emeralds = {
					new Generator(new Location(world, 12.5, 78, -11.5), EMERALD1_SPEED),
					new Generator(new Location(world, -11.5, 78, -11.5), EMERALD1_SPEED),
					new Generator(new Location(world, -11.5, 78, 12.5), EMERALD1_SPEED),
					new Generator(new Location(world, 12.5, 78, 12.5), EMERALD1_SPEED)
					
			};
			
			Generator[] diamonds = {
					new Generator(new Location(world, 0.5, 64, 52.5), DIAMOND1_SPEED),
					new Generator(new Location(world, 52.5, 64, 0.5), DIAMOND1_SPEED),
					new Generator(new Location(world, 0.5, 64, -51.5), DIAMOND1_SPEED),
					new Generator(new Location(world, -51.5, 64, 0.5), DIAMOND1_SPEED)
			};
			
			ArenaTeam gray = new ArenaTeam(Team.GRAY,
					new Location(world, -72, 66, -33),
					new Location(world, -77, 66, -32),
					new Location[]{new Location(world, -65, 66, -33), new Location(world, -64, 66, -33)},
					new Gamer[]{new Gamer(getServer().getPlayer("Blockhead736"), Team.GRAY)});
			
			ArenaTeam green = new ArenaTeam(Team.GREEN,
					new Location(world, 72, 66, -33),
					new Location(world, 77, 66, -34),
					new Location[]{new Location(world, 65, 66, -33), new Location(world, 64, 66, -33)},
					new Gamer[]{new Gamer(getServer().getPlayer("ComputerCart"), Team.GREEN)});
			
			Arena arena = new Arena(new ArenaTeam[] {gray, green}, diamonds, emeralds);
			arena.start();
			
		}
		
		return true;

	}
	
	public static WedBars getInstance() {
		
		return instance;
		
	}


}
