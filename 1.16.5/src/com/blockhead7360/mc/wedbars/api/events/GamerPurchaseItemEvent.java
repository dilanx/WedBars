package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerPurchaseItemEvent extends Event {
	
	private Player player;
	private Gamer gamer;
	private ItemStack item;
	private Material costType;
	private int costAmount;

	/**
	 * Event triggered when a Gamer purchases an Item from the itemshop. Note that the itemshop and teamshop use two
	 * different handlers. Since implementing shops into the arena is done through NPCs not handled by WedBars, the
	 * plugin has no way of knowing which shop on the map things were purchased from.
	 * @param player
	 * @param gamer
	 * @param item
	 * @param costType
	 * @param costAmount
	 */
	public GamerPurchaseItemEvent(Player player, Gamer gamer, ItemStack item, Material costType, int costAmount) {
		this.player = player;
		this.gamer = gamer;
		this.item = item;
		this.costType = costType;
		this.costAmount = costAmount;
	}

	public Player getPlayer() {
		return player;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public ItemStack getItem() {
		return item;
	}

	/**
	 * Fetches the type of currency used for the item (ex. iron, gold, diamond, emerald, etc).
	 * @return a Material
	 */
	public Material getCostType() {
		return costType;
	}

	/**
	 * Returns an int of the amount of the material that was spent on the purchased item.
	 * @return an int
	 * @see #getCostType()
	 */
	public int getCostAmount() {
		return costAmount;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
}
