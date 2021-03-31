package com.blockhead7360.mc.wedbars;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Utility {
	
	
	// more messages
	
	public static void sendDeathTitle(Player player, int time) {
		
		if (time == -2) {
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" times 0 40 10");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" subtitle {\"text\":\"Respawned!\",\"color\":\"green\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" title {\"text\":\"\"}");
			
			return;
			
		}
		
		if (time == -1) {
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" times 0 100 20");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" subtitle {\"text\":\"permanently\",\"color\":\"gray\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
					" title {\"text\":\"YOU DIED!\",\"color\":\"red\"}");
			
			return;
			
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 30 10");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle [{\"text\":\"respawning in \",\"color\":\"gray\"},{\"text\":\"" + ((time / 10)) + "\",\"color\":\"white\"}]");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"You died!\",\"color\":\"red\"}");
		
		return;
		
	}
	
	public static void sendBedBrokenTitle(Player player) {
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" times 0 100 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" subtitle {\"text\":\"You won't respawn when you die.\",\"color\":\"gray\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() +
				" title {\"text\":\"BED DESTROYED!\",\"color\":\"red\"}");
		
	}
	
	public static ItemStack createLeatherArmorPiece(Material mat, Color color, String displayName) {
		
		ItemStack stack = new ItemStack(mat, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setColor(color);
		meta.spigot().setUnbreakable(true);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	public static ItemStack createItemStack(Material mat, int amount, String displayName) {
		
		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.spigot().setUnbreakable(true);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	public static ItemStack createItemStack(Material mat, int amount, short data, String displayName) {
		
		ItemStack stack = new ItemStack(mat, amount, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.spigot().setUnbreakable(true);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	public static String timeFormat(int gamerTicks) {
		
		int totalSeconds = gamerTicks / 10;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		
		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
		
	}
	
	
}
