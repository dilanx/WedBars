package com.blockhead7360.mc.wedbars.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.team.Team;

public class ArenaAutoStart implements Listener {


	private static Inventory ts;
	private static ArenaData data;

	public static void begin(ArenaData data, boolean openInv) {

		ArenaAutoStart.data = data;

		ts = Bukkit.createInventory(null, 9, ChatColor.BOLD + "Select a team");

		updateTeams();

		for (Player player : Bukkit.getOnlinePlayers()) {

			player.closeInventory();
			player.teleport(data.getLobby());
			player.setGameMode(GameMode.ADVENTURE);
			if (openInv) player.openInventory(ts);
			
			player.sendMessage(" ");
			player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Select your teams!" + ChatColor.GREEN + " /team");
			player.sendMessage(" ");

		}
		
		

	}

	public static void updateTeams() {

		ts.clear();

		List<String> allPlayers = new ArrayList<>();

		for (Team team : data.getAllTeams()) {

			ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, team.getStackColor());
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(team.getChatColor() + team.getLabel());

			List<String> players = (WedBars.teamAssignments.getTeamAssignments().containsKey(team) ?
					WedBars.teamAssignments.getTeamAssignments().get(team) : new ArrayList<>());

			List<String> lore = new ArrayList<>(players.size() + 2);
			lore.add(" ");
			lore.add(ChatColor.GRAY + "" + players.size() + " players");

			for (String p : players) {

				lore.add(ChatColor.WHITE + p);
				allPlayers.add(p);

			}

			meta.setLore(lore);

			stack.setItemMeta(meta);

			ts.addItem(stack);

		}

		if (allPlayers.size() == Bukkit.getOnlinePlayers().size() && WedBars.teamAssignments.getTeamAssignments().size() >= 2) {

			for (Player p : Bukkit.getOnlinePlayers()) {

				p.closeInventory();

			}

			Bukkit.broadcastMessage(ChatColor.GREEN + "All players have selected a team!" + ChatColor.GRAY + " Team selection has ended and the game will begin soon.");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team show");
			
			startCountdown();
			return;

		}

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if (e.getView().getTitle().equals(ChatColor.BOLD + "Select a team")) {

			if (e.getCurrentItem() == null) return;
			if (e.getCurrentItem().getType() == Material.AIR) return;
			if (e.getRawSlot() > 8) return;


			e.setCancelled(true);

			Player player = (Player) e.getWhoClicked();

			Team selected = Team.getByLabel(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

			WedBars.teamAssignments.assign(player, selected);
			updateTeams();
			return;

		}

	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		if (WedBars.running || WedBars.resetting || WedBars.starting) {

			Player player = e.getPlayer();

			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GRAY + "The game is currently in progress or is about to start. Hang tight!");
			player.sendMessage(" ");

			return;

		}

	}

	public static void startCountdown() {

		WedBars.starting = true;

		Bukkit.broadcastMessage(ChatColor.GRAY + "The game is starting in " + ChatColor.WHITE + WedBars.LOBBY_START + " seconds" + ChatColor.GRAY + ".");

		new BukkitRunnable() {

			int time = WedBars.LOBBY_START;

			public void run() {

				if (!WedBars.starting) {

					cancel();
					return;

				}

				if (time <= 5 && time >= 1) {

					Bukkit.broadcastMessage(ChatColor.GRAY + "The game is starting in " + ChatColor.WHITE + time + (time == 1 ? " second" : " seconds") + ChatColor.GRAY + ".");

				}

				if (time <= 0) {

					cancel();
					WedBars.starting = false;

					List<String> allPlayers = new ArrayList<>();

					for (List<String> players : WedBars.teamAssignments.getTeamAssignments().values()) {

						allPlayers.addAll(players);

					}

					if (WedBars.teamAssignments.getTeamAssignments().size() < 2) {

						Bukkit.broadcastMessage(ChatColor.RED + "Game start canceled!" + ChatColor.GRAY + " Not enough teams. The team assignments have been cleared. Use '/team' to select your team.");
						WedBars.teamAssignments.clear();
						updateTeams();
						return;

					}

					Arena arena = new Arena(data, WedBars.teamAssignments);
					arena.start();

					return;

				}

				time--;


			}

		}.runTaskTimer(WedBars.getInstance(), 0, 20L);

	}

	public static void selectTeam(Player player) {

		player.openInventory(ts);

	}

}