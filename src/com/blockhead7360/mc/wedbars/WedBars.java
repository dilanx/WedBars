package com.blockhead7360.mc.wedbars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class WedBars extends JavaPlugin {

	// gamerTicks = 10 per second
	
	
	public static boolean running = false;
	public static boolean resetting = false;
	public static Arena arena = null;
	
	
	// speeds (interval between spawns in 10*seconds)
	
	public static final int MAX_EMERALDS_IN_GEN = 3;
	public static final int MAX_DIAMONDS_IN_GEN = 3;
	
	public static final int EMERALD1_SPEED = 300;
	public static final int DIAMOND1_SPEED = 150;
	
	public static final int IRON1_SPEED = 14;
	
	
	public static final int GOLD1_SPEED = 60;
	
	public static final int RESPAWN_TIME = 50;
	
	// not gamerTicks, this one is seconds
	public static final int TIME_BETWEEN_END_AND_RESET = 10;
	
	
	private static WedBars instance;
	
	private static Listeners listeners;
		
	public void onEnable() {
		// test commit
		getLogger().info("Github works!");
		
		listeners = new Listeners();
		getServer().getPluginManager().registerEvents(listeners, this);
		getServer().getPluginManager().registerEvents(new Powerups(), this);
		getServer().getPluginManager().registerEvents(new Shop(), this);
		
		Shop.init();
		
		instance = this;
		
	}
	
	public void onDisable() {

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("wedbars")) {
			
			sender.sendMessage(" ");
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Wed Bars " + getDescription().getVersion() + ChatColor.GRAY + " by Dilan and Jack.");
			sender.sendMessage(ChatColor.GRAY + getDescription().getDescription());
			sender.sendMessage(ChatColor.GRAY + getDescription().getWebsite());
			sender.sendMessage(" ");
			
			// LOL
			
			
		}
		
		
		if (cmd.getName().equalsIgnoreCase("test")) {
			
			if (!sender.hasPermission("wedbars.debug")) {
				
				sender.sendMessage("No lol");
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
					
					World world = getServer().getWorld("world");
					
					Generator[] emeralds = {
							new Generator(new Location(world, 12.5, 78, -11.5), EMERALD1_SPEED),
							new Generator(new Location(world, -11.5, 78, -11.5), EMERALD1_SPEED),
							new Generator(new Location(world, -11.5, 78, 12.5), EMERALD1_SPEED),
							new Generator(new Location(world, 12.5, 78, 12.5), EMERALD1_SPEED)
							
					};
					
					Generator[] diamonds = {
							new Generator(new Location(world, 0.5, 64, 52.5), DIAMOND1_SPEED),
							new Generator(new Location(world, 52.5, 64, 0.5), DIAMOND1_SPEED),
							new Generator(new Location(world, 0.5, 64, -51.5), DIAMOND1_SPEED),
							new Generator(new Location(world, -51.5, 64, 0.5), DIAMOND1_SPEED)
					};
					
					ArenaTeam gray = new ArenaTeam(Team.GRAY,
							new Location(world, -72, 66, -33),
							new Location(world, -77, 66, -32),
							new Location[]{new Location(world, -65, 66, -33), new Location(world, -64, 66, -33)},
							new Gamer[]{new Gamer(getServer().getPlayer("shark_pog"), Team.GRAY)});
					
					ArenaTeam green = new ArenaTeam(Team.GREEN,
							new Location(world, 72, 66, -33),
							new Location(world, 77, 66, -34),
							new Location[]{new Location(world, 65, 66, -33), new Location(world, 64, 66, -33)},
							new Gamer[]{new Gamer(getServer().getPlayer("ComputerCart"), Team.GREEN)});
					
					ArenaTeam yellow = new ArenaTeam(Team.YELLOW,
							new Location(world, 72, 66, 33),
							new Location(world, 77, 66, 32),
							new Location[]{new Location(world, 65, 66, 33), new Location(world, 64, 66, 33)},
							new Gamer[]{new Gamer(getServer().getPlayer("Faience"), Team.YELLOW)});
					
					ArenaTeam white = new ArenaTeam(Team.WHITE,
							new Location(world, -33, 66, 72),
							new Location(world, -32, 66, 77),
							new Location[]{new Location(world, -33, 66, 65), new Location(world, -33, 66, 64)},
							new Gamer[]{new Gamer(getServer().getPlayer("aromazx"), Team.WHITE)});
					
					Arena arena = new Arena(new ArenaTeam[] {gray, green, yellow, white}, diamonds, emeralds);
					arena.start();
					
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
		
		return true;

	}
	
	public static WedBars getInstance() {
		
		return instance;
		
	}
	
	public static Listeners getListeners() {
		
		return listeners;
		
	}


}
