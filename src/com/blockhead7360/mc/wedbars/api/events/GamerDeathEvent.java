package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerDeathEvent extends Event {
	
	private Gamer gamer;
	private boolean isRespawning;
	private boolean didDisconnect;
	
	public GamerDeathEvent(Gamer gamer, boolean isRespawning, boolean didDisconnect) {
		this.gamer = gamer;
		this.isRespawning = isRespawning;
		this.didDisconnect = didDisconnect;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public boolean isRespawning() {
		return isRespawning;
	}

	public boolean isDidDisconnect() {
		return didDisconnect;
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
