package com.blockhead7360.mc.wedbars.team.traps;

import com.blockhead7360.mc.wedbars.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrapBlindness extends TeamTrap {

    public ItemStack icon() {
        return Utility.createIconItemStack(Material.EYE_OF_ENDER, 1, ChatColor.YELLOW + "Blindness Trap");
    }
    public void activate(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
    }
}
