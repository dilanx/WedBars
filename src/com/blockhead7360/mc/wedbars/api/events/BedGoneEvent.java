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
	
	public ArenaTeam getTeam() {
		return team;
	}

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

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
}
