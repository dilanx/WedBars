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

	/**
	 * Fetches the Gamer who placed the block. Note that this is different than the standard BlockPlaceEvent in that
	 * it's triggered by Gamers in WedBars, so it will only start working during an active game. You'll have to decide
	 * which event type is best for your use case.
	 * @return a Gamer
	 * @see #getBlockPlaceEvent()
	 */
	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Fetches the standard Bukkit BlockPlaceEvent associated with this GamerBlockBreakEvent.
	 * @return a BlockPlaceEvent
	 */
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

	/**
	 * Checks if the GamerBlockPlaceEvent was cancelled.
	 * @return boolean isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Sets a GamerBlockPlaceEvent to be cancelled.
	 * @param isCancelled
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
}
