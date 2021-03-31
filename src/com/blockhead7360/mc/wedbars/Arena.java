package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;

public class Arena {


	private World world;
	private Map<Team, ArenaTeam> teams;
	private Map<String, Gamer> gamers;
	private Generator[] diamonds;
	private Generator[] emeralds;

	public Arena(ArenaTeam[] teams, Generator[] diamonds, Generator[] emeralds) {

		this.diamonds = diamonds;
		this.emeralds = emeralds;

		this.world = diamonds[0].getLocation().getWorld();

		this.teams = new HashMap<Team, ArenaTeam>();
		this.gamers = new HashMap<String, Gamer>();

		for (ArenaTeam at : teams) {

			this.teams.put(at.getTeam(), at);

			for (Gamer gamer : at.getGamers()) {

				this.gamers.put(gamer.getPlayer().getName(), gamer);

			}


		}



	}

	/* 10 PER SECOND */

	public void start() {

		WedBars.running = true;
		WedBars.arena = this;

		// reset players

		for (Gamer gamer : gamers.values()) {

			Player p = gamer.getPlayer();
			Team team = gamer.getTeam();
			ArenaTeam at = teams.get(team);

			p.getEnderChest().clear();
			p.getInventory().clear();
			p.setHealth(20);
			p.setGameMode(GameMode.SURVIVAL);

			p.teleport(at.getSpawnLoc());

			p.getInventory().setHelmet(Utility.createLeatherArmorPiece(Material.LEATHER_HELMET, team.getColor(), ChatColor.YELLOW + "Leather Helmet"));
			p.getInventory().setChestplate(Utility.createLeatherArmorPiece(Material.LEATHER_CHESTPLATE, team.getColor(), ChatColor.YELLOW + "Leather Chestplate"));
			p.getInventory().setLeggings(Utility.createLeatherArmorPiece(Material.LEATHER_LEGGINGS, team.getColor(), ChatColor.YELLOW + "Leather Leggings"));
			p.getInventory().setBoots(Utility.createLeatherArmorPiece(Material.LEATHER_BOOTS, team.getColor(), ChatColor.YELLOW + "Leather Boots"));
			p.getInventory().setItem(0, Utility.createUnbreakableItemStack(Material.WOOD_SWORD, 1, ChatColor.YELLOW + "Wooden Sword"));

		}



		GameScoreboard.start(teams.values().toArray(new ArenaTeam[0]));
		GameScoreboard.updateStatus(ChatColor.AQUA + "" + ChatColor.BOLD + "not implemented yet");

		// reset team stuff

		for (ArenaTeam at : teams.values()) {

			for (Location l : at.getBedLoc()) {

				l.getBlock().setType(Material.CAKE_BLOCK);

			}

		}

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=Item]");

		// prepare generator holograms

		for (int i = 0; i < diamonds.length; i++) {

			Generator d = diamonds[i];

			HologramsAPI.registerPlaceholder(WedBars.getInstance(), "{diamond" + i + "}", 0.5, new PlaceholderReplacer() {

				public String update() {

					return Integer.toString(d.getCurTimeLeft() / 10);

				}

			});

			Hologram h = HologramsAPI.createHologram(WedBars.getInstance(), d.getLocation().clone().add(0, 3, 0));
			h.setAllowPlaceholders(true);
			h.appendTextLine(ChatColor.AQUA + "" + ChatColor.BOLD + "DIAMOND GENERATOR");
			h.appendTextLine(ChatColor.WHITE + "spawning in " + ChatColor.RED + "{diamond" + i + "}");

		}

		for (int i = 0; i < emeralds.length; i++) {

			Generator e = emeralds[i];

			HologramsAPI.registerPlaceholder(WedBars.getInstance(), "{emerald" + i + "}", 0.5, new PlaceholderReplacer() {

				public String update() {

					return Integer.toString(e.getCurTimeLeft() / 10);

				}

			});


			Hologram h = HologramsAPI.createHologram(WedBars.getInstance(), e.getLocation().clone().add(0, 3, 0));

			h.setAllowPlaceholders(true);
			h.appendTextLine(ChatColor.GREEN + "" + ChatColor.BOLD + "EMERALD GENERATOR");
			h.appendTextLine(ChatColor.WHITE + "spawning in " + ChatColor.RED + "{emerald" + i + "}");
		}

		// start game

		new BukkitRunnable() {

			int totalTestTime = 6000;

			public void run() {

				// Teams

				for (ArenaTeam at : teams.values()) {

					// Players

					// TODO change messages

					for (Gamer gamer : at.getGamers()) {

						if (gamer.getStatus() == Status.RESPAWNING) {

							Player player = gamer.getPlayer();

							if (gamer.respawnPassTime()) {

								gamer.setStatus(Status.ALIVE);

								player.teleport(at.getSpawnLoc());
								player.setGameMode(GameMode.SURVIVAL);

								Utility.sendDeathTitle(player, -2);

							} else {

								int timeLeft = gamer.getRespawningTimeLeft();

								if (timeLeft % 10 == 0) {

									Utility.sendDeathTitle(player, timeLeft);

								}
							}

						}

					}

					// Iron gen

					Generator i = at.getIronGenerator();

					if (i.passTime()) {

						world.dropItem(i.getLocation(), new ItemStack(Material.IRON_INGOT, 1));

					}


					// Gold gen

					Generator g = at.getGoldGenerator();

					if (g.passTime()) {

						world.dropItem(g.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));

					}


					// Emerald gen if unlocked

					Generator e = at.getEmeraldGenerator();

					if (e != null) {

						if (e.passTime()) {

							world.dropItem(e.getLocation(), new ItemStack(Material.EMERALD, 1));

						}

					}

				}


				// Diamond gen

				for (int i = 0; i < diamonds.length; i++) {

					Generator d = diamonds[i];

					if (d.passTime()) {

						Collection<Entity> entities = world.getNearbyEntities(d.getLocation(), 1.5, 1.5, 1.5);

						int alreadyThere = 0;

						for (Entity ent : entities) {

							if (ent instanceof Item) {

								ItemStack item = ((Item) ent).getItemStack();

								if (item.getType() == Material.DIAMOND) alreadyThere += item.getAmount();

							}

						}

						if (alreadyThere < WedBars.MAX_DIAMONDS_IN_GEN) world.dropItem(d.getLocation(), new ItemStack(Material.DIAMOND, 1));

					}


					/*if (d.getCurTimeLeft() % 10 == 0) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hd setline diamond" + i + " 2 &fspawning in &c" + (d.getCurTimeLeft() / 10));

					}*/

				}

				// Emerald gen

				for (int i = 0; i < emeralds.length; i++) {

					Generator e = emeralds[i];


					if (e.passTime()) {

						Collection<Entity> entities = world.getNearbyEntities(e.getLocation(), 1.5, 1.5, 1.5);

						int alreadyThere = 0;

						for (Entity ent : entities) {

							if (ent instanceof Item) {

								ItemStack item = ((Item) ent).getItemStack();

								if (item.getType() == Material.EMERALD) alreadyThere += item.getAmount();

							}

						}

						if (alreadyThere < WedBars.MAX_EMERALDS_IN_GEN) world.dropItem(e.getLocation(), new ItemStack(Material.EMERALD, 1));

					}

					/*if (e.getCurTimeLeft() % 10 == 0) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hd setline emerald" + i + " 2 &fspawning in &c" + (e.getCurTimeLeft() / 10));

					}*/

				}

				// Update scoreboard

				if (totalTestTime % 10 == 0)
					GameScoreboard.updateTime(Utility.timeFormat(totalTestTime));

				totalTestTime--;

				if (!WedBars.running) {

					cancel();

				}

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 2L);

	}

	public Gamer getGamer(String name) {
		return gamers.get(name);
	}

	public ArenaTeam getTeam(Team team) {
		return teams.get(team);
	}

	public boolean checkForEndGame() {

		List<ArenaTeam> alive = new ArrayList<>();

		for (ArenaTeam team : teams.values()) {

			int playersAlive = 0;

			for (Gamer gamer : team.getGamers()) {

				if (gamer.getStatus() != Status.DEAD)
					playersAlive++;

			}

			if (playersAlive > 0) alive.add(team);

		}

		if (alive.size() <= 1) {

			WedBars.running = false;

			if (alive.size() == 0) {

				// error
				Bukkit.broadcastMessage("Error??");
				return true;

			}

			ArenaTeam winner = alive.get(0);

			for (ArenaTeam team : teams.values()) {

				if (team.getTeam() == winner.getTeam()) {

					for (Gamer gamer : team.getGamers()) {

						Player player = gamer.getPlayer();

						Utility.sendWinTitle(player);

						player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);


					}

				} else {

					for (Gamer gamer : team.getGamers()) {

						Player player = gamer.getPlayer();

						Utility.sendLossTitle(player);

						player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);

					}

				}

			}

			GameScoreboard.updateStatus(ChatColor.GRAY + "map reset");

			new BukkitRunnable() {

				int time = WedBars.TIME_BETWEEN_END_AND_RESET;

				public void run() {

					GameScoreboard.updateTime(Utility.timeFormat(time * 10));

					if (time <= 0) {

						cancel();
						resetBlocks();

					}

					time--;

				}

			}.runTaskTimer(WedBars.getInstance(), 0, 20L);
			
			return true;

		}
		
		return false;

	}

	public void resetBlocks() {

		WedBars.resetting = true;

		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage(ChatColor.GRAY + "The map is resetting...");
		Bukkit.broadcastMessage(" ");

		new BukkitRunnable() {

			public void run() {

				Location loc = WedBars.getListeners().removeFirstBlock();

				if (loc == null) {

					cancel();
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.GRAY + "Map reset complete!");
					Bukkit.broadcastMessage(" ");
					WedBars.resetting = false;
					return;

				}

				loc.getBlock().setType(Material.AIR);

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 1L);

	}

	public ArenaTeam whoseBed(Gamer gamer, Location loc) {

		for (ArenaTeam at : teams.values()) {

			for (Location l : at.getBedLoc()) {

				if (l.equals(loc)) {

					if (at.getTeam() == gamer.getTeam()) return null;

					for (Location lx : at.getBedLoc()) {

						lx.getBlock().setType(Material.AIR);

					}

					return at;

				}

			}

		}

		return null;

	}



}
