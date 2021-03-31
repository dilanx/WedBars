package com.blockhead7360.mc.wedbars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Arena {
	
	
	private World world;
	private Map<Team, ArenaTeam> teams;
	private Generator[] diamonds;
	private Generator[] emeralds;
	
	public Arena(ArenaTeam[] teams, Generator[] diamonds, Generator[] emeralds) {
		
		this.diamonds = diamonds;
		this.emeralds = emeralds;
		
		this.world = diamonds[0].getLocation().getWorld();
		
		this.teams = new HashMap<Team, ArenaTeam>();

		for (ArenaTeam at : teams) {
			
			this.teams.put(at.getTeam(), at);
			
		}
		
		
	}
	
	/* 10 PER SECOND */
	
	public void start() {
		
		new BukkitRunnable() {
			
			public void run() {
				
				
				
				// Diamond gen
				
				for (Generator d : diamonds) {
					
					if (d.passTime()) {
						
						world.dropItem(d.getLocation(), new ItemStack(Material.DIAMOND, 1));
						
					}
					
				}
				
				// Emerald gen
				
				for (Generator e : emeralds) {
					
					if (e.passTime()) {
						
						world.dropItem(e.getLocation(), new ItemStack(Material.EMERALD, 1));
						
					}
					
				}
				
			}
			
		}.runTaskTimer(WedBars.getInstance(), 0, 2L);
		
		
		
	}
	
	
	
}
