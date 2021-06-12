package com.blockhead7360.mc.wedbars.team.traps;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.game.Powerups;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TrapInfestation extends TeamTrap {
    public void activate(Gamer gamer, ArenaTeam team) {
        Powerups.spawnSilverfish(team, gamer.getPlayer().getLocation());
        Powerups.spawnSilverfish(team, gamer.getPlayer().getLocation());
    }

    public ItemStack icon() {
<<<<<<< Updated upstream
        return Utility.createIconItemStack(Material.MONSTER_EGG, 1, ChatColor.YELLOW + "Infestation Trap");
=======
        return Utility.createIconItemStack(Material.SNOWBALL, 1, ChatColor.YELLOW + "Infestation Trap");
>>>>>>> Stashed changes
    }
}
