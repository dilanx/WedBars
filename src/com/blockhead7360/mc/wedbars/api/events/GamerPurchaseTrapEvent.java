package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerPurchaseTrapEvent extends Event {
	
	private Gamer gamer;
	private String trapName;
	private Material costType;
	private int costAmount;
	
	public GamerPurchaseTrapEvent(Gamer gamer, String trapName, Material costType, int costAmount) {
		this.gamer = gamer;
		this.trapName = trapName;
		this.costType = costType;
		this.costAmount = costAmount;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public String getTrapName() {
		return trapName;
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
