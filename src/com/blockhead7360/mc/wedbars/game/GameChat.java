package com.blockhead7360.mc.wedbars.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.arena.SetupWizard;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Status;
import com.blockhead7360.mc.wedbars.team.Team;

public class GameChat implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {

		if (SetupWizard.settingUp != null && SetupWizard.settingUp.getName().equals(e.getPlayer().getName())) {

			return;

		}

		if (WedBars.arena != null) {

			e.setCancelled(true);

			Gamer gamer = WedBars.arena.getGamer(e.getPlayer().getName());

			Status status = Status.DEAD;
			
			String msg = "";
			
			if (gamer == null) {

				msg = ChatColor.GRAY + "Spectator | " + ChatColor.WHITE + e.getPlayer().getName() + ChatColor.GRAY + ": " + e.getMessage();

			} else {

				Team team = gamer.getTeam();
				status = gamer.getStatus();

				if (status == Status.ALIVE) {

					msg = team.getChatColor() + e.getPlayer().getName() + ChatColor.WHITE + ": " + e.getMessage();

				} else {

					msg = ChatColor.GRAY + "Spectator | " + team.getChatColor() + e.getPlayer().getName() + ChatColor.GRAY + ": " + e.getMessage();

				}

			}

			for (Player p : Bukkit.getOnlinePlayers()) {

				if (status == Status.ALIVE) {

					p.sendMessage(msg);
					continue;

				}

				Status s = WedBars.arena.getGamer(p.getName()).getStatus();

				if (status == Status.RESPAWNING || status == Status.DEAD) {

					if (s == Status.RESPAWNING || s == Status.DEAD) {

						p.sendMessage(msg);

					}

				}

			}



		}

	}

}
