package com.blockhead7360.mc.wedbars.game;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Status;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.Team;

public class GameScoreboard {
	
	private static Scoreboard sb;
	private static Objective obj;
	
	private static String time;
	private static String status;
	
	private static Map<Team, TeamScoreboardData> teamData;
	
	public static class TeamScoreboardData {
		
		private String teamData;
		private int teamScoreboardScore;
		
		public TeamScoreboardData(String teamData, int teamScoreboardScore) {
			this.teamData = teamData;
			this.teamScoreboardScore = teamScoreboardScore;
		}

		public String getTeamData() {
			return teamData;
		}

		public int getTeamScoreboardScore() {
			return teamScoreboardScore;
		}

		public void setTeamData(String teamData) {
			this.teamData = teamData;
		}
		
	}
	
	public static void start(String arenaName, ArenaTeam[] teams) {
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("health", "health");
		o.setDisplayName(ChatColor.RED + "HP");
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
		Objective o1 = sb.registerNewObjective("health1", "health");
		o1.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		obj = sb.registerNewObjective("game", "dummy");
		obj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "WED BARS");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			player.setScoreboard(sb);
			
		}
		
		obj.getScore(ChatColor.GRAY + "WB " + WedBars.getInstance().getDescription().getVersion()
				+ " | " + arenaName).setScore(15);
		obj.getScore(" ").setScore(14);
		status = "None1";
		time = "None2";
		obj.getScore("  ").setScore(11);
		
		teamData = new HashMap<>();
		
		int x = 10;
		
		for (int i = 0; i < teams.length; i++) {
			
			teamData.put(teams[i].getTeam(), new TeamScoreboardData(null, x - i));
			updateTeam(teams[i]);
			
		}
		
		
		
	}
	
	public static void updateTime(String time) {
		
		sb.resetScores(GameScoreboard.time);
		
		String text = ChatColor.WHITE + "" + time + ChatColor.GRAY + " until";
		Score score = obj.getScore(text);
		score.setScore(13);
		GameScoreboard.time = text;
		
	}
	
	public static void updateStatus(String status) {
		
		sb.resetScores(GameScoreboard.status);
		Score score = obj.getScore(status);
		score.setScore(12);
		GameScoreboard.status = status;
		
	}
	
	public static void updateTeam(ArenaTeam team) {
		
		TeamScoreboardData tsd = teamData.get(team.getTeam());
		
		if (tsd.getTeamData() != null) sb.resetScores(tsd.getTeamData());
		
		int alive = 0;
		
		for (Gamer gamer : team.getGamers()) {
			
			if (gamer.getStatus() != Status.DEAD) {
				
				alive++;
				
			}
			
		}
		
		String indicator;
		
		if (team.bedExists() && alive > 0) {
			
			indicator = ChatColor.GREEN + "" + ChatColor.BOLD + "✓";
			
		} else {
			
			if (alive > 0) {
				
				indicator = ChatColor.WHITE + "" + alive;
				
			} else {
				
				indicator = ChatColor.RED + "" + ChatColor.BOLD + "✗";
				
			}
			
		}
		
		Team t = team.getTeam();
		
		String text = indicator + " " + t.getChatColor() + t.getLabel();
		
		Score score = obj.getScore(text);
		score.setScore(tsd.getTeamScoreboardScore());
		
		tsd.setTeamData(text);
		
	}
	
	
	
	
}
