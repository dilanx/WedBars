package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.traps.TeamTrap;

public class TrapActivateEvent extends Event implements Cancellable {
	
	private Gamer activator;
	private ArenaTeam team;
	private TeamTrap trap;
	private boolean isCancelled;
	
	public TrapActivateEvent(Gamer activator, ArenaTeam team, TeamTrap trap) {
		this.activator = activator;
		this.team = team;
		this.trap = trap;
	}

	public Gamer getActivator() {
		return activator;
	}

	public ArenaTeam getTeam() {
		return team;
	}

	public TeamTrap getTrap() {
		return trap;
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
