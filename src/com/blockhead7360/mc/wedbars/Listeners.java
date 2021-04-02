package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

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
			player.getInventory().setItem(0, Utility.createUnbreakableItemStack(Material.WOOD_SWORD, 1, ChatColor.YELLOW + "Wooden Sword"));


			// TODO give one tier down of tools

			// i moved the death titles to the timer in Arena.java you'll see why

			player.setHealth(20);
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));

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
