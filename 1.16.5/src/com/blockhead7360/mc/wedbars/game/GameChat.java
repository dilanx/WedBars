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

public class GameChat implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {

		if (SetupWizard.settingUp != null && SetupWizard.settingUp.getName().equals(e.getPlayer().getName())) {

			return;

		}
		
		e.setCancelled(true);

		if (WedBars.arena == null) {

			String msg = ChatColor.GRAY + "Lobby | " + ChatColor.WHITE + e.getPlayer().getName() + ChatColor.GRAY + ": " + e.getMessage();

			Bukkit.broadcastMessage(msg);

			return;

		}

		Gamer gamer = WedBars.arena.getGamer(e.getPlayer().getName());

		if (gamer != null && gamer.getStatus() != Status.DEAD) {

			// game chat

			String msg = gamer.getTeam().getChatColor() + e.getPlayer().getName() + ChatColor.WHITE + ": " + e.getMessage();

			for (Player p : Bukkit.getOnlinePlayers()) {

				p.sendMessage(msg);

			}

		} else {

			// spec chat

			String msg = ChatColor.GRAY + "Spectator | " + ChatColor.WHITE + e.getPlayer().getName() + ChatColor.GRAY + ": " + e.getMessage();

			for (Player p : Bukkit.getOnlinePlayers()) {

				Gamer g = WedBars.arena.getGamer(p.getName());

				if (g == null || g.getStatus() != Status.ALIVE) {

					p.sendMessage(msg);

				}

			}

		}

	}

}
