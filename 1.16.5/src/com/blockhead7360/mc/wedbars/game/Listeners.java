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
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.api.events.GamerBlockBreakEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerBlockPlaceEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerOpenTeamChestEvent;
import com.blockhead7360.mc.wedbars.arena.Arena;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Statistic;
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
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		
		if (!WedBars.running) return;
		
		ItemStack stack = e.getItem().getItemStack();
		
		if (stack.getType() != Material.IRON_INGOT && stack.getType() != Material.GOLD_INGOT) return;
		
		ItemMeta meta = stack.getItemMeta();
		
		
		
		if (!meta.hasDisplayName() || !meta.getDisplayName().endsWith(" *")) return;
		
		String newDisp = meta.getDisplayName().substring(0, meta.getDisplayName().length() - 2);
		if (newDisp.length() == 0) {
			
			stack.setItemMeta(null);
			
			//stack = new ItemStack(stack.getType(), stack.getAmount());
			
		} else {
			
			meta.setDisplayName(newDisp);
			stack.setItemMeta(meta);
			
		}
		
		Player player = e.getPlayer();
		Arena arena = WedBars.arena;
		
		for (ArenaTeam at : arena.getTeams().values()) {
			
			Location genLoc = at.getIronGenerator().getLocation();
			
			if (genLoc.distanceSquared(player.getLocation()) <= 4) {
				
				for (Entity nearby : player.getNearbyEntities(2, 2, 2)) {
					
					if (nearby instanceof Player) {
						
						Player n = (Player) nearby;
						
						if (n.getInventory().firstEmpty() > -1) {
							
							n.getInventory().addItem(stack);
							n.updateInventory();
							
						}
						
					}
					
				}
				
				return;
				
			}
			
		}
		
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		if (!WedBars.running) return;

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getClickedBlock().getType() == Material.CHEST) {

				e.setCancelled(true);

				Player player = e.getPlayer();

				Gamer gamer = WedBars.arena.getGamer(player.getName());
				ArenaTeam team = WedBars.arena.getTeam(gamer.getTeam());
				
				GamerOpenTeamChestEvent gotce = new GamerOpenTeamChestEvent(gamer, team, team.getChest(), e.getClickedBlock().getLocation());
				Bukkit.getPluginManager().callEvent(gotce);
				
				if (gotce.isCancelled()) return;
				
				player.openInventory(team.getChest());
				player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
				
				

			}

		}

	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if (e.getView().getTitle().contains("team chest")) {
			
			Player player = (Player) e.getPlayer();
			
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
			
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

				if (WedBars.arena.getGamer(shooter.getName()).getTeam() == WedBars.arena.getGamer(player.getName()).getTeam()) {

					e.setCancelled(true);
					return;

				}


				DecimalFormat round = new DecimalFormat("##.#");

				if (player.getHealth() - e.getFinalDamage() > 0) {
					shooter.sendMessage(WedBars.arena.getGamer(player.getName()).getTeam().getChatColor() + player.getName()
					+ ChatColor.GRAY + " is now at " + ChatColor.RED + round.format(player.getHealth() - e.getFinalDamage()) + " HP");
				}

			}

			if (e.getDamager() instanceof Player) {
				
				Player damager = (Player) e.getDamager();
				
				if (WedBars.arena.getGamer(damager.getName()).getTeam() == WedBars.arena.getGamer(player.getName()).getTeam()) {

					e.setCancelled(true);
					return;

				}

			}


		}

	}

	@EventHandler
	public void blockPlaced(BlockPlaceEvent e) {

		if (!WedBars.running) return;

		if (e.getBlock().getLocation().getY() > WedBars.arena.getBuildHeight()) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You can't build any higher!");
			return;
		}

		if (e.getBlock().getType() == Material.TNT) {
			e.getBlock().setType(Material.AIR);
			TNTPrimed tp = (TNTPrimed)e.getBlock().getWorld().spawnEntity(e.getBlockPlaced().getLocation(), EntityType.PRIMED_TNT);
			tp.setFuseTicks(WedBars.TNT_FUSE);
			return;
		}

		for (ArenaTeam team : WedBars.arena.getTeams().values()) {

			if (team.getSpawnLoc().distanceSquared(e.getBlock().getLocation()) <= WedBars.SPAWN_PROTECTION_DISTANCE_SQUARED) {

				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "You can't place blocks there!");
				return;

			}

		}
		
		Gamer gamer = WedBars.arena.getGamer(e.getPlayer().getName());
		
		GamerBlockPlaceEvent gbpe = new GamerBlockPlaceEvent(gamer, e);
		Bukkit.getPluginManager().callEvent(gbpe);
		
		if (gbpe.isCancelled()) {
			e.setCancelled(true);
			return;
		}

//		if (e.getBlock().getType() == Material.GLASS) {
//
//			// This is done kinda weird based on my internet findings but
//			// using NMS is probably the easiest way to go about it.
//			Block block = Block.getByName("glass");
//
//			try {
//				Field field = Block.class.getDeclaredField("durability");
//				field.setAccessible(true);
//				field.set(block, 3000f);
//			} catch (NoSuchFieldException | IllegalAccessException exce) {
//				exce.printStackTrace();
//			}
//
//		}

		gamer.addOneToStatistic(Statistic.BPLACED);
		placedBlocks.add(e.getBlock().getLocation());
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {

		if (!WedBars.running) return;

		// TODO switch this to bed when ready

		if (e.getBlock().getType() == Material.RED_BED) {

			e.setCancelled(true);

			Arena arena = WedBars.arena;

			Gamer breaker = arena.getGamer(e.getPlayer().getName());

			ArenaTeam team = arena.whoseBed(breaker, e.getBlock().getLocation());

			if (team == null) {

				e.getPlayer().sendMessage(ChatColor.RED + "Imagine trying to break your own bed (L).");
				return;

			}

			GameActions.bedGone(team, breaker);
			return;

		}

		boolean found = false;
		for (int i = 0; i < placedBlocks.size(); i++) {
			if (e.getBlock().getLocation().equals(placedBlocks.get(i))) {
				found = true;
				placedBlocks.remove(i);
				break;
			}
		}
		if (!found) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You can only break blocks placed by players!");
			return;
		}
		
		Gamer breaker = WedBars.arena.getGamer(e.getPlayer().getName());
		
		GamerBlockBreakEvent gbbe = new GamerBlockBreakEvent(breaker, e);
		Bukkit.getPluginManager().callEvent(gbbe);
		if (gbbe.isCancelled()) {
			e.setCancelled(true);
			return;
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
