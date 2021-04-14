package com.blockhead7360.mc.wedbars.player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GamerStats {
	
	private static boolean enabled;
	private static String host, database, user, pass;
	
	private static Connection connection;
	private static Statement statement;
	
	public static void connect() {
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, pass);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateGamerWithStats(Gamer gamer) {
		
		//TODO finish later
		
	}
	
	public static void sendGamerData() {
		
		
		
	}
	
}
