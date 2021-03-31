package com.blockhead7360.mc.wedbars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WedBars extends JavaPlugin {

	public static boolean running = false;
	
	private static WedBars instance;
		
	public void onEnable() {
		// test commit test
		getLogger().info("Github works!");
		
		
		
		instance = this;
		
	}
	
	public void onDisable() {

	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		
		return true;

	}
	
	public static WedBars getInstance() {
		
		return instance;
		
	}


}
