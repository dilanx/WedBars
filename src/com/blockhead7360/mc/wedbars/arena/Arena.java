package com.blockhead7360.mc.wedbars.arena;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.api.events.ArenaResetEndEvent;
import com.blockhead7360.mc.wedbars.api.events.ArenaResetStartEvent;
import com.blockhead7360.mc.wedbars.api.events.GameEndEvent;
import com.blockhead7360.mc.wedbars.api.events.GameStartEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerRespawnEvent;
import com.blockhead7360.mc.wedbars.api.events.TrapActivateEvent;
import com.blockhead7360.mc.wedbars.game.GameScoreboard;
import com.blockhead7360.mc.wedbars.game.Generator;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.GamerStats;
import com.blockhead7360.mc.wedbars.player.Statistic;
import com.blockhead7360.mc.wedbars.player.Status;
import com.blockhead7360.mc.wedbars.player.Titles;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.ArenaTeamData;
import com.blockhead7360.mc.wedbars.team.Team;
import com.blockhead7360.mc.wedbars.team.TeamAssignments;
import com.blockhead7360.mc.wedbars.team.TeamUpgrade;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;

public class Arena {


	private World world;
	private Location lobby;
	private int stage;
	private int time;
	private Map<Team, ArenaTeam> teams;
	private Map<String, Gamer> gamers;
	private int initialDiamondSpeed, initialEmeraldSpeed;
	private int buildHeight;
	private Generator[] diamonds;
	private Generator[] emeralds;

	private ArenaData data;
	private TeamAssignments ta;

	public Arena(ArenaData data, TeamAssignments teamAssignments) {

		this.lobby = data.getLobby();
		this.time = 0;
		this.stage = 0;
		this.initialDiamondSpeed = data.getDiamondSpeed();
		this.initialEmeraldSpeed = data.getEmeraldSpeed();
		this.buildHeight = data.getBuildHeight();

		this.data = data;
		this.ta = teamAssignments;

		List<Location> dg = data.getDiamondGen();
		int ds = data.getDiamondSpeed();

		diamonds = new Generator[dg.size()];

		for (int i = 0; i < dg.size(); i++) {

			diamonds[i] = new Generator(dg.get(i), ds);

		}


		List<Location> eg = data.getEmeraldGen();
		int es = data.getEmeraldSpeed();

		emeralds = new Generator[eg.size()];

		for (int i = 0; i < eg.size(); i++) {

			emeralds[i] = new Generator(eg.get(i), es);

		}

		this.world = diamonds[0].getLocation().getWorld();

		this.teams = new HashMap<Team, ArenaTeam>();
		this.gamers = new HashMap<String, Gamer>();

		Map<Team, List<String>> ta = teamAssignments.getTeamAssignments();

		for (Team team : ta.keySet()) {

			ArenaTeamData atd = data.getTeamData(team);
			List<String> players = ta.get(team);

			Gamer[] g = new Gamer[players.size()];

			for (int i = 0; i < players.size(); i++) {

				Gamer gamer = new Gamer(Bukkit.getPlayer(players.get(i)), team);
				g[i] = gamer;

				// load stats

				GamerStats.updateGamerWithStats(gamer, false);

				gamers.put(players.get(i), gamer);

			}


			ArenaTeam at = new ArenaTeam(team, atd.getSpawn(), atd.getGenerator(),
					data.getIronSpeed(), data.getGoldSpeed(), data.getPersonalEmeraldSpeed(),
					atd.getBed(), g);

			teams.put(team, at);



		}

	}

	public void stop() {

		WedBars.running = false;
		WedBars.arena = null;

		for (Player player : Bukkit.getOnlinePlayers()) {

			player.setGameMode(GameMode.SPECTATOR);
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
			player.getInventory().clear();

		}

	}

	/* 10 PER SECOND */

	public void start(boolean manual) {

		WedBars.starting = false;
		WedBars.running = true;
		WedBars.arena = this;

		// reset players

		for (Gamer gamer : gamers.values()) {

			Player p = gamer.getPlayer();
			Team team = gamer.getTeam();
			ArenaTeam at = teams.get(team);

			gamer.setStatus(Status.ALIVE);

			p.getEnderChest().clear();
			p.getInventory().clear();
			p.setHealth(20);
			p.setGameMode(GameMode.SURVIVAL);
			p.removePotionEffect(PotionEffectType.FAST_DIGGING);

			p.teleport(at.getSpawnLoc());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
			Titles.start(p, team);

			p.getInventory().setHelmet(Utility.createLeatherArmorPiece(Material.LEATHER_HELMET, team.getColor(), ChatColor.YELLOW + "Leather Helmet"));
			p.getInventory().setChestplate(Utility.createLeatherArmorPiece(Material.LEATHER_CHESTPLATE, team.getColor(), ChatColor.YELLOW + "Leather Chestplate"));
			p.getInventory().setLeggings(Utility.createLeatherArmorPiece(Material.LEATHER_LEGGINGS, team.getColor(), ChatColor.YELLOW + "Leather Leggings"));
			p.getInventory().setBoots(Utility.createLeatherArmorPiece(Material.LEATHER_BOOTS, team.getColor(), ChatColor.YELLOW + "Leather Boots"));
			p.getInventory().setItem(0, Utility.createUnbreakableItemStack(Material.WOODEN_SWORD, 1, ChatColor.YELLOW + "Wooden Sword"));

		}



		GameScoreboard.start(data.getName(), teams.values().toArray(new ArenaTeam[0]));
		GameScoreboard.updateStatus(ChatColor.AQUA + "" + ChatColor.BOLD + "Diamond 2");
		time = 3000;

		// auto-set gamerules
		world.setGameRule(GameRule.KEEP_INVENTORY, true);

		// reset team stuff

		for (ArenaTeam at : teams.values()) {


			ArenaBed.placeBed(at.getBedLoc(), at.getTeam().getColoredBed());

		}

		for (Entity e : world.getEntities()) {

			if (e instanceof Item || e instanceof IronGolem) {

				e.remove();

			}

		}

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
			h.appendTextLine(ChatColor.WHITE + "Spawning in " + ChatColor.RED + "{diamond" + i + "}");

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
			h.appendTextLine(ChatColor.WHITE + "Spawning in " + ChatColor.RED + "{emerald" + i + "}");
		}


		// invoke event

		GameStartEvent gse = new GameStartEvent(data, ta, manual);
		Bukkit.getPluginManager().callEvent(gse);

		// start game

		new BukkitRunnable() {

			public void run() {

				if (!WedBars.running) {

					cancel();
					return;

				}

				// Teams

				for (ArenaTeam at : teams.values()) {

					Location bed = at.getBedLoc()[0];
					int dist = WedBars.TRAP_DISTANCE;
					boolean healPool = at.hasUpgrade(TeamUpgrade.HEAL);
					boolean hasTrap = at.hasTrap();

					for (Entity e : world.getNearbyEntities(bed, dist, dist, dist)) {

						if (e instanceof Player) {

							Player p = (Player) e;

							if (p.getGameMode() != GameMode.SURVIVAL) continue;

							Gamer g = getGamer(p.getName());

							if (g == null) continue;

							if (g.getTeam() == at.getTeam()) {

								if (healPool) {

									p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1));

								}

							} else {

								if (hasTrap && !p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {

									TrapActivateEvent tae = new TrapActivateEvent(g, at, at.getTrap());

									if (!tae.isCancelled()) {

										for (Gamer gamer : at.getGamers()) {

											Player player = gamer.getPlayer();
											Titles.trapTriggered(player);
											player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

										}
										
										p.sendMessage(ChatColor.RED + "You activated a trap!");
										at.getTrap().activate(g, at);
										at.removeTrap();
										hasTrap = false;
										
									}

								}

							}

						}


					}

					for (Gamer gamer : at.getGamers()) {

						Player player = gamer.getPlayer();

						if (gamer.getStatus() == Status.RESPAWNING) {

							if (gamer.respawnPassTime()) {

								gamer.setStatus(Status.ALIVE);

								player.teleport(at.getSpawnLoc());
								player.setGameMode(GameMode.SURVIVAL);

								Titles.death(player, -2);

								GamerRespawnEvent gre = new GamerRespawnEvent(gamer);
								Bukkit.getPluginManager().callEvent(gre);

							} else {

								int timeLeft = gamer.getRespawningTimeLeft();

								if (timeLeft % 10 == 0) {

									Titles.death(player, timeLeft);

								}
							}

						}

						if (gamer.hasInvisArmor() && gamer.getStatus() == Status.ALIVE) {

							if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {

								player.getInventory().setArmorContents(gamer.getInvisArmor());
								gamer.removeInvisArmor();

							}

						}

					}

					// Iron gen

					Generator i = at.getIronGenerator();

					if (i.passTime()) i.spawnItem(new ItemStack(Material.IRON_INGOT, 1), false, true);


					// Gold gen

					Generator g = at.getGoldGenerator();

					if (g.passTime()) g.spawnItem(new ItemStack(Material.GOLD_INGOT, 1), false, true);


					// Emerald gen if unlocked

					Generator e = at.getEmeraldGenerator();

					if (e != null) {

						if (e.passTime()) e.spawnItem(new ItemStack(Material.EMERALD, 1), false, false);

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

						if (alreadyThere < WedBars.MAX_DIAMONDS_IN_GEN) d.spawnItem(new ItemStack(Material.DIAMOND, 1), false, false);

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

						if (alreadyThere < WedBars.MAX_EMERALDS_IN_GEN) e.spawnItem(new ItemStack(Material.EMERALD, 1), false, false);

					}

				}

				// Update scoreboard and game stage

				if (time % 10 == 0)
					GameScoreboard.updateTime(Utility.timeFormat(time));

				time--;

				if (time <= 0) {

					// stages

					nextStage();

				}

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 2L);

	}

	public void nextStage() {

		if (stage == 0) {

			// diamond 2

			if (diamonds.length > 0) {

				double newSpeed = 1 / ((1.0 / initialDiamondSpeed) * WedBars.GEN_DIAMOND2);

				for (Generator g : diamonds) {

					g.setSpeed((int) newSpeed);

				}

			}

			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("     " + ChatColor.YELLOW + ChatColor.BOLD + "NEW STAGE: " + ChatColor.AQUA + ChatColor.BOLD + "Diamond 2");
			Bukkit.broadcastMessage("     " + ChatColor.GRAY + "Diamond generators now generate diamonds faster.");
			Bukkit.broadcastMessage(" ");

			stage = 1;
			time = 3000;
			GameScoreboard.updateStatus(ChatColor.GREEN + "" + ChatColor.BOLD + "Emerald 2");

		}

		else if (stage == 1) {

			// emerald 2

			if (emeralds.length > 0) {

				double newSpeed = 1 / ((1.0 / initialEmeraldSpeed) * WedBars.GEN_EMERALD2);

				for (Generator g : emeralds) {

					g.setSpeed((int) newSpeed);

				}

			}

			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("     " + ChatColor.YELLOW + ChatColor.BOLD + "NEW STAGE: " + ChatColor.GREEN + ChatColor.BOLD + "Emerald 2");
			Bukkit.broadcastMessage("     " + ChatColor.GRAY + "Emerald generators now generate emeralds faster.");
			Bukkit.broadcastMessage(" ");

			stage = 2;
			time = 3000;
			GameScoreboard.updateStatus(ChatColor.AQUA + "" + ChatColor.BOLD + "Diamond 3");

		}

		else if (stage == 2) {

			// diamond 3

			if (diamonds.length > 0) {

				double newSpeed = 1 / ((1.0 / initialDiamondSpeed) * WedBars.GEN_DIAMOND3);

				for (Generator g : diamonds) {

					g.setSpeed((int) newSpeed);

				}

			}

			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("     " + ChatColor.YELLOW + ChatColor.BOLD + "NEW STAGE: " + ChatColor.AQUA + ChatColor.BOLD + "Diamond 3");
			Bukkit.broadcastMessage("     " + ChatColor.GRAY + "Diamond generators now generate diamonds even FASTER than before.");
			Bukkit.broadcastMessage(" ");

			stage = 3;
			time = 3000;
			GameScoreboard.updateStatus(ChatColor.GREEN + "" + ChatColor.BOLD + "Emerald 3");

		}

		else if (stage == 3) {

			// emerald 3

			if (emeralds.length > 0) {

				double newSpeed = 1 / ((1.0 / initialEmeraldSpeed) * WedBars.GEN_EMERALD2);

				for (Generator g : emeralds) {

					g.setSpeed((int) newSpeed);

				}

			}

			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("     " + ChatColor.YELLOW + ChatColor.BOLD + "NEW STAGE: " + ChatColor.GREEN + ChatColor.BOLD + "Emerald 3");
			Bukkit.broadcastMessage("     " + ChatColor.GRAY + "Emerald generators now generate emeralds even FASTER than before.");
			Bukkit.broadcastMessage(" ");

			stage = 4;
			time = 3600;
			GameScoreboard.updateStatus(ChatColor.RED + "" + ChatColor.BOLD + "beds destroyed");

		}

		else if (stage == 4) {

			// beds destroyed

			for (ArenaTeam team : teams.values()) {

				if (team.bedExists()) {

					for (Location loc : team.getBedLoc()) {

						loc.getBlock().setType(Material.AIR);

					}

					team.setBedExists(false);
					GameScoreboard.updateTeam(team);

					for (Gamer g : team.getGamers()) {

						Titles.allBedsBroken(g.getPlayer());

					}

				}

			}

			for (Player player : Bukkit.getOnlinePlayers()) {

				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);

			}

			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("     " + ChatColor.YELLOW + ChatColor.BOLD + "NEW STAGE: " + ChatColor.RED + ChatColor.BOLD + "Beds Destroyed");
			Bukkit.broadcastMessage("     " + ChatColor.GRAY + "Everyone's bed has been destroyed.");
			Bukkit.broadcastMessage(" ");

			stage = 5;
			time = 6000;
			GameScoreboard.updateStatus(ChatColor.RED + "" + ChatColor.BOLD + "game end");

		}

		else if (stage == 5) {

			//TODO

		}

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

			ArenaTeam winner = null;

			if (alive.size() == 0) {

				// error
				Bukkit.broadcastMessage("Error??");

			} else {

				winner = alive.get(0);

			}

			GameEndEvent gee = new GameEndEvent(data, ta, winner);
			Bukkit.getPluginManager().callEvent(gee);

			if (winner == null) return true;

			for (ArenaTeam team : teams.values()) {

				if (team.getTeam() == winner.getTeam()) {

					for (Gamer gamer : team.getGamers()) {

						Player player = gamer.getPlayer();
						Team pteam = gamer.getTeam();

						Titles.win(player, pteam);
						gamer.addOneToStatistic(Statistic.WINS);

						player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
						player.setGameMode(GameMode.SPECTATOR);

					}

				} else {

					for (Gamer gamer : team.getGamers()) {

						Player player = gamer.getPlayer();
						Team pteam = gamer.getTeam();

						Titles.loss(player, pteam);

						player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
						player.setGameMode(GameMode.SPECTATOR);

					}

				}

			}

			for (Gamer gamer : gamers.values()) {

				GamerStats.sendGamerData(gamer, false);

			}
			
			Collection<Hologram> hg = HologramsAPI.getHolograms(WedBars.getInstance());
			for (Hologram h : hg) {
				
				h.delete();
				
			}
			
			HologramsAPI.unregisterPlaceholders(WedBars.getInstance());

			GameScoreboard.updateStatus(ChatColor.GRAY + "map reset");

			new BukkitRunnable() {

				int time = WedBars.TIME_BETWEEN_END_AND_RESET;

				public void run() {

					GameScoreboard.updateTime(Utility.timeFormat(time * 10));

					if (time <= 0) {

						cancel();

						WedBars.arena = null;

						for (Player player : Bukkit.getOnlinePlayers()) {

							player.teleport(lobby);
							player.setGameMode(GameMode.ADVENTURE);
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.getInventory().clear();

						}

						resetBlocks(true);

					}

					time--;

				}

			}.runTaskTimer(WedBars.getInstance(), 0, 20L);

			return true;

		}

		return false;

	}

	public static void resetBlocks(boolean auto) {

		WedBars.resetting = true;

		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage(ChatColor.GRAY + "The map is resetting...");
		Bukkit.broadcastMessage(" ");
		
		ArenaResetStartEvent arse = new ArenaResetStartEvent(WedBars.getListeners().getPlacedBlocks());
		Bukkit.getPluginManager().callEvent(arse);
		

		new BukkitRunnable() {

			public void run() {

				Location loc = WedBars.getListeners().removeFirstBlock();

				if (loc == null) {

					cancel();
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.GRAY + "Map reset complete!");
					Bukkit.broadcastMessage(" ");
					WedBars.resetting = false;
					
					ArenaResetEndEvent aree = new ArenaResetEndEvent();
					Bukkit.getPluginManager().callEvent(aree);
					
					if (auto) anotherGame();
					return;

				}

				loc.getBlock().setType(Material.AIR);

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 1L);

	}

	public static void anotherGame() {

		GameScoreboard.end();
		WedBars.teamAssignments.clear();
		
		List<String> arenas = ArenaLoader.listArenas(WedBars.getInstance());
		arenas.remove(WedBars.loadedArena.getName());
		
		Random r = new Random();
		String nextArena;

		nextArena = arenas.get(r.nextInt(arenas.size() - 1));
		
		WedBars.loadedArena = ArenaLoader.loadArena(WedBars.getInstance(), nextArena, "Auto Starter");
		
		ArenaAutoStart.begin(WedBars.loadedArena, true);

	}

	public Gamer getGamer(String name) {
		return gamers.get(name);
	}

	public void deleteGamer(Gamer gamer) {

		getTeam(gamer.getTeam()).removeGamer(gamer);
		gamers.remove(gamer.getPlayer().getName());

	}

	public ArenaTeam getTeam(Team team) {
		return teams.get(team);
	}
	public Map<Team, ArenaTeam> getTeams() {
		return teams;
	}

	public ArenaTeam whoseBed(Gamer gamer, Location loc) {

		for (ArenaTeam at : teams.values()) {

			for (Location l : at.getBedLoc()) {

				if (l.equals(loc)) {

					if (at.getTeam() == gamer.getTeam()) return null;

					return at;

				}

			}

		}

		return null;

	}

	public int getBuildHeight() {
		return buildHeight;
	}



}
