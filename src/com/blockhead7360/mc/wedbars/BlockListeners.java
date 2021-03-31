package com.blockhead7360.mc.wedbars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BlockListeners implements Listener {

    private List<Location> placedBlocks;

    public BlockListeners() {
        placedBlocks = new LinkedList<>();
    }

    public List<Location> getPlacedBlocks() {
        return placedBlocks;
    }

    @EventHandler
    public void blockPlaced(BlockPlaceEvent event) {
       placedBlocks.add(event.getBlock().getLocation());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        boolean found = false;
        for (Location l : placedBlocks) {
            if (event.getBlock().getLocation().equals(l)) {
                found = true;
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
        //TODO: check if bed is alive, do first, if not do second
        boolean testing = false;
        if (!testing) {
            event.setDeathMessage(event.getEntity().getPlayerListName() + " was killed by " + event.getEntity().getKiller() + ".");
            event.getEntity().setHealth(20);
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + event.getEntity().getName() +
                    " times 0 100 20");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + event.getEntity().getName() +
                    " subtitle {\"text\":\"You will respawn in 5 seconds...\",\"color\":\"grey\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + event.getEntity().getName() +
                    " title {\"text\":\"You died!\",\"color\":\"red\"}");
            //TODO: add respawn handlers and time thing
        } else if (testing) {
            event.setDeathMessage(event.getEntity().getPlayerListName() + " was killed by " +
                    event.getEntity().getKiller()+ ". " + ChatColor.AQUA + ChatColor.BOLD + "FINAL KILL!");
            event.getEntity().setHealth(20);
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            //TODO: more game logic
        }
    }

    @EventHandler
    public void playerEntersVoid(PlayerMoveEvent event) {
        if (!(event.getPlayer().getHealth() == 0) && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            if (event.getPlayer().getLocation().getY() <= 0) {
                event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.0,100.0,0.0));
                event.getPlayer().setHealth(0);
            }
        }
    }

}
