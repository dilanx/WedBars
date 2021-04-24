package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;

public class GamerPurchaseTeamUpgradeEvent extends Event {
	
	private Gamer gamer;
	private int upgrade;
	private Material costType;
	private int costAmount;
	
	public GamerPurchaseTeamUpgradeEvent(Gamer gamer, int upgrade, Material costType, int costAmount) {
		this.gamer = gamer;
		this.upgrade = upgrade;
		this.costType = costType;
		this.costAmount = costAmount;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public int getUpgrade() {
		return upgrade;
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
