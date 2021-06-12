package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.arena.ArenaData;

public class ArenaSaveEvent extends Event {
	
	private ArenaData arena;
	private String saver;

	public ArenaSaveEvent(ArenaData arena, String saver) {
		this.arena = arena;
		this.saver = saver;
	}

	/**
	 * Fetches the ArenaData for the arena that was just saved.
	 * @return ArenaData for the saved arena
	 */
	public ArenaData getArena() {
		return arena;
	}

	public String getSaver() {
		return saver;
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
