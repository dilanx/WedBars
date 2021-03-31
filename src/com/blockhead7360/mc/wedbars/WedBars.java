package com.blockhead7360.mc.wedbars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WedBars extends JavaPlugin {

	public static boolean running = false;
	
	public static final int EMERALD_START_SPEED = 50;
	public static final int DIAMOND_START_SPEED = 20;
	
	
	private static WedBars instance;
		
	public void onEnable() {
		// test commit
		getLogger().info("Github works!");
		
		
		getServer().getPluginManager().registerEvents(new BlockListeners(), this);
		
		instance = this;
		
	}
	
	public void onDisable() {

	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("start")) {
			
			// TEMPORARY TEST
			
			World world = getServer().getWorld("world");
			
			Generator[] emeralds = {
					new Generator(new Location(world, 12.5, 78, -11.5), EMERALD_START_SPEED),
					new Generator(new Location(world, -11.5, 78, -11.5), EMERALD_START_SPEED),
					new Generator(new Location(world, -11.5, 78, 12.5), EMERALD_START_SPEED),
					new Generator(new Location(world, 12.5, 78, 12.5), EMERALD_START_SPEED)
					
			};
			
			Generator[] diamonds = {
					new Generator(new Location(world, 0.5, 64, 51.5), DIAMOND_START_SPEED),
					new Generator(new Location(world, 52.5, 64, 0.5), DIAMOND_START_SPEED),
					new Generator(new Location(world, 0.5, 64, -51.5), DIAMOND_START_SPEED),
					new Generator(new Location(world, -51.5, 64, 0.5), DIAMOND_START_SPEED)
			};
			
			Arena arena = new Arena(new ArenaTeam[0], diamonds, emeralds);
			arena.start();
			
			
		}
		
		return true;

	}
	
	public static WedBars getInstance() {
		
		return instance;
		
	}


}
