package com.blockhead7360.mc.wedbars;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum Team {
	
	RED((short) 14, ChatColor.RED, Color.RED, "Red"),
	GREEN((short) 5, ChatColor.GREEN, Color.LIME, "Green"),
	BLUE((short) 11, ChatColor.BLUE, Color.BLUE, "Blue"),
	YELLOW((short) 4, ChatColor.YELLOW, Color.YELLOW, "Yellow"),
	AQUA((short) 9, ChatColor.DARK_AQUA, Color.AQUA, "Aqua"),
	WHITE((short) 0, ChatColor.WHITE, Color.WHITE, "White"),
	GRAY((short) 15, ChatColor.DARK_GRAY, Color.GRAY, "Gray"),
	PINK((short) 6, ChatColor.LIGHT_PURPLE, Color.FUCHSIA, "Pink");
	
	
	
	
	private final short stackColor;
	private final ChatColor chat;
	private final Color color;
	private final String label;
	
	private Team(short stackColor, ChatColor chat, Color color, String label) {
		
		this.stackColor = stackColor;
		this.chat = chat;
		this.color = color;
		this.label = label;
		
	}
	
	public short getStackColor() {
		
		return stackColor;
		
	}
	
	public ChatColor getChatColor() {
		
		return chat;
		
	}
	
	public Color getColor() {
		
		return color;
		
	}
	
	public String getLabel() {
		
		return label;
		
	}
	
	public static Team getByLabel(String name) {
		
		for (Team t : values()) {
			
			if (t.getLabel().equalsIgnoreCase(name)) {
				
				return t;
				
			}
			
		}
		
		return null;
		
	}
	
}
