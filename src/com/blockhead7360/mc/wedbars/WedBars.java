package com.blockhead7360.mc.wedbars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WedBars extends JavaPlugin {

	public static boolean running = false;
	
	public void onEnable() {
		// test commit test
		getLogger().info("Github works!");
		
	}
	
	public void onDisable() {


	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		return true;

	}


}
