package com.blockhead7360.mc.wedbars.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.api.events.GamerStatsUpdateEvent;
import com.blockhead7360.mc.wedbars.team.Team;

public class Gamer {
	
	private Player player;
	private Team team;
	private Status status;
	private int respawningTimeLeft;
	
	private ItemStack[] invisArmor;
	
	private Map<Statistic, Integer> stats;
	
	public Gamer(Player player, Team team) {
		
		this.player = player;
		this.team = team;
		this.status = Status.WAITING;
		this.respawningTimeLeft = WedBars.RESPAWN_TIME;
		this.invisArmor = null;
		
		stats = new HashMap<>();
		
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

	public boolean hasInvisArmor() {
		return invisArmor != null;
	}
	public ItemStack[] getInvisArmor() {
		return invisArmor;
	}

	public void setInvisArmor(ItemStack[] invisArmor) {
		this.invisArmor = invisArmor;
	}
	
	public void removeInvisArmor() {
		invisArmor = null;
	}
	
	public void setStatistic(Statistic stat, int value) {
		
		GamerStatsUpdateEvent gsue = new GamerStatsUpdateEvent(this, stat, getStatistic(stat), value);
		Bukkit.getPluginManager().callEvent(gsue);
		
		if (gsue.isCancelled()) return;
		
		stats.put(stat, value);
		
	}
	
	public int getStatistic(Statistic stat) {
		
		if (!stats.containsKey(stat))
			stats.put(stat, 0);
		
		return stats.get(stat);
		
	}
	
	public void addOneToStatistic(Statistic stat) {
		
		setStatistic(stat, getStatistic(stat) + 1);
		
	}
	
}
