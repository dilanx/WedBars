package com.blockhead7360.mc.wedbars.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.blockhead7360.mc.wedbars.team.Team;

public class Titles {
	
	public static void start(Player player, Team team) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 100 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"" + team.getLabel().toUpperCase() + " TEAM\",\"color\":\""
				+ team.getChatColor().name().toLowerCase() + "\",\"bold\":true}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"WED BARS\",\"color\":\"yellow\",\"bold\":true}");

	}
	
	public static void win(Player player) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 100 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"VICTORY\",\"color\":\"green\"}");

	}

	public static void loss(Player player) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 100 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"DEFEAT\",\"color\":\"red\"}");

	}
	
	public static void trapTriggered(Player player) {
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 100 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"Another team triggered your trap!\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"TRAP TRIGGERED\",\"color\":\"red\"}");
		
	}

	// more messages

	public static void death(Player player, int time) {

		if (time == -2) {

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" times 0 40 10");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" subtitle {\"text\":\"Respawned!\",\"color\":\"green\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" title {\"text\":\"\"}");

			return;

		}

		if (time == -1) {

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" times 0 100 20");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" subtitle {\"text\":\"PERMANENTLY\",\"color\":\"gray\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" title {\"text\":\"YOU DIED!\",\"color\":\"red\"}");

			return;

		}

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 30 10");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle [{\"text\":\"Respawning in \",\"color\":\"gray\"},{\"text\":\"" + ((time / 10)) + "\",\"color\":\"white\"}]");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"YOU DIED!\",\"color\":\"red\"}");

		return;

	}
	
	public static void allBedsBroken(Player player) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 60 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"All beds have been destroyed.\",\"color\":\"gray\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"BED DESTROYED!\",\"color\":\"red\"}");

	}

	public static void bedBroken(Player player) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 60 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"You won't respawn when you die.\",\"color\":\"gray\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"BED DESTROYED!\",\"color\":\"red\"}");

	}
	
	
}
