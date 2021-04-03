package com.blockhead7360.mc.wedbars.team;

import org.bukkit.Location;

import com.blockhead7360.mc.wedbars.game.Generator;
import com.blockhead7360.mc.wedbars.player.Gamer;

public class ArenaTeam {
	
	private Team team;
	private Location generatorLoc;
	private int emeraldspeed;
	private Generator ironGenerator, goldGenerator, emeraldGenerator;
	private boolean bedExists;
	private Location spawnLoc;
	private Location[] bedLoc;
	
	private Gamer[] gamers;
	
	private boolean[] upgrades;
	
	public ArenaTeam(Team team, Location spawnLoc, Location generatorLoc, int ironspeed, int goldspeed, int personalemeraldspeed, Location[] bedLoc, Gamer[] gamers) {
		
		this.team = team;
		
		this.generatorLoc = generatorLoc;
		this.ironGenerator = new Generator(generatorLoc, ironspeed);
		this.goldGenerator = new Generator(generatorLoc, goldspeed);
		this.emeraldGenerator = null;
		this.emeraldspeed = personalemeraldspeed;
		
		this.bedExists = true;
		
		this.spawnLoc = spawnLoc;
		this.bedLoc = bedLoc;
		this.gamers = gamers;
		
		this.upgrades = new boolean[TeamUpgrade.SIZE];
		
	}
	
	public void createEmeraldGenerator() {
		
		this.emeraldGenerator = new Generator(generatorLoc, emeraldspeed);
		
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
	
	public void removeGamer(Gamer gamer) {
		
		Gamer[] now = new Gamer[gamers.length - 1];
		
		int setIndex = 0;
		
		for (int i = 0; i < gamers.length; i++) {
			
			if (!gamers[i].getPlayer().getName().equals(gamer.getPlayer().getName())) {
				
				now[setIndex] = gamers[setIndex];
				setIndex++;
				
			}
			
		}
		
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
	
	public boolean hasUpgrade(int upgrade) {
		return upgrades[upgrade];
	}
	
	public void addUpgrade(int upgrade) {
		upgrades[upgrade] = true;
	}
	
	public boolean[] getUpgrades() {
		return upgrades;
	}
	
	
}