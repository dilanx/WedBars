package com.blockhead7360.mc.wedbars.game;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.Team;

public class Powerups implements Listener {

	public static void launchFireball(Player player) {
		player.launchProjectile(Fireball.class, player.getLocation().getDirection());
		// f.setVelocity(f.getVelocity().subtract(new Vector(1,0,1)));

	}

	public static void createBridge(Egg egg) {
		Team team = WedBars.arena.getGamer(((Player) egg.getShooter()).getName()).getTeam();
		short stackColor = team.getStackColor();
		World w = egg.getWorld();

		new BukkitRunnable() {

			//Location currentLocation;
			Location previousLocation;
			int time = WedBars.BRIDGE_EGG_TIME;

			@SuppressWarnings("deprecation")
			public void run() {

				//currentLocation = egg.getLocation();

				if (egg.isDead() || !WedBars.running) {
					cancel();
					return;
				}

				if (previousLocation == null) {
					previousLocation = egg.getLocation();
					return;
				}

				int x = previousLocation.getBlockX();
				int y = previousLocation.getBlockY();
				int z = previousLocation.getBlockZ();

				for (int i = 0; i <= 1; i++) {
					for (int j = 0; j <= 1; j++) {
						
						Location l = new Location(w, x + i, y, z + j);
						Block b = l.getBlock();
						
						if (b.getType() == Material.AIR) {

							b.setType(Material.WOOL);
							b.setData((byte) stackColor);

//							Wool w = (Wool) b.getState().getData();
//							w.setColor(blockColor);
//							b.getState().setData(w);
							
							WedBars.getListeners().getPlacedBlocks().add(l);

						}

					}
				}



				previousLocation = egg.getLocation();
				
				if (time <= 0) {
					
					cancel();
					return;
					
				}
				
				time--;

			}
		}.runTaskTimer(WedBars.getInstance(), 5L, 2L);

	}


	public static void spawnGolem(Gamer gamer) {

		Team team = gamer.getTeam();
		Player player = gamer.getPlayer();

		IronGolem golem = (IronGolem) player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
		golem.setTarget(null);
		golem.setMaxHealth(WedBars.GOLEM_HEALTH);


		new BukkitRunnable() {

			int time = WedBars.GOLEM_LIFE;
			int dist = WedBars.GOLEM_ATTACK_DISTANCE;

			public void run() {

				if (golem.isDead() || !WedBars.running) {
					cancel();
					return;
				}

				golem.setCustomName(team.getChatColor() + "" + time + "s | " + golem.getHealth() + " HP");

				boolean found = false;

				for (Entity e : golem.getNearbyEntities(dist, dist, dist)) {

					if (e instanceof Player) {

						Player p = (Player) e;
						Gamer g = WedBars.arena.getGamer(p.getName());

						if (g != null && g.getTeam() != team) {

							golem.setTarget((LivingEntity) p);
							found = true;
							break;

						}

					}

				}

				if (!found) golem.setTarget(null);

				if (time <= 0) {

					golem.remove();
					cancel();

				}

				time--;

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 20L);

	}

	public static void spawnSilverfish(Gamer gamer, Location spawnLoc) {

		Team team = gamer.getTeam();
		Player player = gamer.getPlayer();

		Silverfish bug = (Silverfish) player.getWorld().spawnEntity(spawnLoc, EntityType.SILVERFISH);
		bug.setTarget(null);
		bug.setMaxHealth(WedBars.BUG_HEALTH);


		new BukkitRunnable() {

			int time = WedBars.BUG_LIFE;
			int dist = WedBars.BUG_ATTACK_DISTANCE;

			public void run() {

				if (bug.isDead() || !WedBars.running) {
					cancel();
					return;
				}

				bug.setCustomName(team.getChatColor() + "" + time + "s | " + bug.getHealth() + " HP");

				boolean found = false;

				for (Entity e : bug.getNearbyEntities(dist, dist, dist)) {

					if (e instanceof Player) {

						Player p = (Player) e;
						Gamer g = WedBars.arena.getGamer(p.getName());

						if (g != null && g.getTeam() != team) {

							bug.setTarget((LivingEntity) p);
							found = true;
							break;

						}

					}

				}

				if (!found) bug.setTarget(null);

				if (time <= 0) {

					bug.remove();
					cancel();

				}

				time--;

			}

		}.runTaskTimer(WedBars.getInstance(), 0, 20L);
	}

	//TODO: 2 event handlers?
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

		if (WedBars.running) {

			if (e.getDamager() instanceof Player && (e.getEntity() instanceof IronGolem || e.getEntity() instanceof Silverfish)) {

				Team team = WedBars.arena.getGamer(((Player) e.getDamager()).getName()).getTeam();

				if (e.getEntity().getCustomName().startsWith(team.getChatColor() + "")) {

					e.setCancelled(true);

				}

			}

		}

	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent e) {

		if (WedBars.running) {

			if (e.getEntity() instanceof Silverfish && e.getTarget() instanceof Player) {

				Gamer gamer = WedBars.arena.getGamer(e.getTarget().getName());

				if (gamer != null && e.getEntity().getCustomName().startsWith(gamer.getTeam().getChatColor() + "")) {

					e.setCancelled(true);

				}

			}

		}

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {


		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			ItemStack hand = e.getPlayer().getItemInHand();

			if (hand != null) {

				if (hand.getType() == Material.FIREBALL) {

					e.setCancelled(true);

					launchFireball(e.getPlayer());

					if (hand.getAmount() > 1) hand.setAmount(hand.getAmount() - 1);
					else e.getPlayer().setItemInHand(null);

				}

				if (WedBars.running) {

					if (hand.getType() == Material.MONSTER_EGG) {

						e.setCancelled(true);

						spawnGolem(WedBars.arena.getGamer(e.getPlayer().getName()));
						if (hand.getAmount() > 1) hand.setAmount(hand.getAmount() - 1);
						else e.getPlayer().setItemInHand(null);

					}


				}

			}

		}

	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {

		if (e.getItem().getType() == Material.POTION) {

			PotionMeta meta = (PotionMeta) e.getItem().getItemMeta();
			Player player = e.getPlayer();

			if (meta.getDisplayName().toLowerCase().contains("invisibility")) {
				Gamer gamer = WedBars.arena.getGamer(player.getName());
				gamer.setInvisArmor(player.getInventory().getArmorContents().clone());
				player.getInventory().setArmorContents(null);
			}

		}

		if (e.getItem().getType() == Material.MILK_BUCKET) {

			Player player = e.getPlayer();

			e.setCancelled(true);

			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1, true, false));

			player.setItemInHand(null);

		}

	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {

		if (e.getEntity() instanceof Fireball) {
			e.setCancelled(true);
			TNTPrimed t = (TNTPrimed) e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.PRIMED_TNT);
			t.setFuseTicks(0);
			return;
		}

		if (e.getEntity() instanceof TNTPrimed) {

			e.setCancelled(true);

			e.getLocation().getWorld().playEffect(e.getLocation(), Effect.EXPLOSION_LARGE, 1);

			List<Block> blocks = e.blockList();
			//TODO: fix behavior with glass and bed
			for (Block b : blocks) {

				List<Location> locs = WedBars.getListeners().getPlacedBlocks();

				for (int i = 0; i < locs.size(); i++) {

					if (b.getLocation().equals(locs.get(i))) {

						b.setType(Material.AIR);
						WedBars.getListeners().removeBlock(i);

					}

				}

			}

		}

	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
			Location l = e.getTo();
			l.getWorld().playSound(l, Sound.ENDERMAN_TELEPORT, 2, 1);
			l.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 3);
		}
	}



	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (!WedBars.running) return;
		if (e.getEntityType() == EntityType.SNOWBALL && e.getEntity().getShooter() instanceof Player) {
			spawnSilverfish(WedBars.arena.getGamer(((Player) e.getEntity().getShooter()).getName()), e.getEntity().getLocation());
		}

	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (!WedBars.running) return;
		if (e.getEntityType() == EntityType.EGG) {
			createBridge((Egg) e.getEntity());
		}
	}

}
