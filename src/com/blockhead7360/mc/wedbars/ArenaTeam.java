package com.blockhead7360.mc.wedbars;

import org.bukkit.Location;

public class ArenaTeam {
	
	private Team team;
	private Generator generator;
	private boolean bedExists;
	
	public ArenaTeam(Team team, Location generatorLoc) {
		
		this.team = team;
		
		this.generator = new Generator(generatorLoc, 500);
		this.bedExists = true;
		
		
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public boolean isBedExists() {
		return bedExists;
	}

	public void setBedExists(boolean bedExists) {
		this.bedExists = bedExists;
	}
	
	
}
