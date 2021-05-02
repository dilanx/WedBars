package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.arena.ArenaData;

public class ArenaLoadEvent extends Event {
	
	private ArenaData arena;
	private String loader;
	
	public ArenaLoadEvent(ArenaData arena, String loader) {
		this.arena = arena;
		this.loader = loader;
	}

	/**
	 * Fetches ArenaData for the arena that was just loaded.
	 * @return the ArenaData for the Arena
	 */
	public ArenaData getArena() {
		return arena;
	}


	public String getLoader() {
		return loader;
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
