package com.blockhead7360.mc.wedbars.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.blockhead7360.mc.wedbars.team.Team;

public class Titles {

	// This has all now been updated to use the new Titles API. The old ones are commented out for reference,
	// but may be removed in the future.

	public static void start(Player player, Team team) {

		player.sendTitle("" + ChatColor.YELLOW + ChatColor.BOLD + "WED BARS", "" + team.getChatColor() + ChatColor.BOLD +
				team.getLabel() + " Team", 0,100, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 100 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"" + team.getLabel().toUpperCase() + " TEAM\",\"color\":\""
//				+ team.getChatColor().name().toLowerCase() + "\",\"bold\":true}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"WED BARS\",\"color\":\"yellow\",\"bold\":true}");

	}
	
	public static void win(Player player, Team team) {

		player.sendTitle("" + ChatColor.GREEN + ChatColor.BOLD + "VICTORY!", "" + team.getChatColor() + ChatColor.BOLD +
				team.getLabel() + " Team", 0,100, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 100 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"\"}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"VICTORY\",\"color\":\"green\"}");

	}

	public static void loss(Player player, Team team) {

		player.sendTitle("" + ChatColor.RED + ChatColor.BOLD + "DEFEAT!", "" + team.getChatColor() + ChatColor.BOLD +
				team.getLabel() + " Team", 0,100, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 100 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"\"}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"DEFEAT\",\"color\":\"red\"}");

	}
	
	public static void trapTriggered(Player player) {

		player.sendTitle("" + ChatColor.RED + "TRAP TRIGGERED!", "Another team triggered your trap!", 0,100, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 100 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"Another team triggered your trap!\"}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"TRAP TRIGGERED\",\"color\":\"red\"}");
		
	}

	// more messages

	public static void death(Player player, int time) {

		if (time == -2) {

			player.sendTitle("" + ChatColor.GREEN + "Respawned!", "", 0,40, 10);

//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" times 0 40 10");
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" subtitle {\"text\":\"Respawned!\",\"color\":\"green\"}");
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" title {\"text\":\"\"}");

			return;

		}

		if (time == -1) {

			player.sendTitle("" + ChatColor.RED  + "YOU DIED!",
					"" + ChatColor.GRAY + "You have been eliminated from the game.", 0,100, 20);

//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" times 0 100 20");
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" subtitle {\"text\":\"PERMANENTLY\",\"color\":\"gray\"}");
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//					" title {\"text\":\"YOU DIED!\",\"color\":\"red\"}");

			return;

		}

		player.sendTitle("" + ChatColor.RED  + "YOU DIED!",
				"" + ChatColor.GRAY + "Respawning in " + ChatColor.WHITE + ((time/10)) + ChatColor.GRAY + " seconds."
				, 0,30, 10);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 30 10");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle [{\"text\":\"Respawning in \",\"color\":\"gray\"},{\"text\":\"" + ((time / 10)) + "\",\"color\":\"white\"}]");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"YOU DIED!\",\"color\":\"red\"}");

		return;

	}
	
	public static void allBedsBroken(Player player) {

		player.sendTitle("" + ChatColor.RED  + "BED DESTROYED!",
				"" + ChatColor.GRAY + "All beds have been destroyed.", 0,60, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 60 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"All beds have been destroyed.\",\"color\":\"gray\"}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"BED DESTROYED!\",\"color\":\"red\"}");

	}

	public static void bedBroken(Player player) {

		player.sendTitle("" + ChatColor.RED  + "BED DESTROYED!",
				"" + ChatColor.GRAY + "You will no longer respawn.", 0,60, 20);

//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" times 0 60 20");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" subtitle {\"text\":\"You won't respawn when you die.\",\"color\":\"gray\"}");
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
//				" title {\"text\":\"BED DESTROYED!\",\"color\":\"red\"}");

	}
	
	
}
