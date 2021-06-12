package com.blockhead7360.mc.wedbars.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
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
	private final ChatColor chat;
	private final Color color;
	private final String label;

	private Team(Material coloredWool, Material coloredGlass, Material coloredGlassPane, Material coloredBed, ChatColor chat, Color color, String label) {

		this.coloredWool = coloredWool;
		this.coloredGlass = coloredGlass;
		this.coloredGlassPane = coloredGlassPane;
		this.coloredBed = coloredBed;
		this.chat = chat;
		this.color = color;
		this.label = label;

	}

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
