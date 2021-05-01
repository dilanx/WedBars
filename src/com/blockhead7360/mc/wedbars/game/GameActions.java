package com.blockhead7360.mc.wedbars.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.Utility.EnchantmentSet;
import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.api.events.BedGoneEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerDeathEvent;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.GamerStats;
import com.blockhead7360.mc.wedbars.player.Statistic;
import com.blockhead7360.mc.wedbars.player.Status;
import com.blockhead7360.mc.wedbars.player.Titles;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.Team;
import com.blockhead7360.mc.wedbars.team.TeamUpgrade;

public class GameActions {

	public static void bedGone(ArenaTeam team, Gamer breaker) {
		
		BedGoneEvent bge = new BedGoneEvent(team, breaker);
		Bukkit.getPluginManager().callEvent(bge);
		
		if (bge.isCancelled()) return;
		
		for (Location lx : team.getBedLoc()) {

			lx.getBlock().setType(Material.AIR);

		}
		
		team.setBedExists(false);

		for (Player player : Bukkit.getOnlinePlayers()) {

			player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);

		}

		Team t = team.getTeam();

		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("     " + ChatColor.RED + ChatColor.BOLD + "BED DESTROYED!");
		Bukkit.broadcastMessage("     " + t.getChatColor() + t.getLabel() + ChatColor.GRAY + " team's bed was destroyed by " + breaker.getTeam().getChatColor() + breaker.getPlayer().getName() + ChatColor.GRAY + ".");
		Bukkit.broadcastMessage(" ");

		GameScoreboard.updateTeam(team);

		breaker.addOneToStatistic(Statistic.BKILLS);
		
		for (Gamer gamer : team.getGamers()) {
			Titles.bedBroken(gamer.getPlayer());
			gamer.addOneToStatistic(Statistic.BDEATHS);
		}



	}

	public static void death(Player player, boolean disconnect) {

		Gamer gamer = WedBars.arena.getGamer(player.getName());
		if (gamer == null) return;
		boolean bedExists = WedBars.arena.getTeam(gamer.getTeam()).bedExists();

		if (disconnect) {

			Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName() + ChatColor.GRAY + " disconnected.");
			WedBars.arena.deleteGamer(gamer);

		} else {

			ItemStack[] armor = player.getInventory().getArmorContents();

			ItemStack[] contents = player.getInventory().getContents();

			player.getInventory().clear();

			player.getInventory().setArmorContents(armor);
			
			if (gamer.hasInvisArmor()) {
				
				player.getInventory().setArmorContents(gamer.getInvisArmor());
				gamer.removeInvisArmor();
				
			}
			

			if (WedBars.arena.getTeam(gamer.getTeam()).hasUpgrade(TeamUpgrade.SWORDS))
				player.getInventory().setItem(0, Utility.createEnchantedItemStack(Material.WOOD_SWORD, 1, ChatColor.YELLOW + "Wooden Sword",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DAMAGE_ALL, 1)}));
			else
				player.getInventory().setItem(0, Utility.createUnbreakableItemStack(Material.WOOD_SWORD, 1, ChatColor.YELLOW + "Wooden Sword"));

			for (int i = 0; i < contents.length; i++) {

				if (contents[i] == null) continue;

				if (contents[i].getType() == Material.WOOD_PICKAXE || contents[i].getType() == Material.IRON_PICKAXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.WOOD_PICKAXE, 1, ChatColor.YELLOW + "Wooden Pickaxe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}));

				}

				else if (contents[i].getType() == Material.GOLD_PICKAXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Iron Pickaxe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 2)}));


				}

				else if (contents[i].getType() == Material.DIAMOND_PICKAXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.GOLD_PICKAXE, 1, ChatColor.YELLOW + "Gold Pickaxe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 3),
									new EnchantmentSet(Enchantment.DAMAGE_ALL, 2)}));

				}

				else if (contents[i].getType() == Material.WOOD_AXE || contents[i].getType() == Material.STONE_AXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.WOOD_AXE, 1, ChatColor.YELLOW + "Wooden Axe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}));

				}

				else if (contents[i].getType() == Material.IRON_AXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.STONE_AXE, 1, ChatColor.YELLOW + "Stone Axe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}));

				}

				else if (contents[i].getType() == Material.DIAMOND_AXE) {

					player.getInventory().addItem(Utility.createEnchantedItemStack(Material.IRON_AXE, 1, ChatColor.YELLOW + "Iron Axe",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}));

				}

				else if (contents[i].getType() == Material.SHEARS) {

					player.getInventory().addItem(Utility.createUnbreakableItemStack(Material.SHEARS, 1, ChatColor.YELLOW + "Permanent Shears"));

				}


			}

			// i moved the death titles to the timer in Arena.java you'll see why

			player.setHealth(20);
			player.setGameMode(GameMode.SPECTATOR);

			if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) player.removePotionEffect(PotionEffectType.INVISIBILITY);

			player.teleport(new Location(player.getWorld(), 0, 100, 0));

			Player killer = player.getKiller();

			if (killer == null) {

				Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName()
				+ ChatColor.GRAY + " died." + (bedExists ? "" : ChatColor.RED + "" + ChatColor.BOLD + " FINAL KILL!"));

			} else {

				killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);

				Gamer kGamer = WedBars.arena.getGamer(killer.getName());

				Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName()
				+ ChatColor.GRAY + " was killed by " + kGamer.getTeam().getChatColor() + killer.getName() + ChatColor.GRAY + "."
				+ (bedExists ? "" : ChatColor.RED + "" + ChatColor.BOLD + " FINAL KILL!"));

				// Give items to killer

				for (ItemStack is : contents) {

					if (is == null) continue;

					if (is.getType() == Material.IRON_INGOT || is.getType() == Material.GOLD_INGOT || is.getType() == Material.DIAMOND
							|| is.getType() == Material.EMERALD) {

						if (killer.getInventory().firstEmpty() == -1) break;

						killer.getInventory().addItem(is);

					}

				}

				killer.updateInventory();

				kGamer.addOneToStatistic(Statistic.KILLS);

				if (!bedExists) {
					kGamer.addOneToStatistic(Statistic.FKILLS);
				}

			}

		}

		gamer.addOneToStatistic(Statistic.DEATHS);

		if (bedExists && !disconnect) {

			gamer.setStatus(Status.RESPAWNING);
			Titles.death(player, WedBars.RESPAWN_TIME);

		} else {

			gamer.setStatus(Status.DEAD);
			gamer.addOneToStatistic(Statistic.FDEATHS);
			for (Player p : Bukkit.getOnlinePlayers()) {

				p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1, 1);

			}
			ArenaTeam team = WedBars.arena.getTeam(gamer.getTeam());
			GameScoreboard.updateTeam(team);

			int alive = 0;
			for (Gamer g : team.getGamers()) {

				if (g.getStatus() != Status.DEAD) {

					alive++;

				}

			}

			if (alive == 0) {

				Team t = team.getTeam();
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("     " + ChatColor.RED + ChatColor.BOLD + "TEAM ELIMINATED!");
				Bukkit.broadcastMessage("     " + t.getChatColor() + t.getLabel() + ChatColor.GRAY + " team is gone now.");
				Bukkit.broadcastMessage(" ");

				for (Gamer g : team.getGamers())
					g.addOneToStatistic(Statistic.LOSSES);

			}

			boolean end = WedBars.arena.checkForEndGame();

			if (!end && !disconnect) {

				Titles.death(player, -1);

			}
			
			if (disconnect) {
				
				GamerStats.sendGamerData(gamer, false);
				
			}

		}
		
		GamerDeathEvent gde = new GamerDeathEvent(gamer, bedExists, disconnect);
		Bukkit.getPluginManager().callEvent(gde);

	}

}
