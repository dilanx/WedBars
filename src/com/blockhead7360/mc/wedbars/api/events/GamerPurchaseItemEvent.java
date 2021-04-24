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

	public Material getCostType() {
		return costType;
	}

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
