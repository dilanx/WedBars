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

	/**
	 * Fetches the Gamer who broke the block. Note that this is different than the standard BlockBreakEvent in that
	 * it's triggered by Gamers in WedBars, so it will only start working during an active game. You'll have to decide
	 * which event type is best for your use case.
	 * @return a Gamer
	 * @see #getBlockBreakEvent()
	 */
	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Fetches the standard Bukkit BlockBreakEvent associated with this GamerBlockBreakEvent.
	 * @return a BlockBreakEvent
	 */
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

	/**
	 * Checks if the GamerBlockBreakEvent was cancelled.
	 * @return boolean isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Sets a GamerBlockBreakEvent to be cancelled.
	 * @param isCancelled
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
}
