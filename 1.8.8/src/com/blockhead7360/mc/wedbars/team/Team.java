package com.blockhead7360.mc.wedbars.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public enum Team {

	RED((short) 14, DyeColor.RED, ChatColor.RED, Color.RED, "Red"),
	GREEN((short) 5, DyeColor.LIME, ChatColor.GREEN, Color.LIME, "Green"),
	BLUE((short) 11, DyeColor.BLUE, ChatColor.BLUE, Color.BLUE, "Blue"),
	YELLOW((short) 4, DyeColor.YELLOW, ChatColor.YELLOW, Color.YELLOW, "Yellow"),
	AQUA((short) 9, DyeColor.CYAN, ChatColor.DARK_AQUA, Color.AQUA, "Aqua"),
	WHITE((short) 0, DyeColor.WHITE, ChatColor.WHITE, Color.WHITE, "White"),
	GRAY((short) 7, DyeColor.GRAY, ChatColor.DARK_GRAY, Color.GRAY, "Gray"),
	PINK((short) 6, DyeColor.PINK, ChatColor.LIGHT_PURPLE, Color.FUCHSIA, "Pink");

	private final short stackColor;
	private final DyeColor blockColor;
	private final ChatColor chat;
	private final Color color;
	private final String label;

	private Team(short stackColor, DyeColor blockColor, ChatColor chat, Color color, String label) {

		this.stackColor = stackColor;
		this.blockColor = blockColor;
		this.chat = chat;
		this.color = color;
		this.label = label;

	}

	public short getStackColor() {

		return stackColor;

	}

	public DyeColor getBlockColor() {

		return blockColor;

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
