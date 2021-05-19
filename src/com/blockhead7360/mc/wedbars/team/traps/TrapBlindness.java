package com.blockhead7360.mc.wedbars.team.traps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;

public class TrapBlindness extends TeamTrap {

    public ItemStack icon() {
        return Utility.createIconItemStack(Material.EYE_OF_ENDER, 1, ChatColor.YELLOW + "Blindness Trap");
    }
    public void activate(Gamer gamer, ArenaTeam team) {
        gamer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
    }
    
}
