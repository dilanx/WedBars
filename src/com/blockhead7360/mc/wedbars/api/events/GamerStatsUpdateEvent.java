package com.blockhead7360.mc.wedbars.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.blockhead7360.mc.wedbars.player.Gamer;
import com.blockhead7360.mc.wedbars.player.Statistic;

public class GamerStatsUpdateEvent extends Event implements Cancellable {
	
	private Gamer gamer;
	private Statistic statistic;
	private int statBefore;
	private int statAfter;
	private boolean isCancelled;
	
	public GamerStatsUpdateEvent(Gamer gamer, Statistic statistic, int statBefore, int statAfter) {
		this.gamer = gamer;
		this.statistic = statistic;
		this.statBefore = statBefore;
		this.statAfter = statAfter;
		this.isCancelled = false;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public int getStatBefore() {
		return statBefore;
	}

	public int getStatAfter() {
		return statAfter;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

}
