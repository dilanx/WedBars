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

	/**
	 * Event triggered when players purchase traps for their team. Traps are defensive buffs that will activate once
	 * a member of an enemy team enters the area of the defending team's island.
	 * @param gamer
	 * @param trapName
	 * @param costType
	 * @param costAmount
	 */
	public GamerPurchaseTrapEvent(Gamer gamer, String trapName, Material costType, int costAmount) {
		this.gamer = gamer;
		this.trapName = trapName;
		this.costType = costType;
		this.costAmount = costAmount;
	}

	/**
	 * Returns the Gamer that purchased the trap. Remember that players are able to purchase traps anywhere there is
	 * a team upgrade shop available, even on other team islands.
	 * @return a Gamer
	 */
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
