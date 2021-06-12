package com.blockhead7360.mc.wedbars.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
<<<<<<< Updated upstream

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
=======
import org.bukkit.Material;

public enum Team {

	RED(Material.RED_WOOL, Material.RED_STAINED_GLASS, Material.RED_STAINED_GLASS_PANE, Material.RED_BED, ChatColor.RED, Color.RED, "Red"),
	GREEN(Material.LIME_WOOL, Material.LIME_STAINED_GLASS, Material.LIME_STAINED_GLASS_PANE, Material.GREEN_BED, ChatColor.GREEN, Color.LIME, "Green"),
	BLUE(Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS, Material.BLUE_STAINED_GLASS_PANE, Material.BLUE_BED, ChatColor.BLUE, Color.BLUE, "Blue"),
	YELLOW(Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS, Material.YELLOW_STAINED_GLASS_PANE, Material.YELLOW_BED, ChatColor.YELLOW, Color.YELLOW, "Yellow"),
	AQUA(Material.CYAN_WOOL, Material.CYAN_STAINED_GLASS, Material.CYAN_STAINED_GLASS_PANE, Material.CYAN_BED, ChatColor.DARK_AQUA, Color.AQUA, "Aqua"),
	WHITE(Material.WHITE_WOOL, Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS_PANE, Material.WHITE_BED, ChatColor.WHITE, Color.WHITE, "White"),
	GRAY(Material.GRAY_WOOL, Material.GRAY_STAINED_GLASS, Material.GRAY_STAINED_GLASS_PANE, Material.GRAY_BED, ChatColor.DARK_GRAY, Color.GRAY, "Gray"),
	PINK(Material.PINK_WOOL, Material.PINK_STAINED_GLASS, Material.PINK_STAINED_GLASS_PANE, Material.PINK_BED, ChatColor.LIGHT_PURPLE, Color.FUCHSIA, "Pink");

	private final Material coloredWool;
	private final Material coloredGlass;
	private final Material coloredGlassPane;
	private final Material coloredBed;
>>>>>>> Stashed changes
	private final ChatColor chat;
	private final Color color;
	private final String label;

<<<<<<< Updated upstream
	private Team(short stackColor, DyeColor blockColor, ChatColor chat, Color color, String label) {

		this.stackColor = stackColor;
		this.blockColor = blockColor;
=======
	private Team(Material coloredWool, Material coloredGlass, Material coloredGlassPane, Material coloredBed, ChatColor chat, Color color, String label) {

		this.coloredWool = coloredWool;
		this.coloredGlass = coloredGlass;
		this.coloredGlassPane = coloredGlassPane;
		this.coloredBed = coloredBed;
>>>>>>> Stashed changes
		this.chat = chat;
		this.color = color;
		this.label = label;

	}

<<<<<<< Updated upstream
	public short getStackColor() {

		return stackColor;

	}

	public DyeColor getBlockColor() {

		return blockColor;
=======
	public Material getColoredWool() {

		return coloredWool;

	}

	public Material getColoredGlass() {

		return coloredGlass;

	}

	public Material getColoredGlassPane() {

		return coloredGlassPane;

	}

	public Material getColoredBed() {

		return coloredBed;
>>>>>>> Stashed changes

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
