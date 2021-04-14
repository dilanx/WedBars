package com.blockhead7360.mc.wedbars.game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.arena.Arena;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;

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

				e.setCancelled(true);
				
				Player player = e.getPlayer();
				
				ArenaTeam team = WedBars.arena.getTeam(WedBars.arena.getGamer(player.getName()).getTeam());
				player.openInventory(team.getChest());
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);

			}

		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {

		e.getPlayer().setGameMode(GameMode.ADVENTURE);

	}

	@EventHandler
	public void entityDamageEntity(EntityDamageByEntityEvent e) {

		if (WedBars.running && e.getEntity() instanceof Player) {
			
			Player player = (Player) e.getEntity();
			
			if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				
				Gamer gamer = WedBars.arena.getGamer(player.getName());
				
				if (gamer.hasInvisArmor()) {
					player.getInventory().setArmorContents(gamer.getInvisArmor());
					gamer.removeInvisArmor();
				}
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				player.sendMessage(ChatColor.RED + "You were hit while invisible and lost your invisibility effect!");
				
			}
			
			if (e.getDamager() instanceof Arrow) {

				Player shooter = (Player) ((Arrow) e.getDamager()).getShooter();
				

				DecimalFormat round = new DecimalFormat("##.#");

				if (player.getHealth() - e.getFinalDamage() > 0) {
					shooter.sendMessage(WedBars.arena.getGamer(player.getName()).getTeam().getChatColor() + player.getName()
					+ ChatColor.GRAY + " is now at " + ChatColor.RED + round.format(player.getHealth() - e.getFinalDamage()) + " HP");
				}

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

			GameActions.bedGone(team, breaker);
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

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {

		if (!WedBars.running) return;

		event.setDeathMessage(null);
		GameActions.death(event.getEntity(), false);

	}

	@EventHandler
	public void playerEntersVoid(PlayerMoveEvent event) {

		if (!WedBars.running) return;

		if (event.getPlayer().getLocation().getY() <= WedBars.VOID_LEVEL) {

			if (event.getPlayer().getHealth() != 0 && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {

				GameActions.death(event.getPlayer(), false);

			}


		}


	}

}
