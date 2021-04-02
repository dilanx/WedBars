package com.blockhead7360.mc.wedbars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		if (WedBars.teamAssignments.isAssigned(e.getPlayer())) {
			
			WedBars.teamAssignments.unassign(e.getPlayer());
			
		}
		
		if (WedBars.arena != null) {
			
			if (WedBars.running)
				WedBars.getListeners().death(e.getPlayer(), true);
			else
				WedBars.arena.deleteGamer(WedBars.arena.getGamer(e.getPlayer().getName()));
			
		}
		
	}
	
}
