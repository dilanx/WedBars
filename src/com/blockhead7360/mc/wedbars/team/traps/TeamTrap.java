package com.blockhead7360.mc.wedbars.team.traps;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class TeamTrap {
	
	public abstract void activate(Player player);
	
	public abstract ItemStack icon();
	
}
