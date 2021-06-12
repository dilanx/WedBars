package com.blockhead7360.mc.wedbars.game;

import java.util.List;

import com.blockhead7360.mc.wedbars.team.traps.TrapCounterOffensive;
import com.blockhead7360.mc.wedbars.team.traps.TrapInfestation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blockhead7360.mc.wedbars.Utility;
import com.blockhead7360.mc.wedbars.Utility.EnchantmentSet;
import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.api.events.GamerPurchaseItemEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerPurchaseTeamUpgradeEvent;
import com.blockhead7360.mc.wedbars.api.events.GamerPurchaseTrapEvent;
import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.team.ArenaTeam;
import com.blockhead7360.mc.wedbars.team.TeamUpgrade;
import com.blockhead7360.mc.wedbars.team.traps.TrapBlindness;
import com.blockhead7360.mc.wedbars.team.traps.TrapFatigue;

public class Shop implements Listener {

	private static ItemStack[] categories;
	private static ItemStack[] traps;
	private static ItemStack selected, unselected;

	public static void init() {

		categories = new ItemStack[] {

				Utility.createIconItemStack(Material.NETHER_STAR, 1, ChatColor.GREEN + "Quick Buy"),
<<<<<<< Updated upstream
				Utility.createIconItemStack(Material.HARD_CLAY, 1, ChatColor.GREEN + "Blocks"),
				Utility.createIconItemStack(Material.GOLD_SWORD, 1, ChatColor.GREEN + "Melee"),
				Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.GREEN + "Armor"),
				Utility.createIconItemStack(Material.STONE_PICKAXE, 1, ChatColor.GREEN + "Tools"),
				Utility.createIconItemStack(Material.BOW, 1, ChatColor.GREEN + "Range"),
				Utility.createIconItemStack(Material.BREWING_STAND_ITEM, 1, ChatColor.GREEN + "Potions"),
=======
				Utility.createIconItemStack(Material.CYAN_CONCRETE, 1, ChatColor.GREEN + "Blocks"),
				Utility.createIconItemStack(Material.GOLDEN_SWORD, 1, ChatColor.GREEN + "Melee"),
				Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.GREEN + "Armor"),
				Utility.createIconItemStack(Material.STONE_PICKAXE, 1, ChatColor.GREEN + "Tools"),
				Utility.createIconItemStack(Material.BOW, 1, ChatColor.GREEN + "Range"),
				Utility.createIconItemStack(Material.BREWING_STAND, 1, ChatColor.GREEN + "Potions"),
>>>>>>> Stashed changes
				Utility.createIconItemStack(Material.TNT, 1, ChatColor.GREEN + "Utility")

		};

		traps = new ItemStack[] {

				Utility.createIconItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Miner Fatigue",
						ChatColor.GRAY + "Gives the enemy that triggers it Mining Fatigue for 10 seconds.",
						ChatColor.GRAY + "Price: " + ChatColor.AQUA + "1 diamond"),
<<<<<<< Updated upstream
				Utility.createIconItemStack(Material.EYE_OF_ENDER, 1, ChatColor.YELLOW + "It's a Trap!",
=======
				Utility.createIconItemStack(Material.ENDER_EYE, 1, ChatColor.YELLOW + "It's a Trap!",
>>>>>>> Stashed changes
						ChatColor.GRAY + "Gives the enemy that triggers it Blindness and Slowness for 8 seconds.",
						ChatColor.GRAY + "Price: " + ChatColor.AQUA + "1 diamond"),
				Utility.createIconItemStack(Material.FEATHER, 1, ChatColor.YELLOW + "Counter-Offensive",
						ChatColor.GRAY + "Gives the defending team Speed I and Jump Boost II for 10 seconds.",
						ChatColor.GRAY + "Price: " + ChatColor.AQUA + "1 diamond"),
<<<<<<< Updated upstream
				Utility.createIconItemStack(Material.MONSTER_EGG, 1, ChatColor.YELLOW + "Infestation",
=======
				Utility.createIconItemStack(Material.SNOWBALL, 1, ChatColor.YELLOW + "Infestation",
>>>>>>> Stashed changes
						ChatColor.GRAY + "Spawns 2 bugs on enemy players that trigger it",
						ChatColor.GRAY + "Price: " + ChatColor.AQUA + "2 diamond"),

		};

<<<<<<< Updated upstream
		selected = Utility.createIconItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14, ChatColor.GRAY + "Selected");
		unselected = Utility.createIconItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15, " ");
=======
		selected = Utility.createIconItemStack(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.GRAY + "Selected");
		unselected = Utility.createIconItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, " ");
>>>>>>> Stashed changes

	}

	public static void openTrapMenu(Player player) {

		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "Trap Store");

		for (ItemStack is : traps) {

			inv.addItem(is);

		}

		player.openInventory(inv);

	}

	public static void openTeamUpgrades(Player player) {

		if (WedBars.arena == null) {
			
			player.sendMessage(ChatColor.RED + "The game needs to be running to use this.");
			return;
		}
		
		if (player.getGameMode() == GameMode.SPECTATOR) return;

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Team Upgrades");

		teamUpgrades_inv(player, inv);

<<<<<<< Updated upstream
		player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
=======
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
>>>>>>> Stashed changes

		player.openInventory(inv);

	}

	public static void teamUpgrades_inv(Player player, Inventory inv) {

		inv.clear();

		ArenaTeam team = WedBars.arena.getTeam(WedBars.arena.getGamer(player.getName()).getTeam());

		EnchantmentSet[] sel = {new EnchantmentSet(Enchantment.DURABILITY, 1)};
		EnchantmentSet[] unsel = new EnchantmentSet[0];
		ItemStack locked = Utility.createIconItemStack(Material.BARRIER, 1, ChatColor.RED + "Locked",
				ChatColor.GRAY + "Purchase the upgrade above to", ChatColor.GRAY + "unlock this one.");

		boolean[] has = team.getUpgrades();		

		// sharpened swords

		inv.setItem(10, Utility.createHiddenEnchantedItemStack(Material.DIAMOND_SWORD, 1, ChatColor.GREEN + "Sharpened Swords",
				(has[TeamUpgrade.SWORDS] ? sel : unsel),
				ChatColor.GRAY + "Receive permanent Sharpness I", ChatColor.GRAY + "on all swords.", "",
				ChatColor.GRAY + "Price: " + ChatColor.AQUA + "4 diamond", (has[TeamUpgrade.SWORDS] ? ChatColor.GREEN + "ACTIVE" : "")));

		// heal pool

		inv.setItem(19, Utility.createHiddenEnchantedItemStack(Material.APPLE, 1, ChatColor.GREEN + "Heal Pool",
				(has[TeamUpgrade.HEAL] ? sel : unsel),
				ChatColor.GRAY + "Get a regeneration effect when you", ChatColor.GRAY + "or your team members are at your base.", "",
				ChatColor.GRAY + "Price: " + ChatColor.AQUA + "1 diamond", (has[TeamUpgrade.HEAL] ? ChatColor.GREEN + "ACTIVE" : "")));

		// armor

		inv.setItem(3, Utility.createHiddenEnchantedItemStack(Material.CHAINMAIL_CHESTPLATE, 1, ChatColor.YELLOW + "Average Armor",
				(has[TeamUpgrade.ARMOR1] ? sel : unsel),
				ChatColor.GRAY + "Receive permanent Protection I", ChatColor.GRAY + "on all armor pieces.", "",
				ChatColor.GRAY + "Price: " + ChatColor.AQUA + "2 diamond", (has[TeamUpgrade.ARMOR1] ? ChatColor.GREEN + "ACTIVE" : "")));

		if (has[TeamUpgrade.ARMOR1])
			inv.setItem(12, Utility.createHiddenEnchantedItemStack(Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Nice Armor",
					(has[TeamUpgrade.ARMOR2] ? sel : unsel),
					ChatColor.GRAY + "Receive permanent Protection II", ChatColor.GRAY + "on all armor pieces.", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "4 diamond", (has[TeamUpgrade.ARMOR2] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(12, locked);

		if (has[TeamUpgrade.ARMOR2])
<<<<<<< Updated upstream
			inv.setItem(21, Utility.createHiddenEnchantedItemStack(Material.GOLD_CHESTPLATE, 1, ChatColor.YELLOW + "Gamer Armor (cool kids only)",
=======
			inv.setItem(21, Utility.createHiddenEnchantedItemStack(Material.GOLDEN_CHESTPLATE, 1, ChatColor.YELLOW + "Gamer Armor (cool kids only)",
>>>>>>> Stashed changes
					(has[TeamUpgrade.ARMOR3] ? sel : unsel),
					ChatColor.GRAY + "Receive permanent Protection III", ChatColor.GRAY + "on all armor pieces.", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "8 diamond", (has[TeamUpgrade.ARMOR3] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(21, locked);

		if (has[TeamUpgrade.ARMOR3])
			inv.setItem(30, Utility.createHiddenEnchantedItemStack(Material.DIAMOND_CHESTPLATE, 1, ChatColor.YELLOW + "THIS ARMOR IS CRACKED AT MC MY GUY",
					(has[TeamUpgrade.ARMOR4] ? sel : unsel),
					ChatColor.GRAY + "Receive permanent Protection IV", ChatColor.GRAY + "on all armor pieces.", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "16 diamond", (has[TeamUpgrade.ARMOR4] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(30, locked);

		// mine

		inv.setItem(14, Utility.createHiddenEnchantedItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Pog Miner 1",
				(has[TeamUpgrade.MINE1] ? sel : unsel),
				ChatColor.GRAY + "Receive permanent Haste I", ChatColor.GRAY + "", "",
				ChatColor.GRAY + "Price: " + ChatColor.AQUA + "2 diamond", (has[TeamUpgrade.MINE1] ? ChatColor.GREEN + "ACTIVE" : "")));

		if (has[TeamUpgrade.MINE1])
<<<<<<< Updated upstream
			inv.setItem(23, Utility.createHiddenEnchantedItemStack(Material.GOLD_PICKAXE, 1, ChatColor.YELLOW + "Pog Miner 2",
=======
			inv.setItem(23, Utility.createHiddenEnchantedItemStack(Material.GOLDEN_PICKAXE, 1, ChatColor.YELLOW + "Pog Miner 2",
>>>>>>> Stashed changes
					(has[TeamUpgrade.MINE2] ? sel : unsel),
					ChatColor.GRAY + "Receive permanent Haste II", ChatColor.GRAY + "", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "4 diamond", (has[TeamUpgrade.MINE2] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(23, locked);

		// forge

		inv.setItem(7, Utility.createHiddenEnchantedItemStack(Material.IRON_INGOT, 1, ChatColor.YELLOW + "Better Gen",
				(has[TeamUpgrade.FORGE1] ? sel : unsel),
				ChatColor.GRAY + "Vroom vroom generator go faster", ChatColor.GRAY + "", "",
				ChatColor.GRAY + "Price: " + ChatColor.AQUA + "2 diamond", (has[TeamUpgrade.FORGE1] ? ChatColor.GREEN + "ACTIVE" : "")));

		if (has[TeamUpgrade.FORGE1])
			inv.setItem(16, Utility.createHiddenEnchantedItemStack(Material.GOLD_INGOT, 1, ChatColor.YELLOW + "Insane Gen",
					(has[TeamUpgrade.FORGE2] ? sel : unsel),
					ChatColor.GRAY + "Vroom vroom generator go even faster", ChatColor.GRAY + "", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "4 diamond", (has[TeamUpgrade.FORGE2] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(16, locked);

		if (has[TeamUpgrade.FORGE2])
			inv.setItem(25, Utility.createHiddenEnchantedItemStack(Material.EMERALD, 1, ChatColor.YELLOW + "Sus Gen",
					(has[TeamUpgrade.FORGE3] ? sel : unsel),
					ChatColor.GRAY + "Vroom vroom generator go EVEN faster", ChatColor.GRAY + "", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "6 diamond", (has[TeamUpgrade.FORGE3] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(25, locked);

		if (has[TeamUpgrade.FORGE3])
			inv.setItem(34, Utility.createHiddenEnchantedItemStack(Material.GOLD_BLOCK, 1, ChatColor.YELLOW + "THIS GEN IS LITERALLY HACKING",
					(has[TeamUpgrade.FORGE4] ? sel : unsel),
					ChatColor.GRAY + "OMG vroom vroom generator go so fast omg", ChatColor.GRAY + "", "",
					ChatColor.GRAY + "Price: " + ChatColor.AQUA + "8 diamond", (has[TeamUpgrade.FORGE4] ? ChatColor.GREEN + "ACTIVE" : "")));
		else
			inv.setItem(34, locked);

		// trap

		if (team.hasTrap()) {

			inv.setItem(49, team.getTrap().icon());

		} else {

<<<<<<< Updated upstream
			inv.setItem(49, Utility.createIconItemStack(Material.STAINED_GLASS, 1, (short) 7, ChatColor.WHITE + "No active trap", "", ChatColor.GRAY + "Click to purchase one."));
=======
			inv.setItem(49, Utility.createIconItemStack(Material.LIGHT_GRAY_STAINED_GLASS, 1, ChatColor.WHITE + "No active trap", "", ChatColor.GRAY + "Click to purchase one."));
>>>>>>> Stashed changes

		}

		return;
	}

	public static void openItemShop(Player player) {
		
		if (player.getGameMode() == GameMode.SPECTATOR) return;

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Item Store");

		for (int i = 0; i < categories.length; i++) {

			inv.setItem(i, categories[i]);

		}

		itemShop_inv(player, inv, ShopPage.QUICK_BUY);

<<<<<<< Updated upstream
		player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
=======
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
>>>>>>> Stashed changes

		player.openInventory(inv);


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

			ItemStack[] qb = {

<<<<<<< Updated upstream
					Utility.createIconItemStack(Material.WOOL, 16, ChatColor.YELLOW + "Wool", "",
=======
					Utility.createIconItemStack(Material.WHITE_WOOL, 16, ChatColor.YELLOW + "Wool", "",
>>>>>>> Stashed changes
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.STONE_SWORD, 1, ChatColor.YELLOW + "Stone Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron"),
					Utility.createIconItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.YELLOW + "Permanent Chainmail Armor (buy this if ur loser)", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					nextPick(player),
					Utility.createIconItemStack(Material.BOW, 1, ChatColor.YELLOW + "Bow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					Utility.createPotionItemStack(ChatColor.YELLOW + "Speed Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.SPEED, 600, 1), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "1 emerald"),
					Utility.createIconItemStack(Material.GOLDEN_APPLE, 1, ChatColor.YELLOW + "Golden Apple", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold"),
<<<<<<< Updated upstream
					Utility.createIconItemStack(Material.WOOD, 16, ChatColor.YELLOW + "Wood", "",
=======
					Utility.createIconItemStack(Material.OAK_PLANKS, 16, ChatColor.YELLOW + "Wood", "",
>>>>>>> Stashed changes
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.IRON_SWORD, 1, ChatColor.YELLOW + "Iron Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "7 gold"),
					Utility.createIconItemStack(Material.IRON_BOOTS, 1, ChatColor.YELLOW + "Permanent Iron Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "12 gold"),
					nextAxe(player),
					Utility.createEnchantedItemStack(Material.BOW, 1, ChatColor.YELLOW + "Fancy Bow",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.ARROW_DAMAGE, 1)}, "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "24 gold"),
					Utility.createPotionItemStack(ChatColor.YELLOW + "Jump Boost Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.JUMP, 600, 4), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "1 emerald"),
<<<<<<< Updated upstream
					Utility.createIconItemStack(Material.FIREBALL, 1, ChatColor.YELLOW + "Fireball", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.ENDER_STONE, 12, ChatColor.YELLOW + "End Stone", "",
=======
					Utility.createIconItemStack(Material.FIRE_CHARGE, 1, ChatColor.YELLOW + "Fireball", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.END_STONE, 12, ChatColor.YELLOW + "End Stone", "",
>>>>>>> Stashed changes
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "24 iron"),
					Utility.createIconItemStack(Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Diamond Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald"),
					Utility.createIconItemStack(Material.DIAMOND_BOOTS, 1, ChatColor.YELLOW + "Permanent Diamond Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "6 emerald"),
					Utility.createIconItemStack(Material.SHEARS, 1, ChatColor.YELLOW + "Permanent Shears", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "20 iron"),
					Utility.createIconItemStack(Material.ARROW, 8, ChatColor.YELLOW + "Arrow", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "2 gold"),
					Utility.createPotionItemStack(ChatColor.YELLOW + "Invisibility Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, true, false), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "2 emerald"),
					Utility.createIconItemStack(Material.TNT, 1, ChatColor.YELLOW + "TNT", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold")

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

<<<<<<< Updated upstream
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
=======
					Utility.createIconItemStack(Material.WHITE_WOOL, 16, ChatColor.YELLOW + "Wool", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.CYAN_CONCRETE, 16, ChatColor.YELLOW + "Hardened Clay", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "12 iron"),
					Utility.createIconItemStack(Material.GLASS, 4, ChatColor.YELLOW + "Glass", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "12 iron"),
					Utility.createIconItemStack(Material.END_STONE, 12, ChatColor.YELLOW + "End Stone", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "24 iron"),
					Utility.createIconItemStack(Material.LADDER, 16, ChatColor.YELLOW + "Ladder", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "4 iron"),
					Utility.createIconItemStack(Material.OAK_PLANKS, 16, ChatColor.YELLOW + "Wood", "",
>>>>>>> Stashed changes
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.OBSIDIAN, 4, ChatColor.YELLOW + "Obby :)", "",
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
					Utility.createIconItemStack(Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Diamond Sword", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald"),
					Utility.createEnchantedItemStack(Material.STICK, 1, ChatColor.YELLOW + "Knockback Stick",
							new EnchantmentSet[] {new EnchantmentSet(Enchantment.KNOCKBACK, 1)}, "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "5 gold")

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
					Utility.createIconItemStack(Material.DIAMOND_BOOTS, 1, ChatColor.YELLOW + "Permanent Diamond Armor", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "6 emerald")
			};

			for (int i = 0; i < 3; i++) {

				inv.setItem(19 + i, a[i]);

			}

			break;

		case ShopPage.TOOLS:

			ItemStack[] t = {

					Utility.createIconItemStack(Material.SHEARS, 1, ChatColor.YELLOW + "Permanent Shears", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "20 iron"),
					nextPick(player),
					nextAxe(player)

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

					Utility.createPotionItemStack(ChatColor.YELLOW + "Speed Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.SPEED, 600, 1), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "1 emerald"),
					Utility.createPotionItemStack(ChatColor.YELLOW + "Jump Boost Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.JUMP, 600, 4), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "1 emerald"),
					Utility.createPotionItemStack(ChatColor.YELLOW + "Invisibility Potion" + ChatColor.GRAY + ChatColor.ITALIC + " 30s",
							new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, true, false), "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "2 emerald")

			};

			for (int i = 0; i < 3; i++) {

				inv.setItem(19 + i, p[i]);

			}

			break;

		case ShopPage.UTILITY:

			ItemStack[] u = {

					Utility.createIconItemStack(Material.GOLDEN_APPLE, 1, ChatColor.YELLOW + "Golden Apple", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold"),
<<<<<<< Updated upstream
					Utility.createIconItemStack(Material.SNOW_BALL, 1, ChatColor.YELLOW + "Silverfish", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.MONSTER_EGG, 1, ChatColor.YELLOW + "Gamer Golem", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "120 iron"),
					Utility.createIconItemStack(Material.FIREBALL, 1, ChatColor.YELLOW + "Fireball", "",
=======
					Utility.createIconItemStack(Material.SNOWBALL, 1, ChatColor.YELLOW + "Silverfish", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.SHEEP_SPAWN_EGG, 1, ChatColor.YELLOW + "Gamer Golem", "",
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "120 iron"),
					Utility.createIconItemStack(Material.FIRE_CHARGE, 1, ChatColor.YELLOW + "Fireball", "",
>>>>>>> Stashed changes
							ChatColor.GRAY + "Price: " + ChatColor.WHITE + "40 iron"),
					Utility.createIconItemStack(Material.TNT, 1, ChatColor.YELLOW + "TNT", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),
					Utility.createIconItemStack(Material.ENDER_PEARL, 1, ChatColor.YELLOW + "Ender Pearl", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "4 emerald"),
					Utility.createIconItemStack(Material.EGG, 1, ChatColor.YELLOW + "Bridge Egg", "",
							ChatColor.GRAY + "Price: " + ChatColor.GREEN + "2 emerald"),
					Utility.createIconItemStack(Material.MILK_BUCKET, 1, ChatColor.YELLOW + "Magic Milk" + ChatColor.GRAY + ChatColor.ITALIC + " 30s", "",
							ChatColor.GRAY + "Price: " + ChatColor.GOLD + "4 gold"),

			};

			for (int i = 0; i < 7; i++) {

				inv.setItem(19 + i, u[i]);

			}

			for (int i = 7; i < 8; i++) {

				inv.setItem(21 + i, u[i]);

			}

			break;

		}

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if (e.getSlotType() == SlotType.ARMOR) {

			e.setCancelled(true);
			return;

		}

		if (e.getView().getTitle().equals(ChatColor.BOLD + "Trap Store")) {

			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

			if (e.getRawSlot() > 8) return;

			Player player = (Player) e.getWhoClicked();

			ItemStack item = e.getCurrentItem();
			ItemMeta meta = item.getItemMeta();

			if (meta.hasLore()) {

				List<String> lore = item.getItemMeta().getLore();

				if (lore.size() > 0) {

					String[] price = ChatColor.stripColor(lore.get(lore.size() - 1)).split(" ");

					int number = Integer.parseInt(price[1]);

					Material type = txt2mat(price[2]);

					boolean success = purchaseTrap(player, meta.getDisplayName(), number, type);

					if (success) {

<<<<<<< Updated upstream
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
=======
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
>>>>>>> Stashed changes
						openTeamUpgrades(player);
						return;

					}


				}


			}

<<<<<<< Updated upstream
			player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
=======
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
>>>>>>> Stashed changes
			return;

		}

		if (e.getView().getTitle().equals(ChatColor.BOLD + "Team Upgrades")) {

			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

			if (e.getRawSlot() > 53) return;

			Player player = (Player) e.getWhoClicked();

			ItemStack item = e.getCurrentItem();

<<<<<<< Updated upstream
			if (e.getRawSlot() == 49 && item.getType() == Material.STAINED_GLASS) {
=======
			// TODO: THIS IS WRONG! FIX GLASS COLOR (maybe fix now?)
			if (e.getRawSlot() == 49 && item.getType() == Material.LIGHT_GRAY_STAINED_GLASS) {
>>>>>>> Stashed changes

				openTrapMenu(player);

				return;

			}

			if (item.hasItemMeta()) {

				ItemMeta meta = item.getItemMeta();

				if (meta.hasLore()) {

					List<String> lore = meta.getLore();

					if (lore.size() > 0) {

						String active = lore.get(lore.size() - 1);

						if (!ChatColor.stripColor(active).equals("ACTIVE")) {

							int upgrade = -1;

							switch (item.getType()) {

							case DIAMOND_SWORD:
								upgrade = TeamUpgrade.SWORDS;
								break;

							case APPLE:
								upgrade = TeamUpgrade.HEAL;
								break;

							case CHAINMAIL_CHESTPLATE:
								upgrade = TeamUpgrade.ARMOR1;
								break;

							case IRON_CHESTPLATE:
								upgrade = TeamUpgrade.ARMOR2;
								break;

<<<<<<< Updated upstream
							case GOLD_CHESTPLATE:
=======
							case GOLDEN_CHESTPLATE:
>>>>>>> Stashed changes
								upgrade = TeamUpgrade.ARMOR3;
								break;

							case DIAMOND_CHESTPLATE:
								upgrade = TeamUpgrade.ARMOR4;
								break;

							case IRON_PICKAXE:
								upgrade = TeamUpgrade.MINE1;
								break;

<<<<<<< Updated upstream
							case GOLD_PICKAXE:
=======
							case GOLDEN_PICKAXE:
>>>>>>> Stashed changes
								upgrade = TeamUpgrade.MINE2;
								break;

							case IRON_INGOT:
								upgrade = TeamUpgrade.FORGE1;
								break;

							case GOLD_INGOT:
								upgrade = TeamUpgrade.FORGE2;
								break;

							case EMERALD:
								upgrade = TeamUpgrade.FORGE3;
								break;

							case GOLD_BLOCK:
								upgrade = TeamUpgrade.FORGE4;
								break;

							default:
								break;

							} 

							if (upgrade >= 0) {

								String[] price = ChatColor.stripColor(lore.get(3)).split(" ");

								int number = Integer.parseInt(price[1]);

								Material type = txt2mat(price[2]);

								boolean success = purchaseUpgrade(player, item.getItemMeta().getDisplayName(), upgrade, number, type);

								if (success) {

<<<<<<< Updated upstream
									player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
=======
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
>>>>>>> Stashed changes
									teamUpgrades_inv(player, e.getInventory());
									return;

								}

							}

						}

					}


				}

			}

<<<<<<< Updated upstream
			player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
=======
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
>>>>>>> Stashed changes
			return;


		}

		if (e.getView().getTitle().equals(ChatColor.BOLD + "Item Store")) {

			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;



			Player player = (Player) e.getWhoClicked();

			int slot = e.getRawSlot();

			if (slot < 8) {

<<<<<<< Updated upstream
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1.5f);
=======
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
							player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
=======
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
>>>>>>> Stashed changes

							int page = 0;

							for (int i = 0; i < 8; i++) {

								if (e.getInventory().getItem(i + 9).getDurability() == 14) {

									page = i;

								}

							}

							itemShop_inv(player, e.getInventory(), page);
							return;

						}

					}

				}

<<<<<<< Updated upstream
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
=======
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
>>>>>>> Stashed changes

			}

		}

	}

	public boolean purchaseTrap(Player player, String purchase, int cost, Material type) {

		Gamer gamer = WedBars.arena.getGamer(player.getName());
		ArenaTeam team = WedBars.arena.getTeam(gamer.getTeam());

		boolean has = player.getInventory().contains(type, cost);

		if (!has) {

			player.sendMessage(ChatColor.RED + "Not enough resources!");
			return false;

		}

		String purch = ChatColor.stripColor(purchase).toLowerCase();

		if (purch.contains("fatigue")) {

			team.setTrap(new TrapFatigue());

		}
		
		else if (purch.contains("it's a trap!")) {
			
			team.setTrap(new TrapBlindness());
			
		}

		else if (purch.contains("counter-offensive")) {

			team.setTrap(new TrapCounterOffensive());

		}

		else if (purch.contains("infestation")) {

			team.setTrap(new TrapInfestation());

		}

		else {

			return false;

		}

		for (Gamer g : team.getGamers()) {

			g.getPlayer().sendMessage(team.getTeam().getChatColor() + player.getName() + ChatColor.GRAY + " purchased " + purchase + ChatColor.GRAY + ".");

		}

		player.getInventory().removeItem(new ItemStack(type, cost));
		player.updateInventory();
		
		GamerPurchaseTrapEvent gpte = new GamerPurchaseTrapEvent(gamer, purch, type, cost);
		Bukkit.getPluginManager().callEvent(gpte);

		return true;

	}

	public boolean purchaseUpgrade(Player player, String purchase, int upgrade, int cost, Material type) {

		Gamer gamer = WedBars.arena.getGamer(player.getName());
		ArenaTeam team = WedBars.arena.getTeam(gamer.getTeam());

		boolean has = player.getInventory().contains(type, cost);

		if (!has) {

			player.sendMessage(ChatColor.RED + "Not enough resources!");
			return false;

		}

		team.addUpgrade(upgrade);

		switch (upgrade) {

		case TeamUpgrade.SWORDS:

			for (Gamer g : team.getGamers()) {

				Player p = g.getPlayer();

				ItemStack[] is = p.getInventory().getContents();

				for (int i = 0; i < is.length; i++) {

					if (is[i] == null) continue;

					if (is[i].getType().toString().endsWith("_SWORD")) {

						ItemMeta meta = is[i].getItemMeta();
						meta.removeEnchant(Enchantment.DAMAGE_ALL);
						meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
						is[i].setItemMeta(meta);

					}

				}

			}

			break;

		case TeamUpgrade.ARMOR1:
		case TeamUpgrade.ARMOR2:
		case TeamUpgrade.ARMOR3:
		case TeamUpgrade.ARMOR4:

			for (Gamer g : team.getGamers()) {

				Player p = g.getPlayer();

				ItemStack[] ar = p.getInventory().getArmorContents();

				for (int i = 0; i < ar.length; i++) {

					ItemMeta meta = ar[i].getItemMeta();
					meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, upgrade - 1, true);
					ar[i].setItemMeta(meta);

				}

			}

			break;

		case TeamUpgrade.MINE1:
		case TeamUpgrade.MINE2:

			for (Gamer g : team.getGamers()) {

				Player p = g.getPlayer();

				p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, upgrade - 6, true, false));

			}

			break;


		case TeamUpgrade.FORGE1:
			double ironcalc1 = (double) team.getInitialIronSpeed() / WedBars.FORGE1;
			team.getIronGenerator().setSpeed((int) ironcalc1);
			Bukkit.broadcastMessage(team.getInitialIronSpeed() + "");
			Bukkit.broadcastMessage(ironcalc1 + "");
			double goldcalc1 = (double) team.getInitialGoldSpeed() / WedBars.FORGE1;
			team.getGoldGenerator().setSpeed((int) goldcalc1);

			break;

		case TeamUpgrade.FORGE2:

			double ironcalc2 = (double) team.getInitialIronSpeed() / WedBars.FORGE2;
			team.getIronGenerator().setSpeed((int) ironcalc2);
			double goldcalc2 = (double) team.getInitialIronSpeed() / WedBars.FORGE2;
			team.getGoldGenerator().setSpeed((int) goldcalc2);
			break;

		case TeamUpgrade.FORGE3:

			team.createEmeraldGenerator();
			break;

		case TeamUpgrade.FORGE4:

			double ironcalc3 = (double) team.getInitialIronSpeed() / WedBars.FORGE4;
			team.getIronGenerator().setSpeed((int) ironcalc3);
			double goldcalc3 = (double) team.getInitialIronSpeed() / WedBars.FORGE4;
			team.getGoldGenerator().setSpeed((int) goldcalc3);
			double emeraldcalc3 = (double) team.getInitialEmeraldSpeed() / WedBars.FORGE2;
			team.getEmeraldGenerator().setSpeed((int) emeraldcalc3);
			break;

		}

		for (Gamer g : team.getGamers()) {

			g.getPlayer().sendMessage(team.getTeam().getChatColor() + player.getName() + ChatColor.GRAY + " purchased " + purchase + ChatColor.GRAY + ".");

		}

		player.getInventory().removeItem(new ItemStack(type, cost));
		player.updateInventory();
		
		GamerPurchaseTeamUpgradeEvent gptue = new GamerPurchaseTeamUpgradeEvent(gamer, upgrade, type, cost);
		Bukkit.getPluginManager().callEvent(gptue);

		return true;

	}

	public boolean purchaseItem(Player player, ItemStack item, int cost, Material type) {

		if (player.getInventory().firstEmpty() == -1) {

			player.sendMessage(ChatColor.RED + "Your inventory is full!");
			return false;

		}

		boolean has = player.getInventory().contains(type, cost);

		if (!has) {

			player.sendMessage(ChatColor.RED + "Not enough resources!");
			return false;

		}

		ItemStack stack = item.clone();

		boolean isNotArmor = true;

<<<<<<< Updated upstream
		if (stack.getType() == Material.WOOL && WedBars.arena != null) {

			stack = new ItemStack(Material.WOOL, stack.getAmount(), WedBars.arena.getGamer(player.getName()).getTeam().getStackColor());
=======
		if (stack.getType() == Material.WHITE_WOOL && WedBars.arena != null) {

			stack = new ItemStack(WedBars.arena.getGamer(player.getName()).getTeam().getColoredWool(), stack.getAmount());
>>>>>>> Stashed changes

		}
		
		else if (stack.getType() == Material.GLASS && WedBars.arena != null) {
			
<<<<<<< Updated upstream
			stack = new ItemStack(Material.STAINED_GLASS, stack.getAmount(), WedBars.arena.getGamer(player.getName()).getTeam().getStackColor());
=======
			stack = new ItemStack(WedBars.arena.getGamer(player.getName()).getTeam().getColoredGlassPane(), stack.getAmount());
>>>>>>> Stashed changes
			
		}

		else if (stack.getType() == Material.CHAINMAIL_BOOTS) {

			if (player.getInventory().getBoots().getType() != Material.LEATHER_BOOTS) {

				return false;

			}

			ItemStack leggings = Utility.createUnbreakableItemStack(Material.CHAINMAIL_LEGGINGS, 1, ChatColor.YELLOW + "Chainmail Leggings (lmao why)");
			ItemStack boots = Utility.createUnbreakableItemStack(Material.CHAINMAIL_BOOTS, 1, ChatColor.YELLOW + "Chainmail Boots (lmao why)");

			if (player.getInventory().getChestplate().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {

				ItemMeta chestMeta = player.getInventory().getChestplate().getItemMeta();
				ItemMeta lMeta = leggings.getItemMeta();
				ItemMeta bMeta = boots.getItemMeta();

				for (Enchantment e : chestMeta.getEnchants().keySet()) {

					int l = chestMeta.getEnchantLevel(e);
					lMeta.addEnchant(e, l, true);
					bMeta.addEnchant(e, l, true);

				}

				leggings.setItemMeta(lMeta);
				boots.setItemMeta(bMeta);

			}

			player.getInventory().setLeggings(leggings);
			player.getInventory().setBoots(boots);
			isNotArmor = false;

		}

		else if (stack.getType() == Material.IRON_BOOTS) {

			if (player.getInventory().getBoots().getType() != Material.LEATHER_BOOTS
					&& player.getInventory().getBoots().getType() != Material.CHAINMAIL_BOOTS) {

				return false;

			}
			
			ItemStack leggings = Utility.createUnbreakableItemStack(Material.IRON_LEGGINGS, 1, ChatColor.YELLOW + "Iron Leggings");
			ItemStack boots = Utility.createUnbreakableItemStack(Material.IRON_BOOTS, 1, ChatColor.YELLOW + "Iron Boots");

			if (player.getInventory().getChestplate().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {

				ItemMeta chestMeta = player.getInventory().getChestplate().getItemMeta();
				ItemMeta lMeta = leggings.getItemMeta();
				ItemMeta bMeta = boots.getItemMeta();

				for (Enchantment e : chestMeta.getEnchants().keySet()) {

					int l = chestMeta.getEnchantLevel(e);
					lMeta.addEnchant(e, l, true);
					bMeta.addEnchant(e, l, true);

				}

				leggings.setItemMeta(lMeta);
				boots.setItemMeta(bMeta);

			}


			player.getInventory().setLeggings(leggings);
			player.getInventory().setBoots(boots);
			isNotArmor = false;

		}

		else if (stack.getType() == Material.DIAMOND_BOOTS) {

			if (player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {

				return false;

			}
			
			ItemStack leggings = Utility.createUnbreakableItemStack(Material.DIAMOND_LEGGINGS, 1, ChatColor.YELLOW + "Diamond Leggings");
			ItemStack boots = Utility.createUnbreakableItemStack(Material.DIAMOND_BOOTS, 1, ChatColor.YELLOW + "Diamond Boots");
			
			if (player.getInventory().getChestplate().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {

				ItemMeta chestMeta = player.getInventory().getChestplate().getItemMeta();
				ItemMeta lMeta = leggings.getItemMeta();
				ItemMeta bMeta = boots.getItemMeta();

				for (Enchantment e : chestMeta.getEnchants().keySet()) {

					int l = chestMeta.getEnchantLevel(e);
					lMeta.addEnchant(e, l, true);
					bMeta.addEnchant(e, l, true);

				}

				leggings.setItemMeta(lMeta);
				boots.setItemMeta(bMeta);

			}

			player.getInventory().setLeggings(leggings);
			player.getInventory().setBoots(boots);
			isNotArmor = false;

		}


		else if (stack.getType().toString().endsWith("_PICKAXE")) {

<<<<<<< Updated upstream
			player.getInventory().remove(Material.WOOD_PICKAXE);
			player.getInventory().remove(Material.IRON_PICKAXE);
			player.getInventory().remove(Material.GOLD_PICKAXE);
=======
			player.getInventory().remove(Material.WOODEN_PICKAXE);
			player.getInventory().remove(Material.IRON_PICKAXE);
			player.getInventory().remove(Material.GOLDEN_PICKAXE);
>>>>>>> Stashed changes
			player.getInventory().remove(Material.DIAMOND_PICKAXE);

		}

		else if (stack.getType().toString().endsWith("_AXE")) {

<<<<<<< Updated upstream
			player.getInventory().remove(Material.WOOD_AXE);
=======
			player.getInventory().remove(Material.WOODEN_AXE);
>>>>>>> Stashed changes
			player.getInventory().remove(Material.STONE_AXE);
			player.getInventory().remove(Material.IRON_AXE);
			player.getInventory().remove(Material.DIAMOND_AXE);

		}

		if (stack.getType().isBlock()) {

			stack = new ItemStack(stack.getType(), stack.getAmount(), stack.getDurability());

		} else {

			ItemMeta meta = stack.getItemMeta();

			if (stack.getType().toString().endsWith("_SWORD")) {

				if (WedBars.arena != null) {

					if (WedBars.arena.getTeam(WedBars.arena.getGamer(player.getName()).getTeam()).hasUpgrade(TeamUpgrade.SWORDS)) {

						meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);

					}

				}
				
<<<<<<< Updated upstream
				player.getInventory().remove(Material.WOOD_SWORD);
=======
				player.getInventory().remove(Material.WOODEN_SWORD);
>>>>>>> Stashed changes

			}

			meta.setLore(null);
			stack.setItemMeta(meta);

		}
		
		if (isNotArmor) player.getInventory().addItem(stack);
		player.getInventory().removeItem(new ItemStack(type, cost));
		player.updateInventory();
		
		Gamer gamer = null;
		if (WedBars.arena != null) gamer = WedBars.arena.getGamer(player.getName());
		
		GamerPurchaseItemEvent gpie = new GamerPurchaseItemEvent(player, gamer, stack, type, cost);
		Bukkit.getPluginManager().callEvent(gpie);

		return true;


	}

	private static Material txt2mat(String type) {

		if (type.equals("iron")) {

			return Material.IRON_INGOT;

		} else if (type.equals("gold")) {

			return Material.GOLD_INGOT;

		} else if (type.equals("diamond")) {

			return Material.DIAMOND;

		} else if (type.equals("emerald")) {

			return Material.EMERALD;

		} else {

			return null;

		}

	}

	private static ItemStack nextPick(Player player) {

		ItemStack nextPick = null;

<<<<<<< Updated upstream
		if (player.getInventory().contains(Material.WOOD_PICKAXE)) {
=======
		if (player.getInventory().contains(Material.WOODEN_PICKAXE)) {
>>>>>>> Stashed changes

			nextPick = Utility.createEnchantedItemStack(Material.IRON_PICKAXE, 1, ChatColor.YELLOW + "Iron Pickaxe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 2)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

		} else if (player.getInventory().contains(Material.IRON_PICKAXE)) {

<<<<<<< Updated upstream
			nextPick = Utility.createEnchantedItemStack(Material.GOLD_PICKAXE, 1, ChatColor.YELLOW + "Gold Pickaxe",
=======
			nextPick = Utility.createEnchantedItemStack(Material.GOLDEN_PICKAXE, 1, ChatColor.YELLOW + "Gold Pickaxe",
>>>>>>> Stashed changes
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 3),
							new EnchantmentSet(Enchantment.DAMAGE_ALL, 2)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold");

<<<<<<< Updated upstream
		} else if (player.getInventory().contains(Material.GOLD_PICKAXE)) {
=======
		} else if (player.getInventory().contains(Material.GOLDEN_PICKAXE)) {
>>>>>>> Stashed changes

			nextPick = Utility.createEnchantedItemStack(Material.DIAMOND_PICKAXE, 1, ChatColor.YELLOW + "Diamond Pickaxe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.GOLD + "6 gold");

		} else if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {

			nextPick = Utility.createEnchantedItemStack(Material.DIAMOND_PICKAXE, 1, ChatColor.YELLOW + "Diamond Pickaxe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)});

		} else {

<<<<<<< Updated upstream
			nextPick = Utility.createEnchantedItemStack(Material.WOOD_PICKAXE, 1, ChatColor.YELLOW + "Wooden Pickaxe",
=======
			nextPick = Utility.createEnchantedItemStack(Material.WOODEN_PICKAXE, 1, ChatColor.YELLOW + "Wooden Pickaxe",
>>>>>>> Stashed changes
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

		}

		return nextPick;

	}

	private static ItemStack nextAxe(Player player) {

		ItemStack nextAxe = null;

<<<<<<< Updated upstream
		if (player.getInventory().contains(Material.WOOD_AXE)) {
=======
		if (player.getInventory().contains(Material.WOODEN_AXE)) {
>>>>>>> Stashed changes

			nextAxe = Utility.createEnchantedItemStack(Material.STONE_AXE, 1, ChatColor.YELLOW + "Stone Axe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

		} else if (player.getInventory().contains(Material.STONE_AXE)) {

			nextAxe = Utility.createEnchantedItemStack(Material.IRON_AXE, 1, ChatColor.YELLOW + "Iron Axe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.GOLD + "3 gold");

		} else if (player.getInventory().contains(Material.IRON_AXE)) {

			nextAxe = Utility.createEnchantedItemStack(Material.DIAMOND_AXE, 1, ChatColor.YELLOW + "Diamond Axe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.GOLD + "6 gold");

		} else if (player.getInventory().contains(Material.DIAMOND_AXE)) {

			nextAxe = Utility.createEnchantedItemStack(Material.DIAMOND_AXE, 1, ChatColor.YELLOW + "Diamond Axe",
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)});

		}else {

<<<<<<< Updated upstream
			nextAxe = Utility.createEnchantedItemStack(Material.WOOD_AXE, 1, ChatColor.YELLOW + "Wooden Axe",
=======
			nextAxe = Utility.createEnchantedItemStack(Material.WOODEN_AXE, 1, ChatColor.YELLOW + "Wooden Axe",
>>>>>>> Stashed changes
					new EnchantmentSet[] {new EnchantmentSet(Enchantment.DIG_SPEED, 1)}, "",
					ChatColor.GRAY + "Price: " + ChatColor.WHITE + "10 iron");

		}

		return nextAxe;

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
