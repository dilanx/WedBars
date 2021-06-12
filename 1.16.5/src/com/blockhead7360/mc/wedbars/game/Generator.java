package com.blockhead7360.mc.wedbars.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.blockhead7360.mc.wedbars.api.events.GeneratorSpawnItemEvent;

public class Generator {
	
	private Location location;
	private int speed;
	
	private int curTimeLeft;
	
	public Generator(Location location, int speed) {
		
		this.location = location;
		this.speed = speed;
		this.curTimeLeft = 1;
		
	}

	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean passTime() {
		
		curTimeLeft--;
				
		if (curTimeLeft <= 0) {
			
			curTimeLeft = speed;
			return true;
			
		}
		
		return false;
	}
	
	public void spawnItem(ItemStack item, boolean hasItemMeta) {
		
		GeneratorSpawnItemEvent gsie = new GeneratorSpawnItemEvent(this, item);
		
		if (gsie.isCancelled()) return;

		// TODO: make sure this doesn't cause issues. main goal is to differentiate diamonds and
		//  emeralds from iron and gold since these can't be split
		// There is in fact an issue; generated diamonds really enjoy stacking with dropped diamonds
		// when stacks are on the ground and multiple blocks apart. Then again, could be paper being paper.
		// Either way, the correct amount is generated.

		if (item.getType() == Material.DIAMOND || item.getType() == Material.EMERALD) {
			location.getWorld().dropItem(location, item);
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		if (hasItemMeta) {
			
			meta.setDisplayName(meta.getDisplayName() + " *");
			
		} else {
			
			meta.setDisplayName(" *");
			
		}
		
		item.setItemMeta(meta);
		
		location.getWorld().dropItem(location, item);
		
		
		
	}

	public int getCurTimeLeft() {
		return curTimeLeft;
	}


	public void setCurTimeLeft(int curTimeLeft) {
		this.curTimeLeft = curTimeLeft;
	}
	
	
	
}
