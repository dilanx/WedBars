package com.blockhead7360.mc.wedbars;

import org.bukkit.Location;

public class Generator {
	
	private Location location;
	private int speed;
	
	private int curTimeLeft;
	
	public Generator(Location location, int speed) {
		
		this.location = location;
		this.speed = speed;
		this.curTimeLeft = speed;
		
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

	public int getCurTimeLeft() {
		return curTimeLeft;
	}


	public void setCurTimeLeft(int curTimeLeft) {
		this.curTimeLeft = curTimeLeft;
	}
	
	
	
}
