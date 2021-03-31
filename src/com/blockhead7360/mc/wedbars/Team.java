package com.blockhead7360.mc.wedbars;

import org.bukkit.ChatColor;

public enum Team {
	
	RED((short) 14, ChatColor.RED, "Red"),
	GREEN((short) 5, ChatColor.GREEN, "Green"),
	BLUE((short) 11, ChatColor.BLUE, "Blue"),
	YELLOW((short) 4, ChatColor.YELLOW, "Yellow"),
	AQUA((short) 9, ChatColor.DARK_AQUA, "Aqua"),
	WHITE((short) 0, ChatColor.WHITE, "White"),
	GRAY((short) 15, ChatColor.DARK_GRAY, "Gray"),
	PINK((short) 6, ChatColor.LIGHT_PURPLE, "Pink");
	
	
	
	
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
	
	public ChatColor getChatColor() {
		
		return chat;
		
	}
	
	public String getLabel() {
		
		return label;
		
	}
	
}
