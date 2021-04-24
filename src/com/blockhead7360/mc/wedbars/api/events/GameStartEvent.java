package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.arena.ArenaData;
import com.blockhead7360.mc.wedbars.team.TeamAssignments;

public class GameStartEvent extends Event {
	
	private ArenaData arenaData;
	private TeamAssignments teamAssignments;
	private boolean wasDoneManually;
	
	public GameStartEvent(ArenaData arenaData, TeamAssignments teamAssignments, boolean wasDoneManually) {
		this.arenaData = arenaData;
		this.teamAssignments = teamAssignments;
		this.wasDoneManually = wasDoneManually;
	}

	public ArenaData getArenaData() {
		return arenaData;
	}

	public TeamAssignments getTeamAssignments() {
		return teamAssignments;
	}

	/**
	 * Checks if the game was started by WedBars's auto start feature or manually by an admin using /start.
	 * @return a boolean
	 */
	public boolean wasDoneManually() {
		return wasDoneManually;
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
