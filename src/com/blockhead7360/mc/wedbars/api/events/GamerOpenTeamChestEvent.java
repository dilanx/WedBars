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

	public Gamer getGamer() {
		return gamer;
	}

	public ArenaTeam getTeam() {
		return team;
	}

	public Inventory getChestInventory() {
		return chestInventory;
	}

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
