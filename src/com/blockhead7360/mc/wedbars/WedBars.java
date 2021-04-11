package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.blockhead7360.mc.wedbars.arena.Arena;
import com.blockhead7360.mc.wedbars.arena.ArenaData;
import com.blockhead7360.mc.wedbars.arena.ArenaLoader;
import com.blockhead7360.mc.wedbars.arena.SetupWizard;
import com.blockhead7360.mc.wedbars.game.ConnectionListener;
import com.blockhead7360.mc.wedbars.game.GameChat;
import com.blockhead7360.mc.wedbars.game.Listeners;
import com.blockhead7360.mc.wedbars.game.Powerups;
import com.blockhead7360.mc.wedbars.game.Shop;
import com.blockhead7360.mc.wedbars.team.Team;
import com.blockhead7360.mc.wedbars.team.TeamAssignments;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class WedBars extends JavaPlugin {

	// gamerTicks = 10 per second


	public static boolean running = false;
	public static boolean resetting = false;
	public static Arena arena = null;

	public static ArenaData loadedArena = null;
	public static TeamAssignments teamAssignments = new TeamAssignments();


	// speeds (interval between spawns in 10*seconds)



	// forge scales
	// n times the initial interval as set by the arena

	public static final double FORGE1 = 1.5;
	public static final double FORGE2 = 2;
	// forge 3 is emeralds
	public static final double FORGE4 = 4;


	// gen scales
	// n times the initial speed as set by the arena

	public static final int GEN_DIAMOND2 = 2;
	public static final int GEN_DIAMOND3 = 4;
	public static final int GEN_EMERALD2 = 2;
	public static final int GEN_EMERALD3 = 4;

	public static final int SPAWN_PROTECTION_DISTANCE_SQUARED = 25;
	public static final int TRAP_DISTANCE = 7;

	// not gamerTicks, these are in seconds
	public static int TIME_BETWEEN_END_AND_RESET;
	public static int GOLEM_LIFE = 240;
	public static int GOLEM_ATTACK_DISTANCE = 7;
	public static int GOLEM_HEALTH, BUG_HEALTH;
	public static int BUG_LIFE = 120;
	public static int BUG_ATTACK_DISTANCE = 4;
	



	// these are gamerTicks though
	public static int MAX_DIAMONDS_IN_GEN, MAX_EMERALDS_IN_GEN;
	public static int MAX_BUILD_HEIGHT, TNT_FUSE, RESPAWN_TIME, VOID_LEVEL;
	
	//TODO i didn't put this in config
	public static int BRIDGE_EGG_TIME = 30;


	private static WedBars instance;

	private static Listeners listeners;

	public void onEnable() {

		listeners = new Listeners();
		getServer().getPluginManager().registerEvents(listeners, this);
		getServer().getPluginManager().registerEvents(new Powerups(), this);
		getServer().getPluginManager().registerEvents(new Shop(), this);
		getServer().getPluginManager().registerEvents(new SetupWizard(), this);
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		getServer().getPluginManager().registerEvents(new GameChat(), this);

		Shop.init();

		instance = this;

		saveDefaultConfig();

		MAX_BUILD_HEIGHT = getConfig().getInt("maxBuildHeight");
		TNT_FUSE = getConfig().getInt("tntFuse");
		TIME_BETWEEN_END_AND_RESET = getConfig().getInt("timeBeforeReset");
		MAX_DIAMONDS_IN_GEN = getConfig().getInt("maxDiamondsInGen");
		MAX_EMERALDS_IN_GEN = getConfig().getInt("maxEmeraldsInGen");
		VOID_LEVEL = getConfig().getInt("voidLevel");
		RESPAWN_TIME = getConfig().getInt("respawnTime");
		GOLEM_ATTACK_DISTANCE = getConfig().getInt("golemTargetDistance");
		GOLEM_LIFE = getConfig().getInt("golemLife");
		BUG_ATTACK_DISTANCE = getConfig().getInt("bugTargetDistance");
		BUG_LIFE = getConfig().getInt("bugLife");
		GOLEM_HEALTH = getConfig().getInt("golemHealth");
		BUG_HEALTH = getConfig().getInt("bugHealth");

	}

	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("wedbars")) {

			if (args.length == 0) {
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Wed Bars " + getDescription().getVersion() + ChatColor.GRAY + " by Dilan and Jack.");
				sender.sendMessage(ChatColor.GRAY + getDescription().getDescription());
				sender.sendMessage(ChatColor.GRAY + getDescription().getWebsite());
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.GRAY + "Use " + ChatColor.GREEN + "/wb help" + ChatColor.GRAY + " for commands.");
				sender.sendMessage(" ");
				return true;
			}
			
			sender.sendMessage(" ");
			sender.sendMessage(ChatColor.GRAY + "---[ " + ChatColor.GREEN + "Wed Bars Command Help" + ChatColor.GRAY + " ]---");
			sender.sendMessage(ChatColor.RED + "/setup" + ChatColor.GRAY + " - Setup a new arena.");
			sender.sendMessage(ChatColor.RED + "/load" + ChatColor.GRAY + " - Load an arena.");
			sender.sendMessage(ChatColor.RED + "/team" + ChatColor.GRAY + " - Assign teams manually.");
			sender.sendMessage(ChatColor.RED + "/autoteam" + ChatColor.GRAY + " - Automatically randomly assign teams.");
			sender.sendMessage(ChatColor.RED + "/start" + ChatColor.GRAY + " - Start the game.");
			sender.sendMessage(" ");
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("start")) {

			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You do not have permission to use this command.");
				return true;

			}

			if (loadedArena == null) {

				sender.sendMessage("Load an arena first with /load, then assign teams with /team.");
				return true;

			}

			if (running) {

				sender.sendMessage("The game is already running.");
				return true;

			}

			if (resetting) {

				sender.sendMessage("The map is resetting.");
				return true;

			}

			Arena arena = new Arena(loadedArena, teamAssignments);

			if (arena.getTeams().size() < 2) {

				sender.sendMessage("You must have two or more teams in order to start a game.");
				return true;

			}

			arena.start();

			return true;

		}

		if (cmd.getName().equalsIgnoreCase("load")) {

			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You do not have permission to use this command.");
				return true;

			}

			if (args.length == 0) {

				sender.sendMessage("Use '/load <arena>' to load a Wed Bars arena (unloading any previously loaded one).");
				String list = ArenaLoader.listArenas(this).toString();
				sender.sendMessage("Available arenas: " + list.substring(1, list.length() - 1));

				return true;

			}

			ArenaData data = ArenaLoader.loadArena(this, args[0]);

			if (data == null) {

				sender.sendMessage("Unable to find a saved arena with that name.");
				return true;

			}

			loadedArena = data;
			teamAssignments.clear();

			sender.sendMessage("Successfully loaded the arena data for " + args[0] + " (any team assignments were reset).");

			return true;

		}
		
		if (cmd.getName().equalsIgnoreCase("autoteam")) {
			
			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You do not have permission to use this command.");
				return true;

			}
			
			if (loadedArena == null) {

				sender.sendMessage("Load an arena first with /load.");
				return true;

			}

			if (args.length < 2) {

				sender.sendMessage("/autoteam <teamOption1> <teamOption2> [<teamOption3>...]");
				return true;

			}
			
			List<String> teamOptions = new ArrayList<String>();
			
			for (int i = 0; i < args.length; i++) {
				
				Team team = Team.getByLabel(args[i]);

				if (team == null) {

					sender.sendMessage("Unknown team: " + args[i]);
					return true;

				}

				if (!loadedArena.getAllTeams().contains(team)) {

					sender.sendMessage("This arena doesn't support the " + team.getLabel() + " team.");
					return true;

				}
				
				teamOptions.add(args[i]);
				
			}
			
			List<Player> players = new ArrayList<Player>();
			
			for (Player p : getServer().getOnlinePlayers()) players.add(p);
			
			double pplPerTeam = Math.ceil((double) players.size() / (double) teamOptions.size());
			
			Collections.shuffle(players);
			Collections.shuffle(teamOptions);
			
			int curPlayer = 0;
			int curTeam = 0;
			
			for (Player player : players) {
				
				getServer().dispatchCommand(getServer().getConsoleSender(), "team " + teamOptions.get(curTeam) + " " + player.getName());
				curPlayer++;
				
				if (curPlayer >= pplPerTeam) {
					
					curPlayer = 0;
					curTeam++;
					
				}
				
			}
			
			Bukkit.broadcastMessage(" ");
			
			getServer().dispatchCommand(getServer().getConsoleSender(), "team show");
			
			return true;
			
		}

		if (cmd.getName().equalsIgnoreCase("team")) {

			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You can't assign yourself a team yet, sorry! An admin will do it for you.");
				return true;

			}

			if (loadedArena == null) {

				sender.sendMessage("Load an arena first with /load.");
				return true;

			}

			if (args.length == 0) {

				sender.sendMessage("/team <team> <player>");
				sender.sendMessage("/team show");
				return true;

			}

			if (args[0].equalsIgnoreCase("show")) {

				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Team assignments for the next game");

				Map<Team, List<String>> ta = teamAssignments.getTeamAssignments();

				if (ta.keySet().size() == 0) {

					Bukkit.broadcastMessage(ChatColor.GRAY + "No teams set");

				} else {

					for (Team team : ta.keySet()) {

						List<String> players = ta.get(team);

						String text = team.getChatColor() + team.getLabel() + " (" + players.size() + "): " + ChatColor.WHITE + players.get(0);

						for (int i = 1; i < players.size(); i++) {

							text += ", " + players.get(i);

						}

						Bukkit.broadcastMessage(text);

					}

				}

				Bukkit.broadcastMessage(" ");

				return true;

			}

			if (args.length < 2) {

				sender.sendMessage("/team <team> <player>");
				sender.sendMessage("/team show");
				return true;

			}

			Team team = Team.getByLabel(args[0]);

			if (team == null) {

				sender.sendMessage("Unknown team: " + args[0]);
				return true;

			}

			if (!loadedArena.getAllTeams().contains(team)) {

				sender.sendMessage("This arena doesn't support the " + team.getLabel() + " team.");
				return true;

			}

			Player player = getServer().getPlayer(args[1]);

			if (player == null) {

				sender.sendMessage("Unknown or offline player: " + args[1]);
				return true;

			}

			teamAssignments.assign(player, team);

			Bukkit.broadcastMessage(team.getChatColor() + player.getName() + ChatColor.GRAY + " is now a member of the "
					+ team.getChatColor() + ChatColor.BOLD + team.getLabel() + ChatColor.GRAY + " team.");

			return true;


		}

		if (cmd.getName().equalsIgnoreCase("setup")) {

			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You do not have permission to use this command.");
				return true;

			}

			if (!(sender instanceof Player)) {

				sender.sendMessage("Only players can use this command.");
				return true;

			}

			if (args.length == 0) {

				sender.sendMessage("Use '/setup <arena>' to either set up a new Wed Bars arena or edit an existing one.");

				return true;

			}

			if (SetupWizard.settingUp != null) {

				sender.sendMessage("Another administrator is setting up an arena, and only one person can do it at a time.");
				return true;

			}

			SetupWizard.settingUp = (Player) sender;

			ArenaData data = ArenaLoader.loadArena(this, args[0]);

			if (data == null) {

				SetupWizard.setup = new ArenaData(args[0]);

				sender.sendMessage(ChatColor.YELLOW + "New arena setup!" + ChatColor.GRAY + " You're setting up a new arena named " + args[0] + "."
						+ " Type 'help' into the chat to get started or 'cancel' to cancel.");
				return true;

			}

			SetupWizard.setup = data;

			sender.sendMessage(ChatColor.YELLOW + "Existing arena setup!" + ChatColor.GRAY + " You're editing an existing arena named " + args[0] + "."
					+ " Type 'help' into the chat to get started or 'cancel' to cancel.");
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("test")) {

			if (!sender.hasPermission("wedbars.admin")) {

				sender.sendMessage("You do not have permission to use this command.");
				return true;

			}

			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("fb")) {

					Powerups.launchFireball((Player) sender);
					return true;

				}

				if (args[0].equalsIgnoreCase("shop")) {

					Shop.openItemShop((Player) sender);
					return true;

				}

				if (args[0].equalsIgnoreCase("golem")) {

					if (!running) {
						sender.sendMessage("No");
						return true;
					}

					Powerups.spawnGolem(arena.getGamer(sender.getName()));
					return true;

				}

				if (args[0].equalsIgnoreCase("hg")) {

					Hologram h = HologramsAPI.createHologram(this, new Location(getServer().getWorld("world"), 63, 68, -36));
					h.appendTextLine("test");


				}

				if (args[0].equalsIgnoreCase("start")) {

					if (resetting || running) {

						sender.sendMessage("Already running or resetting");
						return true;

					}

					// TEMPORARY

					//					World world = getServer().getWorld("world");
					//
					//					Generator[] emeralds = {
					//							new Generator(new Location(world, 12.5, 78, -11.5), EMERALD1_SPEED),
					//							new Generator(new Location(world, -11.5, 78, -11.5), EMERALD1_SPEED),
					//							new Generator(new Location(world, -11.5, 78, 12.5), EMERALD1_SPEED),
					//							new Generator(new Location(world, 12.5, 78, 12.5), EMERALD1_SPEED)
					//
					//					};
					//
					//					Generator[] diamonds = {
					//							new Generator(new Location(world, 0.5, 64, 52.5), DIAMOND1_SPEED),
					//							new Generator(new Location(world, 52.5, 64, 0.5), DIAMOND1_SPEED),
					//							new Generator(new Location(world, 0.5, 64, -51.5), DIAMOND1_SPEED),
					//							new Generator(new Location(world, -51.5, 64, 0.5), DIAMOND1_SPEED)
					//					};
					//
					//					ArenaTeam gray = new ArenaTeam(Team.GRAY,
					//							new Location(world, -72, 66, -33),
					//							new Location(world, -77, 66, -32),
					//							1, 1,
					//							new Location[]{new Location(world, -65, 66, -33), new Location(world, -64, 66, -33)},
					//							new Gamer[]{new Gamer(getServer().getPlayer("shark_pog"), Team.GRAY)});
					//
					//					ArenaTeam green = new ArenaTeam(Team.GREEN,
					//							new Location(world, 72, 66, -33),
					//							new Location(world, 77, 66, -34),
					//							1, 1,
					//							new Location[]{new Location(world, 65, 66, -33), new Location(world, 64, 66, -33)},
					//							new Gamer[]{new Gamer(getServer().getPlayer("ComputerCart"), Team.GREEN)});
					//
					//					ArenaTeam yellow = new ArenaTeam(Team.YELLOW,
					//							new Location(world, 72, 66, 33),
					//							new Location(world, 77, 66, 32),
					//							1, 1,
					//							new Location[]{new Location(world, 65, 66, 33), new Location(world, 64, 66, 33)},
					//							new Gamer[]{new Gamer(getServer().getPlayer("Faience"), Team.YELLOW)});
					//
					//					ArenaTeam white = new ArenaTeam(Team.WHITE,
					//							new Location(world, -33, 66, 72),
					//							new Location(world, -32, 66, 77),
					//							1, 1,
					//							new Location[]{new Location(world, -33, 66, 65), new Location(world, -33, 66, 64)},
					//							new Gamer[]{new Gamer(getServer().getPlayer("aromazx"), Team.WHITE)});
					//
					//					Arena arena = new Arena(new ArenaTeam[] {gray, green, yellow, white}, diamonds, emeralds);
					//					arena.start();

				}

			}

		}

		if (cmd.getName().equalsIgnoreCase("itemshop")) {

			if (!sender.hasPermission("wedbars.itemshop")) {

				sender.sendMessage(ChatColor.RED + "Use one of the shop NPCs to access the shop.");
				return true;

			}

			if (!(sender instanceof Player)) {

				sender.sendMessage("Only players lol.");
				return true;

			}

			Shop.openItemShop((Player) sender);
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("teamshop")) {

			if (!sender.hasPermission("wedbars.itemshop")) {

				sender.sendMessage(ChatColor.RED + "Use one of the team upgrade NPCs to access the team upgrade shop.");
				return true;

			}

			if (!(sender instanceof Player)) {

				sender.sendMessage("Only players lol.");
				return true;

			}

			Shop.openTeamUpgrades((Player) sender);
			return true;

		}

		return true;

	}

	public static WedBars getInstance() {

		return instance;

	}

	public static Listeners getListeners() {

		return listeners;

	}


}
