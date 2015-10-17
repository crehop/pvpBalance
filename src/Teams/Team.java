package Teams;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class Team {

	public static ArrayList<Team> teams = new ArrayList<Team>();
	
	public static HashMap<String, Team> playerTeams = new HashMap<String, Team>();
	
	public String teamColor;
	
	public Team(String teamcolor) {
		teamColor = teamcolor;
		teams.add(this);
	}
	
	public void addPlayer(Player p){
		playerTeams.put(p.getName(), this);
	}
	
	public static void removePlayer(Player p) {
		if(hasTeam(p) == true){
			playerTeams.remove(p.getName());
		}
	}
	
	public static boolean hasTeam(Player p) {
		return playerTeams.containsKey(p.getName());
	}
	
	public static Team getTeam(Player p) {
		if(hasTeam(p) == true) {
			return playerTeams.get(p);
		}
		return null;
	}
	
	public String getTeamColor() {
		return teamColor;
	}
	
}
