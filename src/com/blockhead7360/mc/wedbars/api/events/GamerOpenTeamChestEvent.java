package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;

public class GamerOpenTeamChestEvent extends Event implements Cancellable {
	
	private Gamer gamer;
	private ArenaTeam team;
	private Inventory chestInventory;
	private Location chestLocation;
	private boolean isCancelled;
	
	public GamerOpenTeamChestEvent(Gamer gamer, ArenaTeam team, Inventory chestInventory, Location chestLocation) {
		this.gamer = gamer;
		this.team = team;
		this.chestInventory = chestInventory;
		this.chestLocation = chestLocation;
		this.isCancelled = false;
	}

	/**
	 * Fetches the gamer that opened the team chest. Note that the team chest is a normal chest on each island
	 * and is a seperate thing from the personal EnderChest that players also have access to.
	 * @return a Gamer
	 */
	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Fetches the team who owns the chest that was just opened. Each team has a team chest at their base, and can only
	 * be opened by the owning team until they are eliminated.
	 * @return an ArenaTeam
	 */
	public ArenaTeam getTeam() {
		return team;
	}

	/**
	 * Fetches the inventory of the team chest that was just opened.
	 * @return an Inventory
	 */
	public Inventory getChestInventory() {
		return chestInventory;
	}

	/**
	 * Fetches the location of the team chest.
	 * @return a Location
	 */
	public Location getChestLocation() {
		return chestLocation;
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
