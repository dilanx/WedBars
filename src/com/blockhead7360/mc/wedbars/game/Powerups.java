package com.blockhead7360.mc.wedbars.game;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Endermite;
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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.player.Gamer;
import org.bukkit.util.Vector;

public class Powerups implements Listener {

    public static void launchFireball(Player player) {
      Fireball f = player.launchProjectile(Fireball.class, player.getLocation().getDirection());
      f.setVelocity(f.getVelocity().subtract(new Vector(1,0,1)));

    }

    public static void spawnGolem(Gamer gamer) {
        World w = gamer.getPlayer().getWorld();
        IronGolem golem = (IronGolem)w.spawnEntity(gamer.getPlayer().getLocation(), EntityType.IRON_GOLEM);
        golem.setCustomName("" + gamer.getTeam().getLabel() + "'s \"Dream\" Defender");

    }

    public static void spawnSilverfish(ProjectileHitEvent e) {
        Player p = (Player)e.getEntity().getShooter();
        Gamer g = WedBars.arena.getGamer(p.getName());
        World w = e.getEntity().getLocation().getWorld();
        Silverfish s = (Silverfish)w.spawnEntity(e.getEntity().getLocation(), EntityType.SILVERFISH);
        s.setCustomName("" + g.getTeam().getLabel() + "'s Bed Bug");
        for (Entity gm : w.getNearbyEntities(s.getLocation(), 10, 10 ,10)) {
            if (gm instanceof Player && !s.getCustomName().contains(
                    WedBars.arena.getGamer(p.getName()).getTeam().getLabel())) {
                s.setTarget((LivingEntity) e);
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		
    		ItemStack hand = e.getPlayer().getItemInHand();
    		
    		if (hand != null) {
    			
    			if (hand.getType() == Material.FIREBALL) {
    				
    				launchFireball(e.getPlayer());
    				
    				if (hand.getAmount() > 1) hand.setAmount(hand.getAmount() - 1);
    				else e.getPlayer().setItemInHand(null);

                }
    			
    		}
    		
    	}
    	
    }
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {

        if (e.getEntity() instanceof Fireball) {
            e.setCancelled(true);
           TNTPrimed t = (TNTPrimed) e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.PRIMED_TNT);
           t.setFuseTicks(0);
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
    public void golemTargeting(CreatureSpawnEvent event) {
        if (!WedBars.running) return;
        if (event.getEntity() instanceof IronGolem){
            IronGolem golem = (IronGolem)event.getEntity();
            World gw = golem.getWorld();
            for (Entity e : gw.getNearbyEntities(golem.getLocation(), 15,15,15)) {
                if (e instanceof Player && !golem.getCustomName().contains(
                        WedBars.arena.getGamer(e.getName()).getTeam().getLabel())) {
                    golem.setTarget((LivingEntity) e);
                    Endermite trackingMob = (Endermite)gw.spawnEntity(e.getLocation(), EntityType.ENDERMITE);
                  trackingMob.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9, 2, false, false));
                }
            }
        }
    }


    //TODO: Make silverfish target teams, fix execption for casting shooters as players
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!WedBars.running) return;
        if (e.getEntityType() == EntityType.SNOWBALL) {
            spawnSilverfish(e);
        }

    }

}
