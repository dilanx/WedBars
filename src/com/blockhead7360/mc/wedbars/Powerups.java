package com.blockhead7360.mc.wedbars;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Powerups implements Listener {

    public static void launchFireball(Player player) {
      Fireball f = player.launchProjectile(Fireball.class, player.getLocation().getDirection());
      //f.setVelocity(f.getVelocity().subtract(new Vector(3,3,3)));

    }

    public static void spawnGolem(Gamer gamer) {
        World w = gamer.getPlayer().getWorld();
        IronGolem golem = (IronGolem)w.spawnEntity(gamer.getPlayer().getLocation(), EntityType.IRON_GOLEM);
        golem.setCustomName("" + gamer.getTeam().getLabel() + "'s \"Dream\" Defender");

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
    	
    	if (e.getEntity() instanceof Fireball || e.getEntity() instanceof TNTPrimed) {
    		
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
}
