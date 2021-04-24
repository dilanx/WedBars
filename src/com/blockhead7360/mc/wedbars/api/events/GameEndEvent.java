package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.arena.ArenaData;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.TeamAssignments;

public class GameEndEvent extends Event {
	
	private ArenaData arenaData;
	private TeamAssignments teamAssignments;
	private ArenaTeam winner;
		
	public GameEndEvent(ArenaData arenaData, TeamAssignments teamAssignments, ArenaTeam winner) {
		this.arenaData = arenaData;
		this.teamAssignments = teamAssignments;
		this.winner = winner;
	}

	public ArenaData getArenaData() {
		return arenaData;
	}

	public TeamAssignments getTeamAssignments() {
		return teamAssignments;
	}

	public ArenaTeam getWinner() {
		return winner;
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
