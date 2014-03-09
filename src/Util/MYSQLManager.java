package Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


import code.husky.mysql.MySQL;

import PvpBalance.PvpBalance;

public class MYSQLManager {
	private MySQL db;
	
	public MYSQLManager(Plugin plugin){
	}
	
	public void setupDb() throws SQLException{
		this.db = new MySQL(PvpBalance.getPlugin(), "localhost", "3306", "pvpstats", "root", "root");
		this.db.openConnection();
		Statement statement = this.db.getConnection().createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS `pvpstats` (`Name` varchar(32), `Kills` int, `Deaths` int, `EpicKills` int,`EpicDeaths` int, `DuelsWon` int,`DuelsLost` int, `MinutesPlayed` int)");
		statement.close();
	}
	
	public void closeDB(){
		this.db.closeConnection();
	}
	public void storeUserData(Player player, String key, int data) throws SQLException{
		String playerName = player.getName().toLowerCase();
		if(!this.db.checkConnection()){
			this.db.openConnection();
		}
		Statement statement = this.db.getConnection().createStatement();
		int uses = this.getUserData(player, key);
		if(uses != -1){
			statement.executeUpdate("UPDATE pvpstats SET " + key + " = '" + data + "' WHERE Name = '" + playerName + "';");
		}
		else{
			statement.executeUpdate("INSERT INTO pvpstats (Name, Kills, Deaths, EpicKills, EpicDeaths, DuelsWon, DuelsLost, MinutesPlayed) VALUES ('" + playerName + "', 0, 0, 0, 0, 0, 0, 0);");
		}
		statement.close();
	}
	
	public int getUserData(Player player,String key) throws SQLException{
		int value = -1;
		String playerName = player.getName().toLowerCase();
		if(!this.db.checkConnection()){
			this.db.openConnection();
		}
		Statement statement = this.db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM `pvpstats` WHERE `Name`='" + playerName + "';");
		while(rs.next()){
			value = rs.getInt(key);;
			statement.close();
			return value;
		}
		statement.close();
		return value;
	}
	public int getUserData(String playerNameString,String key) throws SQLException{
		int value = -1;
		String playerName = playerNameString.toLowerCase();
		if(!this.db.checkConnection()){
			this.db.openConnection();
		}
		Statement statement = this.db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM `pvpstats` WHERE `Name`='" + playerName + "';");
		while(rs.next()){
			value = rs.getInt(key);;
			statement.close();
			return value;
		}
		statement.close();
		return value;
	}
}

