package com.blockhead7360.mc.wedbars.team.traps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.Utility;

public class TrapFatigue extends TeamTrap {

	public ItemStack icon() {
		
		return Utility.createIconItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Miner Fatigue Trap");
		
	}
	
	
	public void activate(Player player) {
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 160, 2));
		
	}
	
	

}
