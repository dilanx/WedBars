package com.blockhead7360.mc.wedbars.team.traps;

import org.bukkit.inventory.ItemStack;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;

public abstract class TeamTrap {
	
	public abstract void activate(Gamer gamer, ArenaTeam team);
	
	public abstract ItemStack icon();
	
}