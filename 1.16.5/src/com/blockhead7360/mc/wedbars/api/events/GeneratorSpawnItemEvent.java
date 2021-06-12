package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.blockhead7360.mc.wedbars.game.Generator;

public class GeneratorSpawnItemEvent extends Event implements Cancellable {
	
	private Location location;
	private int currentInterval;
	private ItemStack item;
	private boolean isCancelled;
	
	public GeneratorSpawnItemEvent(Generator generator, ItemStack item) {
		this.location = generator.getLocation();
		this.currentInterval = generator.getSpeed();
		this.item = item;
		this.isCancelled = false;
	}
	
	public Location getLocation() {
		return location;
	}

	public int getCurrentInterval() {
		return currentInterval;
	}

	public ItemStack getItem() {
		return item;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
}
