package com.blockhead7360.mc.wedbars.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;

import com.blockhead7360.mc.wedbars.team.ArenaTeamData;
import com.blockhead7360.mc.wedbars.team.Team;

public class ArenaData {
	
	private String name;
	private Location lobby;
	private Map<Team, ArenaTeamData> teams;
	
	private int diamondSpeed;
	private int emeraldSpeed;
	
	private int ironSpeed;
	private int goldSpeed;
	private int personalEmeraldSpeed;
	
	private List<Location> diamondGen, emeraldGen;
	
	public ArenaData(String name) {
		
		this(name, null, 0, 0, 0, 0, 0);
		
	}
	
	public ArenaData(String name, Location lobby, int diamondSpeed, int emeraldSpeed,
			int ironSpeed, int goldSpeed, int personalEmeraldSpeed) {
		
		this.name = name;
		this.lobby = lobby;
		this.diamondGen = new ArrayList<>();
		this.emeraldGen = new ArrayList<>();
		this.teams = new HashMap<>();
		
		this.diamondSpeed = diamondSpeed;
		this.emeraldSpeed = emeraldSpeed;
		this.ironSpeed = ironSpeed;
		this.goldSpeed = goldSpeed;
		this.personalEmeraldSpeed = personalEmeraldSpeed;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public List<Location> getDiamondGen() {
		return diamondGen;
	}

	public void addDiamondGen(Location diamondGen) {
		this.diamondGen.add(diamondGen);
	}
	
	public void clearDiamondGen() {
		this.diamondGen.clear();
	}

	public List<Location> getEmeraldGen() {
		return emeraldGen;
	}

	public void addEmeraldGen(Location emeraldGen) {
		this.emeraldGen.add(emeraldGen);
	}
	
	public void clearEmeraldGen() {
		this.emeraldGen.clear();
	}
	
	public void setTeamData(Team team, ArenaTeamData data) {
		
		teams.put(team, data);
		
	}
	
	public ArenaTeamData getTeamData(Team team) {
		
		return teams.get(team);
		
	}
	
	public Set<Team> getAllTeams(){
		
		return teams.keySet();
		
	}

	public int getDiamondSpeed() {
		return diamondSpeed;
	}

	public void setDiamondSpeed(int diamondSpeed) {
		this.diamondSpeed = diamondSpeed;
	}

	public int getEmeraldSpeed() {
		return emeraldSpeed;
	}

	public void setEmeraldSpeed(int emeraldSpeed) {
		this.emeraldSpeed = emeraldSpeed;
	}

	public int getIronSpeed() {
		return ironSpeed;
	}

	public void setIronSpeed(int ironSpeed) {
		this.ironSpeed = ironSpeed;
	}

	public int getGoldSpeed() {
		return goldSpeed;
	}

	public void setGoldSpeed(int goldSpeed) {
		this.goldSpeed = goldSpeed;
	}

	public int getPersonalEmeraldSpeed() {
		return personalEmeraldSpeed;
	}

	public void setPersonalEmeraldSpeed(int personalEmeraldSpeed) {
		this.personalEmeraldSpeed = personalEmeraldSpeed;
	}
	
}
