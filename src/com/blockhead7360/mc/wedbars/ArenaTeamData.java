package com.blockhead7360.mc.wedbars;

import org.bukkit.Location;

public class ArenaTeamData {
	
	private Team team;
	private Location spawn, generator;
	private Location[] bed;
	
	public ArenaTeamData(Team team) {
		
		this.team = team;
		this.spawn = null;
		this.generator = null;
		this.bed = new Location[2];
		
	}
	
	public ArenaTeamData(Team team, Location spawn, Location generator, Location[] bed) {
		this.team = team;
		this.spawn = spawn;
		this.generator = generator;
		this.bed = bed;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getGenerator() {
		return generator;
	}

	public void setGenerator(Location generator) {
		this.generator = generator;
	}

	public Location[] getBed() {
		return bed;
	}

	public void setBed(Location[] bed) {
		this.bed = bed;
	}
	
	public void setBed(int half, Location bedHalf) {
		
		this.bed[half] = bedHalf;
		
	}
	
	public boolean isBedSet() {
		
		return (bed[0] == null || bed[1] == null);
		
	}
	
	public ArenaTeamData clone() {
		
		return new ArenaTeamData(team, spawn, generator, bed);
		
	}
	
}
