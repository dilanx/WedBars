package com.blockhead7360.mc.wedbars;

import org.bukkit.World;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class Powerups implements Listener {

    public static void launchFireball(Player player) {
      // World w = player.getWorld();
       //Fireball f = (Fireball)w.spawnEntity(player.getLocation(), EntityType.FIREBALL);
      Fireball f = player.launchProjectile(Fireball.class, player.getLocation().getDirection());

    }

    public static void spawnGolem(Gamer gamer) {
        World w = gamer.getPlayer().getWorld();
        IronGolem golem = (IronGolem)w.spawnEntity(gamer.getPlayer().getLocation(), EntityType.IRON_GOLEM);
        golem.setCustomName("" + gamer.getTeam().getLabel() + "'s \"Dream\" Defender");

    }

    @EventHandler
    public void golemTargeting(EntityTargetEvent event) {
        if (event.getEntity() instanceof IronGolem){
            IronGolem golem = (IronGolem)event.getEntity();
            World gw = golem.getWorld();
            for (Entity e : gw.getNearbyEntities(golem.getLocation(), 15,15,15)) {
                if (e instanceof Player && !golem.getCustomName().contains(
                        WedBars.arena.getGamer(e.getName()).getTeam().getLabel())) {
                    golem.setTarget((LivingEntity) e);
                    Endermite trackingMob = (Endermite)gw.spawnEntity(e.getLocation(), EntityType.ENDERMITE);
                  //  trackingMob.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 9, 2, false, false));
                }
            }
        }
    }
}
