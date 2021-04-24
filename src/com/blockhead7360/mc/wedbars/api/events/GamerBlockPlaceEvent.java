package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerBlockPlaceEvent extends Event implements Cancellable {
	
	private Gamer gamer;
	private BlockPlaceEvent blockPlaceEvent;
	private boolean isCancelled;
	
	public GamerBlockPlaceEvent(Gamer gamer, BlockPlaceEvent blockPlaceEvent) {
		this.gamer = gamer;
		this.blockPlaceEvent = blockPlaceEvent;
		this.isCancelled = false;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public BlockPlaceEvent getBlockPlaceEvent() {
		return blockPlaceEvent;
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
