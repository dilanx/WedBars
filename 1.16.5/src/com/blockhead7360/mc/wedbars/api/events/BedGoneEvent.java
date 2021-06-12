package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;

public class BedGoneEvent extends Event implements Cancellable {

	private ArenaTeam team;
	private Gamer breaker;
	private boolean isCancelled;

	public BedGoneEvent(ArenaTeam team, Gamer breaker) {
		this.team = team;
		this.breaker = breaker;
		this.isCancelled = false;
	}

	/**
	 * Fetches the team that got their bed destroyed.
	 * @return an ArenaTeam
	 */
	public ArenaTeam getTeam() {
		return team;
	}

	/**
	 * Fetches the Gamer who broke the team's bed.
	 * @return a Gamer
	 */
	public Gamer getBreaker() {
		return breaker;
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
	 * Checks if the BedGoneEvent was cancelled.
	 * @return boolean isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Cancels the BedGoneEvent.
	 * @param isCancelled
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
}
