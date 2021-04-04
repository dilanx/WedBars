package com.blockhead7360.mc.wedbars.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.team.ArenaTeamData;
import com.blockhead7360.mc.wedbars.team.Team;

import net.md_5.bungee.api.ChatColor;

public class SetupWizard implements Listener {

	public static Player settingUp = null;

	public static ArenaData setup = null;
	public static ArenaTeamData teamSetup = null;

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {

		if (settingUp == null) return;
		
		if (e.getPlayer().getName().equals(settingUp.getName())) {

			e.setCancelled(true);

			String msg = e.getMessage();
			Player player = e.getPlayer();


			if (teamSetup != null) {

				if (msg.equalsIgnoreCase("help") || msg.equalsIgnoreCase("?")) {

					player.sendMessage(" ");
					player.sendMessage(ChatColor.YELLOW + "----- Arena Team Setup Help -----");
					player.sendMessage(ChatColor.WHITE + "checklist | cl" + ChatColor.GRAY + " - see everything you've set up so far for the team");
					player.sendMessage(ChatColor.WHITE + "spawn" + ChatColor.GRAY + " - set the spawn to your current location");
					player.sendMessage(ChatColor.WHITE + "generator | gen" + ChatColor.GRAY + " - set the generator to your current location");
					player.sendMessage(ChatColor.WHITE + "bed0" + ChatColor.GRAY + " - set the one of the bed halves to the block you're looking at");
					player.sendMessage(ChatColor.WHITE + "bed1" + ChatColor.GRAY + " - set the other half to the block you're looking at");
					player.sendMessage(ChatColor.WHITE + "done" + ChatColor.GRAY + " - save the arena team data");
					player.sendMessage(ChatColor.WHITE + "cancel" + ChatColor.GRAY + " - cancel this");
					player.sendMessage(ChatColor.WHITE + "help | ?" + ChatColor.GRAY + " - show this message");
					return;

				}

				if (msg.equalsIgnoreCase("done")) {

					boolean okay = true;

					if (teamSetup.getSpawn() == null) {
						okay = false;
					}

					if (teamSetup.getGenerator() == null) {
						okay = false;
					}

					if (!teamSetup.isBedSet()) {
						okay = false;
					}

					if (!okay) {

						player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " You're missing something. Type 'checklist' to find out what.");
						return;

					}

					String label = teamSetup.getTeam().getLabel();
					setup.setTeamData(teamSetup.getTeam(), teamSetup.clone());
					teamSetup = null;

					player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The " + label + " team is now set up."
							+ " Use 'checklist' to see what you've done so far for this arena.");
					return;

				}

				if (msg.equalsIgnoreCase("cancel")) {

					teamSetup = null;

					player.sendMessage(ChatColor.DARK_AQUA + "Canceled." + ChatColor.WHITE + " Returned to overall arena setup. Type 'help' for help.");
					return;

				}

				if (msg.equalsIgnoreCase("spawn")) {

					teamSetup.setSpawn(player.getLocation().clone());
					player.sendMessage(ChatColor.AQUA + "Success!" + ChatColor.WHITE + " The team's spawn has been set to your current location.");
					return;

				}

				if (msg.equalsIgnoreCase("generator") || msg.equalsIgnoreCase("gen")) {

					teamSetup.setGenerator(player.getLocation().clone());
					player.sendMessage(ChatColor.AQUA + "Success!" + ChatColor.WHITE + " The team's generator has been set to your current location.");
					return;

				}

				if (msg.equalsIgnoreCase("bed0")) {

					teamSetup.setBed(0, Utility.getTargetBlock(player).getLocation());
					player.sendMessage(ChatColor.AQUA + "Success!" + ChatColor.WHITE + " One half of the team's bed has been set to the block you're looking at."
							+ " Now look at the other half and type 'bed1'.");
					return;

				}

				if (msg.equalsIgnoreCase("bed1")) {

					teamSetup.setBed(1, Utility.getTargetBlock(player).getLocation());
					player.sendMessage(ChatColor.AQUA + "Success!" + ChatColor.WHITE + " The other half of the team's bed has been set to the block you're looking at.");
					return;

				}

				if (msg.equalsIgnoreCase("checklist") || msg.equalsIgnoreCase("cl")) {

					player.sendMessage(" ");
					player.sendMessage(ChatColor.WHITE + "Setting up " + ChatColor.YELLOW + setup.getName() + ChatColor.WHITE + ": " + teamSetup.getTeam().getLabel() + " team");

					String spawn = null;
					if (teamSetup.getSpawn() == null) {
						spawn = ChatColor.GRAY + "Spawn not set";
					} else {
						spawn = ChatColor.AQUA + "Spawn set" + ChatColor.GRAY;
					}

					String gen = null;
					if (teamSetup.getGenerator() == null) {
						gen = ChatColor.GRAY + "Generator not set";
					} else {
						gen = ChatColor.AQUA + "Generator set" + ChatColor.GRAY;
					}

					String bed = null;
					if (!teamSetup.isBedSet()) {
						bed = ChatColor.GRAY + "Bed not set";
					} else {
						bed = ChatColor.AQUA + "Bed set" + ChatColor.GRAY;
					}

					player.sendMessage(ChatColor.WHITE + "Team Settings: " + spawn + ", " + gen + ", " + bed);
					return;

				}

				player.sendMessage(ChatColor.RED + "Unknown command." + ChatColor.WHITE + " Type 'help' for help.");
				return;


			}





			if (msg.equalsIgnoreCase("help") || msg.equalsIgnoreCase("?")) {

				player.sendMessage(" ");
				player.sendMessage(ChatColor.YELLOW + "----- Arena Setup Help -----");
				player.sendMessage(ChatColor.WHITE + "checklist | cl" + ChatColor.GRAY + " - see everything you've set up so far");
				player.sendMessage(ChatColor.WHITE + "team <team>" + ChatColor.GRAY + " - setup a team island");
				player.sendMessage(ChatColor.WHITE + "lobby" + ChatColor.GRAY + " - set the lobby to your current location");
				player.sendMessage(ChatColor.WHITE + "diamondgen" + ChatColor.GRAY + " - add your current location as a diamond generator");
				player.sendMessage(ChatColor.WHITE + "emeraldgen" + ChatColor.GRAY + " - add your current location as a diamond generator");
				player.sendMessage(ChatColor.WHITE + "cleargens" + ChatColor.GRAY + " - remove all the diamond and emerald generators");
				player.sendMessage(ChatColor.WHITE + "diamondspeed <speed>" + ChatColor.GRAY + " - set the initial diamond generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "emeraldspeed <speed>" + ChatColor.GRAY + " - set the initial emerald generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "ironspeed <speed>" + ChatColor.GRAY + " - set the initial iron generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "goldspeed <speed>" + ChatColor.GRAY + " - set the initial gold generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "personalemeraldspeed <speed>" + ChatColor.GRAY + " - set the island emerald generator speed (seconds * 10)");
				player.sendMessage(ChatColor.WHITE + "save" + ChatColor.GRAY + " - save the arena data");
				player.sendMessage(ChatColor.WHITE + "cancel" + ChatColor.GRAY + " - cancel this");
				player.sendMessage(ChatColor.WHITE + "help | ?" + ChatColor.GRAY + " - show this message");
				return;

			}

			if (msg.equalsIgnoreCase("save")) {

				boolean okay = true;

				if (setup.getLobby() == null) {
					okay = false;
				}

				if (setup.getDiamondSpeed() == 0) {
					okay = false;
				}
				
				if (setup.getEmeraldSpeed() == 0) {
					okay = false;
				}
				
				if (setup.getIronSpeed() == 0) {
					okay = false;
				}
				
				if (setup.getGoldSpeed() == 0) {
					okay = false;
				}
				
				if (setup.getPersonalEmeraldSpeed() == 0) {
					okay = false;
				}

				if (!okay) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " You're missing something. Type 'checklist' to find out what.");
					return;

				}
				
				String name = setup.getName();

				ArenaLoader.saveArena(WedBars.getInstance(), name, setup);
				
				settingUp = null;
				setup = null;
				teamSetup = null;

				player.sendMessage(ChatColor.YELLOW + "Success!" + ChatColor.WHITE + " The arena data for " + name + " was saved and you have exited setup mode."
						+ " Load the arena data into the game whenever with /load.");
				return;

			}

			if (msg.equalsIgnoreCase("cancel")) {

				settingUp = null;
				setup = null;
				teamSetup = null;

				player.sendMessage(ChatColor.RED + "Canceled." + ChatColor.WHITE + " Okay bye lol.");
				return;

			}

			if (msg.startsWith("team")) {

				if (!msg.contains(" ")) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure to provide a valid team color.");
					return;

				}

				String[] split = msg.split(" ");

				Team team = Team.getByLabel(split[1]);

				if (team == null) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure to provide a valid team color.");
					return;

				}

				teamSetup = new ArenaTeamData(team);
				player.sendMessage(ChatColor.YELLOW + "Team setup started." + ChatColor.WHITE + " You're now setting up the " + team.getLabel() + " team for this arena."
						+ " Type 'help' for a guide, or 'checklist' for stuff you've done / need to do.");
				return;


			}

			if (msg.equalsIgnoreCase("lobby")) {

				setup.setLobby(player.getLocation().clone());
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The arena lobby has been set to your current location.");
				return;

			}

			if (msg.equalsIgnoreCase("diamondgen")) {

				setup.addDiamondGen(player.getLocation().clone());
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " Your current location was added as a diamond generator.");
				return;

			}

			if (msg.equalsIgnoreCase("emeraldgen")) {

				setup.addEmeraldGen(player.getLocation().clone());
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " Your current location was added as an emerald generator.");
				return;

			}
			
			if (msg.equalsIgnoreCase("cleargens")) {
				
				setup.clearDiamondGen();
				setup.clearEmeraldGen();
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " Removed all of the diamond and emerald generators.");
				return;
				
			}

			if (msg.startsWith("diamondspeed")) {

				int number = getNumberArgument(msg);

				if (number == -1) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure you provide an integer greater than 0.");
					return;

				}

				setup.setDiamondSpeed(number);
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The diamond generator initial speed has been set to " + number + ".");
				return;

			}

			if (msg.startsWith("emeraldspeed")) {

				int number = getNumberArgument(msg);

				if (number == -1) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure you provide an integer greater than 0.");
					return;

				}

				setup.setEmeraldSpeed(number);
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The emerald generator initial speed has been set to " + number + ".");
				return;

			}

			if (msg.startsWith("ironspeed")) {

				int number = getNumberArgument(msg);

				if (number == -1) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure you provide an integer greater than 0.");
					return;

				}

				setup.setIronSpeed(number);
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The iron generator initial speed has been set to " + number + ".");
				return;

			}

			if (msg.startsWith("goldspeed")) {

				int number = getNumberArgument(msg);

				if (number == -1) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure you provide an integer greater than 0.");
					return;

				}

				setup.setGoldSpeed(number);
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The gold generator initial speed has been set to " + number + ".");
				return;

			}

			if (msg.startsWith("personalemeraldspeed")) {

				int number = getNumberArgument(msg);

				if (number == -1) {

					player.sendMessage(ChatColor.RED + "Fail!" + ChatColor.WHITE + " Make sure you provide an integer greater than 0.");
					return;

				}

				setup.setPersonalEmeraldSpeed(number);
				player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.WHITE + " The island emerald generator initial speed has been set to " + number + ".");
				return;

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

				player.sendMessage(ChatColor.WHITE + "Arena Setup: " + lobby + ", " + ds + ", " + es + ", " + is + ", " + gs + ", " + pes);

				player.sendMessage(ChatColor.WHITE + "Generator Setup: "
						+ ChatColor.AQUA + setup.getDiamondGen().size() + " diamond gen" + ChatColor.GRAY + ", "
						+ ChatColor.GREEN + setup.getEmeraldGen().size() + " emerald gen");
				
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

				return;

			}

			player.sendMessage(ChatColor.RED + "Unknown command." + ChatColor.WHITE + " Type 'help' for help.");
			return;


		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		if (settingUp == null) return;
		
		if (settingUp.getName().equals(e.getPlayer().getName())) {

			settingUp = null;
			setup = null;
			teamSetup = null;

		}

	}

	public int getNumberArgument(String msg) {

		if (!msg.contains(" ")) {

			return -1;

		}

		String[] split = msg.split(" ");

		try {

			int i = Integer.parseInt(split[1]);

			if (i < 1) return -1;

			return i;

		} catch (NumberFormatException e) {

			return -1;

		}

	}

}
