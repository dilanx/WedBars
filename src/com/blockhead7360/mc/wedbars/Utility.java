package com.blockhead7360.mc.wedbars;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility {
	
	public static ItemStack createItemStack(Material mat, int amount, String displayName) {
		
		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	public static ItemStack createItemStack(Material mat, int amount, short data, String displayName) {
		
		ItemStack stack = new ItemStack(mat, amount, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	
}
