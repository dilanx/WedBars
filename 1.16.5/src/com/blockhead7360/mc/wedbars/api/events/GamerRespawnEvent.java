package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerRespawnEvent extends Event {
	
	private Gamer gamer;
	
	public GamerRespawnEvent(Gamer gamer) {
		this.gamer = gamer;
	}

	public Gamer getGamer() {
		return gamer;
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
