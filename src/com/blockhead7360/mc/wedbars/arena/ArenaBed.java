package com.blockhead7360.mc.wedbars.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

public class ArenaBed {
	
<<<<<<< Updated upstream
	@SuppressWarnings("deprecation")
	public static void placeBed(Location[] locs) {
		
		BlockState bf = locs[0].getBlock().getState();
		BlockState bh = locs[1].getBlock().getState();
		
		bf.setType(Material.BED_BLOCK);
		bh.setType(Material.BED_BLOCK);
		
		byte[] data = bedDirectionData(locs[0], locs[1]);
		
		bf.setRawData(data[0]);
		bh.setRawData(data[1]);
		bf.update(true, false);
		bh.update(true, true);
=======

	public static void placeBed(Location[] locs, Material bed) {
		
		BlockState b = locs[0].getBlock().getState();
		BlockState bh = locs[1].getBlock().getState();
		
		b.setType(bed);
		bh.setType(bed);
//		bh.setType(Material.BED_BLOCK);
//
//		byte[] data = bedDirectionData(locs[0], locs[1]);
//
//		bf.setRawData(data[0]);
//		bh.setRawData(data[1]);
//		bf.update(true, false);
//		bh.update(true, true);
>>>>>>> Stashed changes
		
	}
	
	// foot, head
	private static byte[] bedDirectionData(Location foot, Location head) {
				
		int dx = head.getBlockX() - foot.getBlockX();
		
		int dz = head.getBlockZ() - foot.getBlockZ();
		
		if (dx == 1)
			return new byte[] {3, 11};
		
		if (dx == -1)
			return new byte[] {1, 9};
		
		if (dz == 1)
			return new byte[] {0, 8};
		if (dz == -1)
			return new byte[] {2, 10};
		
		return new byte[] {0, 0};
		
		
		
	}
	
}
