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
		this.respawningTimeLeft = 0;
		
	}
	
	
}
