package com.blockhead7360.mc.wedbars.game;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Statistic;
import com.blockhead7360.mc.wedbars.player.Status;

public class ConnectionListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		if (WedBars.running || WedBars.resetting || WedBars.starting) {

			Player player = e.getPlayer();

			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GRAY + "The game is currently in progress or is about to start. Hang tight!");
			player.sendMessage(" ");

			return;

		}
		
		e.getPlayer().sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Select your team!" + ChatColor.GREEN + " /team");
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		if (WedBars.teamAssignments.isAssigned(e.getPlayer())) {
			
			WedBars.teamAssignments.unassign(e.getPlayer());
			
		}
		
		if (WedBars.arena != null) {
			
			Gamer gamer = WedBars.arena.getGamer(e.getPlayer().getName());
			
			if (gamer == null || gamer.getStatus() == Status.DEAD) return;
			
			if (WedBars.running) {
				
				gamer.addOneToStatistic(Statistic.FDEATHS);
				gamer.addOneToStatistic(Statistic.LOSSES);
				GameActions.death(e.getPlayer(), true);
				
			} else {
				WedBars.arena.deleteGamer(gamer);
			}
			
		}
		
	}
	
}
