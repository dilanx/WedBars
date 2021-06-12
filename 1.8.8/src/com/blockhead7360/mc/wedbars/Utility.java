package com.blockhead7360.mc.wedbars;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockIterator;

public class Utility {

	public static ItemStack createIconItemStack(Material mat, int amount, String displayName, String... lore) {

		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		if (lore != null) meta.setLore(Arrays.asList(lore));
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);
		return stack;

	}

	public static ItemStack createIconItemStack(Material mat, int amount, short data, String displayName, String... lore) {

		ItemStack stack = new ItemStack(mat, amount, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		if (lore != null) meta.setLore(Arrays.asList(lore));
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);
		return stack;

	}

	public static ItemStack createEnchantedItemStack(Material mat, int amount, String displayName, EnchantmentSet[] enchants, String... lore) {

		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		if (lore != null) meta.setLore(Arrays.asList(lore));
		for (EnchantmentSet e : enchants) {
			meta.addEnchant(e.getEnchantment(), e.getLevel(), true);
		}
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);
		return stack;

	}
	
	public static ItemStack createHiddenEnchantedItemStack(Material mat, int amount, String displayName, EnchantmentSet[] enchants, String... lore) {

		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		if (lore != null) meta.setLore(Arrays.asList(lore));
		for (EnchantmentSet e : enchants) {
			meta.addEnchant(e.getEnchantment(), e.getLevel(), true);
		}
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
		return stack;

	}
	
	public static ItemStack createPotionItemStack(String displayName, PotionEffect pe, String... lore) {
		
		ItemStack stack = new ItemStack(Material.POTION, 1);
		PotionMeta meta = (PotionMeta) stack.getItemMeta();
		meta.setDisplayName(displayName);
		if (lore != null) meta.setLore(Arrays.asList(lore));
		meta.addCustomEffect(pe, true);
		meta.setMainEffect(pe.getType());
		stack.setItemMeta(meta);
		
		return stack;
		
	}

	public static class EnchantmentSet {

		private Enchantment enchantment;
		private int level;

		public EnchantmentSet(Enchantment enchantment, int level) {
			this.enchantment = enchantment;
			this.level = level;
		}

		public Enchantment getEnchantment() {
			return enchantment;
		}

		public int getLevel() {
			return level;
		}

	}

	public static ItemStack createLeatherArmorPiece(Material mat, Color color, String displayName) {

		ItemStack stack = new ItemStack(mat, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setColor(color);
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);

		return stack;

	}

	public static ItemStack createUnbreakableItemStack(Material mat, int amount, String displayName) {

		ItemStack stack = new ItemStack(mat, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);

		return stack;

	}

	public static ItemStack createUnbreakableItemStack(Material mat, int amount, short data, String displayName) {

		ItemStack stack = new ItemStack(mat, amount, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.spigot().setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(meta);

		return stack;

	}
	
	// i found this online LMAO
	public static Block getTargetBlock(Player player) {
		
		BlockIterator iter = new BlockIterator(player, 5);
		
        Block lastBlock = iter.next();
        
        while (iter.hasNext()) {
        	
            lastBlock = iter.next();
            
            if (lastBlock.getType() == Material.AIR) {
            	
                continue;
                
            }
            
            break;
            
        }
        
        return lastBlock;
		
	}

	public static String timeFormat(int gamerTicks) {

		int totalSeconds = gamerTicks / 10;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;

		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);

	}

}
