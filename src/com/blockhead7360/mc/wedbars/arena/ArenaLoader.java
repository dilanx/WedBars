package com.blockhead7360.mc.wedbars.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.blockhead7360.mc.wedbars.WedBars;
import com.blockhead7360.mc.wedbars.team.ArenaTeamData;
import com.blockhead7360.mc.wedbars.team.Team;

public class ArenaLoader {

	public static List<String> listArenas(WedBars plugin){

		List<String> list = new ArrayList<>();

		File folder = new File(plugin.getDataFolder(), "arenas");
		if (!folder.exists()) {

			folder.mkdirs();
			return null;

		}

		for (File file : folder.listFiles()) {

			String name = file.getName();

			if (name.endsWith(".yml")) list.add(name.substring(0, name.length() - 4));

		}

		return list;

	}

	public static ArenaData loadArena(WedBars plugin, String name) {

		File folder = new File(plugin.getDataFolder(), "arenas");
		if (!folder.exists()) {

			folder.mkdirs();
			return null;

		}

		File file = new File(plugin.getDataFolder(), "arenas/" + name + ".yml");
		if (!file.exists())
			return null;

		FileConfiguration config = new YamlConfiguration();

		try {
			config.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		Location lobby = (Location) config.get("lobby");

		int ds = config.getInt("diamondspeed");
		int es = config.getInt("emeraldspeed");
		int is = config.getInt("ironspeed");
		int gs = config.getInt("goldspeed");
		int pes = config.getInt("personalemeraldspeed");
		int bh = config.getInt("buildheight");


		ArenaData ad = new ArenaData(name, lobby, ds, es, is, gs, pes, bh);

		if (config.isConfigurationSection("diamond")) {
			for (String key : config.getConfigurationSection("diamond").getKeys(false)) {

				ad.addDiamondGen((Location) config.get("diamond." + key));

			}
		}

		if (config.isConfigurationSection("emerald")) {
			for (String key : config.getConfigurationSection("emerald").getKeys(false)) {

				ad.addEmeraldGen((Location) config.get("emerald." + key));

			}
		}

		for (Team team : Team.values()) {

			String t = team.toString();

			if (!config.isConfigurationSection("team." + t)) continue;

			Location spawnLoc = (Location) config.get("team." + t + ".spawn");
			Location generatorLoc = (Location) config.get("team." + t + ".gen");
			Location[] bedLoc = {
					(Location) config.get("team." + t + ".bed0"),
					(Location) config.get("team." + t + ".bed1")
			};

			ad.setTeamData(team, new ArenaTeamData(team, spawnLoc, generatorLoc, bedLoc));

		}

		return ad;


	}

	public static boolean saveArena(WedBars plugin, String name, ArenaData data) {

		File folder = new File(plugin.getDataFolder(), "arenas");
		if (!folder.exists()) {

			folder.mkdirs();

		}

		File file = new File(plugin.getDataFolder(), "arenas/" + name + ".yml");

		YamlConfiguration config = new YamlConfiguration();

		config.set("lobby", data.getLobby());
		config.set("diamondspeed", data.getDiamondSpeed());
		config.set("emeraldspeed", data.getEmeraldSpeed());
		config.set("ironspeed", data.getIronSpeed());
		config.set("goldspeed", data.getGoldSpeed());
		config.set("personalemeraldspeed", data.getPersonalEmeraldSpeed());
		config.set("buildheight", data.getBuildHeight());

		List<Location> diamondGen = data.getDiamondGen();

		for (int i = 0; i < diamondGen.size(); i++) {

			config.set("diamond." + i, diamondGen.get(i));

		}

		List<Location> emeraldGen = data.getEmeraldGen();

		for (int i = 0; i < emeraldGen.size(); i++) {

			config.set("emerald." + i, emeraldGen.get(i));

		}

		for (Team team : data.getAllTeams()) {

			String t = team.toString();
			ArenaTeamData atd = data.getTeamData(team);

			config.set("team." + t + ".spawn", atd.getSpawn());
			config.set("team." + t + ".gen", atd.getGenerator());
			Location[] bed = atd.getBed();
			config.set("team." + t + ".bed0", bed[0]);
			config.set("team." + t + ".bed1", bed[1]);

		}

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;


	}


}
