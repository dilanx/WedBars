package com.blockhead7360.mc.wedbars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;

public class SetupWizard implements Listener {
	
	public static Player settingUp = null;
	
	public static ArenaData setup = null;
	public static ArenaTeamData teamSetup = null;
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		
		if (e.getPlayer().getName().equals(settingUp.getName())) {
			
			e.setCancelled(true);
			
			String msg = e.getMessage();
			Player player = e.getPlayer();
			
			if (msg.equalsIgnoreCase("help") || msg.equalsIgnoreCase("?")) {
				
				
				player.sendMessage(ChatColor.WHITE + "checklist" + ChatColor.GRAY + " - see everything you've set up so far");
				player.sendMessage(ChatColor.WHITE + "team <team>" + ChatColor.GRAY + " - setup a team island");
				player.sendMessage(ChatColor.WHITE + "lobby" + ChatColor.GRAY + " - set the lobby to your current location");
				player.sendMessage(ChatColor.WHITE + "diamondspeed <speed>" + ChatColor.GRAY + " - set the initial diamond generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "emeraldspeed <speed>" + ChatColor.GRAY + " - set the initial emerald generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "ironspeed <speed>" + ChatColor.GRAY + " - set the initial iron generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "goldspeed <speed>" + ChatColor.GRAY + " - set the initial gold generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "personalemeraldspeed <speed>" + ChatColor.GRAY + " - set the island emerald generator speed (seconds * 10)");
				return;
				
				
			}
			
			if (msg.equalsIgnoreCase("lobby")) {
				
				setup.setLobby(player.getLocation().clone());
				
			}
			
			if (msg.equalsIgnoreCase("checklist") || msg.equalsIgnoreCase("cl")) {
				
				player.sendMessage(" ");
				player.sendMessage(ChatColor.WHITE + "Setting up " + ChatColor.YELLOW + setup.getName());
				
				String lobby = null;
				if (setup.getLobby() == null) {
					lobby = ChatColor.GRAY + "Lobby not set";
				} else {
					lobby = ChatColor.GREEN + "Lobby set" + ChatColor.GRAY;
				}
				
				String ds = null;
				if (setup.getDiamondSpeed() == 0) {
					ds = ChatColor.GRAY + "Diamond speed not set";
				} else {
					ds = ChatColor.GREEN + "Diamond speed set" + ChatColor.GRAY;
				}
				
				String es = null;
				if (setup.getEmeraldSpeed() == 0) {
					es = ChatColor.GRAY + "Emerald speed not set";
				} else {
					es = ChatColor.GREEN + "Emerald speed set" + ChatColor.GRAY;
				}
				
				String is = null;
				if (setup.getIronSpeed() == 0) {
					is = ChatColor.GRAY + "Iron speed not set";
				} else {
					is = ChatColor.GREEN + "Iron speed set" + ChatColor.GRAY;
				}
				
				String gs = null;
				if (setup.getGoldSpeed() == 0) {
					gs = ChatColor.GRAY + "Gold speed not set";
				} else {
					gs = ChatColor.GREEN + "Gold speed set" + ChatColor.GRAY;
				}
				
				String pes = null;
				if (setup.getPersonalEmeraldSpeed() == 0) {
					pes = ChatColor.GRAY + "Island emerald speed not set";
				} else {
					pes = ChatColor.GREEN + "Island emerald speed set" + ChatColor.GRAY;
				}
				
				player.sendMessage(ChatColor.WHITE + "Arena Settings: " + lobby + ", " + ds + ", " + es + ", " + is + ", " + gs + ", " + pes);
				
				if (setup.getAllTeams().size() > 0) {
					
					Team[] teams = new Team[setup.getAllTeams().size()];
					setup.getAllTeams().toArray(teams);
					
					String t = teams[0].getChatColor() + teams[0].getLabel();
					
					for (int i = 1; i < teams.length; i++) {
						
						t += ChatColor.GRAY + ", " + teams[i].getChatColor() + teams[i].getLabel();
						
					}
					
					player.sendMessage(ChatColor.WHITE + "Teams: " + t);
					
				} else {

					player.sendMessage(ChatColor.WHITE + "Teams: " + ChatColor.GRAY + "None set");
					
				}
				
			}
			
			
		}
		
	}

}
