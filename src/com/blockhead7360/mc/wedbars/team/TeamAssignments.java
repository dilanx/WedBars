package com.blockhead7360.mc.wedbars.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamAssignments {

	private Map<Team, List<String>> ta;

	public TeamAssignments() {

		ta = new HashMap<>();

	}

	public void clear() {

		ta.clear();

	}

	public boolean isAssigned(Player player) {

		for (Team team : ta.keySet()) {

			for (String str : ta.get(team)) {

				if (str.equals(player.getName())) return true;

			}

		}

		return false;

	}

	public void unassign(Player player) {

		Object[] arr = ta.keySet().toArray();
		
		for (Object ot : arr) {
			
			Team t = (Team) ot;

			if (ta.get(t).contains(player.getName())) {

				ta.get(t).remove(player.getName());

				if (ta.get(t).size() == 0) {
					ta.remove(t);
				}

			}

		}

	}

	public void assign(Player player, Team team) {

		unassign(player);

		if (!ta.containsKey(team)) {

			List<String> players = new ArrayList<String>();
			players.add(player.getName());
			ta.put(team, players);

		} else {

			ta.get(team).add(player.getName());

		}
		
		player.setPlayerListName(team.getChatColor() + player.getName());
		
		Bukkit.broadcastMessage(team.getChatColor() + player.getName() + ChatColor.GRAY + " is now a member of the "
				+ team.getChatColor() + ChatColor.BOLD + team.getLabel() + ChatColor.GRAY + " team.");

	}

	public Map<Team, List<String>> getTeamAssignments(){

		return ta;

	}

}
