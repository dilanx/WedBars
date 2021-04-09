package com.blockhead7360.mc.wedbars.game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.Utility.EnchantmentSet;
import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.arena.Arena;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Status;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.Team;
import com.blockhead7360.mc.wedbars.team.TeamUpgrade;

public class Listeners implements Listener {

	private List<Location> placedBlocks;

	public Listeners() {
		placedBlocks = new ArrayList<>();
	}

	public List<Location> getPlacedBlocks() {
		return placedBlocks;
	}

	public Location removeFirstBlock() {

		if (placedBlocks.isEmpty()) return null;

		Location loc = placedBlocks.get(0);
		placedBlocks.remove(0);
		return loc;

	}

	public void removeBlock(int index) {

		placedBlocks.remove(index);

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		if (!WedBars.running) return;

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getClickedBlock().getType() == Material.CHEST) {

				// TODO chest implementation
				
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Sorry! Normal chests aren't available for use right now.");
				e.getPlayer().sendMessage(ChatColor.GRAY + "You'll have to use your ender chest instead.");


			}

		}

	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
		
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		
	}

	@EventHandler
	public void entityDamageEntity(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player && WedBars.arena != null) {

			Player shooter = (Player) ((Arrow) e.getDamager()).getShooter();
			Player player = (Player) e.getEntity();

			DecimalFormat round = new DecimalFormat("##.#");

			if (player.getHealth() - e.getFinalDamage() > 0) {
				shooter.sendMessage(WedBars.arena.getGamer(player.getName()).getTeam().getChatColor() + player.getName()
				+ ChatColor.GRAY + " is now at " + ChatColor.RED + round.format(player.getHealth() - e.getFinalDamage()) + " HP");
			}
		}

	}

	@EventHandler
	public void blockPlaced(BlockPlaceEvent event) {

		if (!WedBars.running) return;

		if (event.getBlock().getLocation().getY() >= WedBars.MAX_BUILD_HEIGHT) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You can't build any higher!");
			return;
		}

		if (event.getBlock().getType() == Material.TNT) {
			event.getBlock().setType(Material.AIR);
			TNTPrimed tp = (TNTPrimed)event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
			tp.setFuseTicks(WedBars.TNT_FUSE);
			return;
		}
		
		for (ArenaTeam team : WedBars.arena.getTeams().values()) {
			
			if (team.getSpawnLoc().distanceSquared(event.getBlock().getLocation()) <= WedBars.SPAWN_PROTECTION_DISTANCE_SQUARED) {
				
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "You can't place blocks there!");
				return;
				
			}
			
		}

		placedBlocks.add(event.getBlock().getLocation());
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {

		if (!WedBars.running) return;

		// TODO switch this to bed when ready

		if (event.getBlock().getType() == Material.CAKE_BLOCK) {

			Arena arena = WedBars.arena;

			Gamer breaker = arena.getGamer(event.getPlayer().getName());

			ArenaTeam team = arena.whoseBed(breaker, event.getBlock().getLocation());

			if (team == null) {

				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "Imagine trying to break your own bed (L).");
				return;

			}

			bedGone(team, breaker);
			return;

		}

		boolean found = false;
		for (int i = 0; i < placedBlocks.size(); i++) {
			if (event.getBlock().getLocation().equals(placedBlocks.get(i))) {
				found = true;
				placedBlocks.remove(i);
				break;
			}
		}
		if (!found) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You can only break blocks placed by players!");
		}


	}

	public void bedGone(ArenaTeam team, Gamer breaker) {

		team.setBedExists(false);

		for (Player player : Bukkit.getOnlinePlayers()) {

			player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);

		}

		// TODO more messages

		Team t = team.getTeam();

		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("     " + ChatColor.RED + ChatColor.BOLD + "BED DESTROYED!");
		Bukkit.broadcastMessage("     " + t.getChatColor() + t.getLabel() + ChatColor.GRAY + " team's bed was destroyed by " + breaker.getTeam().getChatColor() + breaker.getPlayer().getName() + ChatColor.GRAY + ".");
		Bukkit.broadcastMessage(" ");

		GameScoreboard.updateTeam(team);



		for (Gamer gamer : team.getGamers()) Utility.sendBedBrokenTitle(gamer.getPlayer());



	}

	public void death(Player player, boolean disconnect) {

		Gamer gamer = WedBars.arena.getGamer(player.getName());
		boolean bedExists = WedBars.arena.getTeam(gamer.getTeam()).bedExists();

		if (disconnect) {

			Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName() + ChatColor.GRAY + " disconnected.");
			WedBars.arena.deleteGamer(gamer);

		} else {

			ItemStack[] armor = player.getInventory().getArmorContents();

			ItemStack[] contents = player.getInventory().getContents();

			player.getInventory().clear();

			player.getInventory().setArmorContents(armor);
						
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
			player.teleport(new Location(player.getWorld(), 0, 100, 0));

			// TODO change messages

			Player killer = player.getKiller();

			if (killer == null) {

				Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName()
				+ ChatColor.GRAY + " died." + (bedExists ? "" : ChatColor.RED + "" + ChatColor.BOLD + " FINAL KILL!"));

			} else {

				killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);

				Bukkit.broadcastMessage(gamer.getTeam().getChatColor() + player.getName()
				+ ChatColor.GRAY + " was killed by " + WedBars.arena.getGamer(killer.getName()).getTeam().getChatColor() + killer.getName() + ChatColor.GRAY + "."
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

			}

		}

		if (bedExists && !disconnect) {

			gamer.setStatus(Status.RESPAWNING);
			Utility.sendDeathTitle(player, WedBars.RESPAWN_TIME);

		} else {

			gamer.setStatus(Status.DEAD);
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

			}

			boolean end = WedBars.arena.checkForEndGame();

			if (!end && !disconnect) {

				Utility.sendDeathTitle(player, -1);

			}

		}

		/*
        if (!testing) {

            player.setHealth(20);
            player.setGameMode(GameMode.SPECTATOR);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " times 0 100 20");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " subtitle {\"text\":\"You will respawn in 5 seconds...\",\"color\":\"grey\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " title {\"text\":\"You died!\",\"color\":\"red\"}");
            player.teleport(new Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0));

            //TODO: add respawn handlers and time thing
        } else {


            player.setHealth(20);
            player.setGameMode(GameMode.SPECTATOR);
            //TODO: more game logic
            player.teleport(new Location(Bukkit.getWorld("world"), 0.0,100.0,0.0));
            Bukkit.broadcastMessage(player.getPlayerListName() + " was killed by " +
            	player.getKiller().getName() + ". " + ChatColor.AQUA + ChatColor.BOLD + "FINAL KILL!");

        }
		 */

	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {

		if (!WedBars.running) return;

		event.setDeathMessage(null);
		death(event.getEntity(), false);

	}

	@EventHandler
	public void playerEntersVoid(PlayerMoveEvent event) {

		if (!WedBars.running) return;

		if (event.getPlayer().getLocation().getY() <= WedBars.VOID_LEVEL) {

			if (event.getPlayer().getHealth() != 0 && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {

				death(event.getPlayer(), false);

			}


		}


	}

}
