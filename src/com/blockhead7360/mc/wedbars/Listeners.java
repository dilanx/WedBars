package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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

	@EventHandler
	public void blockPlaced(BlockPlaceEvent event) {
		placedBlocks.add(event.getBlock().getLocation());
		System.out.println(placedBlocks.toString());
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {

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
				System.out.println(placedBlocks.toString());
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
		Bukkit.broadcastMessage(t.getChatColor() + "" + ChatColor.BOLD + "BED DESTROYED!");
		Bukkit.broadcastMessage(t.getChatColor() + t.getLabel() + ChatColor.GRAY + " team's bed was destroyed by " + breaker.getTeam().getChatColor() + breaker.getPlayer().getName() + ChatColor.GRAY + ".");
		Bukkit.broadcastMessage(" ");



		for (Gamer gamer : team.getGamers()) Utility.sendBedBrokenTitle(gamer.getPlayer());



	}

	public void death(Player player) {

		ItemStack[] armor = player.getInventory().getArmorContents();

		player.getInventory().clear();

		player.getInventory().setArmorContents(armor);
		player.getInventory().setItem(0, Utility.createUnbreakableItemStack(Material.WOOD_SWORD, 1, ChatColor.YELLOW + "Wooden Sword"));

		// TODO give one tier down of tools

		// i moved the death titles to the timer in Arena.java you'll see why

		player.setHealth(20);
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));

		Gamer gamer = WedBars.arena.getGamer(player.getName());

		boolean bedExists = WedBars.arena.getTeam(gamer.getTeam()).bedExists();

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

		}

		if (bedExists) {
			
			gamer.setStatus(Status.RESPAWNING);
			Utility.sendDeathTitle(player, WedBars.RESPAWN_TIME);
			
		} else {
			
			gamer.setStatus(Status.DEAD);
			Utility.sendDeathTitle(player, -1);
			
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
		death(event.getEntity());

	}

	@EventHandler
	public void playerEntersVoid(PlayerMoveEvent event) {

		if (!WedBars.running) return;

		if (event.getPlayer().getLocation().getY() <= 0) {

			if (event.getPlayer().getHealth() != 0 && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {

				death(event.getPlayer());

			}


		}


	}

}
