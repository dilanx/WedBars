package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerBlockBreakEvent extends Event implements Cancellable {
	
	private Gamer gamer;
	private BlockBreakEvent blockBreakEvent;
	private boolean isCancelled;
	
	public GamerBlockBreakEvent(Gamer gamer, BlockBreakEvent blockBreakEvent) {
		this.gamer = gamer;
		this.blockBreakEvent = blockBreakEvent;
		this.isCancelled = false;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public BlockBreakEvent getBlockBreakEvent() {
		return blockBreakEvent;
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
