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

	/**
	 * Fetched the Gamer that just died.
	 * @return Gamer
	 */
	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Checks if the player is currently respawning. If true, this means that the player has just died but still has a
	 * bed, and is currently in spectator mode waiting to respawn.
	 * @return boolean
	 */
	public boolean isRespawning() {
		return isRespawning;
	}

	/**
	 * Checks if the player that died also disconnected. Players that leave and then rejoin an active game will be
	 * treated as if they died, so if they have a bed they will have to wait before getting back in the game. If they
	 * don't have a bed, they will be permanently dead upon login.
	 * @return boolean
	 */
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
