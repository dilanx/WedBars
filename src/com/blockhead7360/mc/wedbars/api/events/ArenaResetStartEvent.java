package com.blockhead7360.mc.wedbars.api.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaResetStartEvent extends Event {

	private List<Location> blockList;
	
	public ArenaResetStartEvent(List<Location> blockList) {
		this.blockList = blockList;
	}

	/**
	 * Fetches the list of locations of blocks that were placed during the game
	 * for the auto reset to clean up.
	 * @return a List of Locations of blocks
	 */
	public List<Location> getBlockList() {
		return blockList;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
}
