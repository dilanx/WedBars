package com.blockhead7360.mc.wedbars;

import org.bukkit.Location;

public class ArenaTeam {
	
	private Team team;
	private Generator ironGenerator, goldGenerator, emeraldGenerator;
	private boolean bedExists;
	private Location spawnLoc;
	private Location[] bedLoc;
	
	private Gamer[] gamers;
	
	public ArenaTeam(Team team, Location spawnLoc, Location generatorLoc, int ironspeed, int goldspeed, Location[] bedLoc, Gamer[] gamers) {
		
		this.team = team;
		
		this.ironGenerator = new Generator(generatorLoc, ironspeed);
		this.goldGenerator = new Generator(generatorLoc, goldspeed);
		this.emeraldGenerator = null;
		
		this.bedExists = true;
		
		this.spawnLoc = spawnLoc;
		this.bedLoc = bedLoc;
		this.gamers = gamers;
		
	}

	public Generator getIronGenerator() {
		return ironGenerator;
	}

	public void setIronGenerator(Generator ironGenerator) {
		this.ironGenerator = ironGenerator;
	}

	public Generator getGoldGenerator() {
		return goldGenerator;
	}

	public void setGoldGenerator(Generator goldGenerator) {
		this.goldGenerator = goldGenerator;
	}

	public Generator getEmeraldGenerator() {
		return emeraldGenerator;
	}

	public void setEmeraldGenerator(Generator emeraldGenerator) {
		this.emeraldGenerator = emeraldGenerator;
	}

	public Gamer[] getGamers() {
		return gamers;
	}

	public void setGamers(Gamer[] gamers) {
		this.gamers = gamers;
	}

	public Location getSpawnLoc() {
		return spawnLoc;
	}

	public void setSpawnLoc(Location spawnLoc) {
		this.spawnLoc = spawnLoc;
	}

	public Location[] getBedLoc() {
		return bedLoc;
	}

	public void setBedLoc(Location[] bedLoc) {
		this.bedLoc = bedLoc;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean bedExists() {
		return bedExists;
	}

	public void setBedExists(boolean bedExists) {
		this.bedExists = bedExists;
	}
	
	
}
