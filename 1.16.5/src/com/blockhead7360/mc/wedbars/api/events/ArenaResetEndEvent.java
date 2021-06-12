package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaResetEndEvent extends Event {
	
	public ArenaResetEndEvent() {
		
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
