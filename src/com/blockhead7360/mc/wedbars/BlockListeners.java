package com.blockhead7360.mc.wedbars;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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
        }
    }

}
