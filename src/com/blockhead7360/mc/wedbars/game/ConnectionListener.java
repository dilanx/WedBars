package com.blockhead7360.mc.wedbars.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Statistic;
import com.blockhead7360.mc.wedbars.player.Status;

public class ConnectionListener implements Listener {
	
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
