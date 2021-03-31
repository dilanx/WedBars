package com.blockhead7360.mc.wedbars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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

}
