package com.blockhead7360.mc.wedbars;

import org.bukkit.ChatColor;

public enum Team {
	
	RED((short) 14, ChatColor.RED, "Red");
	
	
	
	
	private final short stackColor;
	private final ChatColor chat;
	private final String label;
	
	private Team(short stackColor, ChatColor chat, String label) {
		
		this.stackColor = stackColor;
		this.chat = chat;
		this.label = label;
		
	}
	
	public short getStackColor() {
		
		return stackColor;
		
	}
	
	public ChatColor getChat() {
		
		return chat;
		
	}
	
	public String getLabel() {
		
		return label;
		
	}
	
}
