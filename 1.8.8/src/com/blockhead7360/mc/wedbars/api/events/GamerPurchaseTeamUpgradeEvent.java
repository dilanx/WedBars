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

	/**
	 * Event triggered when a Gamer purchases a Team upgrade from the team upgrade shop. Note that the itemshop and
	 * teamshop use two different handlers. Since implementing shops into the arena is done through NPCs not handled
	 * by WedBars, the plugin has no way of knowing which shop on the map things were purchased from.
	 * @param gamer
	 * @param upgrade
	 * @param costType
	 * @param costAmount
	 */
	public GamerPurchaseTeamUpgradeEvent(Gamer gamer, int upgrade, Material costType, int costAmount) {
		this.gamer = gamer;
		this.upgrade = upgrade;
		this.costType = costType;
		this.costAmount = costAmount;
	}

	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Fetches the int associated with the purchased upgrade. Each upgrade has an index associated with it for use
	 * within the plugin.
	 * @return int
	 * @see com.blockhead7360.mc.wedbars.team.TeamUpgrade
	 */
	public int getUpgrade() {
		return upgrade;
	}

	/**
	 * Fetches the type of currency used for the upgrade (ex. iron, gold, diamond, emerald, etc). By default, this will
	 * always be Diamonds unless configured otherwise.
	 * @return a Material
	 */
	public Material getCostType() {
		return costType;
	}

	/**
	 * Returns an int of the amount of the material that was spent on the purchased upgrade.
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
