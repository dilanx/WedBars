package com.blockhead7360.mc.wedbars;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
		
		
		// reset beds
		
		for (ArenaTeam at : teams.values()) {
			
			for (Location l : at.getBedLoc()) {
				
				l.getBlock().setType(Material.CAKE_BLOCK);
				
			}
			
		}

		// start game

		new BukkitRunnable() {

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


					if (d.getCurTimeLeft() % 10 == 0) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hd setline diamond" + i + " 2 &fspawning in &c" + (d.getCurTimeLeft() / 10));

					}

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

					if (e.getCurTimeLeft() % 10 == 0) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hd setline emerald" + i + " 2 &fspawning in &c" + (e.getCurTimeLeft() / 10));

					}

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
