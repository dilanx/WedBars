package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.team.Team;
import com.blockhead7360.mc.wedbars.team.TeamAssignments;

public class PlayerChangeTeamEvent extends Event implements Cancellable {
	
	private Player player;
	private Team team;
	private TeamAssignments previousTeamAssignments;
	private boolean isCancelled;
	
	public PlayerChangeTeamEvent(Player player, Team team, TeamAssignments previousTeamAssignments) {
		this.player = player;
		this.team = team;
		this.previousTeamAssignments = previousTeamAssignments;
		this.isCancelled = true;
	}

	public Player getPlayer() {
		return player;
	}

	public Team getTeam() {
		return team;
	}

	public TeamAssignments getPreviousTeamAssignments() {
		return previousTeamAssignments;
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
