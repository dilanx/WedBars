package com.blockhead7360.mc.wedbars.game;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.blockhead7360.mc.wedbars.WedBars;
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
	
	public void spawnItem(ItemStack item) {
		
		GeneratorSpawnItemEvent gsie = new GeneratorSpawnItemEvent(this, item);
		
		if (gsie.isCancelled()) return;
		
		Item i = location.getWorld().dropItem(location, item);
		i.setMetadata("gen", new FixedMetadataValue(WedBars.getInstance(), true));
		
		
		
	}

	public int getCurTimeLeft() {
		return curTimeLeft;
	}


	public void setCurTimeLeft(int curTimeLeft) {
		this.curTimeLeft = curTimeLeft;
	}
	
	
	
}
