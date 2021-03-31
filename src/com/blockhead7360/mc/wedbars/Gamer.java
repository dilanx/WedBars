package com.blockhead7360.mc.wedbars;

import org.bukkit.entity.Player;

public class Gamer {
	
	private Player player;
	private Team team;
	private Status status;
	private int respawningTimeLeft;
	
	
	public Gamer(Player player, Team team) {
		
		this.player = player;
		this.team = team;
		this.status = Status.WAITING;
		this.respawningTimeLeft = WedBars.RESPAWN_TIME;
		
	}

	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public int getRespawningTimeLeft() {
		return respawningTimeLeft;
	}


	public void setRespawningTimeLeft(int respawningTimeLeft) {
		this.respawningTimeLeft = respawningTimeLeft;
	}
	
	public boolean respawnPassTime() {
		
		respawningTimeLeft--;
		
		if (respawningTimeLeft <= 0) {
			
			respawningTimeLeft = WedBars.RESPAWN_TIME;
			return true;
			
		}
		
		return false;
		
	}
	
}
