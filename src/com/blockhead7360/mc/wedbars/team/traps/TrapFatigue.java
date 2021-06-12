package com.blockhead7360.mc.wedbars.team.traps;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.Utility;

public class TrapFatigue extends TeamTrap {

	public ItemStack icon() {
		
		return Utility.createIconItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Miner Fatigue Trap");
		
	}
	
	
	public void activate(Gamer gamer, ArenaTeam team) {
		
		gamer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 1));
		
	}
	
	

}
