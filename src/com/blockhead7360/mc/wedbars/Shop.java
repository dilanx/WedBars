package com.blockhead7360.mc.wedbars;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.blockhead7360.mc.wedbars.Utility.EnchantmentSet;

public class Shop implements Listener {

	private static ItemStack[] categories;
	private static ItemStack selected, unselected;

	public static void init() {

		categories = new ItemStack[] {

				Utility.createIconItemStack(Material.NETHER_STAR, 1, ChatColor.GREEN + "Quick Buy"),
				Utility.createIconItemStack(Material.HARD_CLAY, 1, ChatColor.GREEN + "Blocks"),
				Utility.createIconItemStack(Material.GOLD_SWORD, 1, ChatColor.GREEN + "Melee"),
				Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.GREEN + "Armor"),
				Utility.createIconItemStack(Material.STONE_PICKAXE, 1, ChatColor.GREEN + "Tools"),
				Utility.createIconItemStack(Material.BOW, 1, ChatColor.GREEN + "Range"),
				Utility.createIconItemStack(Material.BREWING_STAND_ITEM, 1, ChatColor.GREEN + "Potions"),
				Utility.createIconItemStack(Material.TNT, 1, ChatColor.GREEN + "Utility")

		};

		selected = Utility.createIconItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14, ChatColor.GRAY + "Selected");
		unselected = Utility.createIconItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15, " ");




	}

	public static void openItemShop(Player player) {

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Item Store");

		for (int i = 0; i < categories.length; i++) {

			inv.setItem(i, categories[i]);

		}
		
		itemShop_inv(player, inv, ShopPage.QUICK_BUY);
		
		


	}

	private static void itemShop_inv(Player player, Inventory inv, int page) {

		for (int i = 9; i < 54; i++) {

			inv.setItem(i, null);

		}

		for (int i = 9; i < 18; i++) {

			if (page + 9 == i) inv.setItem(i, selected);
			else inv.setItem(i, unselected);

		}

		switch (page) {

		case ShopPage.QUICK_BUY:

			ItemStack blankSpot = Utility.createIconItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7, " ");

			ItemStack[] qb = {

					Utility.createIconItemStack(Material.WOOL, 16, ChatColor.YELLOW + "Wool", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.STONE_SWORD, 1, ChatColor.YELLOW + "Stone Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron"),
					Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.YELLOW + "Permanent Chainmail Armor (buy this if ur loser)", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					blankSpot,
					Utility.createIconItemStack(Material.BOW, 1, ChatColor.YELLOW + "Bow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					Utility.createIconItemStack(Material.POTION, 1, ChatColor.GRAY + "Potion coming soon"),
					Utility.createIconItemStack(Material.TNT, 1, ChatColor.YELLOW + "TNT", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.WOOD, 16, ChatColor.YELLOW + "Wood", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Iron Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "7 gold"),
					Utility.createIconItemStack(Material.IRON_BOOTS, 1, ChatColor.YELLOW + "Permanent Iron Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Permanent Shears", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "20 iron"),
					Utility.createIconItemStack(Material.ARROW, 8, ChatColor.YELLOW + "Arrow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "2 gold"),
					Utility.createIconItemStack(Material.POTION, 1, ChatColor.GRAY + "Potion coming soon"),
					Utility.createIconItemStack(Material.WATER_BUCKET, 1, ChatColor.GRAY + "Water Bucket coming soon"),
					blankSpot, blankSpot, blankSpot, blankSpot, blankSpot, blankSpot, blankSpot


			};

			for (int i = 0; i < 7; i++) {

				inv.setItem(19 + i, qb[i]);

			}

			for (int i = 7; i < 14; i++) {

				inv.setItem(21 + i, qb[i]);

			}

			for (int i = 14; i < 21; i++) {

				inv.setItem(23 + i, qb[i]);

			}

			break;

		case ShopPage.BLOCKS:

			ItemStack[] b = {

					Utility.createIconItemStack(Material.WOOL, 16, ChatColor.YELLOW + "Wool", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.HARD_CLAY, 16, ChatColor.YELLOW + "Hardened Clay", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "12 iron"),
					Utility.createIconItemStack(Material.GLASS, 4, ChatColor.YELLOW + "Glass", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "12 iron"),
					Utility.createIconItemStack(Material.ENDER_STONE, 12, ChatColor.YELLOW + "End Stone", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "24 iron"),
					Utility.createIconItemStack(Material.LADDER, 16, ChatColor.YELLOW + "Ladder", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.WOOD, 16, ChatColor.YELLOW + "Wood", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.OBSIDIAN, 16, ChatColor.YELLOW + "Obby :)", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald")

			};

			for (int i = 0; i < 7; i++) {

				inv.setItem(19 + i, b[i]);

			}

			break;

		case ShopPage.MELEE:

			ItemStack[] m = {

					Utility.createIconItemStack(Material.STONE_SWORD, 1, ChatColor.YELLOW + "Stone Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron"),
					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Iron Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "7 gold"),
					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Diamond Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald"),
					Utility.createIconItemStack(Material.STICK, 1, ChatColor.GRAY + "Knockback Stick coming soon")

			};

			for (int i = 0; i < 4; i++) {

				inv.setItem(19 + i, m[i]);

			}

			break;

		case ShopPage.ARMOR:

			ItemStack[] a = {

					Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.YELLOW + "Permanent Chainmail Armor (buy this if ur loser)", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.IRON_BOOTS, 1, ChatColor.YELLOW + "Permanent Iron Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					Utility.createIconItemStack(Material.IRON_BOOTS, 1, ChatColor.YELLOW + "Permanent Diamond Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "6 emerald")
			};

			for (int i = 0; i < 3; i++) {

				inv.setItem(19 + i, a[i]);

			}

			break;

		case ShopPage.TOOLS:


			ItemStack nextPick = null;

			if (player.getInventory().contains(Material.WOOD_PICKAXE)) {

				nextPick = Utility.createEnchantedItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Iron Pickaxe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 2)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

			} else if (player.getInventory().contains(Material.IRON_PICKAXE)) {

				nextPick = Utility.createEnchantedItemStack(Material.GOLD_PICKAXE, 1, ChatColor.YELLOW + "Gold Pickaxe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 3),
								new EnchantmentSet(Enchantment.DAMAGE_ALL, 2)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold");

			} else if (player.getInventory().contains(Material.GOLD_PICKAXE) || player.getInventory().contains(Material.DIAMOND_PICKAXE)) {

				nextPick = Utility.createEnchantedItemStack(Material.DIAMOND_PICKAXE, 1, ChatColor.YELLOW + "Diamond Pickaxe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.GOLD + "6 iron");

			} else {

				nextPick = Utility.createEnchantedItemStack(Material.WOOD_PICKAXE, 1, ChatColor.YELLOW + "Wooden Pickaxe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

			}

			ItemStack nextAxe = null;

			if (player.getInventory().contains(Material.WOOD_AXE)) {

				nextAxe = Utility.createEnchantedItemStack(Material.STONE_PICKAXE, 1, ChatColor.YELLOW + "Stone Axe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

			} else if (player.getInventory().contains(Material.STONE_AXE)) {

				nextAxe = Utility.createEnchantedItemStack(Material.IRON_AXE, 1, ChatColor.YELLOW + "Iron Axe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold");

			} else if (player.getInventory().contains(Material.IRON_AXE) || player.getInventory().contains(Material.DIAMOND_AXE)) {

				nextAxe = Utility.createEnchantedItemStack(Material.DIAMOND_PICKAXE, 1, ChatColor.YELLOW + "Diamond Axe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.GOLD + "6 iron");

			} else {

				nextAxe = Utility.createEnchantedItemStack(Material.WOOD_AXE, 1, ChatColor.YELLOW + "Wooden Axe",
						new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
						ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

			}




			ItemStack[] t = {

					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Permanent Shears", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "20 iron"),
					nextPick,
					nextAxe

			};

			for (int i = 0; i < 3; i++) {

				inv.setItem(19 + i, t[i]);

			}

			break;

		case ShopPage.RANGED:

			ItemStack[] r = {

					Utility.createIconItemStack(Material.ARROW, 8, ChatColor.YELLOW + "Arrow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "2 gold"),
					Utility.createIconItemStack(Material.BOW, 1, ChatColor.YELLOW + "Bow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					Utility.createEnchantedItemStack(Material.BOW, 1, ChatColor.YELLOW + "Fancy Bow",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.ARROW_DAMAGE, 1)}, "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "24 gold"),
					Utility.createEnchantedItemStack(Material.BOW, 1, ChatColor.YELLOW + "Super Fancy Bow",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.ARROW_DAMAGE, 1),
									new EnchantmentSet(Enchantment.ARROW_KNOCKBACK, 1)}, "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "6 emerald")

			};

			for (int i = 0; i < 4; i++) {

				inv.setItem(19 + i, r[i]);

			}

			break;

		case ShopPage.POTIONS:

			ItemStack[] p = {

					Utility.createIconItemStack(Material.POTION, 1, ChatColor.GRAY + "Potion coming soon"),
					Utility.createIconItemStack(Material.POTION, 1, ChatColor.GRAY + "Potion coming soon"),
					Utility.createIconItemStack(Material.POTION, 1, ChatColor.GRAY + "Potion coming soon"),

			};

			for (int i = 0; i < 3; i++) {

				inv.setItem(19 + i, p[i]);

			}

			break;
			
		case ShopPage.UTILITY:
			
			ItemStack[] u = {
					
					Utility.createIconItemStack(Material.GOLDEN_APPLE, 1, ChatColor.YELLOW + "Golden Apple", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold"),
					Utility.createIconItemStack(Material.SNOW_BALL, 1, ChatColor.GRAY + "Silverfish coming soon"),
					Utility.createIconItemStack(Material.MONSTER_EGG, 1, ChatColor.GRAY + "Iron golem coming soon"),
					Utility.createIconItemStack(Material.FIREBALL, 1, ChatColor.YELLOW + "Fireball", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.TNT, 1, ChatColor.YELLOW + "TNT", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.ENDER_PEARL, 1, ChatColor.YELLOW + "Ender Pearl", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald"),
					Utility.createIconItemStack(Material.EGG, 1, ChatColor.GRAY + "Bridge Egg coming soon"),
					Utility.createIconItemStack(Material.MILK_BUCKET, 1, ChatColor.GRAY + "Magic Milk coming soon"),
					Utility.createIconItemStack(Material.SPONGE, 4, ChatColor.GRAY + "Sponge coming soon"),
					Utility.createIconItemStack(Material.MILK_BUCKET, 1, ChatColor.GRAY + "Popup Tower coming soon")
					
			};
			
			for (int i = 0; i < 7; i++) {
				
				inv.setItem(19 + i, u[i]);
				
			}
			
			for (int i = 7; i < 11; i++) {
				
				inv.setItem(21 + i, u[i]);
				
			}
			
			break;

		}

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if (e.getView().getTitle().equals(ChatColor.BOLD + "Item Store")) {
			
			e.setCancelled(true);
			
			if (e.getCurrentItem() == null) return;
			
			Player player = (Player) e.getWhoClicked();
			
			int slot = e.getRawSlot();
			
			if (slot < 8) {
				
				itemShop_inv(player, e.getInventory(), slot);
				return;
				
			}
			
			if (slot > 18) {
				
				ItemStack stack = e.getCurrentItem();
				
				if (stack.getItemMeta().hasLore()) {
					
					List<String> lore = stack.getItemMeta().getLore();
					
					if (lore.size() > 0) {
						
						String[] price = ChatColor.stripColor(lore.get(1)).split(" ");
						
						
						
						int number = Integer.parseInt(price[1]);
						
						Material type = txt2mat(price[2]);
						
						boolean success = purchaseItem(player, stack, number, type);
						
						if (success) {
							
							for (int i = 0; i < 8; i++) {
								
						//if (e.getInventory().getItem(i + 9).getData())
								
							}
							
							//itemShop_inv(player, e.getInventory(), );
							
						}
						
						
						return;
						
					}
					
				}
				
			}
			
		}

	}
	
	public boolean purchaseItem(Player player, ItemStack stack, int cost, Material type) {
		
		if (player.getInventory().firstEmpty() == -1) {
			
			player.sendMessage(ChatColor.RED + "Your inventory is full!");
			return false;
			
		}
		
		boolean has = player.getInventory().contains(type, cost);
		
		if (!has) {
			
			player.sendMessage(ChatColor.RED + "Not enough resources!");
			return false;
			
		}
		
		
		// TODO check for types of item
		
		ItemStack item = stack.clone();
		ItemMeta meta = item.getItemMeta();
		meta.setLore(null);
		item.setItemMeta(meta);
		
		
		player.getInventory().addItem(item);
		player.getInventory().remove(new ItemStack(type, cost));
		player.updateInventory();
		
		return true;
		
		
	}
	
	private Material txt2mat(String type) {
		
		if (type.equals("iron")) {
			
			return Material.IRON_INGOT;
			
		} else if (type.equals("gold")) {
			
			return Material.GOLD_INGOT;
			
		} else if (type.equals("diamond")) {
			
			return Material.DIAMOND;
			
		} else if (type.equals("emereald")) {
			
			return Material.EMERALD;
			
		} else {
			
			return null;
			
		}
		
	}

	public class ShopPage {

		public static final int
		QUICK_BUY = 0,
		BLOCKS = 1,
		MELEE = 2,
		ARMOR = 3,
		TOOLS = 4,
		RANGED = 5,
		POTIONS = 6,
		UTILITY = 7;


	}


}
