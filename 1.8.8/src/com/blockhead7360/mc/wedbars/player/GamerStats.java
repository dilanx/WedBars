package com.blockhead7360.mc.wedbars.player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;

public class GamerStats {

	private static boolean enabled;
	private static String host, database, user, pass;

	private static Connection connection;

	public static void init(boolean enabled, String host, String database, String user, String pass) {

		GamerStats.enabled = enabled;
		GamerStats.host = host;
		GamerStats.database = database;
		GamerStats.user = user;
		GamerStats.pass = pass;

		if (enabled) connect();


	}

	public static void connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void updateGamerWithStats(Gamer gamer, boolean alreadyTried) {

		if (!enabled) return;

		try {
			
			String uuid = gamer.getPlayer().getUniqueId().toString().replaceAll("-", "");
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery("select * from wb_stats where uuid = '" + uuid + "'");
			
			boolean found = false;
			if (set.next()) {
				
				found = true;
				
				for (Statistic stat : Statistic.values()) {

					gamer.setStatistic(stat, set.getInt(stat.name().toLowerCase()));

				}

			}
			
			if (!found) {
				
				String statsMsg = "";
				
				for (@SuppressWarnings("unused") Statistic stat : Statistic.values()) {
					
					statsMsg += ", 0";
					
				}
				
				statement.executeUpdate("insert into wb_stats values ('" + uuid + "'" + statsMsg + ")");
				
			}

			set.close();
			statement.close();

		} catch (SQLException e) {
			if (alreadyTried) {
				e.printStackTrace();
				gamer.getPlayer().sendMessage(ChatColor.RED + "Unable to load your stats. Please contact an administrator.");
				return;

			}
			connect();
			updateGamerWithStats(gamer, true);
			return;
		}

	}

	public static void sendGamerData(Gamer gamer, boolean alreadyTried) {

		if (!enabled) return;

		try {
			Statement statement = connection.createStatement();
			
			String statsMsg = "";
			
			for (Statistic stat : Statistic.values()) {
				
				statsMsg += stat.name().toLowerCase() + " = " + gamer.getStatistic(stat) + ", ";
				
			}
			
			statsMsg = statsMsg.substring(0, statsMsg.length() - 2);
			
			statement.executeUpdate("update wb_stats set " + statsMsg + " where uuid = '" + gamer.getPlayer().getUniqueId().toString().replaceAll("-", "") + "'");
			
			statement.close();

		} catch (SQLException e) {
			if (alreadyTried) {
				e.printStackTrace();
				gamer.getPlayer().sendMessage(ChatColor.RED + "Unable to save your stats. Please contact an administrator.");
				return;

			}
			connect();
			sendGamerData(gamer, true);
			return;
		}

	}

}
