package com.blockhead7360.mc.wedbars.team.traps;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrapCounterOffensive extends TeamTrap {

    public void activate(Gamer gamer, ArenaTeam team) {
        gamer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
        gamer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
    }

    public ItemStack icon() {
        return Utility.createIconItemStack(Material.FEATHER, 1, ChatColor.YELLOW + "Counter-Offensive Trap");
    }
}
