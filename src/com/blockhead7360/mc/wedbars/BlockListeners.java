package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockListeners implements Listener {

    private List<Location> placedBlocks;

    public BlockListeners() {
        placedBlocks = new ArrayList<>();
    }

    public List<Location> getPlacedBlocks() {
        return placedBlocks;
    }

    @EventHandler
    public void blockPlaced(BlockPlaceEvent event) {
       placedBlocks.add(event.getBlock().getLocation());
       System.out.println(placedBlocks.toString());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        boolean found = false;
        for (int i = 0; i < placedBlocks.size(); i++) {
            if (event.getBlock().getLocation().equals(placedBlocks.get(i))) {
                found = true;
                placedBlocks.remove(i);
                System.out.println(placedBlocks.toString());
                break;
            }
        }
        if (!found) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can only break blocks placed by players!");
        }
    }
    
    public void death(Player player) {
    	
    	//TODO: check if bed is alive, do first, if not do second
        boolean testing = false;
        
    	player.getInventory().clear();

        if (!testing) {
        	
            player.setHealth(20);
            player.setGameMode(GameMode.SPECTATOR);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " times 0 100 20");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " subtitle {\"text\":\"You will respawn in 5 seconds...\",\"color\":\"grey\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
                    " title {\"text\":\"You died!\",\"color\":\"red\"}");
            player.teleport(new Location(Bukkit.getWorld("world"), 0.0,100.0,0.0));
            Bukkit.broadcastMessage(player.getPlayerListName() + " was killed by " + player.getKiller().getName() + ".");

            //TODO: add respawn handlers and time thing
        } else {
        	
            
            player.setHealth(20);
            player.setGameMode(GameMode.SPECTATOR);
            //TODO: more game logic
            player.teleport(new Location(Bukkit.getWorld("world"), 0.0,100.0,0.0));
            Bukkit.broadcastMessage(player.getPlayerListName() + " was killed by " +
            	player.getKiller().getName() + ". " + ChatColor.AQUA + ChatColor.BOLD + "FINAL KILL!");
            
        }
    	
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        
    	event.setDeathMessage(null);
    	death(event.getEntity());
    	
    }

    @EventHandler
    public void playerEntersVoid(PlayerMoveEvent event) {
        if (!(event.getPlayer().getHealth() == 0) && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            if (event.getPlayer().getLocation().getY() <= 0) {
            	
            	
                event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.0,100.0,0.0));
                
                death(event.getPlayer());
                
            }
        }
    }

}
